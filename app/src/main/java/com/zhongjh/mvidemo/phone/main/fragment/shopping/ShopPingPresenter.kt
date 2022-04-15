package com.zhongjh.mvidemo.phone.main.fragment.shopping

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.zhongjh.mvidemo.data.http.retrofit.RetrofitClient
import com.zhongjh.mvidemo.data.http.service.BannerApi
import com.zhongjh.mvidemo.data.http.service.ProductApi
import com.zhongjh.mvidemo.entity.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 *
 * @author zhongjh
 * @date 2022/3/23
 */
class ShopPingPresenter : MviBasePresenter<ShopPingView, ShopPingState>() {

    override fun bindIntents() {
        // 初始化
        val initialize: Observable<ShopPingState> =
            getShopHome()
                .observeOn(AndroidSchedulers.mainThread())
        // 商城刷新
        val shopPingRefresh: Observable<ShopPingState> =
            intent(ShopPingView::pullToRefreshIntent)
                .subscribeOn(Schedulers.io())
                .switchMap { getShopHome() }
                .observeOn(AndroidSchedulers.mainThread())
        // 商城加载下一页
        val shopPingLoad: Observable<ShopPingState> =
            intent(ShopPingView::loadNextPageIntent)
                .subscribeOn(Schedulers.io())
                .switchMap { loadNextProduct(it) }
                .observeOn(AndroidSchedulers.mainThread())

        val merged = Observable.merge(initialize, shopPingRefresh, shopPingLoad)
        subscribeViewState(merged, ShopPingView::render)
    }

    /**
     * 获取商城首页信息
     */
    private fun getShopHome(): Observable<ShopPingState> {
        // 面板广告
        val observableBanner = RetrofitClient.get().create(BannerApi::class.java).json()
        // 正在拍卖的产品
        val observableProductIn = ProductApi.getProducts(0)
        // 商城显示的产品
        val observableProduct = ProductApi.getProducts(0)

        return Observable.zip(observableBanner, observableProductIn, observableProduct,
            { banners, productsIn, products -> getShopHomeZip(banners, productsIn, products) })
            .subscribeOn(Schedulers.io())
            .map<ShopPingState> {
                ShopPingState.DataState(it)
            }
            .startWith(ShopPingState.LoadingState)
            .onErrorReturn { ShopPingState.ErrorState(it.message) }
    }

    /**
     * 合并商城首页的多个接口数据
     */
    private fun getShopHomeZip(
        banners: ApiEntity<List<Banner>>,
        productsIn: ApiEntity<PageEntity<Product>>,
        products: ApiEntity<PageEntity<Product>>
    ): ShopHome {
        val shopHome = ShopHome()
        shopHome.banners = banners.data
        shopHome.productsIn = productsIn.data?.data
        shopHome.products = products.data
        return shopHome
    }

    /**
     * 获取产品的下一页
     */
    private fun loadNextProduct(page: Int): Observable<ShopPingState> {
        // 商城显示的产品
        val observableProduct = ProductApi.getProducts(page)
        return observableProduct
            .subscribeOn(Schedulers.io())
            .map {
                if (it.code == 200) {
                    ShopPingState.LoadNextProductState(it.data)
                } else {
                    ShopPingState.ErrorState(it.msg)
                }
            }
            .startWith(ShopPingState.LoadingState)
            .onErrorReturn { ShopPingState.ErrorState(it.message) }
    }

}
package com.zhongjh.mvidemo.phone.main.fragment.shopping

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.jshvarts.mosbymvi.data.ShopPingApi
import com.zhongjh.mvidemo.data.http.retrofit.RetrofitClient
import com.zhongjh.mvidemo.data.http.service.BannerApi
import com.zhongjh.mvidemo.entity.Banner
import com.zhongjh.mvidemo.entity.Product
import com.zhongjh.mvidemo.entity.ShopHome
import com.zhongjh.mvidemo.entity.WanEntity
import com.zhongjh.mvidemo.phone.privacypolicy.PrivacyPolicyView
import com.zhongjh.mvidemo.phone.splash.SplashState
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function3
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
                .switchMap { loadNextProduct() }
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
        val observableProductIn = ShopPingApi.getProducts()
        // 商城显示的产品
        val observableProduct = ShopPingApi.getProducts()

        return Observable.zip(observableBanner, observableProductIn, observableProduct,
            { banners, productsIn, products -> getShopHomeZip(banners, productsIn, products) })
            .subscribeOn(Schedulers.io())
            .map<ShopPingState> { ShopPingState.DataState(it) }
            .startWith(ShopPingState.LoadingState)
            .onErrorReturn { ShopPingState.ErrorState(it.message) }
    }

    /**
     * 合并商城首页的多个接口数据
     */
    private fun getShopHomeZip(
        banners: WanEntity<List<Banner>>,
        productsIn: WanEntity<List<Product>>,
        products: WanEntity<List<Product>>
    ): ShopHome {
        val shopHome = ShopHome()
        shopHome.banners = banners.data
        shopHome.productsIn = productsIn.data
        shopHome.products = products.data
        return shopHome
    }

    /**
     * 获取产品的下一页
     */
    private fun loadNextProduct(): Observable<ShopPingState> {
        // 商城显示的产品
        val observableProduct = ShopPingApi.getProducts()
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
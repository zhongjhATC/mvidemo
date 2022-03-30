package com.zhongjh.mvidemo.phone.main.fragment.shopping

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.jshvarts.mosbymvi.data.ShopPingApi
import com.zhongjh.mvidemo.data.http.retrofit.RetrofitClient
import com.zhongjh.mvidemo.data.http.service.BannerApi
import com.zhongjh.mvidemo.entity.Banner
import com.zhongjh.mvidemo.entity.Product
import com.zhongjh.mvidemo.entity.WanEntity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.function.BiFunction

/**
 *
 * @author zhongjh
 * @date 2022/3/23
 */
class ShopPingPresenter : MviBasePresenter<ShopPingView, ShopPingState>() {

    override fun bindIntents() {
        val shopPingState: Observable<ShopPingState> =
            intent(ShopPingView::pullToRefreshIntent)
                .subscribeOn(Schedulers.io())
                .switchMap {}
                .observeOn(AndroidSchedulers.mainThread())


    }

    /**
     * 获取商城首页信息
     */
    fun getShopHome(): Observable<ShopPingState> {
        // 面板广告
        val observableBanner = RetrofitClient.get().create(BannerApi::class.java).json()
        // 正在拍卖的产品
        val observableProductIn = ShopPingApi.getProducts()
        // 商城显示的产品
        val observableProduct = ShopPingApi.getProducts()

        Observable.zip(observableBanner,observableProductIn,observableProduct,
            io.reactivex.functions.BiFunction<WanEntity<List<Banner>>,WanEntity<List<Product>>,WanEntity<List<Product>>> {
                    wanEntityBanner: WanEntity<List<Banner>>?, wanEntityProduct: WanEntity<List<Product>>? ->
                a(wanEntityBanner,wanEntityProduct)
            })
//
//        Observable.zip(topJson(), topJson(),
//            io.reactivex.functions.BiFunction<T1, T2, R> { listWanEntity: T1?, listWanEntity2: T2? -> })

        return Observable.merge(observableBanner, observableProductIn, observableProduct)
            .map<ShopPingState> { ShopPingState.DataState(it) }
            .startWith(ShopPingState.LoadingState)
            .onErrorReturn { ShopPingState.ErrorState(it) }
    }

    private fun a(wanEntityBanner: WanEntity<List<Banner>>?, wanEntityProduct: WanEntity<List<Product>>?) {

    }

}
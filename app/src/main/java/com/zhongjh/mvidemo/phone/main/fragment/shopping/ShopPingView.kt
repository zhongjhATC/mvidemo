package com.zhongjh.mvidemo.phone.main.fragment.shopping

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

/**
 *
 * @author zhongjh
 * @date 2022/3/23
 */
interface ShopPingView : MvpView {

    /**
     * 对“下拉刷新”做出反应的意图
     *
     * @return 发出的item布尔值可以被忽略，因为它总是为真
     */
    fun pullToRefreshIntent(): Observable<Boolean>

    /**
     * 对“下拉加载”做出反应的意图
     *
     * @return 发出的item布尔值可以被忽略，因为它总是为真
     */
    fun loadNextPageIntent() : Observable<Boolean>

    /**
     * Render the state in the UI
     */
    fun render(state: ShopPingState)

}
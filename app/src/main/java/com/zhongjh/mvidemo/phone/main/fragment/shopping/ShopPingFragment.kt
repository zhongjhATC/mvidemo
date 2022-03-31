package com.zhongjh.mvidemo.phone.main.fragment.shopping

import android.os.Bundle
import android.util.Log
import com.zhongjh.mvidemo.R
import com.zhongjh.mvidemo.view.CustomRefreshHeader
import com.zhongjh.mvilibrary.base.BaseFragment
import com.zhongjh.mvilibrary.constant.Constants.TAG
import com.zhongjh.mvilibrary.rxjava.smartrefreshlayout.RxSmartRefreshLayout
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_shopping.*

/**
 * 商城Fragment
 * @author zhongjh
 * @date 2022/3/29
 */
class ShopPingFragment : BaseFragment<ShopPingView, ShopPingPresenter>(), ShopPingView {

    override fun initLayoutId() = R.layout.fragment_shopping

    override fun createPresenter() = ShopPingPresenter()

    override fun initParam(savedInstanceState: Bundle?) {
    }

    override fun initListener() {
    }

    override fun initialize() {
        // 自定义刷新
        refreshLayout.setRefreshHeader(CustomRefreshHeader(activity))
    }

    override fun pullToRefreshIntent(): Observable<Boolean> {
        return RxSmartRefreshLayout.refreshes(refreshLayout).map { true }
    }

    override fun render(state: ShopPingState) {
        when (state) {
            is ShopPingState.DataState -> dataState(state)
            is ShopPingState.ErrorState -> errorState(state)
            is ShopPingState.LoadingState -> Log.d(TAG, "LoadingState")
        }
    }

    private fun dataState(state: ShopPingState.DataState) {
        refreshLayout.finishRefresh()
        // 显示Banner
        rlContentHorizontal
    }

    private fun errorState(state: ShopPingState.ErrorState) {
        Log.d(TAG, "ErrorState" + state.error)
        refreshLayout.finishRefresh()
    }

}
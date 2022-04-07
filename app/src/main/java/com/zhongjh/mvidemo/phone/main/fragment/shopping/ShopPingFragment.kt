package com.zhongjh.mvidemo.phone.main.fragment.shopping

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.youth.banner.indicator.CircleIndicator
import com.zhongjh.mvidemo.R
import com.zhongjh.mvidemo.phone.main.fragment.shopping.adapter.ShopPingBannerAdapter
import com.zhongjh.mvidemo.phone.main.fragment.shopping.adapter.ShopPingHorizontalAdapter
import com.zhongjh.mvidemo.phone.main.fragment.shopping.adapter.ShopPingVerticalAdapter
import com.zhongjh.mvidemo.phone.search.SearchActivity
import com.zhongjh.mvidemo.view.CustomRefreshHeader
import com.zhongjh.mvilibrary.base.BaseFragment
import com.zhongjh.mvilibrary.constant.Constants.TAG
import com.zhongjh.mvilibrary.rxjava.smartloadlayout.RxSmartLoadLayout
import com.zhongjh.mvilibrary.rxjava.smartrefreshlayout.RxSmartRefreshLayout
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_shopping.*

/**
 * 商城Fragment
 * @author zhongjh
 * @date 2022/3/29
 */
class ShopPingFragment : BaseFragment<ShopPingView, ShopPingPresenter>(), ShopPingView {

    var mShopPingHorizontalAdapter = ShopPingHorizontalAdapter()
    var mShopPingVerticalAdapter = ShopPingVerticalAdapter()

    override fun initLayoutId() = R.layout.fragment_shopping

    override fun createPresenter() = ShopPingPresenter()

    override fun initParam(savedInstanceState: Bundle?) {
    }

    override fun initListener() {
        vSearch.setOnClickListener {
            // 打开搜索界面
            val intent = Intent(activity, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    override fun initialize() {
        // 添加生命周期观察者
        banner.addBannerLifecycleObserver(this).indicator = CircleIndicator(context)

        // 自定义刷新
        refreshLayout.setRefreshHeader(CustomRefreshHeader(activity))

        // 初始化列表横向列表
        val ms = LinearLayoutManager(context)
        ms.orientation = LinearLayoutManager.HORIZONTAL
        rlContentHorizontal.layoutManager = ms
        rlContentHorizontal.adapter = mShopPingHorizontalAdapter

        // 初始化列表竖向列表
        rlContent.layoutManager = GridLayoutManager(context, 2)
        rlContent.adapter = mShopPingVerticalAdapter
    }

    override fun pullToRefreshIntent(): Observable<Boolean> {
        return RxSmartRefreshLayout.refreshes(refreshLayout).map { true }
    }

    override fun loadNextPageIntent(): Observable<Boolean> {
        return RxSmartLoadLayout.load(refreshLayout).map { true }
    }

    override fun render(state: ShopPingState) {
        when (state) {
            is ShopPingState.DataState -> dataState(state)
            is ShopPingState.ErrorState -> errorState(state)
            is ShopPingState.LoadingState -> Log.d(TAG, "LoadingState")
            is ShopPingState.LoadNextProductState -> loadNextProductState(state)
        }
    }

    /**
     * 刷新数据
     */
    private fun dataState(state: ShopPingState.DataState) {
        refreshLayout.finishRefresh()
        // 显示Banner
        if (state.shopHome.banners != null) {
            banner.adapter = ShopPingBannerAdapter(state.shopHome.banners!!)
        }
        // 显示横向数据
        mShopPingHorizontalAdapter.setList(state.shopHome.productsIn)
        mShopPingHorizontalAdapter.notifyDataSetChanged()
        // 显示竖向数据
        mShopPingVerticalAdapter.setList(state.shopHome.products)
        mShopPingVerticalAdapter.notifyDataSetChanged()
    }

    /**
     * 报错数据
     */
    private fun errorState(state: ShopPingState.ErrorState) {
        Log.d(TAG, "ErrorState" + state.error)
        refreshLayout.finishRefresh()
    }

    /**
     * 加载下一页
     */
    private fun loadNextProductState(state: ShopPingState.LoadNextProductState) {
        if (state.products != null) {
            mShopPingVerticalAdapter.addData(state.products)
        }
        refreshLayout.finishLoadMore()
    }

}
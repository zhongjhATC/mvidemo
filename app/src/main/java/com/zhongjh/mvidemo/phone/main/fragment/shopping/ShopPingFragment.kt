package com.zhongjh.mvidemo.phone.main.fragment.shopping

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.youth.banner.indicator.CircleIndicator
import com.zhongjh.mvidemo.R
import com.zhongjh.mvidemo.diffcallback.DiffProductCallback
import com.zhongjh.mvidemo.entity.PageEntity
import com.zhongjh.mvidemo.entity.Product
import com.zhongjh.mvidemo.phone.main.fragment.shopping.adapter.ShopPingBannerAdapter
import com.zhongjh.mvidemo.phone.main.fragment.shopping.adapter.ShopPingHorizontalAdapter
import com.zhongjh.mvidemo.phone.main.fragment.shopping.adapter.ShopPingVerticalAdapter
import com.zhongjh.mvidemo.phone.search.SearchActivity
import com.zhongjh.mvidemo.view.CustomRefreshHeader
import com.zhongjh.mvilibrary.base.BaseFragment
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

    private val mTag = ShopPingFragment::class.qualifiedName
    private var mShopPingHorizontalAdapter = ShopPingHorizontalAdapter()
    private var mShopPingVerticalAdapter = ShopPingVerticalAdapter()

    /**
     * 竖型列表的当前页码
     */
    private var mVerticalPage = 0

    override fun initLayoutId() = R.layout.fragment_shopping

    override fun createPresenter() = ShopPingPresenter()

    override fun initParam(savedInstanceState: Bundle?) {
    }

    override fun initListener() {
        vSearchTouch.setOnClickListener {
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
        mShopPingHorizontalAdapter.setDiffCallback(DiffProductCallback())

        // 初始化列表竖向列表
        rlContent.layoutManager = GridLayoutManager(context, 2)
        rlContent.adapter = mShopPingVerticalAdapter
        mShopPingVerticalAdapter.setDiffCallback(DiffProductCallback())
    }

    override fun pullToRefreshIntent(): Observable<Boolean> {
        return RxSmartRefreshLayout.refreshes(refreshLayout).map {
            true
        }
    }

    override fun loadNextPageIntent(): Observable<Int> {
        return RxSmartLoadLayout.load(refreshLayout).map {
            val page = mVerticalPage + 1
            page
        }
    }

    override fun render(state: ShopPingState) {
        when (state) {
            is ShopPingState.DataState -> {
                Log.i(mTag, "render DataState")
                dataState(state)
            }
            is ShopPingState.ErrorState -> {
                Log.i(mTag, "render errorState")
                errorState(state)
            }
            is ShopPingState.LoadingState -> {
                Log.d(mTag, "LoadingState")
            }
            is ShopPingState.LoadNextProductState -> {
                Log.i(mTag, "render LoadNextProductState")
                loadNextProductState(state)
            }
            is ShopPingState.NotHappening -> {
                Log.i(mTag, "render notHappening")
                notHappening()
            }
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
        state.shopHome.products?.let { pageEntity ->
            checkNextPage(pageEntity)
            mShopPingVerticalAdapter.setList(pageEntity.data)
            mShopPingVerticalAdapter.notifyDataSetChanged()
        }
        reset()
    }

    /**
     * 报错数据
     */
    private fun errorState(state: ShopPingState.ErrorState) {
        Log.d(mTag, "ErrorState" + state.error)
        refreshLayout.finishRefresh()
        refreshLayout.finishLoadMore()
    }

    /**
     * 加载下一页
     */
    private fun loadNextProductState(state: ShopPingState.LoadNextProductState) {
        state.products?.let { pageEntity ->
            mVerticalPage++
            checkNextPage(pageEntity)
            pageEntity.data?.let { products ->
                mShopPingVerticalAdapter.addData(products)
            }
        }

        refreshLayout.finishLoadMore()
    }

    /**
     * 检查是否能下一页
     */
    private fun checkNextPage(pageEntity: PageEntity<Product>) {
        if (mVerticalPage >= pageEntity.pages - 1) {
            // 关闭下一页，页码已经最大
            refreshLayout.setEnableLoadMore(false)
        }
    }

    /**
     * 重置一些基础数据
     */
    private fun reset() {
        mVerticalPage = 0
        refreshLayout.setEnableLoadMore(true)
    }


}
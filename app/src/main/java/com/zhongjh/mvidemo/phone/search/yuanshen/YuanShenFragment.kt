package com.zhongjh.mvidemo.phone.search.yuanshen

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.KeyboardUtils
import com.zhongjh.mvidemo.R
import com.zhongjh.mvidemo.phone.search.SearchActivity
import com.zhongjh.mvidemo.phone.search.yuanshen.adapter.YuanShenVerticalAdapter
import com.zhongjh.mvidemo.view.CustomRefreshHeader
import com.zhongjh.mvilibrary.base.BaseFragment
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_yuanshen.refreshLayout
import kotlinx.android.synthetic.main.fragment_yuanshen.rlContent

/**
 *
 * @author zhongjh
 * @date 2022/4/8
 */
class YuanShenFragment : BaseFragment<YuanShenView, YuanShenPresenter>(), YuanShenView {

    private val mTag = YuanShenFragment::class.qualifiedName

    private var mSearchContent = ""
    private val mYuanShenVerticalAdapter = YuanShenVerticalAdapter()
    private var mSearchObserver = PublishSubject.create<String>()

    override fun initLayoutId() = R.layout.fragment_yuanshen

    override fun initParam(savedInstanceState: Bundle?) {
    }

    override fun initListener() {
        refreshLayout.setOnRefreshListener {
            mSearchObserver.onNext(mSearchContent)
        }
    }

    override fun initialize() {
        // 自定义刷新
        refreshLayout.setRefreshHeader(CustomRefreshHeader(activity))
        // 初始化列表竖向列表
        rlContent.layoutManager = GridLayoutManager(context, 2)
        rlContent.adapter = mYuanShenVerticalAdapter
    }

    override fun createPresenter() = YuanShenPresenter()

    override fun searchIntent(): Observable<String> {
        return mSearchObserver
    }

    /**
     * 查询数据
     * 如果查询条件没变化，并且有数据，就不再查询
     * @param searchContent 搜索文本
     */
    fun search(searchContent: String) {
        if (mSearchContent == searchContent && mYuanShenVerticalAdapter.data.size > 0) {
            return
        }
        mSearchContent = searchContent
        mSearchObserver.onNext(searchContent)
    }

    override fun render(state: YuanShenState) {
        when (state) {
            is YuanShenState.ErrorState -> errorState(state)
            is YuanShenState.LoadingState -> loadingState()
            is YuanShenState.DataState -> dataState(state)
            is YuanShenState.SearchNotStartedYetState -> searchNotStartedYetState()
        }
    }

    private fun loadingState() {
        refreshLayout.autoRefresh()
    }

    /**
     * 刷新数据
     */
    private fun dataState(state: YuanShenState.DataState) {
        refreshLayout.finishRefresh()
        // 显示竖向数据
        mYuanShenVerticalAdapter.setList(state.products.data?.data)
        mYuanShenVerticalAdapter.notifyDataSetChanged()
        // 隐藏软键盘
        KeyboardUtils.hideSoftInput(activity)
    }

    /**
     * 报错数据
     */
    private fun errorState(state: YuanShenState.ErrorState) {
        Log.d(mTag, "ErrorState" + state.error)
        refreshLayout.finishRefresh()
    }

    /**
     * 搜索内容为空不允许查询
     */
    private fun searchNotStartedYetState() {
        refreshLayout.finishRefresh()
    }

}
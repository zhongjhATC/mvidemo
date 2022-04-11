package com.zhongjh.mvidemo.phone.search.yuanshen

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.jakewharton.rxbinding2.internal.Notification
import com.zhongjh.mvidemo.R
import com.zhongjh.mvidemo.phone.search.yuanshen.adapter.YuanShenVerticalAdapter
import com.zhongjh.mvilibrary.base.BaseFragment
import io.reactivex.Observable
import io.reactivex.Observer
import kotlinx.android.synthetic.main.fragment_yuanshen.*

/**
 *
 * @author zhongjh
 * @date 2022/4/8
 */
class YuanShenFragment : BaseFragment<YuanShenView, YuanShenPresenter>(), YuanShenView {

    private val mTag = YuanShenFragment::class.qualifiedName

    private val mYuanShenVerticalAdapter = YuanShenVerticalAdapter()
    private lateinit var mSearchObserver: Observer<in Any>
    private lateinit var mSearchObservable : Observable<String>

    override fun initLayoutId() = R.layout.fragment_yuanshen

    override fun initParam(savedInstanceState: Bundle?) {
    }

    override fun initListener() {
    }

    override fun initialize() {
        // 初始化列表竖向列表
        rlContent.layoutManager = GridLayoutManager(context, 2)
        rlContent.adapter = mYuanShenVerticalAdapter
    }

    override fun createPresenter() = YuanShenPresenter()

    override fun searchIntent(): Observable<String> {
        mSearchObservable = Observable.just("")
        return mSearchObservable
    }

    /**
     * 查询数据
     * @param searchContent 查询文本
     */
    fun search(searchContent: String) {
        mSearchObservable = Observable.just(searchContent)
        mSearchObserver.onNext(Notification.INSTANCE)
    }

    override fun render(state: YuanShenState) {
        when (state) {
            is YuanShenState.ErrorState -> errorState(state)
            is YuanShenState.LoadingState -> Log.d(mTag, "LoadingState")
            is YuanShenState.DataState -> dataState(state)
            is YuanShenState.SearchNotStartedYet -> Log.d(mTag, "SearchNotStartedYet")
        }
    }

    /**
     * 刷新数据
     */
    private fun dataState(state: YuanShenState.DataState) {
        refreshLayout.finishRefresh()
        // 显示竖向数据
        mYuanShenVerticalAdapter.setList(state.products.data)
        mYuanShenVerticalAdapter.notifyDataSetChanged()
    }

    /**
     * 报错数据
     */
    private fun errorState(state: YuanShenState.ErrorState) {
        Log.d(mTag, "ErrorState" + state.error)
        refreshLayout.finishRefresh()
    }


}
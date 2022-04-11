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
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_yuanshen.*


/**
 *
 * @author zhongjh
 * @date 2022/4/8
 */
class YuanShenFragment : BaseFragment<YuanShenView, YuanShenPresenter>(), YuanShenView {

    private val mTag = YuanShenFragment::class.qualifiedName

    private val mYuanShenVerticalAdapter = YuanShenVerticalAdapter()
    private var mSearchObserver = Observer<String>()


    private lateinit var mSearchObservable: Observable<String>

    override fun initLayoutId() = R.layout.fragment_yuanshen

    override fun initParam(savedInstanceState: Bundle?) {
    }

    override fun initListener() {
    }

    override fun initialize() {
        val observer: Observer<String> = object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                //解除订阅
                Log.i(TAG, "onSubscribe--->")
            }

            override fun onNext(s: String) {
                //发送事件时观察者回调
                Log.i(TAG, "onNext--->$s")
            }

            override fun onError(e: Throwable) {
                //发送事件时观察者回调(事件序列发生异常)
                Log.i(TAG, "onError--->")
            }

            override fun onComplete() {
                //发送事件时观察者回调(事件序列发送完毕)
                Log.i(TAG, "onComplete--->")
            }
        }

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
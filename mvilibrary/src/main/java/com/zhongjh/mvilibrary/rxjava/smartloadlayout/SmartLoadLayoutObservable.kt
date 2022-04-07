package com.zhongjh.mvilibrary.rxjava.smartloadlayout

import com.jakewharton.rxbinding2.internal.Notification
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.zhongjh.mvilibrary.rxjava.RxPreconditions
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable

internal class SmartLoadLayoutObservable(private val view: SmartRefreshLayout) :
    Observable<Any?>() {
    override fun subscribeActual(observer: Observer<in Any?>) {
        if (!RxPreconditions.checkMainThread(observer)) {
            return
        }
        val listener = Listener(
            view, observer
        )
        observer.onSubscribe(listener)
        view.setOnLoadMoreListener(listener)
    }

    internal class Listener(
        private val view: SmartRefreshLayout,
        private val observer: Observer<in Any?>
    ) : MainThreadDisposable(), OnLoadMoreListener {

        override fun onLoadMore(refreshLayout: RefreshLayout) {
            if (!isDisposed) {
                observer.onNext(Notification.INSTANCE)
            }
        }

        override fun onDispose() {
            view.setOnRefreshListener(null)
        }

    }
}
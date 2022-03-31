package com.zhongjh.mvilibrary.rxjava.smartrefreshlayout

import androidx.annotation.CheckResult
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.zhongjh.mvilibrary.rxjava.RxPreconditions
import io.reactivex.Observable
import io.reactivex.functions.Consumer

class RxSmartRefreshLayout private constructor() {

    companion object {
        /**
         * Create an observable of refresh events on `view`.
         *
         *
         * *Warning:* The created observable keeps a strong reference to `view`. Unsubscribe
         * to free this reference.
         */
        @CheckResult
        fun refreshes(
            view: SmartRefreshLayout
        ): Observable<Any?> {
            RxPreconditions.checkNotNull(view, "view == null")
            return SmartRefreshLayoutObservable(view)
        }

        /**
         * An action which sets whether the layout is showing the refreshing indicator.
         *
         *
         * *Warning:* The created observable keeps a strong reference to `view`. Unsubscribe
         * to free this reference.
         */
        @CheckResult
        fun refreshing(
            view: SwipeRefreshLayout
        ): Consumer<in Boolean> {
            RxPreconditions.checkNotNull(view, "view == null")
            return Consumer { value -> view.isRefreshing = value!! }
        }
    }

    init {
        throw AssertionError("No instances.")
    }
}
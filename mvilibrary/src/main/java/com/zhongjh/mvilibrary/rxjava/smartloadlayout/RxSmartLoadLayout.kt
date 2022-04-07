package com.zhongjh.mvilibrary.rxjava.smartloadlayout

import androidx.annotation.CheckResult
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.zhongjh.mvilibrary.rxjava.RxPreconditions
import io.reactivex.Observable
import io.reactivex.functions.Consumer

class RxSmartLoadLayout private constructor() {

    companion object {
        /**
         * Create an observable of refresh events on `view`.
         *
         *
         * *Warning:* The created observable keeps a strong reference to `view`. Unsubscribe
         * to free this reference.
         */
        @CheckResult
        fun load(
            view: SmartRefreshLayout
        ): Observable<Any?> {
            RxPreconditions.checkNotNull(view, "view == null")
            return SmartLoadLayoutObservable(view)
        }

        /**
         * An action which sets whether the layout is showing the refreshing indicator.
         *
         *
         * *Warning:* The created observable keeps a strong reference to `view`. Unsubscribe
         * to free this reference.
         */
        @CheckResult
        fun loading(
            view: SmartRefreshLayout
        ): Consumer<in Boolean> {
            RxPreconditions.checkNotNull(view, "view == null")
            return Consumer { value ->
                if (value) {
                    view.autoLoadMore()
                } else {
                    view.finishLoadMore()
                }
            }
        }
    }

    init {
        throw AssertionError("No instances.")
    }
}
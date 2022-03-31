package com.zhongjh.mvilibrary.rxjava

import android.os.Looper
import io.reactivex.Observer

/**
 * 复制 RxBind ，为了一些第三方控件使用
 * @author zhongjh
 * @date 2022/3/31
 */
class RxPreconditions private constructor() {

    companion object {
        fun checkNotNull(value: Any?, message: String?) {
            if (value == null) {
                throw NullPointerException(message)
            }
        }

        fun checkMainThread(observer: Observer<*>): Boolean {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                observer.onError(
                    IllegalStateException(
                        "Expected to be called on the main thread but was " + Thread.currentThread().name
                    )
                )
                return false
            }
            return true
        }
    }

    init {
        throw AssertionError("No instances.")
    }
}
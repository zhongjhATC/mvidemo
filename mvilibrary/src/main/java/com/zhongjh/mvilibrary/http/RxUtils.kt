package com.zhongjh.mvilibrary.http

import com.zhongjh.mvilibrary.http.ExceptionHandle.handleException
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

/**
 * 有关Rx的工具类
 * @author zhongjh
 * @date 2022/3/30
 */
object RxUtils {
    /**
     * 线程调度器
     */
    fun <T> io2main(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream: Observable<T> ->
            upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     * 异常调度
     */
    fun <T> exceptionTransformer(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream: Observable<T> ->
            upstream.onErrorResumeNext(
                HttpResponseFunc()
            )
        }
    }

    private class HttpResponseFunc<T> : Function<Throwable, Observable<T>> {
        override fun apply(t: Throwable): Observable<T> {
            return Observable.error(handleException(t))
        }
    }
}
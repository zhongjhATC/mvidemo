package com.zhongjh.mvidemo.phone.main.domain

import com.zhongjh.mvidemo.phone.main.MainViewState
import io.reactivex.Observable

/**
 * In a Production app, inject your Use Case into your Presenter instead.
 */
object GetHelloWorldTextUseCase {
    fun getHelloWorldText(): Observable<MainViewState> {
        return HelloWorldRepository.loadHelloWorldText()
                .map<HelloWorldViewState> { HelloWorldViewState.DataState(it) }
                .startWith(HelloWorldViewState.LoadingState)
                .onErrorReturn { HelloWorldViewState.ErrorState(it) }
    }
}


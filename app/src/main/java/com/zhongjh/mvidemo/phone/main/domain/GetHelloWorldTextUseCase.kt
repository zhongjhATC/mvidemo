package com.zhongjh.mvidemo.phone.main.domain

import com.zhongjh.mvidemo.phone.main.MainViewState
import com.zhongjh.mvidemo.phone.main.data.HelloWorldRepository
import io.reactivex.Observable

/**
 * In a Production app, inject your Use Case into your Presenter instead.
 */
object GetHelloWorldTextUseCase {

    fun getHelloWorldText(): Observable<MainViewState> {
        return HelloWorldRepository.loadHelloWorldText()
                .map<MainViewState> { MainViewState.DataState(it) }
                .startWith(MainViewState.LoadingState)
                .onErrorReturn { MainViewState.ErrorState(it) }
    }

}


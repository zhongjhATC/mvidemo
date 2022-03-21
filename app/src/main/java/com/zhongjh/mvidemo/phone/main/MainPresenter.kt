package com.zhongjh.mvidemo.phone.main

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 *
 * @author zhongjh
 * @date 2022/3/21
 */
class MainPresenter : MviBasePresenter<MainView, MainViewState>() {

    override fun bindIntents() {
        val helloWorldState: Observable<MainViewState> = intent(MainView::sayHelloWorldIntent)
            .subscribeOn(Schedulers.io())
            .debounce(400, TimeUnit.MILLISECONDS)
            .switchMap { GetHelloWorldTextUseCase.getHelloWorldText() }
            .doOnNext { Timber.d("Received new state: " + it) }
            .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(helloWorldState, MainView::render)
    }

}
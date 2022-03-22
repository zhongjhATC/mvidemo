package com.zhongjh.mvidemo.phone.main

import android.util.Log
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.zhongjh.mvidemo.phone.main.domain.GetHelloWorldTextUseCase
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

    /**
     * 绑定事件
     */
    override fun bindIntents() {
        val helloWorldState: Observable<MainViewState> = intent(MainView::sayHelloWorldIntent)
            .subscribeOn(Schedulers.io())
            .debounce(400, TimeUnit.MILLISECONDS) // 400 毫秒后才会走后面的逻辑
            .switchMap { GetHelloWorldTextUseCase.getHelloWorldText() } // 请求的过程中，用户又去请求了，发出第二个请求，switchMap 操作符只会发射第二次请求的 Observable
            .doOnNext { Log.d("MainPresenter", "Received new state: $it") }
            .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(helloWorldState, MainView::render)
    }

}
package com.zhongjh.mvidemo.phone.main

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

/**
 *
 * @author zhongjh
 * @date 2022/3/21
 */
interface MainView : MvpView {

    /**
     * 将按钮点击显示为可观察对象
     */
    fun sayHelloWorldIntent(): Observable<Unit>

    /**
     * Render the state in the UI
     */
    fun render(state: MainViewState)

}
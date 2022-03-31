package com.zhongjh.mvidemo.phone.main

import com.hannesdorfmann.mosby3.mvp.MvpView

/**
 *
 * @author zhongjh
 * @date 2022/3/21
 */
interface MainView : MvpView {

    /**
     * Render the state in the UI
     */
    fun render(state: MainState)

}
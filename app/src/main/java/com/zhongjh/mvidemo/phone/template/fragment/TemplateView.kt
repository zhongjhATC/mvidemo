package com.zhongjh.mvidemo.phone.template.fragment

import com.hannesdorfmann.mosby3.mvp.MvpView

/**
 *
 * @author zhongjh
 * @date 2022/3/23
 */
interface TemplateView : MvpView {

    /**
     * Render the state in the UI
     */
    fun render(state: TemplateState)

}
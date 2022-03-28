package com.zhongjh.mvidemo.phone.privacypolicy

import com.hannesdorfmann.mosby3.mvp.MvpView
import com.zhongjh.mvidemo.phone.main.MainViewState
import io.reactivex.Observable

/**
 *
 * @author zhongjh
 * @date 2022/3/23
 */
interface PrivacyPolicyView : MvpView {

    /**
     * 同意
     */
    fun agreeIntent(): Observable<Unit>

    /**
     * 更新状态
     */
    fun render(state: PrivacyPolicyState)

}
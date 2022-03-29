package com.zhongjh.mvidemo.phone.splash

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

/**
 *
 * @author zhongjh
 * @date 2022/3/23
 */
interface SplashView : MvpView {

    /**
     * 更新状态
     */
    fun render(state: SplashState)

    /**
     * 判断广告图片是否存在
     */
    fun splashAdvertisingIsFileExistsIntent(): Observable<Boolean>

    /**
     * 直接跳过不等待
     */
    fun skipIntent(): Observable<Unit>
}
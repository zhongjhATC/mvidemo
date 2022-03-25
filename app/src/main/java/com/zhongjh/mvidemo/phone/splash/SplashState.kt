package com.zhongjh.mvidemo.phone.splash


sealed class SplashState {

    /**
     * 打开隐私政策界面
     */
    object StartPrivacyPolicyActivity : SplashState()

    /**
     * 打开登录界面
     */
    object StartLoginActivity : SplashState()

    /**
     * 显示广告
     */
    object ShowAdvertising : SplashState()

    /**
     * 如果没有广告则显示离线缓存
     */
    object ShowAdvertisingCache : SplashState()

    /**
     * 倒计时的秒数
     */
    data class ShowCountdownSeconds(val time: Long) : SplashState()

    /**
     * 倒计时完成
     */
    object CompleteCountdown : SplashState()
}


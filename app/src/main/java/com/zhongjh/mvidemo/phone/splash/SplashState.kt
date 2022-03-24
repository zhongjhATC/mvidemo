package com.zhongjh.mvidemo.phone.splash


sealed class SplashState {

    object AgreeState : SplashState()

    /**
     * 显示广告
     */
    object showAdvertising : SplashState()

    /**
     * 如果没有广告则显示离线缓存
     */
    object showAdvertisingCache : SplashState()
}


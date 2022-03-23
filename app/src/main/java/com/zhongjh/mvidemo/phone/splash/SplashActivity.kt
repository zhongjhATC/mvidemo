package com.zhongjh.mvidemo.phone.splash

import android.os.Bundle
import com.hannesdorfmann.mosby3.mvi.MviActivity
import com.zhongjh.mvidemo.R

class SplashActivity : MviActivity<SplashView, SplashPresenter>(), SplashView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

    }

    override fun createPresenter() = SplashPresenter()


}
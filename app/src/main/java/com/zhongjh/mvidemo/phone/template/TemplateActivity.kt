package com.zhongjh.mvidemo.phone.template

import android.os.Bundle
import com.hannesdorfmann.mosby3.mvi.MviActivity
import com.zhongjh.mvidemo.R

class TemplateActivity : MviActivity<TemplateView, TemplatePresenter>(), TemplateView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun createPresenter() = TemplatePresenter()

}
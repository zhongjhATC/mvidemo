package com.zhongjh.mvidemo.phone.template

import android.os.Bundle
import com.hannesdorfmann.mosby3.mvi.MviActivity
import com.zhongjh.mvidemo.R
import com.zhongjh.mvilibrary.base.BaseActivity

class TemplateActivity : BaseActivity<TemplateView, TemplatePresenter>(), TemplateView {

    override fun initLayoutId() = R.layout.activity_splash

    override fun initParam(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun initListener() {
        TODO("Not yet implemented")
    }

    override fun initialize() {
        TODO("Not yet implemented")
    }

    override fun createPresenter() = TemplatePresenter()


}
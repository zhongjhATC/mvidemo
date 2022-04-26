package com.zhongjh.mvidemo.phone.template.fragment

import android.os.Bundle
import com.zhongjh.mvidemo.R
import com.zhongjh.mvilibrary.base.BaseFragment

/**
 *
 * @author zhongjh
 * @date 2022/4/8
 */
class TemplateFragment : BaseFragment<TemplateView, TemplatePresenter>(), TemplateView {

    override fun initLayoutId() = R.layout.fragment_shopping

    override fun initParam(savedInstanceState: Bundle?) {
    }

    override fun initListener() {
    }

    override fun initialize() {
    }

    override fun createPresenter() = TemplatePresenter()

    override fun render(state: TemplateState) {
    }

}
package com.zhongjh.mvidemo.phone.template.fragment

import android.os.Bundle
import com.zhongjh.mvilibrary.base.BaseFragment

/**
 *
 * @author zhongjh
 * @date 2022/4/8
 */
class TemplateFragment : BaseFragment<TemplateView, TemplatePresenter>(), TemplateView {

    override fun initLayoutId(): Int {
        TODO("Not yet implemented")
    }

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

    override fun render(state: TemplateState) {
        TODO("Not yet implemented")
    }

}
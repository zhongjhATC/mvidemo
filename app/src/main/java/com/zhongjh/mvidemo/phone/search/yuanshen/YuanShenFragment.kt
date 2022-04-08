package com.zhongjh.mvidemo.phone.search.yuanshen

import android.os.Bundle
import com.zhongjh.mvilibrary.base.BaseFragment

/**
 *
 * @author zhongjh
 * @date 2022/4/8
 */
class YuanShenFragment : BaseFragment<YuanShenView, YuanShenPresenter>(), YuanShenView {

    override fun initLayoutId() = R.layout.

    override fun initParam(savedInstanceState: Bundle?) {
    }

    override fun initListener() {
    }

    override fun initialize() {
    }

    override fun createPresenter() = YuanShenPresenter()

    override fun render(state: YuanShenState) {
    }

}
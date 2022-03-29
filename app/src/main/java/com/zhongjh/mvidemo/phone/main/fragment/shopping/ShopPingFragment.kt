package com.zhongjh.mvidemo.phone.main.fragment.shopping

import android.os.Bundle
import com.zhongjh.mvidemo.R
import com.zhongjh.mvilibrary.base.BaseFragment

/**
 * 商城Fragment
 * @author zhongjh
 * @date 2022/3/29
 */
class ShopPingFragment : BaseFragment<ShopPingView, ShopPingPresenter>(), ShopPingView {

    override fun initLayoutId(): Int {
        return R.layout.activity_privacy_policy
    }

    override fun initParam(savedInstanceState: Bundle?) {
    }

    override fun initListener() {
    }

    override fun createPresenter() = ShopPingPresenter()

}
package com.zhongjh.mvidemo.phone.privacypolicy

import android.os.Bundle
import android.util.Log
import com.jakewharton.rxbinding2.view.clicks
import com.zhongjh.mvidemo.R
import com.zhongjh.mvilibrary.base.activity.BaseActivity
import com.zhongjh.mvilibrary.constant.Constants.TAG
import com.zhongjh.mvilibrary.listener.ThrottleOnClickListener
import kotlinx.android.synthetic.main.activity_privacy_policy.*

class PrivacyPolicyActivity : BaseActivity<PrivacyPolicyView, PrivacyPolicyPresenter>(),
    PrivacyPolicyView {

    override fun initLayoutId(): Int {
        return R.layout.activity_privacy_policy
    }

    override fun initParam(savedInstanceState: Bundle?) {
    }

    override fun initListener() {
        btnOK.setOnClickListener(object : ThrottleOnClickListener() {
            override fun onClick() {
                renderAgreeState()
            }
        })
    }

    override fun initialize() {
    }

    override fun createPresenter() = PrivacyPolicyPresenter()
    override fun cancelIntent() = btnCancel.clicks()

    override fun render(state: PrivacyPolicyState) {
        when (state) {
            is PrivacyPolicyState.AgreeState -> renderAgreeState()
        }
    }

    private fun renderAgreeState() {
        Log.d(TAG, "renderAgreeState")
    }


}
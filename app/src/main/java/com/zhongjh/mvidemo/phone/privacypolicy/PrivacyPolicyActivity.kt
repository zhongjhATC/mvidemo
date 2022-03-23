package com.zhongjh.mvidemo.phone.privacypolicy

import android.os.Bundle
import com.hannesdorfmann.mosby3.mvi.MviActivity
import com.jakewharton.rxbinding2.view.clicks
import com.zhongjh.mvidemo.R
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_privacy_policy.*

class PrivacyPolicyActivity : MviActivity<PrivacyPolicyView, PrivacyPolicyPresenter>(),
    PrivacyPolicyView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)
    }

    override fun createPresenter() = PrivacyPolicyPresenter()
    override fun cancelIntent() = btnCancel.clicks()
    override fun agreeIntent() = btnOK.clicks()


}
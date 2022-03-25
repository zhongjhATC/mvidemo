package com.zhongjh.mvidemo.phone.privacypolicy

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.util.Log
import com.jakewharton.rxbinding2.view.clicks
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
        setContent()
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

    /**
     * 设置文本和链接
     */
    private fun setContent() {
        // 文本内容
        val ss =
            SpannableString("欢迎您使用家在深圳！\n\n在使用App之前，请您阅读并充分理解家在深圳的《隐私政策》，了解您的用户权益及相关数据的处理方法。如果您同意以上协议内容，请点击“同意”，开始使用我们的产品和服务。我们将以业界领先的信息安全保护水平，保护您的个人信息，感谢您对家在深圳的信任。")
        // 设置字符颜色,点击事件
        ss.setSpan(
            MyClickText(this@PrivacyPolicyActivity),
            35,
            41,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tvContent.text = ss
        // 不添加这一句，拨号，http，发短信的超链接不能执行.
        tvContent.movementMethod = LinkMovementMethod.getInstance()
    }

}
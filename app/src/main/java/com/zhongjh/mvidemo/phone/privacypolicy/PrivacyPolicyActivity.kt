package com.zhongjh.mvidemo.phone.privacypolicy

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import com.jakewharton.rxbinding2.view.clicks
import com.zhongjh.mvidemo.R
import com.zhongjh.mvidemo.phone.main.MainActivity
import com.zhongjh.mvilibrary.base.BaseActivity
import com.zhongjh.mvilibrary.listener.ThrottleOnClickListener
import com.zhongjh.mvilibrary.utils.LinkUrlText
import kotlinx.android.synthetic.main.activity_privacy_policy.*

/**
 *
 * @author zhongjh
 * @date 2022/3/23
 */
class PrivacyPolicyActivity : BaseActivity<PrivacyPolicyView, PrivacyPolicyPresenter>(),
    PrivacyPolicyView {

    override fun initLayoutId(): Int {
        return R.layout.activity_privacy_policy
    }

    override fun initParam(savedInstanceState: Bundle?) {
    }

    override fun initListener() {
        btnCancel.setOnClickListener(object : ThrottleOnClickListener() {
            override fun onClick() {
                this@PrivacyPolicyActivity.finish()
            }
        })
    }

    override fun initialize() {
        setContent()
    }

    override fun createPresenter() = PrivacyPolicyPresenter()
    override fun agreeIntent() = btnOK.clicks()

    override fun render(state: PrivacyPolicyState) {
        when (state) {
            is PrivacyPolicyState.AgreeState -> agreeState()
        }
    }

    private fun agreeState() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        this.finish()
    }

    /**
     * 设置文本和链接
     */
    private fun setContent() {
        // 文本内容
        val ss =
            SpannableString("欢迎您使用MVIDemo！\n\n在使用App之前，请您阅读并充分理解MVIDemo的《隐私政策》，了解您的用户权益及相关数据的处理方法。如果您同意以上协议内容，请点击“同意”，开始使用我们的产品和服务。我们将以业界领先的信息安全保护水平，保护您的个人信息，感谢您对MVIDemo的信任。")
        // 设置字符颜色,点击事件
        ss.setSpan(
            LinkUrlText(this@PrivacyPolicyActivity, "https://www.baidu.com/"),
            41,
            47,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tvContent.text = ss
        // 不添加这一句，拨号，http，发短信的超链接不能执行.
        tvContent.movementMethod = LinkMovementMethod.getInstance()
    }

}
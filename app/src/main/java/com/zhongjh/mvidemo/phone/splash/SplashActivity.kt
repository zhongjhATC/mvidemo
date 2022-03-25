package com.zhongjh.mvidemo.phone.splash

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.FileUtils
import com.bumptech.glide.Glide
import com.zhongjh.mvidemo.R
import com.zhongjh.mvidemo.constant.FilePaths
import com.zhongjh.mvidemo.phone.privacypolicy.PrivacyPolicyActivity
import com.zhongjh.mvilibrary.base.activity.BaseActivity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.view_countdown.*

/**
 * 初始化界面，判断调用的下一个界面是哪个
 * 也可以装载广告
 *
 * @author zhongjh
 * @date 2022/3/22
 */
class SplashActivity : BaseActivity<SplashView, SplashPresenter>(),
    SplashView {

    override fun initLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initParam(savedInstanceState: Bundle?) {
    }

    override fun initListener() {
    }

    override fun initialize() {
    }

    override fun createPresenter() = SplashPresenter()

    override fun render(state: SplashState) {
        when (state) {
            is SplashState.StartPrivacyPolicyActivity -> startPrivacyPolicyActivity()
            is SplashState.ShowAdvertising -> showAdvertising()
            is SplashState.ShowAdvertisingCache -> showAdvertisingCache()
            is SplashState.StartLoginActivity -> startLoginActivity()
            is SplashState.ShowCountdownSeconds -> showCountdownSeconds(state)
            is SplashState.CompleteCountdown -> completeCountdown()
        }.let { }
    }

    override fun splashAdvertisingIsFileExists(): Observable<Boolean> {
        return Observable.just(
            FileUtils.isFileExists(
                FilePaths.splashAdvertisingFile(
                    applicationContext
                )
            )
        )
    }

    /**
     * 打开隐私政策界面
     */
    private fun startPrivacyPolicyActivity() {
        val intent = Intent(this, PrivacyPolicyActivity::class.java)
        startActivity(intent)
        this.finish()
    }

    /**
     * 打开登录界面
     */
    private fun startLoginActivity() {
        val intent = Intent(this, PrivacyPolicyActivity::class.java)
        startActivity(intent)
        this.finish()
    }

    /**
     * 显示倒计时时间
     */
    private fun showCountdownSeconds(state: SplashState.ShowCountdownSeconds) {
        tvCountdownTime.visibility = View.VISIBLE
        tvCountdownTime.text = (5 - state.time).toString()
    }

    /**
     * 倒计时结束
     */
    private fun completeCountdown() {
        this.finish()
    }

    /**
     * 显示广告图
     */
    private fun showAdvertising() {
        Glide.with(this)
            .load(FilePaths.splashAdvertisingFile(applicationContext))
            .into(imgSplash)
    }

    /**
     * 显示离线缓存图
     */
    private fun showAdvertisingCache() {
        Glide.with(this)
            .load(R.drawable.bg_splash)
            .into(imgSplash)
    }

}
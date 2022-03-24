package com.zhongjh.mvidemo.phone.splash

import android.content.Intent
import android.os.Bundle
import com.blankj.utilcode.util.FileUtils
import com.tencent.mmkv.MMKV
import com.zhongjh.mvidemo.R
import com.zhongjh.mvidemo.constant.FilePaths
import com.zhongjh.mvidemo.data.local.MMKVLocal
import com.zhongjh.mvidemo.phone.main.MainViewState
import com.zhongjh.mvidemo.phone.privacypolicy.PrivacyPolicyActivity
import com.zhongjh.mvilibrary.base.activity.BaseActivity
import io.reactivex.Observable

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
            is SplashState.AgreeState -> startPrivacyPolicyActivity()
        }
    }

    override fun splashAdvertisingIsFileExists(): Observable<Boolean> {
        return Observable.just(FileUtils.isFileExists(FilePaths.splashAdvertisingFile(applicationContext)))
    }

    private fun startPrivacyPolicyActivity() {
        val intent = Intent(this, PrivacyPolicyActivity::class.java)
        startActivity(intent)
    }

}
package com.zhongjh.mvidemo.phone

import android.app.Activity
import com.zhongjh.mvidemo.R
import com.zhongjh.mvidemo.phone.splash.SplashActivity
import com.zhongjh.mvilibrary.base.BaseApplication

/**
 *
 * @author zhongjh
 * @date 2022/3/22
 * 代码规范：https://github.com/getActivity/AndroidCodeStandard
 */
class MyApplication : BaseApplication() {

    override fun getLauncher(): Int {
        return R.mipmap.ic_launcher
    }

    override fun getSplashActivity(): Class<out Activity> {
        return SplashActivity::class.java
    }

}
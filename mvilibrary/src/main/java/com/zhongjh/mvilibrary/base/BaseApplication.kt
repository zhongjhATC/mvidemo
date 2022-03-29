package com.zhongjh.mvilibrary.base

import android.app.Activity
import android.app.Application
import android.util.Log
import android.view.Gravity
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.blankj.utilcode.util.LogUtils
import com.tencent.mmkv.MMKV
import com.tencent.smtt.sdk.QbSdk
import com.tencent.smtt.sdk.QbSdk.PreInitCallback
import com.zhongjh.mvilibrary.BuildConfig
import com.zhongjh.mvilibrary.constant.FilePaths
import com.zhongjh.mvilibrary.phone.ErrorActivity
import com.zhongjh.mvilibrary.utils.ApplicationUtil
import com.zhongjh.mvilibrary.utils.LogUtil
import com.zhongjh.mvilibrary.utils.ToastUtils
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


/**
 * Application基类
 *
 * @author zhongjh
 * @date 2022/3/22
 */
abstract class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        // 初始化工具类
        ApplicationUtil.init(this)
        MMKV.initialize(this)

        // 初始化腾讯x5 QbSdk
        QbSdk.initX5Environment(this, object : PreInitCallback {
            override fun onCoreInitFinished() {
                Log.e(TAG, "========onCoreInitFinished===")
            }

            override fun onViewInitFinished(b: Boolean) {
                Log.e(TAG, "x5初始化结果====$b")
            }
        })

    }

    companion object {
        var instance: BaseApplication by NotNullSingleValue()
        val TAG = BaseApplication::class.java.simpleName.toString()
    }

    private class NotNullSingleValue<T> : ReadWriteProperty<Any?, T> {
        private var value: T? = null
        override fun getValue(thisRef: Any?, property: KProperty<*>): T {
            return value ?: throw IllegalStateException("application not initialized")
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            if (this.value == null) {
                this.value = value
            } else {
                throw IllegalStateException("application already initialized")
            }
        }
    }

    /**
     * 初始化
     * 在同意协议后，再调用该方法，符合平台上架规范
     */
    open fun init() {
        // 是否开启打印日志
        LogUtil.init(BuildConfig.DEBUG)
        // 初始化全局异常崩溃
        initCrash()
        // 初始化Log
        initLog()
        // 初始化Toast的全局样式
        ToastUtils.setGravity(Gravity.CENTER, 0, 0)
    }

    /**
     * 异常奔溃后自动打开新的Activity,还可以选择重新启动
     */
    private fun initCrash() {
        CaocConfig.Builder.create() // 背景模式,开启沉浸式
            .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) // 是否启动全局异常捕获
            .enabled(true) // 是否显示错误详细信息
            .showErrorDetails(true) // 是否显示重启按钮
            .showRestartButton(true) // 是否跟踪Activity
            .trackActivities(true) // 崩溃的间隔时间(毫秒)
            .minTimeBetweenCrashesMs(2000) // 错误图标
            .errorDrawable(getLauncher()) // 重新启动后的activity
            .restartActivity(getSplashActivity()) // 崩溃后的错误监听
            // .eventListener(new YourCustomEventListener())
            // 崩溃后的错误activity
            .errorActivity(ErrorActivity::class.java)
            .apply()
    }

    /**
     * 初始化log，搭配奔溃把奔溃信息存储到Log
     */
    private fun initLog() {
        LogUtils.getConfig().setLogSwitch(true).setLog2FileSwitch(true)
            .setDir(FilePaths.logFile(this.applicationContext)).saveDays = 7
    }

    abstract fun getLauncher(): Int

    abstract fun getSplashActivity(): Class<out Activity>
}
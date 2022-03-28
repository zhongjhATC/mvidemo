package com.zhongjh.mvilibrary.base

import android.app.Application
import android.util.Log
import com.tencent.mmkv.MMKV
import com.tencent.smtt.sdk.QbSdk
import com.tencent.smtt.sdk.QbSdk.PreInitCallback
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


/**
 * Application基类
 *
 * @author zhongjh
 * @date 2022/3/22
 */
open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
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
        private var instance: BaseApplication by NotNullSingleValue()
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
    fun init() {

    }

}
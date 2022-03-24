package com.zhongjh.mvilibrary.base

import android.app.Application
import com.tencent.mmkv.MMKV
import java.lang.IllegalStateException
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
    }

    companion object {
        private var instance: BaseApplication by NotNullSingleValue()
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

}
package com.zhongjh.mvilibrary.utils

import android.app.Application

/**
 * Created by zhongjh on 2021/3/25.
 * 用于提供给其他静态类调用context
 */
class ApplicationUtil private constructor() {

    companion object {

        private var application: Application? = null

        /**
         * 初始化工具类
         *
         * @param application 应用
         */
        fun init(application: Application) {
            this.application = application
        }

        /**
         * 获取ApplicationContext
         *
         * @return ApplicationContext
         */
        fun getApplication(): Application {
            if (application != null) {
                return application as Application
            }
            throw NullPointerException("应该在Application初始化")
        }
    }

    init {
        throw UnsupportedOperationException("不能直接实例化该类，仅仅提供给其他静态类调用context")
    }
}
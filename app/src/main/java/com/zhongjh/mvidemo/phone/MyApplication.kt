package com.zhongjh.mvidemo.phone

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.zhongjh.mvidemo.R
import com.zhongjh.mvidemo.phone.splash.SplashActivity
import com.zhongjh.mvilibrary.base.BaseApplication
import com.zhongjh.mvilibrary.utils.DynamicTimeFormat

/**
 *
 * @author zhongjh
 * @date 2022/3/22
 * 代码规范：https://github.com/getActivity/AndroidCodeStandard
 */
class MyApplication : BaseApplication() {

    override fun init() {
        super.init()
        initSmartRefresh()
    }

    override fun getLauncher(): Int {
        return R.mipmap.ic_launcher
    }

    override fun getSplashActivity(): Class<out Activity> {
        return SplashActivity::class.java
    }

    private fun initSmartRefresh() {
        // 启用矢量图兼容
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        // 设置全局默认配置（优先级最低，会被其他设置覆盖）
        SmartRefreshLayout.setDefaultRefreshInitializer { context: Context?, layout: RefreshLayout ->
            // 全局设置（优先级最低）
            layout.setEnableAutoLoadMore(true)
            layout.setEnableOverScrollDrag(false)
            layout.setEnableOverScrollBounce(true)
            layout.setEnableLoadMoreWhenContentNotFull(true)
            layout.setEnableScrollContentWhenRefreshed(true)
            layout.setPrimaryColorsId(android.R.color.white, android.R.color.black)
            layout.setFooterMaxDragRate(4.0f)
            layout.setFooterHeight(45f)
        }
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context: Context?, layout: RefreshLayout ->
            // 全局设置主题颜色（优先级第二低，可以覆盖 DefaultRefreshInitializer 的配置，与下面的ClassicsHeader绑定）
            layout.setEnableHeaderTranslationContent(true)
            ClassicsHeader(context).setTimeFormat(DynamicTimeFormat("更新于 %s"))
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context: Context?, layout: RefreshLayout? ->
            ClassicsFooter(
                context
            )
        }
    }

}
package com.zhongjh.mvilibrary.phone

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cat.ereza.customactivityoncrash.CustomActivityOnCrash
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ScreenUtils
import com.zhongjh.mvilibrary.R
import com.zhongjh.mvilibrary.listener.ThrottleOnClickListener
import kotlinx.android.synthetic.main.activity_error.*

/**
 * 报错后会打开该Activity
 * @author zhongjh
 * @date 2022/3/28
 */
class ErrorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        ScreenUtils.setFullScreen(this@ErrorActivity)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_error)
        init()
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        // 获取所有的信息
        val errorDetails = CustomActivityOnCrash.getAllErrorDetailsFromIntent(this, intent)
        // 获取堆栈跟踪信息
        val stackTrace = CustomActivityOnCrash.getStackTraceFromIntent(intent)
        // 获取错误报告的Log信息
        val activityLog = CustomActivityOnCrash.getActivityLogFromIntent(intent)
        // 获得配置信息
        val config = CustomActivityOnCrash.getConfigFromIntent(intent)
        tvError.text =
            "【errorDetails】\n$errorDetails\n\n\n【stackTrace】\n$stackTrace\n\n\n【activityLog】\n$activityLog"
        tvError.setTextColor(Color.BLUE)
        LogUtils.e(tvError.text.toString())
        btnRestart.setOnClickListener(object : ThrottleOnClickListener() {
            override fun onClick() {
                CustomActivityOnCrash.restartApplication(this@ErrorActivity, config!!)
            }
        })
    }

}
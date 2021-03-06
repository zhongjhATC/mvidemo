package com.zhongjh.mvilibrary.base

import android.app.ProgressDialog
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.hannesdorfmann.mosby3.mvi.MviActivity
import com.hannesdorfmann.mosby3.mvi.MviPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.zhongjh.mvilibrary.R
import com.zhongjh.mvilibrary.utils.ScreenUtil


/**
 * Activity基类
 * @author zhongjh
 * @date 2022/3/21
 */
abstract class BaseActivity<V : MvpView, P : MviPresenter<V, *>?> : MviActivity<V, P>(), MvpView {

    override fun onCreate(savedInstanceState: Bundle?) {
        ScreenUtil.setFullScreen(this@BaseActivity, false)
        super.onCreate(savedInstanceState)
        setContentView(initLayoutId())
        initParam(savedInstanceState)
        initListener()
        initialize()
    }

    /**
     * 初始化布局ID
     */
    abstract fun initLayoutId(): Int

    /**
     * 初始化初始参数
     */
    abstract fun initParam(savedInstanceState: Bundle?)

    /**
     * 初始化事件
     */
    abstract fun initListener()

    /**
     * 初始化数据
     */
    abstract fun initialize()

    private val progressDialog: ProgressDialog by lazy {
        initDialog()
    }

    private fun initDialog(): ProgressDialog {
        val progressDialog = ProgressDialog(this@BaseActivity)
        progressDialog.setMessage(this.resources.getString(R.string.common_loading))
        return progressDialog
    }

    /**
     * 等待对话框显示
     */
    open fun showDialog() {
        progressDialog.show()
    }

    /**
     * 等待对话框消失
     */
    open fun dismissDialog() {
        progressDialog.dismiss()
    }

    /**
     * 关闭触摸，一般用于dialog的时候禁止触摸防止别的意外
     */
    open fun closeTouching() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    /**
     * 开启触摸
     */
    open fun openTouching() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

}
package com.zhongjh.mvilibrary.base

import android.R
import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.hannesdorfmann.mosby3.mvi.MviPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView


/**
 * Fragment基类
 * @author zhongjh
 * @date 2022/3/29
 */
abstract class BaseFragment<V : MvpView, P : MviPresenter<V, *>?> : MviFragment<V, P>(), MvpView {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(initLayoutId(), container, false)
        initParam(savedInstanceState)
        initListener()
        return view
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

    private fun initDialog(): ProgressDialog {
        val progressDialog = ProgressDialog(this@BaseFragment.context)
        progressDialog.setMessage(this.resources.getString(com.zhongjh.mvilibrary.R.string.common_loading))
        return progressDialog
    }

    private val progressDialog: ProgressDialog by lazy {
        initDialog()
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
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    /**
     * 开启触摸
     */
    open fun openTouching() {
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }


}
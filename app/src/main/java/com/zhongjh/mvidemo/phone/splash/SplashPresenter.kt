package com.zhongjh.mvidemo.phone.splash

import android.content.Intent.getIntent
import com.blankj.utilcode.util.FileUtils
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.tencent.mmkv.MMKV
import com.zhongjh.mvidemo.constant.FilePaths
import com.zhongjh.mvidemo.data.local.MMKVLocal
import com.zhongjh.mvidemo.phone.main.MainView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer


/**
 *
 * @author zhongjh
 * @date 2022/3/23
 */
class SplashPresenter : MviBasePresenter<SplashView, SplashState>() {

    override fun bindIntents() {
        val showAdvertising: Observable<SplashState> =
            intent(SplashView::splashAdvertisingIsFileExists)
                .flatMap { showAdvertising() }
        val initialize: Observable<SplashState> = initialize()
        subscribeViewState(initialize, SplashView::render)
    }

    private fun showAdvertising(): Observable<SplashState> {
    }

    private fun initialize(): Observable<SplashState> {
        // 判断运行下一个页面的逻辑
        val kv = MMKV.defaultMMKV()
        if (kv != null) {
            // 如果需要打开隐私政策，则运行该界面
            if (kv.decodeBool(MMKVLocal.IS_PRIVACY_POLICY, false)) {
                return Observable.just(SplashState.AgreeState)
            }
            // 如果没有用户数据，则需要先登录
            if (kv.decodeString(MMKVLocal.USER_JSON) != null) {
                return Observable.just(SplashState.AgreeState)
            }
        }
        // 照常运行
        return Observable.just(SplashState.AgreeState)
    }

}
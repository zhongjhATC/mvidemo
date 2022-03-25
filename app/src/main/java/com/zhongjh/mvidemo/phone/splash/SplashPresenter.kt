package com.zhongjh.mvidemo.phone.splash

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.tencent.mmkv.MMKV
import com.zhongjh.mvidemo.data.local.MMKVLocal
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import java.util.concurrent.TimeUnit
import java.util.concurrent.Flow as Flow1


/**
 *
 * @author zhongjh
 * @date 2022/3/23
 */
class SplashPresenter : MviBasePresenter<SplashView, SplashState>() {

    override fun bindIntents() {
        val showAdvertising: Observable<SplashState> =
            intent(SplashView::splashAdvertisingIsFileExists)
                .map { showAdvertising(it) }
        val initialize: Observable<SplashState> = initialize()
        val merged = Observable.merge(showAdvertising, initialize)
        subscribeViewState(merged, SplashView::render)
    }

    /**
     * 显示广告
     */
    private fun showAdvertising(splashAdvertisingIsFileExists: Boolean): SplashState {
        // 如果存在广告图则显示广告，否则显示缓存
        return if (splashAdvertisingIsFileExists) {
            SplashState.ShowAdvertising
        } else {
            SplashState.ShowAdvertisingCache
        }
    }

    /**
     * 判断运行下一个页面的逻辑
     */
    private fun initialize(): Observable<SplashState> {
        val kv = MMKV.defaultMMKV()
        if (kv != null) {
            // 如果需要打开隐私政策，则运行该界面
            if (kv.decodeBool(MMKVLocal.IS_PRIVACY_POLICY, true)) {
                return Observable.just(SplashState.StartPrivacyPolicyActivity)
            }
            // 如果没有用户数据，则需要先登录
            if (kv.decodeString(MMKVLocal.USER_JSON) != null) {
                return Observable.just(SplashState.StartLoginActivity)
            }
        }
        // 倒计时照常运行
        return startAdvertising()
    }

    /**
     * 倒计时
     */
    private fun startAdvertising(): Observable<SplashState> {
        // 从0开始发射5个数字为：0-6依次输出，延时0s执行，每1s发射一次。
        return Observable.intervalRange(0, 6, 0, 1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                if (it == 5L) {
                    SplashState.CompleteCountdown
                } else {
                    SplashState.ShowCountdownSeconds(it)
                }
            }
    }

}
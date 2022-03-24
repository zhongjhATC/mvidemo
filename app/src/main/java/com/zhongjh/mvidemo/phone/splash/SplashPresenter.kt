package com.zhongjh.mvidemo.phone.splash

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.tencent.mmkv.MMKV
import com.zhongjh.mvidemo.data.local.MMKVLocal
import io.reactivex.Observable


/**
 *
 * @author zhongjh
 * @date 2022/3/23
 */
class SplashPresenter : MviBasePresenter<SplashView, SplashState>() {

    override fun bindIntents() {
        val showAdvertising: Observable<SplashState> =
            intent(SplashView::splashAdvertisingIsFileExists)
                .switchMap { showAdvertising(it) }
        val initialize: Observable<SplashState> = initialize()
        subscribeViewState(initialize, SplashView::render)
    }

    private fun showAdvertising(splashAdvertisingIsFileExists: Boolean): Observable<SplashState> {
        // 如果存在广告图则显示广告，否则显示缓存
        return if (splashAdvertisingIsFileExists) {
            Observable.just(SplashState.showAdvertising)
        } else {
            Observable.just(SplashState.showAdvertisingCache)
        }
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
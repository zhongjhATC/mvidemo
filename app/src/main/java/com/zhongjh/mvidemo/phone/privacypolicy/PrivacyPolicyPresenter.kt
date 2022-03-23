package com.zhongjh.mvidemo.phone.privacypolicy

import android.util.Log
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.zhongjh.mvidemo.phone.main.MainView
import com.zhongjh.mvidemo.phone.main.MainViewState
import com.zhongjh.mvidemo.phone.main.domain.GetHelloWorldTextUseCase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 *
 * @author zhongjh
 * @date 2022/3/23
 */
class PrivacyPolicyPresenter : MviBasePresenter<PrivacyPolicyView, PrivacyPolicyState>() {

    override fun bindIntents() {
        val privacyPolicyState: Observable<PrivacyPolicyState> =
            intent(PrivacyPolicyView::agreeIntent)
                .subscribeOn(Schedulers.io())
                // 400 毫秒后才会走后面的逻辑
                .debounce(400, TimeUnit.MILLISECONDS)
                // 请求的过程中，用户又去请求了，发出第二个请求，switchMap 操作符只会发射第二次请求的 Observable
                .switchMap { PrivacyPolicyState.AgreeState }
                .doOnNext { Log.d("MainPresenter", "Received new state: $it") }
                .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(privacyPolicyState, PrivacyPolicyView::render)
    }

}
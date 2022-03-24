package com.zhongjh.mvidemo.phone.privacypolicy

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
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
//        val privacyPolicyState: Observable<PrivacyPolicyState> =
//            intent(PrivacyPolicyView::agreeIntent)
//                .subscribeOn(Schedulers.io())
//                // 400 毫秒后才会走后面的逻辑
//                .debounce(400, TimeUnit.MILLISECONDS)
//                .map<PrivacyPolicyState> { PrivacyPolicyState.AgreeState }
//                .observeOn(AndroidSchedulers.mainThread())
//
//        subscribeViewState(privacyPolicyState, PrivacyPolicyView::render)
    }

}
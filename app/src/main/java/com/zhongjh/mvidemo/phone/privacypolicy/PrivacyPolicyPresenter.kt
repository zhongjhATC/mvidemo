package com.zhongjh.mvidemo.phone.privacypolicy

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.tencent.mmkv.MMKV
import com.zhongjh.mvidemo.data.local.MMKVLocal
import com.zhongjh.mvilibrary.utils.SPCacheUtil
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
                .debounce(400, TimeUnit.MILLISECONDS)
                .map<PrivacyPolicyState> { agree() }
                .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(privacyPolicyState, PrivacyPolicyView::render)
    }

    /**
     * 同意
     */
    private fun agree(): PrivacyPolicyState.AgreeState {
        // 设置不需要再运行隐私界面
        SPCacheUtil.put(MMKVLocal.IS_PRIVACY_POLICY, false)
        return PrivacyPolicyState.AgreeState
    }

}
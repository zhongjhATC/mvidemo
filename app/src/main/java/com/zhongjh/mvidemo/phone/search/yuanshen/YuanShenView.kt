package com.zhongjh.mvidemo.phone.search.yuanshen

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

/**
 *
 * @author zhongjh
 * @date 2022/3/23
 */
interface YuanShenView : MvpView {

    /**
     * Render the state in the UI
     */
    fun render(state: YuanShenState)

    /**
     * 搜索的意图
     */
    fun searchIntent(): Observable<String>

}
package com.zhongjh.mvidemo.phone.search

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

/**
 *
 * @author zhongjh
 * @date 2022/3/23
 */
interface SearchView : MvpView {

    /**
     * 搜索的意图
     */
    fun searchIntent(): Observable<String>

}
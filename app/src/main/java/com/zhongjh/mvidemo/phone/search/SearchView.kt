package com.zhongjh.mvidemo.phone.search

import com.hannesdorfmann.mosby3.mvp.MvpView
import com.zhongjh.mvidemo.entity.SearchConditions
import com.zhongjh.mvidemo.phone.main.MainState
import io.reactivex.Observable

/**
 *
 * @author zhongjh
 * @date 2022/3/23
 */
interface SearchView : MvpView {

    /**
     * 点击搜索的意图
     */
    fun searchClickIntent(): Observable<String>

    /**
     * 搜索文本改变内容的意图
     */
    fun searchTextChangesIntent(): Observable<String>

    /**
     * Render the state in the UI
     */
    fun render(state: SearchState)

}
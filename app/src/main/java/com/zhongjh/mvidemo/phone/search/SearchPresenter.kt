package com.zhongjh.mvidemo.phone.search

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.zhongjh.mvidemo.entity.SearchConditions
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 *
 * @author zhongjh
 * @date 2022/3/23
 */
class SearchPresenter : MviBasePresenter<SearchView, SearchState>() {

    override fun bindIntents() {
        // 搜索
        val searchClick: Observable<SearchState> =
            intent(SearchView::searchClickIntent)
                .subscribeOn(Schedulers.io())
                .switchMap { search(it) }

        val searchTextChanges: Observable<SearchState> =
            intent(SearchView::searchTextChangesIntent)
                .subscribeOn(Schedulers.io())
                .switchMap { search(it) }

        val merged = Observable.merge(searchClick, searchTextChanges)

        subscribeViewState(merged, SearchView::render)
    }

    private fun search(searchConditions: SearchConditions): Observable<SearchState> {
        return Observable.just(SearchState.SearchNoticeState(searchConditions))
    }

}
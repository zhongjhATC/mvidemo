package com.zhongjh.mvidemo.phone.search

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.jshvarts.mosbymvi.data.ShopPingApi
import com.zhongjh.mvidemo.entity.SearchConditions
import com.zhongjh.mvidemo.entity.SearchType.Product
import com.zhongjh.mvidemo.entity.SearchType.YuanShen
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *
 * @author zhongjh
 * @date 2022/3/23
 */
class SearchPresenter : MviBasePresenter<SearchView, SearchState>() {

    override fun bindIntents() {
        // 搜索
        val search: Observable<SearchState> =
            intent(SearchView::searchIntent)
                .subscribeOn(Schedulers.io())
                .switchMap { search(it) }
                .observeOn(AndroidSchedulers.mainThread())
        subscribeViewState(search, SearchView::render)
    }

    private fun search(searchConditions: SearchConditions): Observable<SearchState> {
        // 判断类型
        val observableProductIn =
            when (searchConditions.type) {
                YuanShen -> ShopPingApi.getProducts()
                Product -> ShopPingApi.getProducts()
                else -> ShopPingApi.getProducts()
            }
        return observableProductIn
            .subscribeOn(Schedulers.io())
            .map<SearchState> { SearchState.SearchProductState(it, searchConditions) }
            .startWith(SearchState.LoadingState)
            .onErrorReturn { SearchState.ErrorState(it.message) }

    }

}
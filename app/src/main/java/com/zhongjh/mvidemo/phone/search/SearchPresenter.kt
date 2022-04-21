package com.zhongjh.mvidemo.phone.search

import android.util.Log
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.zhongjh.mvidemo.data.db.business.SearchContentBusiness
import com.zhongjh.mvidemo.entity.db.SearchContent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 *
 * @author zhongjh
 * @date 2022/3/23
 */
class SearchPresenter : MviBasePresenter<SearchView, SearchState>() {

    private val mTag = SearchPresenter::class.qualifiedName
    private val mSearchContentBusiness = SearchContentBusiness()

    override fun bindIntents() {
        // 初始化
        val initialize: Observable<SearchState> =
            Observable.just(mSearchContentBusiness.getSearchContents()).
            subscribeOn(Schedulers.io())
                .switchMap { showSearchHistory(it) }
                .observeOn(AndroidSchedulers.mainThread())

        // 搜索点击
        val searchClick: Observable<String> =
            intent(SearchView::searchClickIntent)
        // 搜索文本改变
        val searchTextChanges: Observable<String> =
            intent(SearchView::searchTextChangesIntent)

        val searchContent = Observable.merge(searchClick, searchTextChanges)
            .throttleFirst(1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .switchMap { search(it) }
            .observeOn(AndroidSchedulers.mainThread())

        val merged = Observable.merge(initialize, searchContent)

        subscribeViewState(merged, SearchView::render)
    }

    /**
     * 进行搜索
     * @param searchConditions 搜索文本
     */
    private fun search(searchConditions: String): Observable<SearchState> {
        Log.d(mTag, "search$searchConditions")
        // 添加到数据库
        mSearchContentBusiness.addSearchContents(searchConditions)
        return Observable.just(SearchState.SearchNoticeState(searchConditions))
    }

    private fun showSearchHistory(searchContents: MutableList<SearchContent>): Observable<SearchState> {
        return Observable.just(SearchState.InitSearchContentsState(searchContents))
    }

}
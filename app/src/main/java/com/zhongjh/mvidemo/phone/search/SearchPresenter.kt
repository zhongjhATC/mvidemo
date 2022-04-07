package com.zhongjh.mvidemo.phone.search

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.zhongjh.mvidemo.phone.main.fragment.shopping.ShopPingState
import com.zhongjh.mvidemo.phone.main.fragment.shopping.ShopPingView
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
            intent(SearchView::pullToRefreshIntent)
                .subscribeOn(Schedulers.io())
                .switchMap { getShopHome() }
                .observeOn(AndroidSchedulers.mainThread())
    }

}
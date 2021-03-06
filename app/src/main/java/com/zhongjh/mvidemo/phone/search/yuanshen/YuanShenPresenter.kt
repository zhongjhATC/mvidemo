package com.zhongjh.mvidemo.phone.search.yuanshen

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.zhongjh.mvidemo.data.http.service.ProductApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *
 * @author zhongjh
 * @date 2022/3/23
 */
class YuanShenPresenter : MviBasePresenter<YuanShenView, YuanShenState>() {

    override fun bindIntents() {
        // 搜索
        val search: Observable<YuanShenState> =
            intent(YuanShenView::searchIntent)
                .subscribeOn(Schedulers.io())
                .switchMap { search(it) }
                .observeOn(AndroidSchedulers.mainThread())
        subscribeViewState(search, YuanShenView::render)
    }

    private fun search(search: String): Observable<YuanShenState> {
        return if (search.isEmpty()) {
            Observable.just(YuanShenState.SearchNotStartedYetState)
        } else
            ProductApi.getProducts(0)
            .subscribeOn(Schedulers.io())
            .map<YuanShenState> { YuanShenState.DataState(it) }
            .startWith(YuanShenState.LoadingState)
            .onErrorReturn { YuanShenState.ErrorState(it.message) }
    }


}
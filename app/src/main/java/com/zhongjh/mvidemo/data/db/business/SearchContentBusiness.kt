package com.zhongjh.mvidemo.data.db.business

import com.zhongjh.mvidemo.data.http.service.ProductApi
import com.zhongjh.mvidemo.entity.ApiEntity
import com.zhongjh.mvidemo.entity.PageEntity
import com.zhongjh.mvidemo.entity.Product
import com.zhongjh.mvidemo.phone.MyApplication
import com.zhongjh.mvilibrary.base.BaseApplication
import com.zhongjh.mvilibrary.base.BaseApplication.Companion.instance
import io.reactivex.Observable

/**
 * 搜索内容的相关逻辑
 * @author zhongjh
 * @date 2022/4/15
 */
class SearchContentBusiness {


//    fun getObservableSearchContents(): Observable<ApiEntity<PageEntity<Product>>> =
//        simulationProducts(page)
//
    /**
     * 获取搜索内容，最高12条数据
     */
    private fun getSearchContents() {
        val searchContentDao = MyApplication.instance.daoSession
    }

}
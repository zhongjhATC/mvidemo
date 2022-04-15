package com.zhongjh.mvidemo.data.db.business

import com.zhongjh.mvidemo.data.http.service.ProductApi
import com.zhongjh.mvidemo.entity.ApiEntity
import com.zhongjh.mvidemo.entity.PageEntity
import com.zhongjh.mvidemo.entity.Product
import io.reactivex.Observable

/**
 * 搜索内容的相关逻辑
 * @author zhongjh
 * @date 2022/4/15
 */
object SearchContentBusiness {

    fun getSearchContents(): Observable<ApiEntity<PageEntity<Product>>> =
        ProductApi.simulationProducts(page)

}
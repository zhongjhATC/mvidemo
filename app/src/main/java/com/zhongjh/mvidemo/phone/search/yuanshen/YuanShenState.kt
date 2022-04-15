package com.zhongjh.mvidemo.phone.search.yuanshen

import com.zhongjh.mvidemo.entity.Product
import com.zhongjh.mvidemo.entity.ApiEntity
import com.zhongjh.mvidemo.entity.PageEntity

sealed class YuanShenState {

    object LoadingState : YuanShenState()
    data class ErrorState(val error: String?) : YuanShenState()
    data class DataState(val products: ApiEntity<PageEntity<Product>>) : YuanShenState()
    object SearchNotStartedYetState : YuanShenState()


}


package com.zhongjh.mvidemo.phone.search.yuanshen

import com.zhongjh.mvidemo.entity.Product
import com.zhongjh.mvidemo.entity.WanEntity

sealed class YuanShenState {

    object LoadingState : YuanShenState()
    data class ErrorState(val error: String?) : YuanShenState()
    data class DataState(val products: WanEntity<List<Product>>) : YuanShenState()
    object SearchNotStartedYet : YuanShenState()


}


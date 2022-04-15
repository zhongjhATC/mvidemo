package com.zhongjh.mvidemo.phone.main.fragment.shopping

import com.zhongjh.mvidemo.entity.PageEntity
import com.zhongjh.mvidemo.entity.Product
import com.zhongjh.mvidemo.entity.ShopHome

sealed class ShopPingState {

    object LoadingState : ShopPingState()
    object NotHappening : ShopPingState()
    data class DataState(val shopHome: ShopHome) : ShopPingState()
    data class ErrorState(val error: String?) : ShopPingState()
    data class LoadNextProductState(val products: PageEntity<Product>?) : ShopPingState()

}


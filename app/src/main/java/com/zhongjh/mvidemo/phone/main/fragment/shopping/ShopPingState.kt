package com.zhongjh.mvidemo.phone.main.fragment.shopping

import com.zhongjh.mvidemo.entity.ShopHome

sealed class ShopPingState {

    object LoadingState : ShopPingState()
    data class DataState(val shopHome: ShopHome) : ShopPingState()
    data class ErrorState(val error: Throwable) : ShopPingState()

}


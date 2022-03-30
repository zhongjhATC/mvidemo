package com.zhongjh.mvidemo.phone.main.fragment.shopping

sealed class ShopPingState {

    object LoadingState : ShopPingState()
    data class DataState(val greeting: String) : ShopPingState()
    data class ErrorState(val error: Throwable) : ShopPingState()

}


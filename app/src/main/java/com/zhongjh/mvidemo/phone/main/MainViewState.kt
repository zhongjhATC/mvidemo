package com.zhongjh.mvidemo.phone.main

sealed class MainViewState {

    object LoadingState : MainViewState()
    data class DataState(val greeting: String) : MainViewState()
    data class ErrorState(val error: Throwable) : MainViewState()

}


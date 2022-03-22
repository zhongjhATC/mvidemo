package com.zhongjh.mvilibrary.base.activity

sealed class BaseViewState {

    object LoadingState : BaseViewState()
    data class DataState(val greeting: String) : BaseViewState()
    data class ErrorState(val error: Throwable) : BaseViewState()

}
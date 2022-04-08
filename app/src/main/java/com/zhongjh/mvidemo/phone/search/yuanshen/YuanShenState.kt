package com.zhongjh.mvidemo.phone.search.yuanshen

sealed class YuanShenState {

    object LoadingState : YuanShenState()
    data class ErrorState(val error: String?) : YuanShenState()

}


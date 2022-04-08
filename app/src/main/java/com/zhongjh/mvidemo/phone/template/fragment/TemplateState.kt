package com.zhongjh.mvidemo.phone.template.fragment

sealed class TemplateState {

    object LoadingState : TemplateState()
    data class ErrorState(val error: String?) : TemplateState()

}


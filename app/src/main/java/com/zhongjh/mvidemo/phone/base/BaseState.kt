package com.zhongjh.mvidemo.phone.base

import com.zhongjh.mvidemo.entity.ShopHome
import com.zhongjh.mvidemo.phone.main.fragment.shopping.ShopPingState

/**
 * 状态基类
 * @author zhongjh
 * @date 2022/4/8
 */
sealed interface  BaseState {

    object LoadingState : BaseState
    data class ErrorState(val error: String?) : BaseState

}
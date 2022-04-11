package com.zhongjh.mvidemo.phone.search

import com.zhongjh.mvidemo.entity.Product
import com.zhongjh.mvidemo.entity.SearchConditions
import com.zhongjh.mvidemo.entity.ShopHome
import com.zhongjh.mvidemo.entity.WanEntity
import com.zhongjh.mvidemo.phone.main.fragment.shopping.ShopPingState


sealed class SearchState {

    object LoadingState : SearchState()
    data class ErrorState(val error: String?) : SearchState()
    data class SearchYuanShenState(val products: WanEntity<List<Product>>, val searchConditions: SearchConditions) : SearchState()
    data class SearchProductState(val products: WanEntity<List<Product>>, val searchConditions: SearchConditions) : SearchState()
    data class SearchAuctionState(val products: WanEntity<List<Product>>, val searchConditions: SearchConditions) : SearchState()
    data class SearchInvitationState(val products: WanEntity<List<Product>>, val searchConditions: SearchConditions) : SearchState()
    data class SearchConsultState(val products: WanEntity<List<Product>>, val searchConditions: SearchConditions) : SearchState()
    data class SearchUserState(val products: WanEntity<List<Product>>, val searchConditions: SearchConditions) : SearchState()
    data class SearchBarState(val products: WanEntity<List<Product>>, val searchConditions: SearchConditions) : SearchState()

}


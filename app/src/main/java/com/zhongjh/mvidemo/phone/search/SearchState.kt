package com.zhongjh.mvidemo.phone.search

import com.zhongjh.mvidemo.entity.Product
import com.zhongjh.mvidemo.entity.SearchConditions
import com.zhongjh.mvidemo.entity.ShopHome
import com.zhongjh.mvidemo.entity.WanEntity
import com.zhongjh.mvidemo.phone.main.fragment.shopping.ShopPingState


sealed class SearchState {

    object LoadingState : SearchState()
    data class ErrorState(val error: String?) : SearchState()
    data class SearchYuanShen(val products: WanEntity<List<Product>>,val searchConditions: SearchConditions) : SearchState()
    data class SearchProduct(val products: WanEntity<List<Product>>,val searchConditions: SearchConditions) : SearchState()
    data class SearchAuction(val products: WanEntity<List<Product>>,val searchConditions: SearchConditions) : SearchState()
    data class SearchInvitation(val products: WanEntity<List<Product>>,val searchConditions: SearchConditions) : SearchState()
    data class SearchConsult(val products: WanEntity<List<Product>>,val searchConditions: SearchConditions) : SearchState()
    data class SearchUser(val products: WanEntity<List<Product>>,val searchConditions: SearchConditions) : SearchState()
    data class SearchBar(val products: WanEntity<List<Product>>,val searchConditions: SearchConditions) : SearchState()

}


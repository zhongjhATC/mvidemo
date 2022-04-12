package com.zhongjh.mvidemo.phone.search

import com.zhongjh.mvidemo.entity.SearchConditions


sealed class SearchState {

    data class SearchNoticeState(val searchConditions: SearchConditions) : SearchState()

}


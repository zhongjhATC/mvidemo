package com.zhongjh.mvidemo.phone.search

import com.zhongjh.mvidemo.entity.SearchConditions
import com.zhongjh.mvidemo.entity.db.SearchContent


sealed class SearchState {

    /**
     * 搜索后的通知
     */
    data class SearchNoticeState(val searchConditions: String) : SearchState()

    /**
     * 初始化搜索历史列表
     */
    data class InitSearchContentsState(val searchContents: List<SearchContent>) : SearchState()

}


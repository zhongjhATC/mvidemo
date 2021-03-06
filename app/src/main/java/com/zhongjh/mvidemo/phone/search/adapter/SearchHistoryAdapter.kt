package com.zhongjh.mvidemo.phone.search.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zhongjh.mvidemo.R
import com.zhongjh.mvidemo.entity.db.SearchContent

/**
 * 搜索历史
 *
 * @author zhongjh
 * @date 2022/4/7
 */
class SearchHistoryAdapter :
    BaseQuickAdapter<SearchContent, BaseViewHolder>(R.layout.item_search_history) {

    override fun convert(holder: BaseViewHolder, item: SearchContent) {
        holder.setText(R.id.tvContext, item.content)
    }


}
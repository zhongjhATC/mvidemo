package com.zhongjh.mvidemo.entity

/**
 * 搜索的条件
 * @author zhongjh
 * @date 2022/4/8
 */
class SearchConditions {

    /**
     * 搜索的内容
     */
    lateinit var content: String

    /**
     * 搜索的类型
     */
    lateinit var type: SearchType
}
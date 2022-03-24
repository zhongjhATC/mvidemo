package com.zhongjh.mvilibrary.constant

/**
 *
 * @author zhongjh
 * @date 2022/3/24
 */
object Constants {

    /**
     * 共用标签
     */
    val Any.TAG: String
        get() {
            val tag = javaClass.simpleName
            return if (tag.length <= 23) tag else tag.substring(0, 23)
        }

    /**
     * 点击时间，1秒内防抖动多次点击
     */
    const val CLICK_DURATION: Long = 1000


}
package com.zhongjh.mvidemo.entity

/**
 *
 * @author zhongjh
 * @date 2022/3/30
 *
 * 封装wan接口的返回实体类
 */
class WanEntity<T> {

    var code = 0
    var msg: String? = null
    var data: T? = null
}
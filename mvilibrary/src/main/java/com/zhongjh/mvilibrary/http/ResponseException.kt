package com.zhongjh.mvilibrary.http

/**
 * 封装HttpException的一个实体
 *
 * @author zhongjh
 * @date 2022/3/30
 */
class ResponseException(throwable: Throwable, var code: Int) : Exception(throwable) {

    override var message: String? = null

}
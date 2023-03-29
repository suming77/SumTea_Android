package com.sum.network.error

import java.io.IOException

/**
 * 结果异常类
 * 服务器非200状态，对应的异常
 */
open class ApiException : Exception {
    var errCode: Int
    var errMsg: String

    constructor(error: ERROR, e: Throwable? = null) : super(e) {
        errCode = error.code
        errMsg = error.errMsg
    }

    constructor(code: Int, msg: String, e: Throwable? = null) : super(e) {
        this.errCode = code
        this.errMsg = msg
    }
}

/**
 * 无网络连接异常
 */
class NoNetWorkException : IOException {
    var errCode: Int
    var errMsg: String

    constructor(error: ERROR, e: Throwable? = null) : super(e) {
        errCode = error.code
        errMsg = error.errMsg
    }
}

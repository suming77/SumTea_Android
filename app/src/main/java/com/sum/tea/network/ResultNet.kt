package com.sum.tea.network

/**
 * @创建者 mingyan.su
 * @创建时间 2023/2/20 11:20
 * @类描述 结果请求
 */
sealed class ResultNet<out T : Any> {
    data class Success<out T : Any>(val data: T?) : ResultNet<T>()
    data class Error(val errCode: Int, val errMsg: String) : ResultNet<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$errMsg]"
        }
    }
}

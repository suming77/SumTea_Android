package com.sum.network.error

import android.net.ParseException
import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import com.sum.framework.toast.TipsToast
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException

/**
 * 统一错误处理工具类
 */
object ExceptionHandler {

    fun handleException(e: Throwable): ApiException {

        val ex: ApiException
        if (e is ApiException) {
            ex = ApiException(e.errCode, e.errMsg, e)
            if (ex.errCode == 1001){
                //登录失效
            }
        } else if (e is NoNetWorkException) {
            TipsToast.showTips("网络异常，请尝试刷新")
            ex = ApiException(ERROR.NETWORD_ERROR, e)
        } else if (e is HttpException) {
            ex = when (e.code()) {
                ERROR.UNAUTHORIZED.code -> ApiException(ERROR.UNAUTHORIZED, e)
                ERROR.FORBIDDEN.code -> ApiException(ERROR.FORBIDDEN, e)
                ERROR.NOT_FOUND.code -> ApiException(ERROR.NOT_FOUND, e)
                ERROR.REQUEST_TIMEOUT.code -> ApiException(ERROR.REQUEST_TIMEOUT, e)
                ERROR.GATEWAY_TIMEOUT.code -> ApiException(ERROR.GATEWAY_TIMEOUT, e)
                ERROR.INTERNAL_SERVER_ERROR.code -> ApiException(ERROR.INTERNAL_SERVER_ERROR, e)
                ERROR.BAD_GATEWAY.code -> ApiException(ERROR.BAD_GATEWAY, e)
                ERROR.SERVICE_UNAVAILABLE.code -> ApiException(ERROR.SERVICE_UNAVAILABLE, e)
                else -> ApiException(e.code(), e.message(), e)
            }
        } else if (e is JsonParseException
                || e is JSONException
                || e is ParseException
                || e is MalformedJsonException
        ) {
            ex = ApiException(ERROR.PARSE_ERROR, e)
        } else if (e is ConnectException) {
            ex = ApiException(ERROR.NETWORD_ERROR, e)
        } else if (e is javax.net.ssl.SSLException) {
            ex = ApiException(ERROR.SSL_ERROR, e)
        } else if (e is java.net.SocketException) {
            ex = ApiException(ERROR.TIMEOUT_ERROR, e)
        } else if (e is java.net.SocketTimeoutException) {
            ex = ApiException(ERROR.TIMEOUT_ERROR, e)
        } else if (e is java.net.UnknownHostException) {
            ex = ApiException(ERROR.UNKNOW_HOST, e)
        } else {
            ex = if (!e.message.isNullOrEmpty()) ApiException(1000, e.message!!, e)
            else ApiException(ERROR.UNKNOWN, e)
        }
        return ex
    }
}

package com.sum.network.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sum.framework.log.LogUtil
import com.sum.network.error.ApiException
import com.sum.network.response.BaseResponse
import com.sum.network.error.ExceptionHandler
import com.sum.network.callback.IApiErrorCallback
import com.sum.network.error.ERROR
import com.sum.network.flow.requestFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout

/**
 * @author mingyan.su
 * @date   2023/2/24 13:03
 * @desc   viewModel基类
 */
open class BaseViewModel : ViewModel() {
    /**
     * 运行在主线程中，可直接调用
     * @param errorBlock 错误回调
     * @param responseBlock 请求函数
     */
    fun launchUI(errorBlock: (Int?, String?) -> Unit, responseBlock: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.Main) {
            safeApiCall(errorBlock = errorBlock, responseBlock)
        }
    }

    /**
     * 需要运行在协程作用域中
     * @param errorBlock 错误回调
     * @param responseBlock 请求函数
     */
    suspend fun <T> safeApiCall(
        errorBlock: suspend (Int?, String?) -> Unit,
        responseBlock: suspend () -> T?
    ): T? {
        try {
            return responseBlock()
        } catch (e: Exception) {
            e.printStackTrace()
            LogUtil.e(e)
            val exception = ExceptionHandler.handleException(e)
            errorBlock(exception.errCode, exception.errMsg)
        }
        return null
    }

    /**
     * 不依赖BaseRepository，运行在主线程中，可直接调用
     * @param errorCall 错误回调
     * @param responseBlock 请求函数
     * @param successBlock 请求回调
     */
    fun <T> launchUIWithResult(
        responseBlock: suspend () -> BaseResponse<T>?,
        errorCall: IApiErrorCallback?,
        successBlock: (T?) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = safeApiCallWithResult(errorCall = errorCall, responseBlock)
            successBlock(result)
        }
    }
    /**
     * 不依赖BaseRepository，需要在作用域中运行
     * @param errorCall 错误回调
     * @param responseBlock 请求函数
     */
    suspend fun <T> safeApiCallWithResult(
        errorCall: IApiErrorCallback?,
        responseBlock: suspend () -> BaseResponse<T>?
    ): T? {
        try {
            val response = withContext(Dispatchers.IO) {
                withTimeout(10 * 1000) {
                    responseBlock()
                }
            } ?: return null

            if (response.isFailed()) {
                throw ApiException(response.errorCode, response.errorMsg)
            }
            return response.data
        } catch (e: Exception) {
            e.printStackTrace()
            LogUtil.e(e)
            val exception = ExceptionHandler.handleException(e)
            if (ERROR.UNLOGIN.code == exception.errCode) {
                errorCall?.onLoginFail(exception.errCode, exception.errMsg)
            } else {
                errorCall?.onError(exception.errCode, exception.errMsg)
            }
        }
        return null
    }

    /**
     * flow 运行在主线程中，可直接调用
     * @param errorCall 错误回调
     * @param requestCall 请求函数
     * @param showLoading 是否展示加载框
     * @param successBlock 请求结果
     */
    fun <T> launchFlow(
        errorCall: IApiErrorCallback? = null,
        requestCall: suspend () -> BaseResponse<T>?,
        showLoading: ((Boolean) -> Unit)? = null,
        successBlock: (T?) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.Main) {
            val data = requestFlow(errorBlock = { code, error ->
                if (ERROR.UNLOGIN.code == code) {
                    errorCall?.onLoginFail(code, error)
                } else {
                    errorCall?.onError(code, error)
                }
            }, requestCall, showLoading)
            successBlock(data)
        }
    }
}
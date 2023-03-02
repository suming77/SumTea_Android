package com.sum.network.callback

import com.sum.framework.toast.TipsToast

/**
 * @author mingyan.su
 * @date   2023/3/1 12:05
 * @desc   接口请求错误回调
 */
interface IApiErrorCallback {
    /**
     * 错误回调处理
     */
    fun onError(code: Int?, error: String?) {
        TipsToast.showTips(error)
    }

    /**
     * 登录失效处理
     */
    fun onLoginFail(code: Int?, error: String?) {
        TipsToast.showTips(error)
    }
}
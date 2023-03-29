package com.sum.network.interceptor

import com.sum.framework.helper.SumAppHelper
import com.sum.framework.utils.AppManager
import com.sum.framework.utils.DeviceInfoUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.net.URLEncoder

/**
 * @author mingyan.su
 * @date   2023/3/27 08:27
 * @desc   公共参数拦截器
 */
class PublicParameterInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newBuilder = request.newBuilder().apply {
            addHeader("device-type", "android")
            addHeader("app-version", AppManager.getAppVersionName(SumAppHelper.getApplication()))
            addHeader("device-id", DeviceInfoUtils.androidId)
            addHeader("device-os-version", AppManager.getDeviceBuildRelease())//获取手机系统版本号
            val deviceNameStr = AppManager.getDeviceBuildBrand().plus("_")
                    .plus(AppManager.getDeviceBuildModel())
            addHeader("device-name", URLEncoder.encode(deviceNameStr, "UTF-8"))//获取设备类型
        }

        return chain.proceed(newBuilder.build())
    }
}
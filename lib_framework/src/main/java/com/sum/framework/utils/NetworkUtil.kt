package com.sum.framework.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.telephony.TelephonyManager

@SuppressLint("MissingPermission")
object NetworkUtil {

    /**
     * 网络可访问
     */
    fun isConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = cm.getNetworkCapabilities(cm.activeNetwork)
            networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        } else {
            // 只是判断有网络连接
            cm.activeNetworkInfo?.isConnected == true
        }
    }

    /**
     * 网络类型
     */
    fun getNetworkType(context: Context): NetworkType {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        @Suppress("DEPRECATION") val isWiFi = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = cm.getNetworkCapabilities(cm.activeNetwork)
            networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
        } else {
            cm.activeNetworkInfo?.type == ConnectivityManager.TYPE_WIFI
        }
        if (isWiFi) {
            return NetworkType.NETWORK_WIFI
        }

        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return when (tm.networkType) {
            TelephonyManager.NETWORK_TYPE_GPRS,
            TelephonyManager.NETWORK_TYPE_GSM,
            TelephonyManager.NETWORK_TYPE_EDGE,
            TelephonyManager.NETWORK_TYPE_CDMA,
            TelephonyManager.NETWORK_TYPE_1xRTT,
            TelephonyManager.NETWORK_TYPE_IDEN -> NetworkType.NETWORK_2G

            TelephonyManager.NETWORK_TYPE_UMTS,
            TelephonyManager.NETWORK_TYPE_EVDO_0,
            TelephonyManager.NETWORK_TYPE_EVDO_A,
            TelephonyManager.NETWORK_TYPE_HSDPA,
            TelephonyManager.NETWORK_TYPE_HSUPA,
            TelephonyManager.NETWORK_TYPE_HSPA,
            TelephonyManager.NETWORK_TYPE_EVDO_B,
            TelephonyManager.NETWORK_TYPE_EHRPD,
            TelephonyManager.NETWORK_TYPE_HSPAP,
            TelephonyManager.NETWORK_TYPE_TD_SCDMA -> NetworkType.NETWORK_3G

            TelephonyManager.NETWORK_TYPE_LTE -> NetworkType.NETWORK_4G

            TelephonyManager.NETWORK_TYPE_IWLAN -> NetworkType.NETWORK_WIFI
            TelephonyManager.NETWORK_TYPE_NR -> NetworkType.NETWORK_5G

            else -> NetworkType.NETWORK_UNKNOWN
        }
    }

    /**
     * 获取运营商
     */
    fun getOperatorName(context: Context): String {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return tm.simOperatorName
    }

    /**
     * <4G网络
     */
    fun isWeakNetwork(ctx: Context): Boolean {
        if (!isConnected(ctx)) {
            return true
        }
        val type = getNetworkType(ctx)
        return type != NetworkType.NETWORK_WIFI && type != NetworkType.NETWORK_4G && type != NetworkType.NETWORK_5G
    }
}

enum class NetworkType {
    NETWORK_UNKNOWN, NETWORK_WIFI, NETWORK_2G, NETWORK_3G, NETWORK_4G, NETWORK_5G
}
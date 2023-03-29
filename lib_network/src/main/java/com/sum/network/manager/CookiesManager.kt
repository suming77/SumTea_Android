package com.sum.network.manager

import com.sum.common.constant.HTTP_COOKIES_INFO
import com.sum.framework.log.LogUtil
import com.tencent.mmkv.MMKV

/**
 * @author mingyan.su
 * @date   2023/3/27 08:05
 * @desc   Cookies管理类
 */
object CookiesManager {

    /**
     * 保存Cookies
     * @param cookies
     */
    fun saveCookies(cookies: String) {
        val mmkv = MMKV.defaultMMKV()
        mmkv.encode(HTTP_COOKIES_INFO, cookies)
    }

    /**
     * 获取Cookies
     * @return cookies
     */
    fun getCookies(): String? {
        val mmkv = MMKV.defaultMMKV()
        return mmkv.decodeString(HTTP_COOKIES_INFO, "")
    }

    /**
     * 清除Cookies
     * @param cookies
     */
    fun clearCookies() {
        saveCookies("")
    }

    /**
     * 解析Cookies
     * @param cookies
     */
    fun encodeCookie(cookies: List<String>?): String {
        val sb = StringBuilder()
        val set = HashSet<String>()
        cookies
                ?.map { cookie ->
                    cookie.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                }
                ?.forEach {
                    it.filterNot { set.contains(it) }.forEach { set.add(it) }
                }
        LogUtil.e("cookiesList:$cookies", tag = "smy")
        val ite = set.iterator()
        while (ite.hasNext()) {
            val cookie = ite.next()
            sb.append(cookie).append(";")
        }
        val last = sb.lastIndexOf(";")
        if (sb.length - 1 == last) {
            sb.deleteCharAt(last)
        }
        return sb.toString()
    }
}
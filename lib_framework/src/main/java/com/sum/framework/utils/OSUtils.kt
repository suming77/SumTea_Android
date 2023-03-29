package com.sum.framework.utils

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.text.TextUtils
import com.sum.framework.log.LogUtil
import java.io.BufferedReader
import java.io.FileReader


/**
 * 手机系统ROM判断
 *
 */
object OSUtils {
    private const val KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name"
    private const val KEY_EMUI_VERSION_NAME = "ro.build.version.emui"
    private const val KEY_DISPLAY = "ro.build.display.id"

    /**
     * 判断是否为miui
     * Is miui boolean.
     *
     * @return the boolean
     */
    val isMIUI: Boolean
        get() {
            val property = getSystemProperty(
                KEY_MIUI_VERSION_NAME,
                ""
            )
            return !TextUtils.isEmpty(property)
        }

    /**
     * 判断miui版本是否大于等于6
     * Is miui 6 later boolean.
     *
     * @return the boolean
     */
    val isMIUI6Later: Boolean
        get() {
            val version = mIUIVersion
            val num: Int
            return if (!version.isEmpty()) {
                try {
                    num = Integer.valueOf(version.substring(1))
                    num >= 6
                } catch (e: NumberFormatException) {
                    false
                }
            } else false
        }

    /**
     * 获得miui的版本
     * Gets miui version.
     *
     * @return the miui version
     */
    val mIUIVersion: String
        get() = if (isMIUI) getSystemProperty(
            KEY_MIUI_VERSION_NAME,
            ""
        ) else ""

    /**
     * 判断是否为emui
     * Is emui boolean.
     *
     * @return the boolean
     */
    val isEMUI: Boolean
        get() {
            val property = getSystemProperty(
                KEY_EMUI_VERSION_NAME,
                ""
            )
            return !TextUtils.isEmpty(property)
        }

    /**
     * 得到emui的版本
     * Gets emui version.
     *
     * @return the emui version
     */
    val eMUIVersion: String
        get() = if (isEMUI) getSystemProperty(
            KEY_EMUI_VERSION_NAME,
            ""
        ) else ""

    /**
     * 判断是否为emui3.1版本
     * Is emui 3 1 boolean.
     *
     * @return the boolean
     */
    val isEMUI3_1: Boolean
        get() {
            val property = eMUIVersion
            return "EmotionUI 3" == property || property.contains("EmotionUI_3.1")
        }

    /**
     * 判断是否为emui3.0版本
     * Is emui 3 1 boolean.
     *
     * @return the boolean
     */
    val isEMUI3_0: Boolean
        get() {
            val property = eMUIVersion
            return property.contains("EmotionUI_3.0")
        }

    /**
     * 判断是否为emui3.x版本
     * Is emui 3 x boolean.
     *
     * @return the boolean
     */
    val isEMUI3_x: Boolean
        get() = isEMUI3_0 || isEMUI3_1

    /**
     * 判断是否为flymeOS
     * Is flyme os boolean.
     *
     * @return the boolean
     */
    val isFlymeOS: Boolean
        get() = flymeOSFlag.toLowerCase().contains("flyme")

    /**
     * 判断flymeOS的版本是否大于等于4
     * Is flyme os 4 later boolean.
     *
     * @return the boolean
     */
    val isFlymeOS4Later: Boolean
        get() {
            val version = flymeOSVersion
            val num: Int
            return if (version.isNotEmpty()) {
                try {
                    num = if (version.toLowerCase().contains("os")) {
                        Integer.valueOf(version.substring(9, 10))
                    } else {
                        Integer.valueOf(version.substring(6, 7))
                    }
                    num >= 4
                } catch (e: NumberFormatException) {
                    false
                }
            } else false
        }

    /**
     * 判断flymeOS的版本是否等于5
     * Is flyme os 5 boolean.
     *
     * @return the boolean
     */
    val isFlymeOS5: Boolean
        get() {
            val version = flymeOSVersion
            val num: Int
            return if (version.isNotEmpty()) {
                try {
                    num = if (version.toLowerCase().contains("os")) {
                        Integer.valueOf(version.substring(9, 10))
                    } else {
                        Integer.valueOf(version.substring(6, 7))
                    }
                    num == 5
                } catch (e: NumberFormatException) {
                    false
                }
            } else false
        }

    /**
     * 得到flymeOS的版本
     * Gets flyme os version.
     *
     * @return the flyme os version
     */
    val flymeOSVersion: String
        get() = if (isFlymeOS) getSystemProperty(
            KEY_DISPLAY,
            ""
        ) else ""

    private val flymeOSFlag: String
        private get() = getSystemProperty(
            KEY_DISPLAY,
            ""
        )

    private fun getSystemProperty(key: String, defaultValue: String): String {
        try {
            @SuppressLint("PrivateApi") val clz =
                Class.forName("android.os.SystemProperties")
            val method =
                clz.getMethod("get", String::class.java, String::class.java)
            return method.invoke(clz, key, defaultValue) as String
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return defaultValue
    }

    /**
     * 获取操作系统
     *
     * @return
     */
    fun getOS(): String {
        return "Android" + Build.VERSION.RELEASE
    }

    /**
     * 获取手机的内存信息，包含可用存储和总存储大小
     */
    fun getMemoryInfo(context: Context): String {
        var result = ""
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memInfo = ActivityManager.MemoryInfo()
        am.getMemoryInfo(memInfo)
        result += "avail:${memInfo.availMem / 1024}KB;"
        val str = "/proc/meminfo"
        var localBufferedReader: BufferedReader? = null
        try {
            val fileReader = FileReader(str)
            localBufferedReader = BufferedReader(fileReader, 8192)
            val totalMemory = localBufferedReader.readLine()
            result += totalMemory
        } catch (e: Exception) {
            LogUtil.e(e)
        } finally {
            localBufferedReader?.close()
        }
        return result
    }

}
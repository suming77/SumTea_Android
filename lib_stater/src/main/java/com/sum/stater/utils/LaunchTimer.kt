package com.sum.stater.utils

import com.sum.framework.log.LogUtil

/**
 * 自定义埋点工具
 */
object LaunchTimer {
    object LaunchTimer {
        private var currentTime: Long = 0

        // 记录开始时间
        fun startRecord() {
            currentTime = System.currentTimeMillis()
        }

        // 记录结束时间，某个tag的耗时
        @JvmOverloads
        fun stopRecord(title: String = "") {
            val t = System.currentTimeMillis() - currentTime
            LogUtil.d("$title | time：$t", tag = "LaunchTimer")
        }
    }
}
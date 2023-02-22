package com.sum.tea.listener

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 * Author mingyan.su
 * Time   2023/2/21 14:20
 * Desc   App前后台切换监听
 */
object AppFrontBack {
    /**
     * 打开的Activity数量统计
     */
    private var activityStartCount = 0

    /**
     * 注册状态监听，仅在Application中使用
     * @param application
     * @param listener
     */
    fun register(application: Application, listener: AppFrontBackListener) {
        application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            }

            override fun onActivityStarted(activity: Activity) {
                activityStartCount++
                if (activityStartCount == 1) {
                    listener.onFront(activity)
                }
            }

            override fun onActivityStopped(activity: Activity) {
                activityStartCount--
                if (activityStartCount == 0) {
                    listener.onBack(activity)
                }
            }

        })
    }
}

/**
 * App状态监听
 */
interface AppFrontBackListener {
    /**
     * 前台
     */
    fun onFront(activity: Activity?)

    /**
     * 后台
     */
    fun onBack(activity: Activity?)
}
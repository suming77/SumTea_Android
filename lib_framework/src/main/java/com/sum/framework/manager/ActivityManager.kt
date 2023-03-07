package com.sum.framework.manager

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES

/**
 * Activity管理类
 */
object ActivityManager {

    private val tasks = mutableListOf<Activity>()

    fun push(activity: Activity) {
        tasks.add(activity)
    }

    fun pop(activity: Activity) {
        tasks.remove(activity)
    }

    fun top(): Activity? {
        return tasks.last()
    }

    fun finishAllActivity(callback: (() -> Unit)? = null) {
        val it = tasks.iterator()
        while (it.hasNext()) {
            val item = it.next()
            it.remove()
            item.finish()
        }
        callback?.invoke()
    }

    /**
     * 关闭其他activity
     */
    fun finishOtherActivity(clazz: Class<out Activity>) {
        val it = tasks.iterator()
        while (it.hasNext()) {
            val item = it.next()
            if (item::class.java != clazz) {
                it.remove()
                item.finish()
            }
        }
    }


    /**
     * 关闭activity
     */
    fun finishActivity(clazz: Class<out Activity>) {
        val it = tasks.iterator()
        while (it.hasNext()) {
            val item = it.next()
            if (item::class.java == clazz) {
                it.remove()
                item.finish()
                break
            }
        }
    }

    /**
     * activity是否在栈中
     */
    fun isActivityExistsInStack(clazz: Class<out Activity>?): Boolean {
        if (clazz != null) {
            for (task in tasks) {
                if (task::class.java == clazz) {
                    return true
                }
            }
        }
        return false
    }

    /**
     * Activity是否销毁
     * @param context
     */
    fun isActivityDestroy(context: Context): Boolean {
        val activity = findActivity(context)
        return if (activity != null) {
            if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR1) {
                activity.isDestroyed || activity.isFinishing
            } else activity.isFinishing
        } else true
    }

    /**
     * ContextWrapper是context的包装类，AppcompatActivity，service，application实际上都是ContextWrapper的子类
     * AppcompatXXX类的context都会被包装成TintContextWrapper
     * @param context
     */
    private fun findActivity(context: Context): Activity? {
        // 怎么判断context是不是Activity
        if (context is Activity) { // 这种方法不够严谨
            return context
        } else if (context is ContextWrapper) {
            return findActivity(context.baseContext)
        }
        return null
    }
}
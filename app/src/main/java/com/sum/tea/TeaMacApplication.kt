package com.sum.tea

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.multidex.MultiDex
import com.sum.tea.listener.AppFrontBack
import com.sum.tea.listener.AppFrontBackListener
import com.sum.tea.manager.ActivityManager
import com.sum.tea.stater.dispatcher.TaskDispatcher
import com.sum.tea.stater.inittasks.InitGlideTask
import com.sum.tea.stater.inittasks.InitLanguageTask
import com.sum.tea.stater.inittasks.InitShareManagerTask
import com.sum.tea.stater.inittasks.InitNetWorkTask
import com.sum.tea.utils.AppManager
import com.sum.tea.utils.TipsToast
import com.sum.tea.utils.log.LogUtil
import com.tencent.mmkv.MMKV

/**
 * @author mingyan.su
 * @date   2023/2/9 23:19
 * @desc
 */
class TeaMacApplication : Application() {

    companion object {
        private lateinit var instance: TeaMacApplication

        fun getInstance() = instance
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        //注册APP前后台切换监听
        appFrontBackRegister()
        // App启动立即注册监听
        registerActivityLifecycle()
        TipsToast.init(this)
        //TaskDispatcher初始化
        TaskDispatcher.init(this)

        val rootDir: String = MMKV.initialize(this)
        LogUtil.d("mmkv root: $rootDir", tag = "MMKV")

        AppManager.init(this)

        //创建dispatcher实例
        val dispatcher: TaskDispatcher = TaskDispatcher.createInstance()

        //添加任务并且启动任务
        dispatcher.addTask(InitGlideTask(this))
                .addTask(InitLanguageTask(this))
                .addTask(InitNetWorkTask(this))
                .addTask(InitShareManagerTask(this))
                .start()

        //等待，需要等待的方法执行完才可以往下执行
        dispatcher.await()
    }

    /**
     * 注册APP前后台切换监听
     */
    private fun appFrontBackRegister() {
        AppFrontBack.register(this, object : AppFrontBackListener {
            override fun onBack(activity: Activity?) {
                LogUtil.d("onBack")
                Log.d("LogUtil", "onBack")

            }

            override fun onFront(activity: Activity?) {
                LogUtil.d("onFront")
                Log.d("LogUtil", "onFront")
            }
        })
    }

    /**
     * 注册Activity生命周期监听
     */
    private fun registerActivityLifecycle() {
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityDestroyed(activity: Activity) {
                ActivityManager.pop(activity)
            }

            override fun onActivitySaveInstanceState(activity: Activity, p1: Bundle) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivityCreated(activity: Activity, p1: Bundle?) {
                ActivityManager.push(activity)
            }

            override fun onActivityResumed(activity: Activity) {
            }
        })
    }
}
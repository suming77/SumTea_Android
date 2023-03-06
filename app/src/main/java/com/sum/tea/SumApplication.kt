package com.sum.tea

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.multidex.MultiDex
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.sum.framework.helper.SumAppHelper
import com.sum.framework.listener.AppFrontBack
import com.sum.framework.listener.AppFrontBackListener
import com.sum.framework.log.LogUtil
import com.sum.framework.manager.ActivityManager
import com.sum.framework.toast.TipsToast
import com.sum.framework.utils.AppManager
import com.sum.stater.dispatcher.TaskDispatcher
import com.sum.stater.inittasks.InitGlideTask
import com.sum.stater.inittasks.InitLanguageTask
import com.sum.stater.inittasks.InitNetWorkTask
import com.sum.stater.inittasks.InitShareManagerTask
import com.tencent.mmkv.MMKV

/**
 * @author mingyan.su
 * @date   2023/2/9 23:19
 * @desc   应用类
 */
class SumApplication : Application() {

    companion object {
        private lateinit var instance: SumApplication

        fun getInstance() = instance
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        SumAppHelper.init(this, BuildConfig.DEBUG)
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
        initRefreshLayout()
    }

    /**
     * 全局初始化SmartRefreshLayout
     */
    private fun initRefreshLayout() {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(android.R.color.white)
//            CustomRefreshHeader(context)
            ClassicsHeader(context)
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            //指定为经典Footer，默认是 BallPulseFooter
            ClassicsFooter(context)
        }
    }

    /**
     * 注册APP前后台切换监听
     */
    private fun appFrontBackRegister() {
        AppFrontBack.register(this, object : AppFrontBackListener {
            override fun onBack(activity: Activity?) {
                LogUtil.d("onBack")
            }

            override fun onFront(activity: Activity?) {
                LogUtil.d("onFront")
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
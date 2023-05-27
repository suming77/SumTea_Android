package com.sum.common

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.sum.framework.helper.SumAppHelper
import com.sum.framework.log.LogUtil
import com.sum.framework.manager.AppManager
import com.sum.stater.task.Task
import com.sum.stater.utils.DispatcherExecutor
import com.tencent.mmkv.MMKV
import com.tencent.mmkv.MMKVLogLevel
import java.util.concurrent.ExecutorService

/**
 * 初始化启动任务
 */

/**
 * 初始化全局帮助类
 */
class InitSumHelperTask(val application: Application) : Task() {
    //异步线程执行的Task在被调用await的时候等待
    override fun needWait(): Boolean {
        return true
    }

    override fun run() {
        SumAppHelper.init(application, BuildConfig.DEBUG)
    }
}

/**
 * 初始化MMKV
 */
class InitMmkvTask() : Task() {

    //异步线程执行的Task在被调用await的时候等待
    override fun needWait(): Boolean {
        return true
    }

    //依赖某些任务，在某些任务完成后才能执行
    override fun dependsOn(): MutableList<Class<out Task>> {
        val tasks = mutableListOf<Class<out Task?>>()
        tasks.add(InitSumHelperTask::class.java)
        return tasks
    }

    //指定线程池
    override fun runOn(): ExecutorService? {
        return DispatcherExecutor.iOExecutor
    }

    //执行任务初始化
    override fun run() {
        val rootDir: String = MMKV.initialize(SumAppHelper.getApplication())
        MMKV.setLogLevel(
            if (BuildConfig.DEBUG) {
                MMKVLogLevel.LevelDebug
            } else {
                MMKVLogLevel.LevelError
            }
        )
        LogUtil.d("mmkv root: $rootDir", tag = "MMKV")
    }
}

/**
 * 初始化AppManager
 */
class InitAppManagerTask() : Task() {
    //异步线程执行的Task在被调用await的时候等待
    override fun needWait(): Boolean {
        return true
    }

    //依赖某些任务，在某些任务完成后才能执行
    override fun dependsOn(): MutableList<Class<out Task>> {
        val tasks = mutableListOf<Class<out Task?>>()
        tasks.add(InitSumHelperTask::class.java)
        return tasks
    }

    override fun run() {
        AppManager.init(SumAppHelper.getApplication())
    }
}

/**
 * 全局初始化SmartRefreshLayout
 */
class InitRefreshLayoutTask() : Task() {
    //异步线程执行的Task在被调用await的时候等待
    override fun needWait(): Boolean {
        return true
    }

    override fun run() {
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
}

/**
 * 初始化ShareManager
 */
class InitArouterTask() : Task() {
    //异步线程执行的Task在被调用await的时候等待
    override fun needWait(): Boolean {
        return true
    }

    //依赖某些任务，在某些任务完成后才能执行
    override fun dependsOn(): MutableList<Class<out Task>> {
        val tasks = mutableListOf<Class<out Task?>>()
        tasks.add(InitSumHelperTask::class.java)
        return tasks
    }

    //执行任务，任务真正的执行逻辑
    override fun run() {
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        if (BuildConfig.DEBUG) {
            // 开启打印日志
            ARouter.openLog()
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug()
        }
        ARouter.init(SumAppHelper.getApplication())
    }
}

/**
 * 初始化A
 */
class InitTaskA() : Task() {

    override fun run() {
        //...
    }
}

/**
 * 初始化B
 */
class InitTaskB() : Task() {

    override fun run() {
        //...
    }
}
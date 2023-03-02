package com.sum.stater.inittasks

import android.app.Application
import com.sum.stater.task.Task
import com.sum.stater.utils.DispatcherExecutor
import java.util.concurrent.ExecutorService

/**
 * 初始化启动任务
 */

/**
 * Glide
 */
class InitGlideTask(val application: Application) : Task() {

    //异步线程执行的Task在被调用await的时候等待
    override fun needWait(): Boolean {
        return true
    }

    //依赖某些任务，在某些任务完成后才能执行
    override fun dependsOn(): MutableList<Class<out Task>> {
        val tasks = mutableListOf<Class<out Task?>>()
        tasks.add(InitLanguageTask::class.java)
        return tasks
    }

    //指定线程池
    override fun runOn(): ExecutorService? {
        return DispatcherExecutor.iOExecutor
    }

    //执行任务初始化
    override fun run() {

    }
}

/**
 * 初始化Language
 */
class InitLanguageTask(val application: Application) : Task() {
    //异步线程执行的Task在被调用await的时候等待
    override fun needWait(): Boolean {
        return true
    }

    override fun run() {
    }
}

/**
 * 初始化网络
 */
class InitNetWorkTask(val application: Application) : Task() {
    //异步线程执行的Task在被调用await的时候等待
    override fun needWait(): Boolean {
        return true
    }

    override fun run() {
    }
}

/**
 * 初始化ShareManager
 */
class InitShareManagerTask(val application: Application) : Task() {
    //异步线程执行的Task在被调用await的时候等待
    override fun needWait(): Boolean {
        return true
    }

    //执行任务，任务真正的执行逻辑
    override fun run() {
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
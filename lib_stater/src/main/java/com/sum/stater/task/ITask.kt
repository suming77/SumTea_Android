package com.sum.stater.task

import android.os.Process
import androidx.annotation.IntRange
import java.util.concurrent.Executor

interface ITask {
    /**
     * 优先级的范围，可根据Task重要程度及工作量指定；之后根据实际情况决定是否有必要放更大
     *
     * @return
     */
    @IntRange(from = Process.THREAD_PRIORITY_FOREGROUND.toLong(), to = Process.THREAD_PRIORITY_LOWEST.toLong())
    fun priority(): Int

    /**
     * 任务真正执行的地方
     */
    fun run()

    /**
     * Task执行所在的线程池，可指定，一般默认
     *
     * @return
     */
    fun runOn(): Executor?

    /**
     * 依赖关系
     *
     * @return
     */
    fun dependsOn(): List<Class<out Task?>?>?

    /**
     * 异步线程执行的Task是否需要在被调用await的时候等待，默认不需要
     *
     * @return
     */
    fun needWait(): Boolean

    /**
     * 是否在主线程执行
     *
     * @return
     */
    fun runOnMainThread(): Boolean

    /**
     * 只是在主进程执行
     *
     * @return
     */
    fun onlyInMainProcess(): Boolean

    /**
     * Task主任务执行完成之后需要执行的任务
     *
     * @return
     */
    val tailRunnable: Runnable?

    /**
     * 设置任务CallBack
     */
    fun setTaskCallBack(callBack: TaskCallBack?)

    /**
     * 是否需要回调
     */
    fun needCall(): Boolean
}

/**
 * 任务Callback
 */
interface TaskCallBack {
    fun call()
}
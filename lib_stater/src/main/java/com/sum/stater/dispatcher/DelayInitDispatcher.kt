package com.sum.stater.dispatcher

import android.os.Looper
import android.os.MessageQueue.IdleHandler
import com.sum.stater.task.DispatchRunnable
import com.sum.stater.task.Task
import java.util.LinkedList
import java.util.Queue

/**
 * 延迟初始化
 * 利用IdleHandler的等待主线程空闲特性，在空闲时才去执行任务
 */
class DelayInitDispatcher {
    // 任务集合
    private val mDelayTasks: Queue<Task> = LinkedList()

    private val mIdleHandler = IdleHandler {
        if (mDelayTasks.size > 0) {
            val task = mDelayTasks.poll()
            task?.let {
                DispatchRunnable(it).run()
            }
        }
        !mDelayTasks.isEmpty()
    }

    /**
     * 添加任务
     */
    fun addTask(task: Task): DelayInitDispatcher {
        mDelayTasks.add(task)
        return this
    }

    /**
     * 开启延迟启动器
     */
    fun start() {
        Looper.myQueue().addIdleHandler(mIdleHandler)
    }
}
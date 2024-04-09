package com.sum.framework.log

import android.app.Application
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import com.sum.framework.utils.DeviceInfoUtils

/**
 *  API is the same to {@link android.util.Log}
 */
object LogUtil {
    private const val TAG = "SumTea"
    var application: Application? = null

    private var isDebug = false

    private var logger: XLogger = XLogger()

    private var mLogPath: String? = null

    fun init(application: Application, logPath: String, namePrefix: String = "SRM", isDebug: Boolean = false) {
        LogUtil.application = application
        LogUtil.isDebug = isDebug
        mLogPath = logPath
//        logger.init(application, isDebug, logPath, namePrefix)
        // persist env info
        val metrics = DisplayMetrics()
        (application.getSystemService(Context.WINDOW_SERVICE) as WindowManager)
                .defaultDisplay.getMetrics(metrics)
        val envInfo = """
            *******************************
            ${DeviceInfoUtils.phoneManufacturer}_${DeviceInfoUtils.phoneModel}_${metrics.widthPixels}x${metrics.heightPixels}_${metrics.densityDpi}_${Build.VERSION.SDK_INT}
            *******************************
        """.trimIndent()
//        logger.e(TAG, envInfo)
    }

    @JvmOverloads
    fun v(message: String, throwable: Throwable? = null, tag: String? = null, saveLog: Boolean = false) {
        prepareLog(Log.VERBOSE, tag, message, throwable, saveLog)
    }

    @JvmOverloads
    fun d(message: String, throwable: Throwable? = null, tag: String? = null, saveLog: Boolean = false) {
        prepareLog(Log.DEBUG, tag, message, throwable, saveLog)
    }

    @JvmOverloads
    fun i(message: String, throwable: Throwable? = null, tag: String? = null, saveLog: Boolean = false) {
        prepareLog(Log.INFO, tag, message, throwable, saveLog)
    }

    @JvmOverloads
    fun w(message: String, throwable: Throwable? = null, tag: String? = null, saveLog: Boolean = false) {
        prepareLog(Log.WARN, tag, message, throwable, saveLog)
    }

    @JvmOverloads
    fun e(message: String, throwable: Throwable? = null, tag: String? = null, saveLog: Boolean = false) {
        prepareLog(Log.ERROR, tag, message, throwable, saveLog)
    }

    @JvmOverloads
    fun w(throwable: Throwable? = null, saveLog: Boolean = false) {
        prepareLog(Log.WARN, "", "", throwable, saveLog)
    }

    @JvmOverloads
    fun e(throwable: Throwable? = null, saveLog: Boolean = false) {
        prepareLog(Log.ERROR, "", "", throwable, saveLog)
    }

    private fun prepareLog(priority: Int, tag: String?, message: String, throwable: Throwable?, saveLog: Boolean) {
        val logTag = tag ?: TAG
        throwable?.let {
//            logger.logThrowable(logTag, it, message)
        } ?: run {
            // 按照日志级别打印，线上版本在warn及以上级别会打印到日志文件中
//            when (priority) {
//                Log.VERBOSE -> logger.v(logTag, message)
//                Log.DEBUG -> logger.d(logTag, message)
//                Log.INFO -> logger.i(logTag, message)
//                Log.WARN -> logger.w(logTag, message)
//                Log.ERROR -> logger.e(logTag, message)
//                else -> logger.v(logTag, message)
//            }
            when (priority) {
                Log.VERBOSE -> Log.v(logTag, message)
                Log.DEBUG -> Log.d(logTag, message)
                Log.INFO -> Log.i(logTag, message)
                Log.WARN -> Log.w(logTag, message)
                Log.ERROR -> Log.e(logTag, message)
                else -> Log.v(logTag, message)
            }
        }
    }

    /**
     * 将缓存中的日志刷新到文件里去
     */
    fun flushLog() {
//        logger.flushLog()
    }

    /**
     * 获取日志存放路径
     */
    fun getLogPath(): String? {
        return mLogPath
    }

}

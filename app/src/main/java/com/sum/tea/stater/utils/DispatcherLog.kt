package com.sum.tea.stater.utils

import android.util.Log
import androidx.viewbinding.BuildConfig

object DispatcherLog {
    var isDebug = BuildConfig.DEBUG

    @JvmStatic
    fun i(msg: String?) {
        if (!isDebug || msg == null) {
            return
        }
        Log.i("StartTask", msg)
    }
}
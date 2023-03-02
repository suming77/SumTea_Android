package com.sum.stater.utils

import com.sum.framework.helper.SumAppHelper
import com.sum.framework.log.LogUtil

object DispatcherLog {
    var isDebug = SumAppHelper.isDebug()

    @JvmStatic
    fun i(msg: String?) {
        if (msg == null) {
            return
        }
        LogUtil.i(msg, tag = "StartTask")
    }
}
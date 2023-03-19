package com.sum.framework.ext

//秒的long时间转化
fun Long.getDay(): String = (this / (3600 * 24)).asTwoDigit()
fun Long.getHour(): String = (this % (3600 * 24) / 3600).asTwoDigit()
fun Long.getMin(): String = (this % (3600 * 24) % 3600 / 60).asTwoDigit()
fun Long.getSec(): String = (this % (3600 * 24) % 3600 % 60).asTwoDigit()

fun Long.formattedTime(keepHours: Boolean = false): String {
    val seconds = this
    val h = seconds / 3600
    val m = (seconds % 3600) / 60
    val s = (seconds % 3600) % 60

    return if (!keepHours && h == 0L) {
        "${m.asTwoDigit()}:${s.asTwoDigit()}"
    } else {
        "${h.asTwoDigit()}:${m.asTwoDigit()}:${s.asTwoDigit()}"
    }
}

fun Long.asTwoDigit(): String {
    val value = StringBuilder()
    if (this < 10) {
        value.append("0")
    }
    value.append(toString())
    return value.toString()
}
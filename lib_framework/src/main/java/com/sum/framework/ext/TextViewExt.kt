package com.sum.framework.ext

import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.forEach
import androidx.core.view.marginEnd
import androidx.core.view.marginStart


fun TextView.getViewWidth(): Float {
    return paint.measureText(text.toString()) +
            paddingStart + paddingEnd + marginStart + marginEnd
}

fun getTextGroupWidth(view: ViewGroup): Float {
    var width: Float = 0f
    view.forEach {
        if (it is TextView) {
            width += it.getViewWidth()
        }
    }
    return width
}

/**
 * 给TextView添加删除线
 */
fun TextView.strikeThroughText() {
    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
}

/**
 * 给TextView取消删除线
 */
fun TextView.cancelStrikeThroughText() {
    paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
}

/**
 * 显示会员码的通用格式文本
 */
fun TextView.showMemCodeText(memCoed: String?) {
    text = memCoed?.let {
        val showText = StringBuffer("")
        if (it.length > 8) {
            showText.append(memCoed.substring(0, 3))
            showText.append(" ")
            showText.append(memCoed.substring(3, 9))
            showText.append(" ")
            showText.append(memCoed.substring(9, memCoed.length))
        } else {
            showText.append(memCoed.length)
        }
        showText.toString()
    } ?: let {
        ""
    }
}

/**
 * 设置字体加小粗
 */
fun TextView.bold() {
    paint.isFakeBoldText = true
}

/**
 * 设置字体加大粗
 */
fun TextView.Bold() {
    typeface = Typeface.defaultFromStyle(Typeface.BOLD)
}

fun TextView.compoundDrawable(
    start: Int = 0,
    top: Int = 0,
    end: Int = 0,
    bottom: Int = 0
) = apply {
    setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom)
}

fun TextView.compoundDrawable(
    start: Drawable? = null,
    top: Drawable? = null,
    end: Drawable? = null,
    bottom: Drawable? = null
) = apply {
    setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom)
}
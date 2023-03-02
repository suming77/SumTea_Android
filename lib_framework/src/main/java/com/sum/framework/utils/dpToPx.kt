@file:JvmName("DisplayUtil")
@file:JvmMultifileClass

package com.sum.framework.utils

import android.util.TypedValue
import com.sum.framework.helper.SumAppHelper

fun dpToPx(dpValue: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dpValue,
        SumAppHelper.getApplication().resources.displayMetrics
    )
}

fun dpToPx(dpValue: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dpValue.toFloat(),
        SumAppHelper.getApplication().resources.displayMetrics
    ).toInt()
}

/**
 * 所有字体均使用dp
 */
fun spToPx(spValue: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        spValue,
        SumAppHelper.getApplication().resources.displayMetrics
    )
}
@file:JvmName("DisplayUtil")
@file:JvmMultifileClass

package com.sum.tea.utils

import android.util.TypedValue
import com.sum.tea.TeaMacApplication

fun dpToPx(dpValue: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dpValue,
        TeaMacApplication.getInstance().resources.displayMetrics
    )
}

fun dpToPx(dpValue: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dpValue.toFloat(),
        TeaMacApplication.getInstance().resources.displayMetrics
    ).toInt()
}

/**
 * 所有字体均使用dp
 */
fun spToPx(spValue: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        spValue,
        TeaMacApplication.getInstance().resources.displayMetrics
    )
}
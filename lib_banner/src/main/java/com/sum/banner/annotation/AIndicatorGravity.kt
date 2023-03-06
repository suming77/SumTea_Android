package com.sum.banner.annotation

import androidx.annotation.IntDef
import com.sum.banner.mode.IndicatorGravity
import java.lang.annotation.ElementType
import java.lang.annotation.Target

/**
 * 指示器位置
 */
@IntDef(IndicatorGravity.CENTER, IndicatorGravity.START, IndicatorGravity.END)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@Target(ElementType.PARAMETER)
annotation class AIndicatorGravity()

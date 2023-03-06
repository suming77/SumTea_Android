package com.sum.banner.annotation

import android.view.View
import androidx.annotation.IntDef
import java.lang.annotation.ElementType
import java.lang.annotation.Target

/**
 * 指示器可见性
 */
@IntDef(View.VISIBLE, View.INVISIBLE, View.GONE)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@Target(ElementType.PARAMETER)
annotation class Visibility()

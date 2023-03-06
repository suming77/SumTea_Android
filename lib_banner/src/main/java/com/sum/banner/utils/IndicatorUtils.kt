package com.sum.banner.utils

import android.content.res.Resources
import com.sum.banner.options.IndicatorOptions

/**
 * 指示器工具类
 */
object IndicatorUtils {

    @JvmStatic
    fun dp2px(dpValue: Float): Int {
        return (0.5f + dpValue * Resources.getSystem().displayMetrics.density).toInt()
    }

    fun getCoordinateX(
        indicatorOptions: IndicatorOptions,
        maxDiameter: Float,
        index: Int
    ): Float {
        val normalIndicatorWidth = indicatorOptions.normalSliderWidth
        return maxDiameter / 2 + (normalIndicatorWidth + indicatorOptions.sliderGap) * index
    }

    fun getCoordinateY(maxDiameter: Float): Float {
        return maxDiameter / 2
    }
}

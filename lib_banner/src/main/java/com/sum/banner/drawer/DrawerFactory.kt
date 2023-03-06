package com.sum.banner.drawer

import com.sum.banner.mode.IndicatorStyle
import com.sum.banner.options.IndicatorOptions

/**
 * Indicator Drawer Factory.
 */
internal object DrawerFactory {
    fun createDrawer(indicatorOptions: IndicatorOptions): IDrawer {
        return when (indicatorOptions.indicatorStyle) {
            IndicatorStyle.DASH -> DashDrawer(indicatorOptions)
            IndicatorStyle.ROUND_RECT -> RoundRectDrawer(indicatorOptions)
            else -> CircleDrawer(indicatorOptions)
        }
    }
}

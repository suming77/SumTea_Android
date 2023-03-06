package com.sum.banner.indicator

import androidx.viewpager.widget.ViewPager
import com.sum.banner.options.IndicatorOptions

/**
 * IIndicator
 */
interface IIndicator : ViewPager.OnPageChangeListener {

    fun notifyDataChanged()

    fun setIndicatorOptions(options: IndicatorOptions)
}

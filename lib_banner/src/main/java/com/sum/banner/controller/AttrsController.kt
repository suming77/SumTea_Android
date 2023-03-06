package com.sum.banner.controller

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import com.sum.banner.R
import com.sum.banner.options.IndicatorOptions
import com.sum.banner.utils.IndicatorUtils.dp2px

/**
 * Attrs控制器
 */
object AttrsController {
    fun initAttrs(
        context: Context, attrs: AttributeSet?,
        indicatorOptions: IndicatorOptions
    ) {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.IndicatorView)
            val indicatorSlideMode = typedArray.getInt(R.styleable.IndicatorView_vpi_slide_mode, 0)
            val indicatorStyle = typedArray.getInt(R.styleable.IndicatorView_vpi_style, 0)
            val checkedColor = typedArray.getColor(
                R.styleable.IndicatorView_vpi_slider_checked_color,
                Color.parseColor("#6C6D72")
            )
            val normalColor = typedArray.getColor(
                R.styleable.IndicatorView_vpi_slider_normal_color,
                Color.parseColor("#8C18171C")
            )
            val orientation = typedArray.getInt(R.styleable.IndicatorView_vpi_orientation, 0)
            val radius = typedArray.getDimension(
                R.styleable.IndicatorView_vpi_slider_radius,
                dp2px(8f).toFloat()
            )
            indicatorOptions.setCheckedColor(checkedColor)
            indicatorOptions.normalSliderColor = normalColor
            indicatorOptions.orientation = orientation
            indicatorOptions.indicatorStyle = indicatorStyle
            indicatorOptions.slideMode = indicatorSlideMode
            indicatorOptions.setSliderWidth(radius * 2, radius * 2)
            typedArray.recycle()
        }
    }
}
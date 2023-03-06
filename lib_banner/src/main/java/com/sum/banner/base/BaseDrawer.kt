package com.sum.banner.base

import android.animation.ArgbEvaluator
import android.graphics.Paint
import com.sum.banner.drawer.IDrawer
import com.sum.banner.mode.IndicatorOrientation
import com.sum.banner.mode.IndicatorSlideMode
import com.sum.banner.options.IndicatorOptions

/**
 * Drawer基类
 */
abstract class BaseDrawer internal constructor(internal var mIndicatorOptions: IndicatorOptions) :
    IDrawer {

    private val mMeasureResult: MeasureResult
    internal var maxWidth: Float = 0.toFloat()
    internal var minWidth: Float = 0.toFloat()
    internal var mPaint: Paint = Paint()
    internal var argbEvaluator: ArgbEvaluator? = null

    companion object {
        const val INDICATOR_PADDING_ADDITION = 6
        const val INDICATOR_PADDING = 3
    }

    protected val isWidthEquals: Boolean
        get() = mIndicatorOptions.normalSliderWidth == mIndicatorOptions.checkedSliderWidth

    init {
        mPaint.isAntiAlias = true
        mMeasureResult = MeasureResult()
        if (mIndicatorOptions.slideMode == IndicatorSlideMode.SCALE
                || mIndicatorOptions.slideMode == IndicatorSlideMode.COLOR
        ) {
            argbEvaluator = ArgbEvaluator()
        }
    }

    override fun onMeasure(
        widthMeasureSpec: Int,
        heightMeasureSpec: Int
    ): MeasureResult {
        maxWidth =
            mIndicatorOptions.normalSliderWidth.coerceAtLeast(mIndicatorOptions.checkedSliderWidth)
        minWidth =
            mIndicatorOptions.normalSliderWidth.coerceAtMost(mIndicatorOptions.checkedSliderWidth)
        if (mIndicatorOptions.orientation == IndicatorOrientation.INDICATOR_VERTICAL) {
            mMeasureResult.setMeasureResult(measureHeight(), measureWidth())
        } else {
            mMeasureResult.setMeasureResult(measureWidth(), measureHeight())
        }
        return mMeasureResult
    }

    protected open fun measureHeight(): Int {
        return mIndicatorOptions.sliderHeight.toInt() + INDICATOR_PADDING
    }

    private fun measureWidth(): Int {
        val pageSize = mIndicatorOptions.pageSize
        val indicatorGap = mIndicatorOptions.sliderGap
        return ((pageSize - 1) * indicatorGap + maxWidth + (pageSize - 1) * minWidth).toInt() + INDICATOR_PADDING_ADDITION
    }

    override fun onLayout(
        changed: Boolean,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
    }

    inner class MeasureResult {

        var measureWidth: Int = 0
            internal set

        var measureHeight: Int = 0
            internal set

        internal fun setMeasureResult(
            measureWidth: Int,
            measureHeight: Int
        ) {
            this.measureWidth = measureWidth
            this.measureHeight = measureHeight
        }
    }
}

package com.sum.banner.drawer

import android.graphics.Canvas
import com.sum.banner.base.BaseDrawer

import com.sum.banner.options.IndicatorOptions

/**
 * Indicator Drawer Proxy.
 */
class DrawerProxy(indicatorOptions: IndicatorOptions) : IDrawer {

    private lateinit var mIDrawer: IDrawer

    init {
        init(indicatorOptions)
    }

    private fun init(indicatorOptions: IndicatorOptions) {
        mIDrawer = DrawerFactory.createDrawer(indicatorOptions)
    }

    override fun onLayout(
        changed: Boolean,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
    }

    override fun onMeasure(
        widthMeasureSpec: Int,
        heightMeasureSpec: Int
    ): BaseDrawer.MeasureResult {
        return mIDrawer.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        mIDrawer.onDraw(canvas)
    }

    fun setIndicatorOptions(indicatorOptions: IndicatorOptions) {
        init(indicatorOptions)
    }
}

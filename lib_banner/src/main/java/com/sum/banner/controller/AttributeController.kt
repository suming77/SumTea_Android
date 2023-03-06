package com.sum.banner.controller

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import com.sum.banner.R
import com.sum.banner.options.BannerOptions
import com.sum.banner.options.BannerOptions.Companion.DEFAULT_REVEAL_WIDTH
import com.sum.framework.utils.dpToPx

/**
 * Attribute控制器
 */
class AttributeController(var mBannerOptions: BannerOptions) {

    fun init(context: Context?, attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray = context?.obtainStyledAttributes(attrs, R.styleable.BannerViewPager)
            typedArray?.let {
                initBannerAttrs(it)
                initIndicatorAttrs(it)
                it.recycle()
            }
        }
    }

    private fun initIndicatorAttrs(typedArray: TypedArray) {
        val indicatorCheckedColor = typedArray.getColor(
            R.styleable.BannerViewPager_bvp_indicator_checked_color,
            Color.parseColor("#8C18171C")
        )
        val indicatorNormalColor = typedArray.getColor(
            R.styleable.BannerViewPager_bvp_indicator_normal_color,
            Color.parseColor("#8C6C6D72")
        )
        val normalIndicatorWidth = typedArray.getDimension(
            R.styleable.BannerViewPager_bvp_indicator_radius,
            dpToPx(8f)
        )
        val indicatorGravity =
            typedArray.getInt(R.styleable.BannerViewPager_bvp_indicator_gravity, 0)
        val indicatorStyle = typedArray.getInt(R.styleable.BannerViewPager_bvp_indicator_style, 0)
        val indicatorSlideMode =
            typedArray.getInt(R.styleable.BannerViewPager_bvp_indicator_slide_mode, 0)
        val indicatorVisibility =
            typedArray.getInt(R.styleable.BannerViewPager_bvp_indicator_visibility, 0)
        mBannerOptions.setIndicatorSliderColor(indicatorNormalColor, indicatorCheckedColor)
        mBannerOptions.setIndicatorSliderWidth(
            normalIndicatorWidth.toInt(),
            normalIndicatorWidth.toInt()
        )
        mBannerOptions.setIndicatorGravity(indicatorGravity)
        mBannerOptions.setIndicatorStyle(indicatorStyle)
        mBannerOptions.setIndicatorSlideMode(indicatorSlideMode)
        mBannerOptions.setIndicatorVisibility(indicatorVisibility)
        mBannerOptions.setIndicatorGap(normalIndicatorWidth)
        mBannerOptions.setIndicatorHeight((normalIndicatorWidth / 2f).toInt())
    }

    private fun initBannerAttrs(typedArray: TypedArray) {
        val interval = typedArray.getInteger(R.styleable.BannerViewPager_bvp_interval, 3000)
        val isAutoPlay = typedArray.getBoolean(R.styleable.BannerViewPager_bvp_auto_play, true)
        val isCanLoop = typedArray.getBoolean(R.styleable.BannerViewPager_bvp_can_loop, true)
        val pageMargin =
            typedArray.getDimension(R.styleable.BannerViewPager_bvp_page_margin, 0f).toInt()
        val roundCorner =
            typedArray.getDimension(R.styleable.BannerViewPager_bvp_round_corner, 0f).toInt()
        val revealWidth = typedArray.getDimension(
            R.styleable.BannerViewPager_bvp_reveal_width,
            DEFAULT_REVEAL_WIDTH.toFloat()
        ).toInt()
        val pageStyle = typedArray.getInt(R.styleable.BannerViewPager_bvp_page_style, 0)
        val scrollDuration = typedArray.getInt(R.styleable.BannerViewPager_bvp_scroll_duration, 0)
        mBannerOptions.setInterval(interval.toLong())
        mBannerOptions.setAutoPlay(isAutoPlay)
        mBannerOptions.setCanLoop(isCanLoop)
        mBannerOptions.setPageMargin(pageMargin)
        mBannerOptions.setRoundRectRadius(roundCorner)
        mBannerOptions.setRightRevealWidth(revealWidth)
        mBannerOptions.setLeftRevealWidth(revealWidth)
        mBannerOptions.setPageStyle(pageStyle)
        mBannerOptions.setScrollDuration(scrollDuration)
    }
}
package com.sum.banner.options

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.sum.banner.mode.PageStyle
import com.sum.banner.mode.IndicatorOrientation
import com.sum.banner.transform.ScaleInTransformer.Companion.DEFAULT_MIN_SCALE
import com.sum.framework.utils.dpToPx

/**
 * Banner的配置参数
 */
class BannerOptions {
    companion object {
        const val DEFAULT_REVEAL_WIDTH = -1000
    }

    private var offScreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT

    private var interval = 0L

    private var isCanLoop = false

    private var isAutoPlay = false

    private var indicatorGravity = 0

    private var pageMargin = dpToPx(20f).toInt()

    private var rightRevealWidth = DEFAULT_REVEAL_WIDTH

    private var leftRevealWidth = DEFAULT_REVEAL_WIDTH

    private var pageStyle: Int = PageStyle.NORMAL

    private var pageScale: Float = DEFAULT_MIN_SCALE

    private var mIndicatorMargin: IndicatorMargin? = null

    private var mIndicatorVisibility = View.VISIBLE

    private var scrollDuration = 0

    private var roundRadiusArray: FloatArray? = null

    private var roundRadius = 0

    private var userInputEnabled = true

    private var orientation = ViewPager2.ORIENTATION_HORIZONTAL

    private var rtl = false

    private var disallowParentInterceptDownEvent = false

    private var stopLoopWhenDetachedFromWindow = true

    private var mIndicatorOptions: IndicatorOptions = IndicatorOptions()

    fun getInterval(): Long {
        return interval
    }

    fun setInterval(interval: Long) {
        this.interval = interval
    }

    fun isCanLoop(): Boolean {
        return isCanLoop
    }

    fun setCanLoop(canLoop: Boolean) {
        isCanLoop = canLoop
    }

    fun isAutoPlay(): Boolean {
        return isAutoPlay
    }

    fun setAutoPlay(autoPlay: Boolean) {
        isAutoPlay = autoPlay
    }

    fun getIndicatorGravity(): Int {
        return indicatorGravity
    }

    fun setIndicatorGravity(indicatorGravity: Int) {
        this.indicatorGravity = indicatorGravity
    }

    fun getIndicatorNormalColor(): Int {
        return mIndicatorOptions.normalSliderColor
    }

    fun getIndicatorCheckedColor(): Int {
        return mIndicatorOptions.checkedSliderColor
    }

    fun getNormalIndicatorWidth(): Int {
        return mIndicatorOptions.normalSliderWidth.toInt()
    }

    fun setIndicatorSliderColor(normalColor: Int, checkedColor: Int) {
        mIndicatorOptions.setSliderColor(normalColor, checkedColor)
    }

    fun setIndicatorSliderWidth(normalWidth: Int, checkedWidth: Int) {
        mIndicatorOptions.setSliderWidth(normalWidth.toFloat(), checkedWidth.toFloat())
    }

    fun showIndicatorWhenOneItem(showIndicatorWhenOneItem: Boolean) {
        mIndicatorOptions.showIndicatorOneItem = showIndicatorWhenOneItem
    }

    fun getCheckedIndicatorWidth(): Int {
        return mIndicatorOptions.checkedSliderWidth.toInt()
    }

    fun getIndicatorOptions(): IndicatorOptions {
        return mIndicatorOptions
    }

    fun getPageMargin(): Int {
        return pageMargin
    }

    fun setPageMargin(pageMargin: Int) {
        this.pageMargin = pageMargin
    }

    fun getRightRevealWidth(): Int {
        return rightRevealWidth
    }

    fun setRightRevealWidth(rightRevealWidth: Int) {
        this.rightRevealWidth = rightRevealWidth
    }

    fun getLeftRevealWidth(): Int {
        return leftRevealWidth
    }

    fun setLeftRevealWidth(leftRevealWidth: Int) {
        this.leftRevealWidth = leftRevealWidth
    }

    fun getIndicatorStyle(): Int {
        return mIndicatorOptions.indicatorStyle
    }

    fun setIndicatorStyle(indicatorStyle: Int) {
        mIndicatorOptions.indicatorStyle = indicatorStyle
    }

    fun getIndicatorSlideMode(): Int {
        return mIndicatorOptions.slideMode
    }

    fun setIndicatorSlideMode(indicatorSlideMode: Int) {
        mIndicatorOptions.slideMode = indicatorSlideMode
    }

    fun getIndicatorGap(): Float {
        return mIndicatorOptions.sliderGap
    }

    fun setIndicatorGap(indicatorGap: Float) {
        mIndicatorOptions.sliderGap = indicatorGap
    }

    fun getIndicatorHeight(): Float {
        return mIndicatorOptions.sliderHeight
    }

    fun setIndicatorHeight(indicatorHeight: Int) {
        mIndicatorOptions.sliderHeight = indicatorHeight.toFloat()
    }

    fun getPageStyle(): Int {
        return pageStyle
    }

    fun setPageStyle(pageStyle: Int) {
        this.pageStyle = pageStyle
    }

    fun getPageScale(): Float {
        return pageScale
    }

    fun setPageScale(pageScale: Float) {
        this.pageScale = pageScale
    }

    fun getIndicatorMargin(): IndicatorMargin? {
        return mIndicatorMargin
    }

    fun setIndicatorMargin(left: Int, top: Int, right: Int, bottom: Int) {
        mIndicatorMargin = IndicatorMargin(left, top, right, bottom)
    }

    fun getRoundRectRadiusArray(): FloatArray? {
        return roundRadiusArray
    }

    fun getRoundRectRadius(): Int {
        return roundRadius
    }

    fun setRoundRectRadius(radius: Int) {
        roundRadius = radius
    }

    fun setRoundRectRadius(
        topLeftRadius: Int, topRightRadius: Int, bottomLeftRadius: Int,
        bottomRightRadius: Int
    ) {
        roundRadiusArray = FloatArray(8)
        roundRadiusArray?.let {
            it[0] = topLeftRadius.toFloat()
            it[1] = topLeftRadius.toFloat()
            it[2] = topRightRadius.toFloat()
            it[3] = topRightRadius.toFloat()
            it[4] = bottomRightRadius.toFloat()
            it[5] = bottomRightRadius.toFloat()
            it[6] = bottomLeftRadius.toFloat()
            it[7] = bottomLeftRadius.toFloat()
        }

    }

    fun getScrollDuration(): Int {
        return scrollDuration
    }

    fun setScrollDuration(scrollDuration: Int) {
        this.scrollDuration = scrollDuration
    }

    fun getIndicatorVisibility(): Int {
        return mIndicatorVisibility
    }

    fun setIndicatorVisibility(indicatorVisibility: Int) {
        mIndicatorVisibility = indicatorVisibility
    }

    fun getOrientation(): Int {
        return orientation
    }

    fun setOrientation(orientation: Int) {
        this.orientation = orientation
        mIndicatorOptions.orientation = orientation
    }

    fun isUserInputEnabled(): Boolean {
        return userInputEnabled
    }

    fun setUserInputEnabled(userInputEnabled: Boolean) {
        this.userInputEnabled = userInputEnabled
    }

    fun resetIndicatorOptions() {
        mIndicatorOptions.currentPosition = 0
        mIndicatorOptions.slideProgress = 0f
    }

    fun isDisallowParentInterceptDownEvent(): Boolean {
        return disallowParentInterceptDownEvent
    }

    fun setDisallowParentInterceptDownEvent(disallowParentInterceptDownEvent: Boolean) {
        this.disallowParentInterceptDownEvent = disallowParentInterceptDownEvent
    }

    fun getOffScreenPageLimit(): Int {
        return offScreenPageLimit
    }

    fun setOffScreenPageLimit(offScreenPageLimit: Int) {
        this.offScreenPageLimit = offScreenPageLimit
    }

    fun isRtl(): Boolean {
        return rtl
    }

    fun setRtl(rtl: Boolean) {
        this.rtl = rtl
        mIndicatorOptions.orientation =
            if (rtl) IndicatorOrientation.INDICATOR_RTL else IndicatorOrientation.INDICATOR_HORIZONTAL

    }

    fun isStopLoopWhenDetachedFromWindow(): Boolean {
        return stopLoopWhenDetachedFromWindow
    }

    fun setStopLoopWhenDetachedFromWindow(stopLoopWhenDetachedFromWindow: Boolean) {
        this.stopLoopWhenDetachedFromWindow = stopLoopWhenDetachedFromWindow
    }

    class IndicatorMargin(val left: Int, val top: Int, val right: Int, val bottom: Int)
}
package com.sum.banner.mode

/**
 * 指示器页面样式
 */
object PageStyle {
    const val NORMAL = 0

    @Deprecated("please use {@link BannerViewPager#setRevealWidth(int)} instead.")
    const val MULTI_PAGE = 1 shl 1

    /**
     * Requires Api Version >= 21
     */
    const val MULTI_PAGE_OVERLAP = 1 shl 2

    const val MULTI_PAGE_SCALE = 1 shl 3
}
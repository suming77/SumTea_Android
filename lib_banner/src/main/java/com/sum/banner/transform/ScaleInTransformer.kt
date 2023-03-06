package com.sum.banner.transform

import android.view.View
import androidx.viewpager2.widget.ViewPager2

/**
 * 缩放Transformer
 */
class ScaleInTransformer(var mMinScale: Float = 0f) : ViewPager2.PageTransformer {
    companion object {
        private const val DEFAULT_CENTER = 0.5f
        const val DEFAULT_MIN_SCALE = 0.85f
    }

    override fun transformPage(view: View, position: Float) {
        val pageWidth = view.width
        val pageHeight = view.height
        view.pivotY = pageHeight / 2f
        view.pivotX = pageWidth / 2f
        if (position < -1) {
            // This page is way off-screen to the left.
            view.scaleX = mMinScale
            view.scaleY = mMinScale
            view.pivotX = pageWidth.toFloat()
        } else if (position <= 1) {
            // Modify the default slide transition to shrink the page as well
            if (position < 0) {
                val scaleFactor = (1 + position) * (1 - mMinScale) + mMinScale
                view.scaleX = scaleFactor
                view.scaleY = scaleFactor
                view.pivotX = pageWidth * (DEFAULT_CENTER + DEFAULT_CENTER * -position)
            } else {
                val scaleFactor = (1 - position) * (1 - mMinScale) + mMinScale
                view.scaleX = scaleFactor
                view.scaleY = scaleFactor
                view.pivotX = pageWidth * ((1 - position) * DEFAULT_CENTER)
            }
        } else {
            view.pivotX = 0f
            view.scaleX = mMinScale
            view.scaleY = mMinScale
        }
    }
}
package com.sum.banner.manager

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.sum.banner.controller.AttributeController
import com.sum.banner.options.BannerOptions
import com.sum.banner.transform.OverlapPageTransformer
import com.sum.banner.transform.ScaleInTransformer

/**
 * Banner管理类
 */
class BannerManager {
    private var mBannerOptions: BannerOptions = BannerOptions()

    private var mAttributeController: AttributeController? = null

    private var mCompositePageTransformer: CompositePageTransformer? = null

    private var mMarginPageTransformer: MarginPageTransformer? = null

    private var mDefaultPageTransformer: ViewPager2.PageTransformer? = null

    init {
        mAttributeController = AttributeController(mBannerOptions)
        mCompositePageTransformer = CompositePageTransformer()
    }

    fun getBannerOptions(): BannerOptions {
        if (mBannerOptions == null) {
            mBannerOptions = BannerOptions()
        }
        return mBannerOptions
    }

    fun initAttrs(context: Context?, attrs: AttributeSet?) {
        mAttributeController?.init(context, attrs)
    }

    fun getCompositePageTransformer(): CompositePageTransformer? {
        return mCompositePageTransformer
    }

    fun addTransformer(transformer: ViewPager2.PageTransformer) {
        mCompositePageTransformer?.addTransformer(transformer)
    }

    fun removeTransformer(transformer: ViewPager2.PageTransformer) {
        mCompositePageTransformer?.removeTransformer(transformer)
    }

    fun removeMarginPageTransformer() {
        mMarginPageTransformer?.let {
            mCompositePageTransformer?.removeTransformer(it)
        }
    }

    fun removeDefaultPageTransformer() {
        if (mDefaultPageTransformer != null) {
            mCompositePageTransformer!!.removeTransformer(mDefaultPageTransformer!!)
        }
    }

    fun setPageMargin(pageMargin: Int) {
        mBannerOptions?.setPageMargin(pageMargin)
    }

    fun createMarginTransformer() {
        removeMarginPageTransformer()
        mMarginPageTransformer = MarginPageTransformer(mBannerOptions?.getPageMargin() ?: 0)
        mMarginPageTransformer?.let {
            mCompositePageTransformer?.addTransformer(it)
        }
    }

    fun setMultiPageStyle(overlap: Boolean, scale: Float) {
        removeDefaultPageTransformer()
        if (overlap && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mDefaultPageTransformer = OverlapPageTransformer(
                mBannerOptions?.getOrientation() ?: ViewPager2.ORIENTATION_HORIZONTAL, scale, 0f, 1f, 0f
            )
        } else {
            mDefaultPageTransformer = ScaleInTransformer(scale)
        }
        mDefaultPageTransformer?.let {
            mCompositePageTransformer?.addTransformer(it)
        }
    }
}
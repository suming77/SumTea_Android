package com.sum.banner.indicator

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.os.Build
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.sum.banner.base.BaseIndicatorView

/**
 * 图片选择器
 */
class DrawableIndicator @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseIndicatorView(context!!, attrs, defStyleAttr) {
    // 选中时的Bitmap
    private var mCheckedBitmap: Bitmap? = null

    // 未选中时的Bitmap
    private var mNormalBitmap: Bitmap? = null

    // 图片之间的间距
    private var mIndicatorPadding = 0

    // 选中图片的宽度
    private var mCheckedBitmapWidth = 0

    // 选中图片的高度
    private var mCheckedBitmapHeight = 0

    //未选中图片的宽高
    private var mNormalBitmapWidth = 0
    private var mNormalBitmapHeight = 0
    private var mIndicatorSize: IndicatorSize? = null
    private var normalCanResize = true
    private var checkCanResize = true
    override fun onMeasure(
        widthMeasureSpec: Int,
        heightMeasureSpec: Int
    ) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val maxHeight = mCheckedBitmapHeight.coerceAtLeast(mNormalBitmapHeight)
        val realWidth =
            mCheckedBitmapWidth + (mNormalBitmapWidth + mIndicatorPadding) * (getPageSize() - 1)
        setMeasuredDimension(realWidth, maxHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (getPageSize() > 1 && mCheckedBitmap != null && mNormalBitmap != null) {
            for (i in 1 until getPageSize() + 1) {
                var left: Int
                var top: Int
                var bitmap = mNormalBitmap
                val index = i - 1
                when {
                    index < getCurrentPosition() -> {
                        left = (i - 1) * (mNormalBitmapWidth + mIndicatorPadding)
                        top = measuredHeight / 2 - mNormalBitmapHeight / 2
                    }
                    index == getCurrentPosition() -> {
                        left = (i - 1) * (mNormalBitmapWidth + mIndicatorPadding)
                        top = measuredHeight / 2 - mCheckedBitmapHeight / 2
                        bitmap = mCheckedBitmap
                    }
                    else -> {
                        left =
                            (i - 1) * mIndicatorPadding + (i - 2) * mNormalBitmapWidth + mCheckedBitmapWidth
                        top = measuredHeight / 2 - mNormalBitmapHeight / 2
                    }
                }
                drawIcon(canvas, left, top, bitmap)
            }
        }
    }

    private fun drawIcon(
        canvas: Canvas,
        left: Int,
        top: Int,
        icon: Bitmap?
    ) {
        if (icon == null) {
            return
        }
        canvas.drawBitmap(icon, left.toFloat(), top.toFloat(), null)
    }

    private fun initIconSize() {
        mCheckedBitmap?.let { bitmap ->
            mIndicatorSize?.let { size ->
                if (bitmap.isMutable && checkCanResize) {
                    bitmap.width = size.checkedWidth
                    bitmap.height = size.checkedHeight
                } else {
                    val width = bitmap.width
                    val height = bitmap.height
                    val scaleWidth = size.checkedWidth.toFloat() / width
                    val scaleHeight = size.checkedHeight.toFloat() / height
                    val matrix = Matrix()
                    matrix.postScale(scaleWidth, scaleHeight)
                    mCheckedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true)
                }
            }
            mCheckedBitmapWidth = mCheckedBitmap?.width ?: 0
            mCheckedBitmapHeight = mCheckedBitmap?.height ?: 0
        }
        mNormalBitmap?.let { bitmap ->
            mIndicatorSize?.let { size ->
                if (bitmap.isMutable && normalCanResize) {
                    bitmap.width = size.normalWidth
                    bitmap.height = size.normalHeight
                } else {
                    val width = bitmap.width
                    val height = bitmap.height
                    val scaleWidth = size.normalWidth.toFloat() / bitmap.width
                    val scaleHeight = size.normalHeight.toFloat() / bitmap.height
                    val matrix = Matrix()
                    matrix.postScale(scaleWidth, scaleHeight)
                    mNormalBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true)
                }
            }
            mNormalBitmapWidth = mNormalBitmap?.width ?: 0
            mNormalBitmapHeight = mNormalBitmap?.height ?: 0
        }
    }

    fun setIndicatorDrawable(
        @DrawableRes normalDrawable: Int,
        @DrawableRes checkedDrawable: Int
    ): DrawableIndicator {
        mNormalBitmap = BitmapFactory.decodeResource(resources, normalDrawable)
        mCheckedBitmap = BitmapFactory.decodeResource(resources, checkedDrawable)
        if (mNormalBitmap == null) {
            mNormalBitmap = getBitmapFromVectorDrawable(context, normalDrawable)
            normalCanResize = false
        }
        if (mCheckedBitmap == null) {
            mCheckedBitmap = getBitmapFromVectorDrawable(context, checkedDrawable)
            checkCanResize = false
        }
        initIconSize()
        postInvalidate()
        return this
    }

    fun setIndicatorSize(
        normalWidth: Int,
        normalHeight: Int,
        checkedWidth: Int,
        checkedHeight: Int
    ): DrawableIndicator {
        mIndicatorSize = IndicatorSize(normalWidth, normalHeight, checkedWidth, checkedHeight)
        initIconSize()
        postInvalidate()
        return this
    }

    fun setIndicatorGap(padding: Int): DrawableIndicator {
        if (padding >= 0) {
            mIndicatorPadding = padding
            postInvalidate()
        }
        return this
    }

    internal class IndicatorSize(
        var normalWidth: Int,
        var normalHeight: Int,
        var checkedWidth: Int,
        var checkedHeight: Int
    )

    private fun getBitmapFromVectorDrawable(
        context: Context,
        drawableId: Int
    ): Bitmap? {
        var drawable = ContextCompat.getDrawable(context, drawableId)
        if (drawable != null && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = DrawableCompat.wrap(drawable).mutate()
        }
        drawable?.let {
            val bitmap = Bitmap.createBitmap(
                it.intrinsicWidth,
                it.intrinsicHeight, Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            it.setBounds(0, 0, canvas.width, canvas.height)
            it.draw(canvas)
            return bitmap
        }
        return null
    }
}
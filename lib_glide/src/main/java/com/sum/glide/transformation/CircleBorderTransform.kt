package com.sum.glide.transformation

import android.graphics.Bitmap
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import android.graphics.Canvas
import android.graphics.Paint
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool

/**
 * 圆形Transform
 */
class CircleBorderTransform(private val borderWidth: Float, private val borderColor: Int) : CircleCrop() {
    private var borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        borderPaint.color = borderColor
        borderPaint.style = Paint.Style.STROKE
        borderPaint.strokeWidth = borderWidth
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        //因为继承自CircleCrop，并且CircleCrop是圆形，在这里获取的bitmap是裁剪后的圆形bitmap
        val transform = super.transform(pool, toTransform, outWidth, outHeight)
        val canvas = Canvas(transform)
        val halfWidth = (outWidth / 2).toFloat()
        val halfHeight = (outHeight / 2).toFloat()
        canvas.drawCircle(
            halfWidth,
            halfHeight,
            halfWidth.coerceAtMost(halfHeight) - borderWidth / 2,
            borderPaint
        )
        canvas.setBitmap(null)
        return transform
    }
}

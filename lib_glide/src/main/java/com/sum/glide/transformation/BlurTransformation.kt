package com.sum.glide.transformation

import android.content.Context
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import com.sum.glide.blur.RSBlur
import android.renderscript.RSRuntimeException
import com.sum.glide.blur.FastBlur
import java.security.MessageDigest

/**
 * 高斯模糊Transformation
 */
class BlurTransformation @JvmOverloads constructor(
    private val radius: Int = MAX_RADIUS,
    private val sampling: Int = DEFAULT_DOWN_SAMPLING
) : BitmapTransformation() {
    override fun transform(
        context: Context, pool: BitmapPool,
        toTransform: Bitmap, outWidth: Int, outHeight: Int
    ): Bitmap {
        val width = toTransform.width
        val height = toTransform.height
        val scaledWidth = width / sampling
        val scaledHeight = height / sampling
        var bitmap = pool[scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888]
        setCanvasBitmapDensity(toTransform, bitmap)
        val canvas = Canvas(bitmap)
        canvas.scale(1 / sampling.toFloat(), 1 / sampling.toFloat())
        val paint = Paint()
        paint.flags = Paint.FILTER_BITMAP_FLAG
        canvas.drawBitmap(toTransform, 0f, 0f, paint)
        bitmap = try {
            RSBlur.blur(context, bitmap, radius)
        } catch (e: RSRuntimeException) {
            FastBlur.blur(bitmap, radius, true)
        }
        return bitmap
    }

    override fun toString(): String {
        return "BlurTransformation(radius=$radius, sampling=$sampling)"
    }

    override fun equals(o: Any?): Boolean {
        return o is BlurTransformation && o.radius == radius && o.sampling == sampling
    }

    override fun hashCode(): Int {
        return ID.hashCode() + radius * 1000 + sampling * 10
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update((ID + radius + sampling).toByteArray(CHARSET))
    }

    companion object {
        private const val VERSION = 1
        private const val ID = "com.sum.glide.transformation.BlurTransformation.$VERSION"
        private const val MAX_RADIUS = 25
        private const val DEFAULT_DOWN_SAMPLING = 1
    }
}
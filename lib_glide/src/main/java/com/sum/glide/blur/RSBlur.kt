package com.sum.glide.blur

import android.content.Context
import android.renderscript.RSRuntimeException
import android.graphics.Bitmap
import android.renderscript.RenderScript
import android.renderscript.Allocation
import android.renderscript.ScriptIntrinsicBlur
import android.renderscript.RenderScript.RSMessageHandler
import android.os.Build
import android.renderscript.Element
/**
 * Android原生，性能比较好，在c/c++层做的处理，但是只能在系统4.2版本以上使用
 */
object RSBlur {
    @Throws(RSRuntimeException::class)
    fun blur(context: Context?, bitmap: Bitmap, radius: Int): Bitmap {
        var rs: RenderScript? = null
        var input: Allocation? = null
        var output: Allocation? = null
        var blur: ScriptIntrinsicBlur? = null
        try {
            rs = RenderScript.create(context)
            rs.messageHandler = RSMessageHandler()
            input = Allocation.createFromBitmap(
                rs, bitmap, Allocation.MipmapControl.MIPMAP_NONE,
                Allocation.USAGE_SCRIPT
            )
            output = Allocation.createTyped(rs, input.type)
            blur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
            blur.setInput(input)
            blur.setRadius(radius.toFloat())
            blur.forEach(output)
            output.copyTo(bitmap)
        } finally {
            if (rs != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    RenderScript.releaseAllContexts()
                } else {
                    rs.destroy()
                }
            }
            input?.destroy()
            output?.destroy()
            blur?.destroy()
        }
        return bitmap
    }
}
package com.sum.common.view

import android.content.Context
import com.sum.framework.log.LogUtil
import androidx.appcompat.widget.AppCompatImageView
import com.sum.common.R
import android.util.AttributeSet
import com.sum.framework.utils.dpToPx

/**
 * 16:9的视频封面图片 默认 16：9
 */
class VideoImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatImageView(context, attrs) {
    private var ratio = 0.56f //比例
    private var widthOffset = 0 //宽度偏差

    init {
        if (attrs != null) {
            val array = context.obtainStyledAttributes(attrs, R.styleable.VideoImageView)
            ratio = array.getFloat(R.styleable.VideoImageView_vimg_ratio, 0.56f)
            widthOffset = array.getInt(R.styleable.VideoImageView_vimg_woffset, 0)
            array.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthSpec = widthMeasureSpec
        var heightSpec = heightMeasureSpec
        setBackgroundResource(R.color.transparent)
        // 父容器传过来的宽度方向上的模式
        val widthMode = MeasureSpec.getMode(widthSpec)
        // 父容器传过来的高度方向上的模式
        val heightMode = MeasureSpec.getMode(heightSpec)
        // 父容器传过来的宽度的值
        var width: Int =
            MeasureSpec.getSize(widthSpec) - paddingLeft - paddingRight - dpToPx(widthOffset)
        // 父容器传过来的高度的值
        var height = MeasureSpec.getSize(heightSpec) - paddingLeft - paddingRight
        if (widthMode == MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY && ratio != 0.0f) {
//            LogUtil.d("表示宽度确定，要测量高度")
            // 判断条件为，宽度模式为Exactly，也就是填充父窗体或者是指定宽度；
            // 且高度模式不是Exaclty，代表设置的既不是fill_parent也不是具体的值，于是需要具体测量
            // 且图片的宽高比已经赋值完毕，不再是0.0f
            // 表示宽度确定，要测量高度
            height = (width / ratio).toInt() // + 0.5f
            heightSpec = MeasureSpec.makeMeasureSpec(
                height,
                MeasureSpec.EXACTLY
            )
        } else if (widthMode != MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY && ratio != 0.0f) {
            // 判断条件跟上面的相反，宽度方向和高度方向的条件互换
            // 表示高度确定，要测量宽度
//            LogUtil.d("表示高度确定，要测量宽度")
            width = (height * ratio).toInt() // + 0.5f
            widthSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY)
        }
//        LogUtil.d("widthMeasureSpec==$widthSpec | heightMeasureSpec:$heightSpec")
        super.onMeasure(widthSpec, heightSpec)
    }
}
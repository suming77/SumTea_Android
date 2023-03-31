package com.sum.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.sum.common.R
import com.sum.common.databinding.ViewEmptyDataBinding
import com.sum.framework.utils.dpToPx

/**
 * @author mingyan.su
 * @date 2022/01/11 16:11
 * @desc 空数据view
 */
class EmptyDataView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var mBinding: ViewEmptyDataBinding

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        mBinding = ViewEmptyDataBinding.inflate(LayoutInflater.from(context), this, true)
        obtainAttributes(context, attrs)
    }

    private fun obtainAttributes(context: Context, attrs: AttributeSet?) {
        attrs?.let {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.EmptyDataView)
            val emptyText = ta.getString(R.styleable.EmptyDataView_emptyText)
            val emptyImage = ta.getResourceId(R.styleable.EmptyDataView_emptyImage, R.mipmap.ic_data_empty)
            val bgColor = ta.getColor(
                R.styleable.EmptyDataView_bg_color,
                ContextCompat.getColor(context, R.color.white)
            )
            val emptyPaddingBottom = ta.getDimension(
                R.styleable.EmptyDataView_emptyPaddingBottom,
                0.0f
            ).toInt()
            ta.recycle()

            if (!emptyText.isNullOrEmpty()) {
                mBinding.tvNoData.text = emptyText
            }
            mBinding.ivNoData.setImageResource(emptyImage)
            setBackgroundColor(bgColor)
            setPadding(0, 0, 0, emptyPaddingBottom)
        }
    }

    /**
     * 设置背景颜色
     *
     * @param colorId 颜色值
     *
     * @return
     */
    fun setBgColor(colorId: Int): EmptyDataView {
        setBackgroundColor(colorId)
        return this
    }

    /**
     * 设置空视图icon
     *
     * @param resId 图片id
     *
     * @return
     */
    fun setImageResource(resId: Int): EmptyDataView {
        if (resId == 0) {
            mBinding.ivNoData.visibility = GONE
            return this
        }
        mBinding.ivNoData.visibility = VISIBLE
        mBinding.ivNoData.setImageResource(resId)
        return this
    }

    /**
     * 设置提示文字
     *
     * @param str 内容
     *
     * @return
     */
    fun setText(str: String?): EmptyDataView {
        mBinding.tvNoData.text = str
        return this
    }
}

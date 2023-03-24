package com.sum.common.view

import android.app.Activity
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.sum.common.R
import com.sum.common.databinding.LayoutTitleBarBinding
import com.sum.framework.ext.onClick
import com.sum.framework.manager.ActivityManager

/**
 * @author: smy
 * @date:  2022/5/6
 * @desc: 标题控件
 */
class TitleBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private lateinit var mBinding: LayoutTitleBarBinding

    init {
        init(context, attrs)
    }

    /**
     * 初始化
     *
     * @param context 上下文
     * @param attrs   attrs
     */
    private fun init(context: Context, attrs: AttributeSet?) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val array = context.obtainStyledAttributes(attrs, R.styleable.TitleBar)
        mBinding = LayoutTitleBarBinding.inflate(inflater, this, true)

        mBinding.llBackLayer.onClick {
            val activity = context as Activity
            if (ActivityManager.isActivityDestroy(context)) {
                return@onClick
            }
            activity.finish()
        }
        setAttrs(attrs, array)
    }

    /**
     * 设置属性
     *
     * @param attrs attrs
     * @param array array
     */
    private fun setAttrs(attrs: AttributeSet?, array: TypedArray) {
        if (attrs == null) {
            return
        }
        val count = array.indexCount
        for (i in 0 until count) {
            when (val attr = array.getIndex(i)) {
                R.styleable.TitleBar_leftText -> {
                    val resId = array.getString(attr)
                    mBinding.tvLeft.text = resId
                }
                R.styleable.TitleBar_middleText -> {
                    val resId = array.getString(attr)
                    mBinding.tvMiddle.text = resId
                }
                R.styleable.TitleBar_rightText -> {
                    val resId = array.getString(attr)
                    mBinding.tvRight.text = resId
                }
                R.styleable.TitleBar_leftIcon -> {
                    val drawable = array.getDrawable(attr)
                    mBinding.ivLeftIcon.setImageDrawable(drawable)
                }
                R.styleable.TitleBar_rightIcon -> {
                    val drawable = array.getDrawable(attr)
                    mBinding.rightIvIcon.setImageDrawable(drawable)
                }
                R.styleable.TitleBar_middleTextColor -> {
                    val color = array.getColor(attr, 0)
                    mBinding.tvMiddle.setTextColor(color)
                }
                R.styleable.TitleBar_leftTextColor -> {
                    val color = array.getColor(attr, 0)
                    mBinding.tvLeft.setTextColor(color)
                }
                R.styleable.TitleBar_rightTextColor -> {
                    val color = array.getColor(attr, 0)
                    mBinding.tvRight.setTextColor(color)
                }
                R.styleable.TitleBar_background -> {
                    val color = array.getColor(attr, 0)
                    mBinding.root.setBackgroundColor(color)
                }
                R.styleable.TitleBar_leftTextSize -> {
                    val size = array.getDimension(attr, 0f)
                    mBinding.tvLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
                }
                R.styleable.TitleBar_rightTextSize -> {
                    val size = array.getDimension(attr, 0f)
                    mBinding.tvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
                }
                R.styleable.TitleBar_leftTextBold -> {
                    val bold = array.getBoolean(attr, false)
                    val paint = mBinding.tvLeft.paint
                    paint.isFakeBoldText = bold
                }
                R.styleable.TitleBar_middleTextBold -> {
                    val bold = array.getBoolean(attr, false)
                    val paint = mBinding.tvMiddle.paint
                    paint.isFakeBoldText = bold
                }
                R.styleable.TitleBar_rightTextBold -> {
                    val bold = array.getBoolean(attr, false)
                    val paint = mBinding.tvRight.paint
                    paint.isFakeBoldText = bold
                }
                R.styleable.TitleBar_leftVisible -> {
                    val show = array.getBoolean(attr, true)
                    val visible = if (show) VISIBLE else INVISIBLE
                    mBinding.llBackLayer.visibility = visible
                    mBinding.tvLeft.visibility = visible
                }
                R.styleable.TitleBar_rightVisible -> {
                    val show = array.getBoolean(attr, true)
                    val visible = if (show) VISIBLE else INVISIBLE
                    mBinding.llRightLayer.visibility = visible
                    mBinding.tvRight.visibility = visible
                }
                R.styleable.TitleBar_middleTextVisible -> {
                    val show = array.getBoolean(attr, false)
                    val visible = if (show) VISIBLE else GONE
                    mBinding.tvMiddle.visibility = visible
                }
                R.styleable.TitleBar_rightIconVisible -> {
                    val show = array.getBoolean(attr, false)
                    val visible = if (show) VISIBLE else GONE
                    mBinding.rightIvIcon.visibility = visible
                }
                R.styleable.TitleBar_showDividerLine -> {
                    val show = array.getBoolean(attr, false)
                    val visible = if (show) VISIBLE else GONE
                    mBinding.dividerLine.visibility = visible
                }
            }
        }
        array.recycle()
    }

    fun setLeftText(title: String?) {
        mBinding.tvLeft.text = title
    }

    fun setMiddleText(title: String?) {
        mBinding.tvMiddle.text = title
    }

    fun setMiddleText(resId: Int) {
        mBinding.tvMiddle.setText(resId)
    }

    fun getTitleRootView(): ConstraintLayout {
        return mBinding.root
    }

    fun getLeftTextView(): TextView {
        return mBinding.tvLeft
    }

    fun getMiddleTextView(): TextView {
        return mBinding.tvMiddle
    }

    fun getRightTextView(): TextView {
        return mBinding.tvRight
    }

}
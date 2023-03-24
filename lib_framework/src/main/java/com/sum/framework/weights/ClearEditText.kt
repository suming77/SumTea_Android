package com.sum.framework.weights

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatEditText
import android.view.View.OnFocusChangeListener
import android.text.TextWatcher
import android.view.MotionEvent
import android.text.Editable
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.view.animation.CycleInterpolator
import androidx.core.content.ContextCompat
import com.sum.framework.R

/**
 * 带清除按钮的EditTextView
 */
class ClearEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = android.R.attr.editTextStyle
) : AppCompatEditText(
    context, attrs, defStyle
), OnFocusChangeListener, TextWatcher {

    //删除按钮的引用
    private var mClearDrawable: Drawable? = null

    //控件是否有焦点
    private var mHasFocus = false

    /**
     * 初始化视图和数据
     */
    init {
        //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        mClearDrawable = compoundDrawables[2]
        if (mClearDrawable == null) {
            //throw new NullPointerException("You can add drawableRight attribute in XML");
            mClearDrawable = ContextCompat.getDrawable(context, R.mipmap.ic_delete_gray)
        }
        mClearDrawable?.setBounds(0, 0, mClearDrawable?.intrinsicWidth ?: 0, mClearDrawable?.intrinsicHeight ?: 0)
        //默认设置隐藏图标
        setClearIconVisible(false)
        //设置焦点改变监听
        onFocusChangeListener = this
        //设置输入框里面内容发生改变的监听
        addTextChangedListener(this)
    }

    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置在EditText的宽度 - 图标到控件右边的距离 - 图标的宽度 和
     * EditText宽度 - 图标到控件右边的的距离之间我们就算点击了图标，竖直方向就没有考虑
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            if (compoundDrawables[2] != null) {
                val touchable = event.x > width - totalPaddingRight && event.x < width - paddingRight
                if (touchable) {
                    this.setText("")
                }
            }
        }
        return super.onTouchEvent(event)
    }

    /**
     * 设置清除图片的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    fun setClearIconVisible(visible: Boolean) {
        //判断是否输入了内容,如果输入了内容我们就把弹窗关闭
        val right = if (visible) mClearDrawable else null
        setCompoundDrawables(compoundDrawables[0], compoundDrawables[1], right, compoundDrawables[3])
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     *
     * @param v
     * @param hasFocus
     */
    override fun onFocusChange(v: View, hasFocus: Boolean) {
        mHasFocus = hasFocus
        if (hasFocus) {
            setClearIconVisible(text!!.isNotEmpty())
        } else {
            setClearIconVisible(false)
        }
    }

    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    override fun onTextChanged(
        text: CharSequence, start: Int, before: Int,
        after: Int
    ) {
        if (mHasFocus) {
            //获取焦点设置回之前的图标
            mClearDrawable = ContextCompat.getDrawable(context, R.mipmap.ic_delete_gray)
            mClearDrawable?.setBounds(0, 0, mClearDrawable?.intrinsicWidth ?: 0, mClearDrawable?.intrinsicHeight ?: 0)
            setClearIconVisible(text.isNotEmpty())
        }
    }

    override fun beforeTextChanged(
        s: CharSequence, start: Int, count: Int,
        after: Int
    ) {
    }

    override fun afterTextChanged(s: Editable) {}

    /**
     * 设置晃动动画
     */
    fun setShakeAnimation() {
        this.animation = shakeAnimation(5)
    }

    /**
     * 晃动动画
     *
     * @param counts 1秒晃动多少下
     * @return
     */
    private fun shakeAnimation(counts: Int): Animation {
        val trAnimation: Animation = TranslateAnimation(0f, 10f, 0f, 0f)
        trAnimation.interpolator = CycleInterpolator(counts.toFloat())
        trAnimation.duration = 1000
        return trAnimation
    }
}
package com.sum.tea.weights

import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import com.sum.tea.R

/**
 * 通用加载中弹窗
 */
class CenterLoadingView(context: Context, theme: Int) : Dialog(context, R.style.loading_dialog) {
    private var ivImage: ImageView? = null
    private var tvMsg: TextView? = null
    private var animation: Animation? = null
    private fun init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_loading)
        ivImage = findViewById<View>(R.id.ivImage) as ImageView
        tvMsg = findViewById<View>(R.id.tvMsg) as TextView
        initAnim()
    }

    private fun initAnim() {
        animation = RotateAnimation(
            0f,
            360f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        animation?.duration = 2000
        animation?.repeatCount = 10
        animation?.fillAfter = true
    }

    override fun show() {
        super.show()
        ivImage!!.startAnimation(animation)
    }

    override fun dismiss() {
        super.dismiss()
        ivImage!!.clearAnimation()
    }

    override fun setTitle(title: CharSequence?) {
        if (!TextUtils.isEmpty(title) && null != tvMsg) {
            tvMsg!!.text = title
        }
    }

    init {
        init()
    }
}

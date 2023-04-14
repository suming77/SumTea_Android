package com.sum.framework.loading

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import com.sum.framework.R
import com.sum.framework.databinding.DialogLoadingBinding

/**
 * 通用加载中弹窗
 */
class CenterLoadingView(context: Context, theme: Int) : Dialog(context, R.style.loading_dialog) {

    private var mBinding: DialogLoadingBinding
    private var animation: Animation? = null

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        mBinding = DialogLoadingBinding.inflate(LayoutInflater.from(context))
        setContentView(mBinding.root)
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
        animation?.repeatCount = 40
        animation?.fillAfter = true
    }

    override fun show() {
        super.show()
        mBinding.ivImage.startAnimation(animation)
    }

    override fun dismiss() {
        super.dismiss()
        mBinding.ivImage.clearAnimation()
    }

    override fun setTitle(title: CharSequence?) {
        if (!title.isNullOrEmpty()) {
            mBinding.tvMsg.text = title
        }
    }
}

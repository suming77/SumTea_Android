package com.sum.common.dialog

import android.app.Dialog
import com.sum.framework.base.BaseDialog.AnimStyle.TOAST
import com.sum.common.R
import com.sum.framework.base.BaseDialog
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorInt
import androidx.fragment.app.FragmentActivity
import com.sum.common.databinding.DialogMessageBinding
import com.sum.framework.base.BaseDialogFragment
import com.sum.framework.ext.onClick

/**
 * 普通弹框
 */
class MessageDialog {

    class Builder(
        activity: FragmentActivity
    ) : BaseDialogFragment.Builder<Builder>(activity) {
        private val mBinding: DialogMessageBinding = DialogMessageBinding.inflate(LayoutInflater.from(activity))

        private var mAutoDismiss = true // 设置点击按钮后自动消失

        init {
            setContentView(mBinding.root)
            setAnimStyle(TOAST)
            setGravity(Gravity.CENTER)
        }

        fun setTitle(resId: Int): Builder {
            return setTitle(getText(resId))
        }

        fun setTitle(text: CharSequence?): Builder {
            mBinding.tvDialogMessageTitle.text = text
            return this
        }

        fun setMessage(resId: Int): Builder {
            return setMessage(getText(resId))
        }

        fun setMessage(text: CharSequence?): Builder {
            mBinding.tvDialogMessageMessage.text = text
            return this
        }

        fun setCancel(resId: Int): Builder {
            return setCancel(getText(resId))
        }

        fun setCancel(text: CharSequence?): Builder {
            mBinding.tvDialogMessageCancel.text = text
            val isEmpty = text.isNullOrEmpty()
            mBinding.tvDialogMessageCancel.visibility = if (isEmpty) View.GONE else View.VISIBLE
            mBinding.vDialogMessageLine.visibility = if (isEmpty) View.GONE else View.VISIBLE
            mBinding.tvDialogMessageConfirm.setBackgroundResource(if (isEmpty) R.drawable.dialog_message_one_button else R.drawable.dialog_message_right_button)
            return this
        }

        fun setConfirm(resId: Int): Builder {
            return setConfirm(getText(resId))
        }

        fun setConfirm(text: CharSequence?): Builder {
            mBinding.tvDialogMessageConfirm.text = text
            return this
        }

        fun setConfirmTxtColor(@ColorInt color: Int): Builder {
            mBinding.tvDialogMessageConfirm.setTextColor(color)
            return this
        }

        fun setCancelTextColor(@ColorInt color: Int): Builder {
            mBinding.tvDialogMessageCancel.setTextColor(color)
            return this
        }

        fun setAutoDismiss(dismiss: Boolean): Builder {
            mAutoDismiss = dismiss
            return this
        }

        fun setonConfirmListener(onConfirm: (dialog: Dialog?) -> Unit): Builder {
            mBinding.tvDialogMessageConfirm.onClick {
                onConfirm?.invoke(dialog)
            }
            return this
        }

        fun setonCancelListener(onCancel: (dialog: Dialog?) -> Unit): Builder {
            mBinding.tvDialogMessageCancel.onClick {
                onCancel?.invoke(dialog)
            }
            return this
        }

        override fun create(): BaseDialog {
            // 如果标题为空就隐藏
            if (mBinding.tvDialogMessageTitle.text.isNullOrEmpty()) {
                mBinding.tvDialogMessageTitle.visibility = View.GONE
            }
            // 如果内容为空就抛出异常
//           if (TextUtils.isEmpty(mMessageView.getText())) {
//                //throw new IllegalArgumentException("Dialog message not null");
//           }
            return super.create()
        }
    }
}
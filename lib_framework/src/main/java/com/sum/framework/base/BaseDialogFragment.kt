package com.sum.framework.base

import android.app.Dialog
import com.sum.framework.log.LogUtil
import androidx.appcompat.app.AppCompatDialogFragment
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import java.lang.Exception

/**
 * DialogFragment基类
 */
class BaseDialogFragment : AppCompatDialogFragment() {
    private var mDialog: BaseDialog? = null

    companion object {
        private var sShowTag: String? = null
        private var sLastTime: Long = 0
    }

    /**
     * 父类同名方法简化
     */
    fun show(fragment: Fragment?) {
        if (fragment != null && fragment.activity != null
                && !(fragment.requireActivity().isFinishing) && fragment.isAdded
        ) {
            show(fragment.parentFragmentManager, fragment.javaClass.name)
        }
    }

    /**
     * 父类同名方法简化
     */
    fun show(activity: FragmentActivity?) {
        if (activity != null && !activity.isFinishing) {
            show(activity.supportFragmentManager, activity.javaClass.name)
        }
    }

    override fun show(manager: FragmentManager, tag: String?) {
        if (manager != null && !manager.isDestroyed) {
            if (!isRepeatedShow(tag)) {
                try {
                    super.show(manager, tag)
                } catch (e: Exception) {
                    LogUtil.e(e)
                }
            }
        }
    }

    override fun show(transaction: FragmentTransaction, tag: String?): Int {
        if (transaction == null) {
            return -1
        }
        if (!isRepeatedShow(tag)) {
            try {
                return super.show(transaction, tag)
            } catch (e: Exception) {
                LogUtil.e(e)
            }
        }
        return -1
    }

    /**
     * 根据 tag 判断这个 Dialog 是否重复显示了
     *
     * @param tag Tag标记
     */
    protected fun isRepeatedShow(tag: String?): Boolean {
        val result = tag == sShowTag && SystemClock.uptimeMillis() - sLastTime < 500
        sShowTag = tag
        sLastTime = SystemClock.uptimeMillis()
        return result
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return if (mDialog != null) {
            mDialog!!
        } else {
            // 不使用 Dialog，替换成 BaseDialog 对象
            BaseDialog(activity).also { mDialog = it }
        }
    }

    override fun getDialog(): Dialog? {
        return if (mDialog != null) {
            mDialog
        } else super.getDialog()
    }

    fun setDialog(dialog: BaseDialog?) {
        mDialog = dialog
    }


   open class Builder<B : BaseDialog.Builder<B>>(
        /**
         * 获取当前 Activity 对象（仅供子类调用）
         */
        protected val activity: FragmentActivity
    ) : BaseDialog.Builder<B>(activity) {

        /**
         * 获取当前 DialogFragment 对象（仅供子类调用）
         */
        protected var dialogFragment: BaseDialogFragment? = null
            private set

        /**
         * 获取 Fragment 的标记
         */
        protected val fragmentTag: String
            protected get() = javaClass.name

        /*
        // 重写父类的方法（仅供子类调用）
        @Override
        protected void dismiss() {
            try {
                mDialogFragment.dismiss();
            } catch (IllegalStateException e) {
                // java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
                // 这里不能调用 DialogFragment 的 dismiss 方法，因为在前台 show 之后却在后台 dismiss 会导致崩溃
                // 使用 Dialog 的 dismiss 方法却不会出现这种情况，除此之外没有更好的解决方案，故此这句 API 被注释
            }
        }
        */
        override fun show(): BaseDialog {
            val dialog = create()
            try {
                dialogFragment = initDialogFragment()
                dialogFragment?.setDialog(dialog)
                if (activity != null && !activity.isFinishing) {
                    dialogFragment?.show(activity.supportFragmentManager, fragmentTag)
                }
                // 解决 Dialog 设置了而 DialogFragment 没有生效的问题
                dialogFragment?.isCancelable = isCancelable
            } catch (e: Exception) {
                LogUtil.e("@BaseDialog，弹窗show失败$e")
            }
            return dialog
        }

        protected fun initDialogFragment(): BaseDialogFragment {
            return BaseDialogFragment()
        }
    }
}
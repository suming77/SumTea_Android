package com.sum.framework.utils

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import com.sum.framework.loading.CenterLoadingView
import com.sum.framework.R

/**
 * 等待提示框
 */
class LoadingUtils(private val mContext: Context) {
    private var load: CenterLoadingView? = null

    /**
     * 统一耗时操作Dialog
     */
    fun showLoading(txt: String?) {
        if (load == null) {
            load = CenterLoadingView(mContext, R.style.dialog)
            // load.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        if (load!!.isShowing) {
            load!!.dismiss()
        }
        if (!TextUtils.isEmpty(txt)) {
            load!!.setTitle(txt as CharSequence)
        }
        if (mContext is Activity && mContext.isFinishing) {
            return
        }
        load!!.show()
    }

    /**
     * 关闭Dialog
     */
    fun dismissLoading() {
        if (mContext is Activity && mContext.isFinishing) {
            return
        }

        load?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }
}

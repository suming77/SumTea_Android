package com.sum.framework.utils

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import com.sum.framework.loading.CenterLoadingView
import com.sum.framework.R
import com.sum.framework.log.LogUtil

/**
 * 等待提示框
 */
class LoadingUtils(private val mContext: Context) {
    private var loadView: CenterLoadingView? = null

    /**
     * 统一耗时操作Dialog
     */
    fun showLoading(txt: String?) {
        if (loadView == null) {
            loadView = CenterLoadingView(mContext, R.style.dialog)
            // loadView.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        if (loadView?.isShowing == true) {
            loadView?.dismiss()
        }
        if (!TextUtils.isEmpty(txt)) {
            loadView?.setTitle(txt as CharSequence)
        }
        LogUtil.d("showLoading0", tag = "smy")
        if (mContext is Activity && mContext.isFinishing) {
            return
        }
        LogUtil.d("showLoading", tag = "smy")
        loadView?.show()
    }

    /**
     * 关闭Dialog
     */
    fun dismissLoading() {
        if (mContext is Activity && mContext.isFinishing) {
            return
        }

        loadView?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }
}

package com.sum.framework.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import com.sum.framework.R
import com.sum.framework.log.LogUtil
import com.sum.framework.utils.LoadingUtils
import com.sum.framework.toast.TipsToast

/**
 * @author mingyan.su
 * @date   2023/2/20 12:34
 * @desc Fragment基类
 */
abstract class BaseFragment : Fragment() {
    protected var TAG: String? = this::class.java.simpleName

    protected var mIsViewCreate = false

    private val dialogUtils by lazy {
        LoadingUtils(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return getContentView(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mIsViewCreate = true
        initView(view, savedInstanceState)
        initData()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        //手动切换首页tab时，先调用此方法设置fragment的可见性
        if (mIsViewCreate) {
            onFragmentVisible(isVisibleToUser)
        }
    }

    override fun onResume() {
        super.onResume()
        if (userVisibleHint) {
            onFragmentVisible(true)
        }
    }

    override fun onStop() {
        super.onStop()
        if (userVisibleHint) {
            onFragmentVisible(false)
        }
    }

    open fun onFragmentVisible(isVisibleToUser: Boolean) {
        LogUtil.w("onFragmentVisible-${TAG}-isVisibleToUser:$isVisibleToUser")
    }

    /**
     * 设置布局View
     */
    open fun getContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(getLayoutResId(), null)
    }

    /**
     * 初始化视图
     * @return Int 布局id
     * 仅用于不继承BaseDataBindFragment类的传递布局文件
     */
    abstract fun getLayoutResId(): Int

    /**
     * 初始化布局
     * @param savedInstanceState Bundle?
     */
    abstract fun initView(view: View, savedInstanceState: Bundle?)

    /**
     * 初始化数据
     */
    open fun initData() {}

    /**
     * 加载中……弹框
     */
    fun showLoading() {
        showLoading(getString(R.string.default_loading))
    }

    /**
     * 加载提示框
     */
    fun showLoading(msg: String?) {
        dialogUtils.showLoading(msg)
    }

    /**
     * 加载提示框
     */
    fun showLoading(@StringRes res: Int) {
        showLoading(getString(res))
    }

    /**
     * 关闭提示框
     */
    fun dismissLoading() {
        dialogUtils.dismissLoading()
    }

    /**
     * Toast
     * @param msg Toast内容
     */
    fun showToast(msg: String) {
        TipsToast.showTips(msg)
    }

    /**
     * Toast
     * @param resId 字符串id
     */
    fun showToast(@StringRes resId: Int) {
        TipsToast.showTips(resId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
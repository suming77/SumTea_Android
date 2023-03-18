package com.sum.framework.base

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.viewbinding.ViewBinding

/**
 * @author mingyan.su
 * @date   2023/2/26 11:48
 * @desc   dataBinding Activity基类
 */
abstract class BaseDataBindActivity<DB : ViewDataBinding> : BaseActivity() {
    lateinit var mBinding: DB

    override fun setContentLayout() {
        mBinding = DataBindingUtil.setContentView(this, getLayoutResId())
    }
}
package com.sum.tea.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * @author mingyan.su
 * @date   2023/2/26 21:41
 * @desc   dataBinding Fragment 基类
 */
abstract class BaseDataBindFragment<DB : ViewDataBinding> : BaseFragment() {
    lateinit var mBinding: DB

    override fun getContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false)
        return mBinding.root
    }

}
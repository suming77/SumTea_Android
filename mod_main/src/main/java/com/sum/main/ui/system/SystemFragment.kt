package com.sum.main.ui.system

import android.os.Bundle
import android.view.View
import com.sum.framework.base.BaseMvvmFragment
import com.sum.main.R
import com.sum.main.databinding.FragmentSystemBinding

/**
 * @author mingyan.su
 * @date   2023/3/3 8:18
 * @desc   体系
 */
class SystemFragment : BaseMvvmFragment<FragmentSystemBinding, SystemViewModel>() {

    override fun getLayoutResId(): Int = R.layout.fragment_system

    override fun initView(view: View, savedInstanceState: Bundle?) {

    }

}
package com.sum.main.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sum.framework.base.BaseMvvmFragment
import com.sum.framework.log.LogUtil
import com.sum.main.R
import com.sum.main.databinding.FragmentHomeVideoBinding
import com.sum.main.ui.home.viewmodel.HomeViewModel

/**
 * @author mingyan.su
 * @date   2023/3/5 20:11
 * @desc   首页资讯列表
 */
class HomeTabFragment : BaseMvvmFragment<FragmentHomeVideoBinding, HomeViewModel>() {

    override fun getLayoutResId(): Int = R.layout.fragment_home_video

    override fun initView(view: View, savedInstanceState: Bundle?) {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        LogUtil.e("HomeTabFragment:onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}
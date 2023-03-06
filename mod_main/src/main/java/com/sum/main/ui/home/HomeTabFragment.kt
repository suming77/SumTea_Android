package com.sum.main.ui.home

import android.os.Bundle
import android.view.View
import com.sum.framework.base.BaseMvvmFragment
import com.sum.main.R
import com.sum.main.databinding.FragmentHomeVideoBinding

/**
 * @author mingyan.su
 * @date   2023/3/5 20:11
 * @desc   首页资讯列表
 */
class HomeTabFragment : BaseMvvmFragment<FragmentHomeVideoBinding, HomeViewModel>() {
    override fun initView(view: View, savedInstanceState: Bundle?) {

    }

    override fun getLayoutResId(): Int = R.layout.fragment_home_video

}
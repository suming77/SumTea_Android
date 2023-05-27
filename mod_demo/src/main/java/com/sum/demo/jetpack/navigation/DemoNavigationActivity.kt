package com.sum.demo.jetpack.navigation

import android.os.Bundle
import androidx.navigation.Navigation
import com.alibaba.android.arouter.facade.annotation.Route
import com.sum.common.constant.DEMO_ACTIVITY_NAVIGATION
import com.sum.demo.R
import com.sum.demo.databinding.ActivityNavigationBinding
import com.sum.framework.base.BaseDataBindActivity
import com.sum.framework.ext.onClick

/**
 * @author mingyan.su
 * @date   2023/5/12 15:32
 * @desc   Navigation Demo
 */
@Route(path = DEMO_ACTIVITY_NAVIGATION)
class DemoNavigationActivity : BaseDataBindActivity<ActivityNavigationBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
//        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
//        mBinding.tvHomeNav.onClick {
//            navController.navigate(R.id.nav_home)
//        }
//        mBinding.tvMineNav.onClick {
//            navController.navigate(R.id.navi_mine)
//        }
    }
}
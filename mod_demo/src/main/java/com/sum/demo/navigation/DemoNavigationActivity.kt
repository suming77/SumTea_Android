package com.sum.demo.navigation

import android.os.Bundle
import androidx.navigation.findNavController
import com.alibaba.android.arouter.facade.annotation.Route
import com.sum.common.constant.DEMO_ACTIVITY_NAVIGATION
import com.sum.demo.R
import com.sum.demo.databinding.ActivityNavigationBinding
import com.sum.framework.base.BaseDataBindActivity

/**
 * @author mingyan.su
 * @date   2023/5/12 15:32
 * @desc   Navigation Demo
 */
@Route(path = DEMO_ACTIVITY_NAVIGATION)
class DemoNavigationActivity : BaseDataBindActivity<ActivityNavigationBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        // MainFragment 为首次加载的Fragment
        val navController = findNavController(R.id.nav_host_fragment)

        //进入页面
//        navController.navigate(Uri.parse("www.baidu.com"))
//
//        //回退页面
//        navController.navigateUp()
//        navController.popBackStack(
//            R.id.blankFragment,
//            true
//        )//回退到blankFragment页面，inclusive表示是否一同将blankFragment回退
    }
}
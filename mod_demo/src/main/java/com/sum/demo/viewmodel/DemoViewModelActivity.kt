package com.sum.demo.viewmodel

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.sum.common.constant.DEMO_ACTIVITY_VIEWMODEL
import com.sum.demo.databinding.ActivityViewmodelBinding
import com.sum.framework.base.BaseDataBindActivity
import com.sum.framework.ext.onClick
import com.sum.framework.log.LogUtil

/**
 * @author mingyan.su
 * @date   2023/6/14 06:39
 * @desc
 */
@Route(path = DEMO_ACTIVITY_VIEWMODEL)
class DemoViewModelActivity : BaseDataBindActivity<ActivityViewmodelBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        LogUtil.d("onCreate()")
        //val v :HomeViewModel by viewModels()
        //通过ViewModelProvider来获取ViewModel对象
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        //注册监听，监听数据的回调
        viewModel.userLiveData.observe(this, Observer {
            //接收到数据
            dismissLoading()
            mBinding.tvUserInfo.text = it
//            mBinding.tvUserInfo.postDelayed({
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
//            }, 2000)

        })

        mBinding.tvRequestUserInfo.onClick {
            // 请求数据
            showLoading()
            viewModel.getUserInfo()
        }
    }

    override fun onStop() {
        super.onStop()
        LogUtil.d("onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.d("onDestroy()")
    }
}

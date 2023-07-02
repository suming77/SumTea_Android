package com.sum.demo.livedata

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.sum.common.constant.DEMO_ACTIVITY_LIVEDATA
import com.sum.demo.databinding.ActivityLivedataBinding
import com.sum.framework.base.BaseDataBindActivity
import com.sum.framework.ext.onClick
import com.sum.framework.log.LogUtil

/**
 * @author mingyan.su
 * @date   2023/6/28 17:39
 * @desc
 */
@Route(path = DEMO_ACTIVITY_LIVEDATA)
class DemoLiveDataActivity : BaseDataBindActivity<ActivityLivedataBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        LogUtil.d("onCreate()")
        //val v :HomeViewModel by viewModels()
        //通过ViewModelProvider来获取ViewModel对象
        val viewModel = ViewModelProvider(this).get(LiveDataViewModel::class.java)
        //注册监听，监听数据的回调
        viewModel.userLiveData.observe(this, Observer {
            //接收到数据
            dismissLoading()
            mBinding.tvUserInfo.text = it
        })

        mBinding.tvRequestUserInfo.onClick {
            // 请求数据
            showLoading()
            viewModel.getUserInfo()
        }

        mediatorLiveData()
        transformationsMap()
        liveDataBus()
    }

    /**
     * 事件总线
     */
    private fun liveDataBus() {
        LiveDataBus.with<String?>("eventName").observeSticky(this, { data ->
            mBinding.tvUserInfo.text = data
            LogUtil.e("事件总线 数据变化:$data")
        }, true)
    }

    /**
     * MediatorLiveData
     * 将多个LiveData数据源集合起来，可以达到一个组件监听多个LiveData数据变化的目的
     */
    fun mediatorLiveData() {
        val liveData1 = MutableLiveData<String>()
        val liveData2 = MutableLiveData<String>()
        //在创建一个聚合类MediatorLiveData
        val mediatorLiveData = MediatorLiveData<String>()
        //分别把LiveData合并到mediatorLiveData中
        mediatorLiveData.addSource(liveData1, Observer { data ->
            mediatorLiveData.value = data
        })
        mediatorLiveData.addSource(liveData2, Observer { data ->
            mediatorLiveData.value = data
        })

        //数据监听，一旦liveData1或者LiveData2发送了数据，observer便能观察到，以便统一处理更新
        mediatorLiveData.observe(this, Observer { data ->
            LogUtil.e("mediatorLiveData:$data")
            mBinding.tvUserInfo.text = data
        })

        // 模拟发送数据
        mBinding.tvLiveData1.onClick {
            liveData1.postValue("liveData1 苏火火苏火火")
        }
        mBinding.tvLiveData2.onClick {
            liveData2.postValue("liveData2 苏火火苏火火")
        }
    }

    /**
     * 可以对LiveData的数据进行变化，并且返回一个新的LiveData对象
     */
    fun transformationsMap() {
        val mapLiveData = MutableLiveData<Int>()

        //数据转换
        val transformLiveData: LiveData<Int> = Transformations.map(mapLiveData) { input ->
            input * 2
        }

        //使用转换后生成的transformLiveData去观察数据
        transformLiveData.observe(this) { output ->
            mBinding.tvUserInfo.text = output.toString()
            LogUtil.e("transformationsMap 数据转换变化:$output")
        }

        //使用原始的LiveData发送数据
        mBinding.tvMapLiveData.onClick {
            mapLiveData.value = 10
        }
    }
}
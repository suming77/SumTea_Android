package com.sum.demo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author mingyan.su
 * @date   2023/6/14 06:44
 * @desc
 */
class MainViewModel : ViewModel() {
    val userLiveData = MutableLiveData<String>()
    val shareLiveData = MutableLiveData<String>()

    fun getUserInfo() {
        //为了适配因配置变更而导致的页面重建，重复利用之前的数据，加快新页面渲染，不在请求接口
        //如果value不为空，说明这个ViewModel肯定是复用的，因为新建的ViewModel的liveData是不会有数据的
        if (userLiveData.value == null) {
            // 模拟请求接口返回数据
            viewModelScope.launch {
                delay(1000)
                userLiveData.postValue("苏火火 苏火火 苏火火 苏火火 苏火火")
            }
        }
    }
}

//让Application实现ViewModelStoreOwner接口
//class SumApplication : Application(), ViewModelStoreOwner {
//
//    private val appViewModelStore: ViewModelStore by lazy {
//        ViewModelStore()
//    }
//
//    override fun getViewModelStore(): ViewModelStore {
//        return appViewModelStore
//    }
//}
//
//val viewModel = ViewModelProvider(application).get(HiViewModel::class.java)
package com.sum.demo.livedata

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author mingyan.su
 * @date   2023/6/28 17:42
 * @desc
 */
class LiveDataViewModel : ViewModel() {
    val userLiveData = MutableLiveData<String>()

    fun getUserInfo() {
        // 模拟请求接口返回数据
        viewModelScope.launch {
            delay(1000)
            userLiveData.postValue("苏火火 苏火火 苏火火 苏火火 苏火火")
        }
    }


    val shareLiveData = MutableLiveData<String>()

}
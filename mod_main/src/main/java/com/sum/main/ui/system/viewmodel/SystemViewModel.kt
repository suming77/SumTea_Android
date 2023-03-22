package com.sum.main.ui.system.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.sum.common.model.SystemList
import com.sum.framework.toast.TipsToast
import com.sum.network.callback.IApiErrorCallback
import com.sum.network.manager.ApiManager
import com.sum.network.viewmodel.BaseViewModel

/**
 * @author mingyan.su
 * @date   2023/3/3 8:19
 * @desc   体系ViewModel
 */
class SystemViewModel : BaseViewModel() {

    //错误无数据回调
    val errorListLiveData: MutableLiveData<String> = MutableLiveData()

    /**
     * 获取体系列表
     */
    fun getSystemList(): LiveData<MutableList<SystemList>> {
        return liveData {
            val data = safeApiCallWithResult(errorCall = object : IApiErrorCallback {
                override fun onError(code: Int?, error: String?) {
                    TipsToast.showTips(error)
                    errorListLiveData.value = error
                }
            }) {
                ApiManager.api.getSystemList()
            }
            data?.let {
                emit(it)
            } ?: kotlin.run {
                errorListLiveData.value = ""
            }
        }
    }

}
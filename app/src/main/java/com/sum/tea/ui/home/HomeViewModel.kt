package com.sum.tea.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.sum.tea.model.ProjectTabItem
import com.sum.tea.network.ApiManager
import com.sum.tea.network.BaseViewModel
import com.sum.tea.network.error.IApiErrorCallback
import com.sum.tea.ui.HomeRepository
import com.sum.tea.utils.TipsToast
import com.sum.tea.utils.log.LogUtil

class HomeViewModel : BaseViewModel() {
    val tabItem = MutableLiveData<MutableList<ProjectTabItem>>()

    val homeRepository by lazy { HomeRepository() }

    fun getBannerList(): LiveData<MutableList<ProjectTabItem>> {
        launchUI(errorBlock = { code, errorMsg ->
            TipsToast.showTips(errorMsg)
        }) {
            val data = homeRepository.getHomeBannerData()
            data?.let {
                tabItem.value = it
            }
            LogUtil.e("data:$data")
        }
        return tabItem
    }

    fun getBannerList2(): LiveData<MutableList<ProjectTabItem>> {
        return liveData {
            val response = safeApiCall(errorBlock = { code, errorMsg ->
                TipsToast.showTips(errorMsg)
            }) {
                homeRepository.getHomeBannerData()
            }
            response?.let {
                emit(it)
            }
        }
    }

    fun getBannerList3(): LiveData<MutableList<ProjectTabItem>> {
        launchUIWithResult(responseBlock = {
            ApiManager.api.getTabData()
        }, errorCall = object : IApiErrorCallback {
            override fun onError(code: Int?, errorMsg: String?) {
                super.onError(code, errorMsg)
                TipsToast.showTips(errorMsg)
            }
        }) {
            it?.let {
                tabItem.value = it
            }
        }
        return tabItem
    }

    fun getBannerList4(): LiveData<MutableList<ProjectTabItem>> {
        return liveData {
            val data = safeApiCallWithResult(errorCall = object : IApiErrorCallback {
                override fun onError(code: Int?, error: String?) {
                    TipsToast.showTips(error)
                }
            }) {
                ApiManager.api.getTabData()
            }
            data?.let {
                emit(it)
            }
        }
    }
}
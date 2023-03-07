package com.sum.main.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.sum.common.model.Banner
import com.sum.framework.log.LogUtil
import com.sum.framework.toast.TipsToast
import com.sum.main.repository.HomeRepository
import com.sum.network.callback.IApiErrorCallback
import com.sum.network.manager.ApiManager
import com.sum.network.model.ProjectTabItem
import com.sum.network.viewmodel.BaseViewModel

/**
 * @author mingyan.su
 * @date   2023/3/3 8:15
 * @desc   首页ViewModel
 */
class HomeViewModel : BaseViewModel() {
    val tabItem = MutableLiveData<MutableList<ProjectTabItem>>()
    val bannersLiveData = MutableLiveData<MutableList<Banner>?>()

    val homeRepository by lazy { HomeRepository() }

    /**
     * 首页banner
     */
    fun getBannerList(): LiveData<MutableList<Banner>?> {
        launchUI(errorBlock = { code, errorMsg ->
            TipsToast.showTips(errorMsg)
        }) {
            val data = homeRepository.getHomeBanner()
            bannersLiveData.value = data
            LogUtil.e("data:$data")
        }
        return bannersLiveData
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
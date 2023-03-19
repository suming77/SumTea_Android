package com.sum.main.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.sum.common.model.Banner
import com.sum.common.model.HomeInfoList
import com.sum.common.model.ProjectSubList
import com.sum.framework.log.LogUtil
import com.sum.framework.toast.TipsToast
import com.sum.main.repository.HomeRepository
import com.sum.network.callback.IApiErrorCallback
import com.sum.network.manager.ApiManager
import com.sum.common.model.ProjectTabItem
import com.sum.network.viewmodel.BaseViewModel

/**
 * @author mingyan.su
 * @date   2023/3/3 8:15
 * @desc   首页ViewModel
 */
class HomeViewModel : BaseViewModel() {
    val projectItemLiveData = MutableLiveData<ProjectSubList?>()
    val bannersLiveData = MutableLiveData<MutableList<Banner>?>()

    val homeRepository by lazy { HomeRepository() }

    /**
     * 首页banner
     */
    fun getBannerList(): LiveData<MutableList<Banner>?> {
        launchUI(errorBlock = { code, errorMsg ->
            TipsToast.showTips(errorMsg)
            bannersLiveData.value = null
        }) {
            val data = homeRepository.getHomeBanner()
            bannersLiveData.value = data
        }
        return bannersLiveData
    }

    /**
     * 首页列表
     * @param count 页码
     */
    fun getHomeInfoList(count: Int): LiveData<HomeInfoList> {
        return liveData {
            val response = safeApiCall(errorBlock = { code, errorMsg ->
                TipsToast.showTips(errorMsg)
            }) {
                homeRepository.getHomeInfoList(count)
            }
            response?.let {
                emit(it)
            }
        }
    }

    /**
     * 首页Project tab
     */
    fun getProjectTab(): LiveData<MutableList<ProjectTabItem>?> {
        return liveData {
            val response = safeApiCall(errorBlock = { code, errorMsg ->
                TipsToast.showTips(errorMsg)
            }) {
                homeRepository.getProjectTab()
            }
            emit(response)
        }
    }

    /**
     * 获取项目列表数据
     */
    fun getProjectList(count: Int, cid: Int): LiveData<ProjectSubList?> {
        launchUI(errorBlock = { code, errorMsg ->
            TipsToast.showTips(errorMsg)
            projectItemLiveData.value = null
        }) {
            val data = homeRepository.getProjectList(count, cid)
            projectItemLiveData.value = data
        }
        return projectItemLiveData
    }

    /**
     * 首页Project tab
     */
//    fun getProjectTab(): LiveData<MutableList<ProjectTabItem>?> {
//        launchUIWithResult(responseBlock = {
//            ApiManager.api.getProjectTab()
//        }, errorCall = object : IApiErrorCallback {
//            override fun onError(code: Int?, error: String?) {
//                super.onError(code, error)
//                TipsToast.showTips(error)
//                projectTabLiveData.value = null
//            }
//        }) {
//            projectTabLiveData.value = it
//        }
//        return projectTabLiveData
//    }

//    fun getBannerList4(): LiveData<MutableList<ProjectTabItem>> {
//        return liveData {
//            val data = safeApiCallWithResult(errorCall = object : IApiErrorCallback {
//                override fun onError(code: Int?, error: String?) {
//                    TipsToast.showTips(error)
//                }
//            }) {
//                ApiManager.api.getTabData()
//            }
//            data?.let {
//                emit(it)
//            }
//        }
//    }
}
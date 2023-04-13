package com.sum.main.ui.home.viewmodel

import android.content.res.AssetManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.sum.common.constant.FILE_VIDEO_LIST
import com.sum.common.model.ArticleList
import com.sum.common.model.Banner
import com.sum.common.model.ProjectSubInfo
import com.sum.framework.toast.TipsToast
import com.sum.main.repository.HomeRepository
import com.sum.common.model.ProjectTabItem
import com.sum.main.ParseFileUtils
import com.sum.network.flow.requestFlow
import com.sum.network.manager.ApiManager
import com.sum.network.viewmodel.BaseViewModel
import com.sum.room.entity.VideoInfo
import com.sum.room.manager.VideoCacheManager
import kotlinx.coroutines.launch

/**
 * @author mingyan.su
 * @date   2023/3/3 8:15
 * @desc   首页ViewModel
 */
class HomeViewModel : BaseViewModel() {
    val projectItemLiveData = MutableLiveData<MutableList<ProjectSubInfo>?>()
    val bannersLiveData = MutableLiveData<MutableList<Banner>?>()

    val homeRepository by lazy { HomeRepository() }

    /**
     * 首页banner
     */
    fun getBannerList(): LiveData<MutableList<Banner>?> {
//        launchUI(errorBlock = { code, errorMsg ->
//            TipsToast.showTips(errorMsg)
//            bannersLiveData.value = null
//        }) {
//            val data = homeRepository.getHomeBanner()
//            bannersLiveData.value = data
//        }
//        return bannersLiveData
        //通过flow来请求
        viewModelScope.launch {
            val data = requestFlow(requestCall = {
                ApiManager.api.getHomeBanner()
            }, errorBlock = { code, error ->
                TipsToast.showTips(error)
                bannersLiveData.value = null
            })
            bannersLiveData.value = data
        }
        return bannersLiveData
    }

    /**
     * 首页列表
     * @param page 页码
     */
    fun getHomeInfoList(page: Int): LiveData<ArticleList> {
        return liveData {
            val response = safeApiCall(errorBlock = { code, errorMsg ->
                TipsToast.showTips(errorMsg)
            }) {
                homeRepository.getHomeInfoList(page)
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
     * @param page
     * @param cid
     */
    fun getProjectList(page: Int, cid: Int): LiveData<MutableList<ProjectSubInfo>?> {
        launchUI(errorBlock = { code, errorMsg ->
            TipsToast.showTips(errorMsg)
            projectItemLiveData.value = null
        }) {
            val data = homeRepository.getProjectList(page, cid)
            projectItemLiveData.value = data?.datas
        }
        return projectItemLiveData
    }

    /**
     * 首页视频列表
     */
    fun getVideoList(assetManager: AssetManager): LiveData<MutableList<VideoInfo>?> {
        return liveData {
            val response = safeApiCall(errorBlock = { code, errorMsg ->
                TipsToast.showTips(errorMsg)
            }) {
                var list = homeRepository.getVideoListCache()
                //缓存为空则创建视频数据
                if (list.isNullOrEmpty()) {
                    list = ParseFileUtils.parseAssetsFile(assetManager, FILE_VIDEO_LIST)
                    VideoCacheManager.saveVideoList(list)
                }
                list
            }

            emit(response)
        }
    }

}
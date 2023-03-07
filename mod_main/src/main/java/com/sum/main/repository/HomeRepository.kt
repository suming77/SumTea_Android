package com.sum.main.repository

import com.sum.common.model.Banner
import com.sum.network.manager.ApiManager
import com.sum.network.model.ProjectTabItem
import com.sum.network.repository.BaseRepository

/**
 * @author mingyan.su
 * @date   2023/2/27 18:58
 * @desc   首页请求仓库
 */
class HomeRepository : BaseRepository() {
    /**
     * 首页Banner
     */
    suspend fun getHomeBanner(): MutableList<Banner>? {
        return requestResponse {
            ApiManager.api.getHomeBanner()
        }
    }

    suspend fun getHomeBannerData(): MutableList<ProjectTabItem>? {
        return requestResponse {
            ApiManager.api.getTabData()
        }
    }
}
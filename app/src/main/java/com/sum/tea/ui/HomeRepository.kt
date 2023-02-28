package com.sum.tea.ui

import com.sum.tea.model.ProjectTabItem
import com.sum.tea.network.ApiManager
import com.sum.tea.network.BaseRepository

/**
 * @author mingyan.su
 * @date   2023/2/27 18:58
 * @desc   首页请求仓库
 */
class HomeRepository : BaseRepository() {
    suspend fun getHomeBannerData(): MutableList<ProjectTabItem>? {
        return requestResponse {
            ApiManager.api.getTabData()
        }
    }
}
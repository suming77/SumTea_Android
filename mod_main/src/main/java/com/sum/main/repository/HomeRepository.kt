package com.sum.main.repository

import com.sum.common.model.ArticleList
import com.sum.common.model.Banner
import com.sum.common.model.ProjectSubList
import com.sum.network.manager.ApiManager
import com.sum.common.model.ProjectTabItem
import com.sum.network.repository.BaseRepository
import com.sum.room.entity.VideoInfo
import com.sum.room.manager.VideoCacheManager

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

    /**
     * 首页列表
     * @param page 页码
     * @param pageSize 每页数量
     */
    suspend fun getHomeInfoList(page: Int): ArticleList? {
        return requestResponse {
            ApiManager.api.getHomeList(page, 20)
        }
    }

    /**
     * 项目tab
     */
    suspend fun getProjectTab(): MutableList<ProjectTabItem>? {
        return requestResponse {
            ApiManager.api.getProjectTab()
        }
    }

    /**
     * 项目列表
     * @param page
     * @param cid
     */
    suspend fun getProjectList(page: Int, cid: Int): ProjectSubList? {
        return requestResponse {
            ApiManager.api.getProjectList(page, cid)
        }
    }

    /**
     * 获取视频列表数据
     */
    suspend fun getVideoListCache(): MutableList<VideoInfo>? {
        return VideoCacheManager.getVideoList()
    }
}
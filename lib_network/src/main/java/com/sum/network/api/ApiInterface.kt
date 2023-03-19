package com.sum.network.api

import com.sum.common.model.Banner
import com.sum.common.model.CategoryItem
import com.sum.common.model.HomeInfoList
import com.sum.common.model.ProjectSubList
import com.sum.network.response.BaseResponse
import com.sum.common.model.ProjectTabItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author mingyan.su
 * @date   2023/2/27 19:07
 * @desc   API接口类
 */
interface ApiInterface {
    /**
     * 首页轮播图
     */
    @GET("/banner/json")
    suspend fun getHomeBanner(): BaseResponse<MutableList<Banner>>?

    /**
     * 首页资讯
     * @param count    页码
     * @param pageSize 每页数量
     */
    @GET("/article/list/{count}/json")
    suspend fun getHomeList(
        @Path("count") count: Int,
        @Query("page_size") pageSize: Int
    ): BaseResponse<HomeInfoList>

    /**
     * 首页项目
     */
    @GET("/project/tree/json")
    suspend fun getProjectTab(): BaseResponse<MutableList<ProjectTabItem>>?

    /**
     * 项目二级列表
     * @param count  分页数量
     * @param cid    项目分类的id
     */
    @GET("/project/list/{count}/json")
    suspend fun getProjectList(
        @Path("count") count: Int,
        @Query("cid") cid: Int
    ): BaseResponse<ProjectSubList>?

    /**
     * 分类列表
     */
    @GET("/navi/json")
    suspend fun getCategoryData(): BaseResponse<MutableList<CategoryItem>>?

//    @GET("/project/tree/json")
//    suspend fun getTabData(): BaseResponse<MutableList<ProjectTabItem>>?
}
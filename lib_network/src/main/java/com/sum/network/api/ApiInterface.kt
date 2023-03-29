package com.sum.network.api

import com.sum.common.model.ArticleList
import com.sum.common.model.Banner
import com.sum.common.model.CategoryItem
import com.sum.common.model.ProjectSubList
import com.sum.network.response.BaseResponse
import com.sum.common.model.ProjectTabItem
import com.sum.common.model.SystemList
import com.sum.common.model.User
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
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
     * @param page    页码
     * @param pageSize 每页数量
     */
    @GET("/article/list/{page}/json")
    suspend fun getHomeList(
        @Path("page") page: Int,
        @Query("page_size") pageSize: Int
    ): BaseResponse<ArticleList>?

    /**
     * 首页项目
     */
    @GET("/project/tree/json")
    suspend fun getProjectTab(): BaseResponse<MutableList<ProjectTabItem>>?

    /**
     * 项目二级列表
     * @param page  分页数量
     * @param cid    项目分类的id
     */
    @GET("/project/list/{page}/json")
    suspend fun getProjectList(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): BaseResponse<ProjectSubList>?

    /**
     * 分类列表
     */
    @GET("/navi/json")
    suspend fun getCategoryData(): BaseResponse<MutableList<CategoryItem>>?

    /**
     * 获取体系列表
     */
    @GET("/tree/json")
    suspend fun getSystemList(): BaseResponse<MutableList<SystemList>>?

    /**
     * 项目二级列表
     * @param page  分页数量
     * @param cid    项目分类的id
     */
    @GET("/article/list/{page}/json")
    suspend fun getArticleList(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): BaseResponse<ArticleList?>?

    /**
     * 登录
     * @param username  用户名
     * @param password  密码
     */
    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String,
    ): BaseResponse<User?>?

    /**
     * 注册
     * @param username  用户名
     * @param password  密码
     * @param repassword  确认密码
     */
    @FormUrlEncoded
    @POST("/user/register")
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String
    ): BaseResponse<User?>?

    /**
     * 登出
     * @param username  用户名
     * @param password  密码
     * @param repassword  确认密码
     */
    @GET("/user/logout/json")
    suspend fun logout(): BaseResponse<Any?>?

    /**
     * 我的收藏列表
     * @param page  页码
     */
    @GET("/lg/collect/list/{page}/json")
    suspend fun getCollectList(
        @Path("page") page: Int
    ): BaseResponse<ArticleList?>?

    /**
     * 收藏站内文章
     * @param id 文章id
     */
    @POST("lg/collect/{id}/json")
    suspend fun collectArticle(
        @Path("id") id: Int
    ): BaseResponse<Any>?

    /**
     * 收藏站外文章
     * @param title 标题
     * @param author 作者
     * @param link 链接
     */
    @POST("lg/collect/add/json")
    @FormUrlEncoded
    suspend fun collectOutsideArticle(
        @Field("title") title: String,
        @Field("author") author: String,
        @Field("link") link: String
    ): BaseResponse<Any>?

    /**
     * 文章列表中取消收藏文章
     * @param id
     */
    @POST("lg/uncollect_originId/{id}/json")
    suspend fun cancelCollectArticle(
        @Path("id") id: Int
    ): BaseResponse<Any>?

    /**
     * 收藏列表中取消收藏文章
     * @param id
     * @param originId
     */
    @POST("lg/uncollect/{id}/json")
    @FormUrlEncoded
    suspend fun cancelMyCollect(
        @Path("id") id: Int,
        @Field("originId") originId: Int = -1
    ): BaseResponse<Any>?
}
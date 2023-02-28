package com.sum.tea.api

import com.sum.tea.model.ProjectTabItem
import com.sum.tea.network.BaseResponse
import retrofit2.http.GET

/**
 * @author mingyan.su
 * @date   2023/2/27 19:07
 * @desc
 */
interface ApiInterface {
    @GET("/project/tree/json")
    suspend fun getTabData(): BaseResponse<MutableList<ProjectTabItem>>?
}
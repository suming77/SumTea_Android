package com.sum.network.api

import com.sum.common.model.MercariModel
import com.sum.network.response.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface MercicaAPI {
    @GET
    suspend fun getFeeds(@Url url: String?): MutableList<MercariModel>?
    @GET
    suspend fun getFeedsWithBaseResponse(@Url url: String?): BaseResponse<MutableList<MercariModel>?>?
}

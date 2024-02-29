package com.sum.common.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MercariModel(
    @SerializedName("id") @Expose val id: String? = null,

    @SerializedName("name") @Expose val name: String? = null,

    @SerializedName("status") @Expose val status: String? = null,

    @SerializedName("num_likes") @Expose val numLikes: Int? = null,

    @SerializedName("num_comments") @Expose val numComments: Int? = null,

    @SerializedName("price") @Expose val price: Int? = null,

    @SerializedName("photo") @Expose val photo: String? = null
)
package com.sum.common.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author mingyan.su
 * @date   2023/3/24 18:46
 * @desc   用户信息
 */
@Parcelize
data class User(
    val id: Int? = 0,
    val username: String?,
    val nickname: String?,
    val token: String?,
    val icon: String? = "",
    val email: String? = "",
    val password: String?,
    val signature: String?
) : Parcelable
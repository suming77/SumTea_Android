package com.sum.common.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author mingyan.su
 * @date   2023/3/21 7:52
 * @desc   体系
 */
data class SystemList(
    val id: Int,
    val courseId: Int,
    val name: String,       //一级名称
    val children: MutableList<SystemSecondList>,
    val visible: Int
)

/**
 * 二级列表
 */
@Parcelize
data class SystemSecondList(
    val id: Int,
    val name: String, // 二级名称
    val visible: Int
) : Parcelable
package com.sum.common.model

/**
 * @author mingyan.su
 * @date   2023/2/27 21:19
 * @desc   项目信息
 */
data class ProjectTabItem(val id: Int, val name: String)

/**
 * 项目二级列表
 */
data class ProjectSubList(val datas: MutableList<ProjectSubInfo>)

/**
 * 项目列表信息
 */
data class ProjectSubInfo(
    val id: Int?,
    val author: String?,
    val desc: String?,
    val envelopePic: String?,
    val link: String?,
    val niceDate: String?,
    val title: String?,
    val shareUser: String?
)
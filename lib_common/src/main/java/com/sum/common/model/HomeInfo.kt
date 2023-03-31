package com.sum.common.model

/**
 * @author mingyan.su
 * @date   2023/3/8 08:08
 * @desc   首页资讯列表
 */
data class HomeInfoList(
    val curPage: Int = 0,
    val offset: Int = 0,
    val pageCount: Int = 0,
    val size: Int = 0,
    val total: Int = 0,
    val over: Boolean = false,
    val datas: MutableList<HomeInfo>? = mutableListOf()
)

/**
 * 首页列表item
 */
data class HomeInfo(
    val id: Int?,
    val title: String?,
    val desc: String?,
    val link: String?,
    val niceDate: String?,
    val author: String?,
    val shareUser: String?,
    val chapterName: String?
)
package com.sum.common.model

/**
 * @author mingyan.su
 * @date   2023/3/8 08:08
 * @desc   首页资讯列表
 */
data class HomeInfoList(
    val curPage: Int,
    val offset: Int,
    val pageCount: Int,
    val size: Int,
    val total: Int,
    val over: Boolean,
    val datas: MutableList<HomeInfo>,
)

/**
 * 首页列表item
 */
data class HomeInfo(
    val id: Int,
    val title: String,
    val desc: String,
    val link: String,
    val niceDate: String,
    val author: String,
    val shareUser: String,
    val chapterName: String
)
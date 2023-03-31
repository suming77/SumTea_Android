package com.sum.common.model

/**
 * @author mingyan.su
 * @date   2023/3/21 23:16
 * @desc   文章info
 */
data class ArticleList(
    val curPage: Int? = 0,
    val offset: Int? = 0,
    val size: Int? = 0,
    val total: Int? = 0,
    val pageCount: Int? = 0,
    val datas: MutableList<ArticleInfo>? = mutableListOf()
)

data class ArticleInfo(
    val id: Int,
    val userId: Int,
    val courseId: Int?,
    val originId: Int?,
    var collect: Boolean? = false,
    val title: String?,
    val desc: String?,
    val link: String?,
    val zan: Int?,
    val niceShareDate: String?,
    val niceDate: String?,
    val publishTime: Long,
    val shareUser: String?,
    val author: String? = "",
    val superChapterName: String? = "",
    val chapterName: String?,
    val tags: MutableList<Any>? = arrayListOf(),
)
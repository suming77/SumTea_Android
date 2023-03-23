package com.sum.common.model

/**
 * @author mingyan.su
 * @date   2023/3/21 23:16
 * @desc   文章info
 */
data class ArticleList(
    val curPage: Int,
    val offset: Int,
    val size: Int,
    val total: Int,
    val pageCount: Int,
    val datas: MutableList<ArticleInfo>
)

data class ArticleInfo(
    val id: String,
    val userId: String,
    val courseId: String,
    val collect: Boolean,
    val title: String?,
    val desc: String?,
    val link: String?,
    val zan: Int?,
    val niceShareDate: String?,
    val publishTime: Long,
    val shareUser: String?,
    val author: String?,
    val superChapterName: String?,
    val chapterName: String?,
    val tags: MutableList<Any>? = arrayListOf(),
)
package com.sum.common.model

/**
 * @author mingyan.su
 * @date   2023/3/8 23:11
 * @desc   视频列表信息
 */
data class VideoInfo(
    val title: String,
    val desc: String,
    val authorName: String,
    val playUrl: String,
    val image: String,
    val collectionCount: String
)
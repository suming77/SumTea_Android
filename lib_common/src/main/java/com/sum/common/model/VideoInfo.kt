package com.sum.common.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author mingyan.su
 * @date   2023/3/8 23:11
 * @desc   视频列表信息
 */
@Parcelize
data class VideoInfo(
    val title: String?,
    val desc: String?,
    val authorName: String?,
    var playUrl: String?,
    val imageUrl: String?,
    val collectionCount: String?
) : Parcelable
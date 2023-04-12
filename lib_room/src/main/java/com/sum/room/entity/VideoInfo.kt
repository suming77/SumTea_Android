package com.sum.room.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.sum.common.constant.TABLE_VIDEO_LIST
import kotlinx.parcelize.Parcelize

/**
 * @author mingyan.su
 * @date   2023/3/8 23:11
 * @desc   视频列表信息
 */
@Parcelize
// 定义数据表，指定表名，如果不指定表名则默认为类名
@Entity(tableName = TABLE_VIDEO_LIST)
data class VideoInfo(
    //必须至少要有一个主键，不能为空，autoGenerate表示cache_key的值是否由数据库自动生成
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    //如果想改变title这个字段在数据库中的名称，可以使用ColumnInfo来改变，defaultValue指定默认值
    @ColumnInfo(name = "title", defaultValue = "")
    var title: String?,

    @ColumnInfo(name = "desc")
    var desc: String?,

    @ColumnInfo(name = "authorName")
    var authorName: String?,

    @ColumnInfo(name = "playUrl")
    var playUrl: String?,

    @ColumnInfo(name = "imageUrl")
    var imageUrl: String?,

    //部分字段不想映射到数据库表中，添加Ignore注解忽略，就不会成为数据库表中的类名
    @Ignore
    var collectionCount: String?
) :Parcelable{
    constructor() : this(0, "", "", "", "", "", "");
}
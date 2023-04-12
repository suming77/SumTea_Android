package com.sum.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sum.common.constant.TABLE_VIDEO_LIST
import com.sum.room.entity.VideoInfo

/**
 * @author mingyan.su
 * @date   2023/4/11 15:29
 * @desc   定义数据操作的方法，增删改查 一般为interface
 */
@Dao
interface VideoListCacheDao {
    /**
     * 插入单个数据
     * entity操作的表，OnConflictStrategy冲突策略，
     * ABORT:终止本次操作
     * IGNORE:忽略本次操作，也终止
     * REPLACE:覆盖老数据
     */
    @Insert(entity = VideoInfo::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(videoInfo: VideoInfo) //这条VideoInfo对象新的数据，id已经存在了这个表当中，此时就会发生冲突

    /**
     * 插入多个数据
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(videoList: MutableList<VideoInfo>)

    /**
     * 根据id删除数据
     */
    @Query("DELETE FROM $TABLE_VIDEO_LIST WHERE id=:id")
    fun deleteById(id: Long)

    /**
     * 删除指定item
     * 使用主键将传递的实体实例与数据库中的行进行匹配。如果没有具有相同主键的行，则不会进行任何更改
     */
    @Delete
    fun delete(videoInfo: VideoInfo): Int

    /**
     * 删除表中所有数据
     */
    @Query("DELETE FROM $TABLE_VIDEO_LIST")
    suspend fun deleteAll()

    /**
     * 更新某个item
     * 不指定的entity也可以，会根据你传入的参数对象来找到你要操作的那张表
     */
    @Update
    fun update(videoInfo: VideoInfo): Int

    /**
     * 根据id更新数据
     */
    @Query("UPDATE $TABLE_VIDEO_LIST SET title=:title WHERE id=:id")
    fun updateById(id: Long, title: String)

    /**
     * 查询所有数据
     */
    @Query("SELECT * FROM $TABLE_VIDEO_LIST")
    fun queryAll(): MutableList<VideoInfo>?

    /**
     * 根据id查询某个数据
     */
    @Query("SELECT * FROM $TABLE_VIDEO_LIST WHERE id=:id")
    fun query(id: Long): VideoInfo?

    /**
     * 通过LiveData以观察者的形式获取数据库数据，可以避免不必要的NPE，
     * 可以监听数据库表中的数据的变化，也可以和RXJava的Observer使用
     * 一旦发生了insert，update，delete，room会自动读取表中最新的数据，发送给UI层，刷新页面
     * 不要使用MutableLiveData和suspend 会报错
     */
    @Query("SELECT * FROM $TABLE_VIDEO_LIST")
    fun queryAllLiveData(): LiveData<List<VideoInfo>>
}
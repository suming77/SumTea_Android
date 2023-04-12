package com.sum.room.manager

import androidx.lifecycle.LiveData
import com.sum.room.database.SumDataBase
import com.sum.room.entity.VideoInfo
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 * @author mingyan.su
 * @date   2023/4/11 16:57
 * @desc   视频缓存管理类
 */
object VideoCacheManager {

    private val videoDao by lazy { SumDataBase.getInstance().videoListDao() }

    /**
     * 保存视频列表数据
     */
    suspend fun saveVideoList(list: MutableList<VideoInfo>) {
        videoDao.insertAll(list)
    }

    /**
     * 插入一个视频数据
     * @param videoInfo
     */
    fun insertVideoInfo(videoInfo: VideoInfo) {
        MainScope().launch {
            videoDao.insert(videoInfo)
        }
    }

    /**
     * 根据id删除视频Item
     * @param id
     */
    fun deleteVideoItem(id: Long) {
        MainScope().launch {
            videoDao.deleteById(id)
        }
    }

    /**
     * 根据videoInfo删除视频Item
     * @param videoInfo
     */
    fun deleteVideoItem(videoInfo: VideoInfo) {
        MainScope().launch {
            videoDao.delete(videoInfo)
        }
    }

    /**
     * 根据videoInfo更新视频Item
     * @param videoInfo
     */
    fun updateVideoItem(videoInfo: VideoInfo) {
        MainScope().launch {
            videoDao.update(videoInfo)
        }
    }

    /**
     * 根据id更新视频title
     * @param id
     * @param title
     */
    fun updateVideoItem(id: Long, title: String) {
        MainScope().launch {
            videoDao.updateById(id, title)
        }
    }

    /**
     * 根据id获取视频Item
     * @param id
     */
    fun getVideoItem(id: Long): VideoInfo? {
        return videoDao.query(id)
    }

    /**
     * 获取视频列表
     */
    fun getVideoList(): MutableList<VideoInfo>? {
        return videoDao.queryAll()
    }

    /**
     * 获取视频列表
     * @return MutableLiveData
     */
    fun getVideoListLiveData(): LiveData<List<VideoInfo>> {
        return videoDao.queryAllLiveData()
    }

    /**
     * 清除视频列表缓存
     * @param callBack
     */
    fun clearVideoList(callBack: (String) -> Unit) {
        MainScope().launch {
            videoDao.deleteAll()
            callBack("删除成功")
        }
    }
}
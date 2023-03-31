package com.sum.search.manager

import com.sum.common.constant.SEARCH_HISTORY_INFO
import com.sum.framework.ext.toBeanOrNull
import com.sum.framework.ext.toJson
import com.tencent.mmkv.MMKV

/**
 * @author mingyan.su
 * @date   2023/3/30 23:47
 * @desc   搜索管理类
 */
object SearchManager {

    private var mmkv = MMKV.defaultMMKV()

    /**
     * 保存搜索历史
     * @param searchList
     */
    fun saveSearchHistory(searchList: MutableList<String>) {
        val histories = getSearchHistory() ?: mutableListOf()
        histories.addAll(searchList)
        val duplicateRemoval = histories.distinct()
        mmkv.encode(SEARCH_HISTORY_INFO, duplicateRemoval.toJson(true))
    }

    /**
     * 添加搜索历史
     * @param keyWord
     */
    fun addSearchHistory(keyWord: String) {
        val histories = getSearchHistory() ?: mutableListOf()
        histories.add(keyWord)
        val duplicateRemoval = histories.distinct()
        mmkv.encode(SEARCH_HISTORY_INFO, duplicateRemoval.toJson())
    }

    /**
     * 获取搜索历史
     * @return MutableList
     */
    fun getSearchHistory(): MutableList<String>? {
        return mmkv.decodeString(SEARCH_HISTORY_INFO)?.toBeanOrNull()
    }

    /**
     * 清除搜索历史数据
     */
    fun clearSearchHistory() {
        mmkv.encode(SEARCH_HISTORY_INFO, "")
    }
}
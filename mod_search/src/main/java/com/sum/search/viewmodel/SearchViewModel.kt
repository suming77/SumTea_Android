package com.sum.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.sum.common.model.ArticleInfo
import com.sum.common.model.HotSearch
import com.sum.framework.toast.TipsToast
import com.sum.network.viewmodel.BaseViewModel
import com.sum.search.SearchRepository

/**
 * @author mingyan.su
 * @date   2023/3/29 18:23
 * @desc   搜索ViewModel
 */
class SearchViewModel : BaseViewModel() {
    val hotSearchLiveData = MutableLiveData<MutableList<HotSearch>?>()
    val searchResultLiveData = MutableLiveData<MutableList<ArticleInfo>?>()
    val collectArticleLivedata = MutableLiveData<Boolean?>()

    val repository by lazy { SearchRepository() }

    /**
     * 搜索热词
     */
    fun getHotSearchData(): LiveData<MutableList<HotSearch>?> {
        launchUI(errorBlock = { code, error ->
            TipsToast.showTips(error)
            hotSearchLiveData.value = null
        }, responseBlock = {
            val data = repository.getHotSearchData()
            hotSearchLiveData.value = data
        })
        return hotSearchLiveData
    }

    /**
     * 搜索结果
     * @param page   页码
     * @param keyWord  关键词，支持多个，空格分开
     */
    fun searchResult(
        page: Int,
        keyWord: String
    ): LiveData<MutableList<ArticleInfo>?> {
        launchUI(errorBlock = { code, error ->
            TipsToast.showTips(error)
            searchResultLiveData.value = null
        }, responseBlock = {
            val data = repository.searchResult(page, keyWord)
            searchResultLiveData.value = data?.datas
        })
        return searchResultLiveData
    }

    /**
     * 收藏 or 取消收藏站内文章
     * @param id  文章id
     * @param isCollect 是否收藏
     */
    fun collectArticle(id: Int, isCollect: Boolean): LiveData<Boolean?> {
        launchUI(errorBlock = { code, error ->
            TipsToast.showTips(error)
            collectArticleLivedata.value = null
        }, responseBlock = {
            repository.collectArticle(id, isCollect)
            collectArticleLivedata.value = isCollect
        })
        return collectArticleLivedata
    }

}
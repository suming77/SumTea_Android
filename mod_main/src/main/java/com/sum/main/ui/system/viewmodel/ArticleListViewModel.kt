package com.sum.main.ui.system.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sum.common.model.ArticleInfo
import com.sum.network.callback.IApiErrorCallback
import com.sum.network.manager.ApiManager
import com.sum.network.viewmodel.BaseViewModel

/**
 * @author mingyan.su
 * @date   2023/3/21 18:27
 * @desc   文章列表viewModel
 */
class ArticleListViewModel : BaseViewModel() {
    val articleListLiveData: MutableLiveData<MutableList<ArticleInfo>?> = MutableLiveData()

    /**
     * 项目二级列表
     * @param page  分页数量
     * @param cid   项目分类的id
     */
    fun getArticleList(page: Int, cId: Int): LiveData<MutableList<ArticleInfo>?> {
        launchUIWithResult(responseBlock = {
            ApiManager.api.getArticleList(page, cId)
        }, errorCall = object : IApiErrorCallback {
            override fun onError(code: Int?, error: String?) {
                super.onError(code, error)
                articleListLiveData.value = null
            }
        }, successBlock = {
            if (it == null || it.datas.isEmpty()) {
                articleListLiveData.value = null
            } else {
                articleListLiveData.value = it.datas
            }
        })
        return articleListLiveData
    }
}
package com.sum.main.ui.system.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sum.common.model.ArticleInfo
import com.sum.network.callback.IApiErrorCallback
import com.sum.network.error.ERROR
import com.sum.network.manager.ApiManager
import com.sum.network.viewmodel.BaseViewModel

/**
 * @author mingyan.su
 * @date   2023/3/21 18:27
 * @desc   文章列表viewModel
 */
class ArticleListViewModel : BaseViewModel() {
    val articleListLiveData: MutableLiveData<MutableList<ArticleInfo>?> = MutableLiveData()
    val collectLiveData: MutableLiveData<Int?> = MutableLiveData()
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
            if (it == null || it.datas.isNullOrEmpty()) {
                articleListLiveData.value = null
            } else {
                articleListLiveData.value = it.datas
            }
        })
        return articleListLiveData
    }

    /**
     * 收藏 or 取消收藏站内文章
     * @param id  文章id
     * @param isCollect 是否收藏
     */
    fun collectArticle(id: Int, isCollect: Boolean): LiveData<Int?> {
        launchUIWithResult(responseBlock = {
            if (!isCollect) {
                //收藏站内文章
                ApiManager.api.collectArticle(id)
            } else {
                //取消收藏站内文章
                ApiManager.api.cancelCollectArticle(id)
            }
        }, errorCall = object : IApiErrorCallback {
            override fun onError(code: Int?, error: String?) {
                super.onError(code, error)
                collectLiveData.value = null
            }

            override fun onLoginFail(code: Int?, error: String?) {
                super.onLoginFail(code, error)
                collectLiveData.value = ERROR.UNLOGIN.code
            }
        }) {
            collectLiveData.value = if (isCollect) 0 else 1
        }
        return collectLiveData
    }
}
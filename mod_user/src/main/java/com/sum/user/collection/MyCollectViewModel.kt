package com.sum.user.collection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.sum.common.model.ArticleInfo
import com.sum.framework.toast.TipsToast
import com.sum.network.callback.IApiErrorCallback
import com.sum.network.manager.ApiManager
import com.sum.network.viewmodel.BaseViewModel

/**
 * @author mingyan.su
 * @date   2023/3/24 18:29
 * @desc   我的收藏
 */
class MyCollectViewModel : BaseViewModel() {
    var collectListLiveData = MutableLiveData<MutableList<ArticleInfo>?>()

    /**
     * 我的收藏列表
     * @param page  页码
     */
    fun getMyCollectList(page: Int) {
        launchUIWithResult(errorCall = object : IApiErrorCallback {
            override fun onError(code: Int?, error: String?) {
                TipsToast.showTips(error)
                collectListLiveData.value = null
            }
        }, responseBlock = {
            ApiManager.api.getCollectList(page)
        }) {
            collectListLiveData.value = it?.datas
        }
    }

    /**
     * 收藏站内文章
     * @param id  文章id
     * @param originId 收藏之前的那篇文章本身的id
     */
    fun collectArticle(id: Int, originId: Int): LiveData<Any?> {
        return liveData {
            val data = safeApiCallWithResult(errorCall = object : IApiErrorCallback {
                override fun onError(code: Int?, error: String?) {
                    super.onError(code, error)
                }
            }) {
                ApiManager.api.cancelMyCollect(id, originId)
            }
            emit(data)
        }
    }
}
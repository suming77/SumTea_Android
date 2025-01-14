package com.sum.main.ui.mercari.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sum.common.model.MercariModel
import com.sum.network.manager.ApiManager
import com.sum.network.viewmodel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MercariViewModel : BaseViewModel() {
    val mercicaListLiveData: MutableLiveData<MutableList<MercariModel>?> = MutableLiveData()

    fun getArticleList(): LiveData<MutableList<MercariModel>?> {
//        launchUIWithResult(responseBlock = {
//            ApiManager.apiMercica.getFeeds("https://s3-ap-northeast-1.amazonaws.com/m-et/Android/json/all.json")
//        }, errorCall = object : IApiErrorCallback {
//            override fun onError(code: Int?, error: String?) {
//                super.onError(code, error)
//                mercicaListLiveData.value = null
//            }
//        }, successBlock = {
//            if (it.isNullOrEmpty()) {
//                mercicaListLiveData.value = null
//            } else {
//                mercicaListLiveData.value = it
//            }
//        })
//        return mercicaListLiveData

        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                val feeds =
                    ApiManager.apiMercica.getFeeds("https://s3-ap-northeast-1.amazonaws.com/m-et/Android/json/all.json");

                withContext(Dispatchers.Main) {
                    mercicaListLiveData.value = feeds
                }
            }
        }

        return mercicaListLiveData
    }
}
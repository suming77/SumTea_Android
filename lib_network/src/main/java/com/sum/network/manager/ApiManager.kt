package com.sum.network.manager

import com.sum.network.api.ApiInterface
import com.sum.network.api.MercicaAPI

/**
 * @author mingyan.su
 * @date   2023/2/27 21:14
 * @desc   API管理器
 */
object ApiManager {
    val api by lazy { HttpManager.create(ApiInterface::class.java) }
    val apiMercica by lazy { HttpManager.create(MercicaAPI::class.java) }
}
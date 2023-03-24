package com.sum.login.login

import com.sum.common.model.User
import com.sum.network.manager.ApiManager
import com.sum.network.repository.BaseRepository

/**
 * @author mingyan.su
 * @date   2023/3/24 18:36
 * @desc   登录仓库
 */
class LoginRepository : BaseRepository() {

    /**
     * 登录
     * @param username  用户名
     * @param password  密码
     */
    suspend fun login(username: String, password: String): User? {
        return requestResponse {
            ApiManager.api.login(username, password)
        }
    }

    /**
     * 注册
     * @param username  用户名
     * @param password  密码
     * @param repassword  确认密码
     */
    suspend fun register(username: String, password: String, repassword: String): User? {
        return requestResponse {
            ApiManager.api.register(username, password, repassword)
        }
    }
}
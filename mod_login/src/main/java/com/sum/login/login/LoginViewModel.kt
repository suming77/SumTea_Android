package com.sum.login.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.sum.common.model.User
import com.sum.framework.toast.TipsToast
import com.sum.network.viewmodel.BaseViewModel

/**
 * @author mingyan.su
 * @date   2023/3/25 16:38
 * @desc   登录viewModel
 */
class LoginViewModel : BaseViewModel() {
    val loginLiveData = MutableLiveData<User?>()
    val registerLiveData = MutableLiveData<User?>()
    val loginRepository by lazy { LoginRepository() }

    /**
     * 登录
     * @param username  用户名
     * @param password  密码
     * @return
     */
    fun login(username: String, password: String): LiveData<User?> {
        launchUI(errorBlock = { code, error ->
            TipsToast.showTips(error)
            loginLiveData.value = null
        }) {
            val data = loginRepository.login(username, password)
            loginLiveData.value = data
        }
        return loginLiveData
    }

    /**
     * 注册
     * @param username  用户名
     * @param password  密码
     * @param repassword  确认密码
     * @return
     */
    fun register(username: String, password: String, repassword: String): LiveData<User?> {
        launchUI(errorBlock = { code, error ->
            TipsToast.showTips(error)
            registerLiveData.value = null
        }) {
            val data = loginRepository.register(username, password, repassword)
            registerLiveData.value = data
        }
        return registerLiveData
    }
}
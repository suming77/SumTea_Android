package com.sum.common.service

import androidx.lifecycle.LiveData
import com.alibaba.android.arouter.facade.template.IProvider
import com.sum.common.model.User

/**
 * @author mingyan.su
 * @date   2023/3/25 11:29
 * @desc   用户信息服务相关接口
 * 只是定义了一个接口，提供了对外相关能力，其他模块只需要按需添加，需要在User模块实现
 */
interface IUserService : IProvider {
    /**
     * 是否登录
     * @return Boolean
     */
    fun isLogin(): Boolean

    /**
     * 获取用户信息
     * @return User or null
     */
    fun getUserInfo(): User?

    /**
     * 保存用户信息
     * @param user
     */
    fun saveUserInfo(user: User?)

    /**
     * 清除用户信息
     */
    fun clearUserInfo()

    /**
     * 获取User信息LiveData
     */
    fun getUserLiveData(): LiveData<User?>

    /**
     * 保存用户手机号码
     * @param phone
     */
    fun saveUserPhone(phone: String?)

    /**
     * 保存用户手机号码
     * @return phone
     */
    fun getUserPhone(): String?
}
package com.sum.common.service

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.template.IProvider
import com.sum.common.model.User

/**
 * @author mingyan.su
 * @date   2023/3/25 11:29
 * @desc   登录服务相关接口
 * 只是定义了一个接口，提供了对外相关能力，其他模块只需要按需添加，需要在login模块实现
 */
interface ILoginService : IProvider {
    /**
     * 是否登录
     * @return Boolean
     */
    fun isLogin(): Boolean

    /**
     * 跳转登录页
     * @param context
     */
    fun login(context: Context)

    /**
     * 跳转隐私协议
     * @param context
     */
    fun readPolicy(context: Context)

    /**
     * 登出
     * @param context
     * @param lifecycleOwner
     * @param observer
     */
    fun logout(
        context: Context,
        lifecycleOwner: LifecycleOwner?,
        observer: Observer<Boolean>
    )

}
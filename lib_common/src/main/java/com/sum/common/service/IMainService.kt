package com.sum.common.service

import android.content.Context
import com.alibaba.android.arouter.facade.template.IProvider

/**
 * @author mingyan.su
 * @date   2023/3/26 18:20
 * @desc   主页模块相关接口
 * 提供主页模块对外能力，其他模块只需要按需添加，需要在Main模块实现
 */
interface IMainService : IProvider {
    /**
     * 跳转主页
     * @param context
     * @param index tab位置
     */
    fun toMain(context: Context, index: Int)

    /**
     * 跳转主页
     * @param url
     * @param title tab位置
     */
    fun toArticleDetail(context: Context, url: String, title: String)
}
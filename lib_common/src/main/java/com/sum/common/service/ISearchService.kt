package com.sum.common.service

import android.content.Context
import com.alibaba.android.arouter.facade.template.IProvider

/**
 * @author mingyan.su
 * @date   2023/3/29 23:18
 * @desc   登录服务相关接口
 * 提供了对外相关能力，其他模块只需要按需添加，需要在SearchService模块实现
 */
interface ISearchService : IProvider {
    /**
     * 跳转搜索页
     * @param context
     */
    fun toSearch(context: Context)

    /**
     * 清除搜索历史缓存
     */
    fun clearSearchHistoryCache()
}
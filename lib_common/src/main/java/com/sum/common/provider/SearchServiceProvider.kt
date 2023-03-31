package com.sum.common.provider

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.sum.common.constant.SEARCH_SERVICE_SEARCH
import com.sum.common.service.ISearchService

/**
 * @author mingyan.su
 * @date   2023/3/29 23:20
 * @desc   SearchService提供类，对外提供相关能力
 * 任意模块就能通过SearchServiceProvider使用对外暴露的能力
 */
object SearchServiceProvider {
    @Autowired(name = "/search/service/search22")
    lateinit var searchService: ISearchService

    init {
        ARouter.getInstance().inject(this)
    }

    /**
     * 跳转搜索页
     * @param context
     */
    fun toSearch(context: Context) {
        searchService.toSearch(context)
    }

    /**
     * 清除搜索历史缓存
     */
    fun clearSearchHistoryCache() {
        searchService.clearSearchHistoryCache()
    }
}
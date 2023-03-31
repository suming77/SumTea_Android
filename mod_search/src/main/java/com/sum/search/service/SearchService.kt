package com.sum.search.service

import android.content.Context
import android.content.Intent
import com.alibaba.android.arouter.facade.annotation.Route
import com.sum.common.constant.SEARCH_SERVICE_SEARCH
import com.sum.common.service.ISearchService
import com.sum.search.activity.SearchActivity
import com.sum.search.manager.SearchManager

/**
 * @author mingyan.su
 * @date   2023/3/29 23:26
 * @desc   搜索Service
 * 提供对ISearchService接口的具体实现
 */
@Route(path = "/search/service/search22")
class SearchService : ISearchService {

    /**
     * 跳转搜索页
     * @param context
     */
    override fun toSearch(context: Context) {
        val intent = Intent(context, SearchActivity::class.java)
        context.startActivity(intent)
    }

    /**
     * 清除搜索历史缓存
     */
    override fun clearSearchHistoryCache() {
        SearchManager.clearSearchHistory()
    }

    override fun init(context: Context?) {

    }
}
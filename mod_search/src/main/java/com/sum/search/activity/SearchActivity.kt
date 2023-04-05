package com.sum.search.activity

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.sum.common.R
import com.sum.common.constant.SEARCH_ACTIVITY_SEARCH
import com.sum.common.provider.LoginServiceProvider
import com.sum.common.provider.MainServiceProvider
import com.sum.framework.base.BaseMvvmActivity
import com.sum.framework.ext.gone
import com.sum.framework.ext.onClick
import com.sum.framework.ext.visible
import com.sum.framework.toast.TipsToast
import com.sum.framework.utils.ViewUtils
import com.sum.framework.utils.dpToPx
import com.sum.framework.utils.getColorFromResource
import com.sum.search.SearchResultAdapter
import com.sum.search.viewmodel.SearchViewModel
import com.sum.search.databinding.ActivitySearchBinding
import com.sum.search.manager.SearchManager

/**
 * @author mingyan.su
 * @date   2023/3/29 18:14
 * @desc   搜索Activity
 */
@Route(path = SEARCH_ACTIVITY_SEARCH)
class SearchActivity : BaseMvvmActivity<ActivitySearchBinding, SearchViewModel>(), OnLoadMoreListener {
    private var page = 0
    private lateinit var mAdapter: SearchResultAdapter

    /**
     * 搜索历史item点击
     */
    private val clickCallBack = { keyWord: String ->
        mBinding.etSearch.setText(keyWord)
        getSearchResult()
    }

    override fun initView(savedInstanceState: Bundle?) {
        initRecyclerView()
        initListener()
        window.statusBarColor = getColorFromResource(R.color.color_f0f2f4)
        ViewUtils.setClipViewCornerRadius(mBinding.etSearch, dpToPx(6))
        ViewUtils.setClipViewCornerRadius(mBinding.tvSearch, dpToPx(4))
        ViewUtils.setClipViewCornerTopRadius(mBinding.clSearchResult, dpToPx(14))
        ViewUtils.setClipViewCornerTopRadius(mBinding.viewSearchHistory, dpToPx(14))
    }

    private fun initListener() {
        mBinding.searchBack.onClick {
            finish()
        }
        mBinding.tvSearch.onClick {
            page = 0
            getSearchResult()
        }
//        mBinding.etSearch.textChangeFlow()
//                .filter { it.isNotEmpty() }
//                .debounce(300)
//                //.flatMapLatest { searchFlow(it.toString()) }
//                .flowOn(Dispatchers.IO)
//                .onEach {
//                    LogUtil.e("结果：$it")
//                }
//                .launchIn(lifecycleScope)
        mBinding.etSearch.addTextChangedListener {
            val content = it.toString()
            if (content.isEmpty()) {
                mBinding.clSearchResult.gone()
            }
        }
        mBinding.etSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == (EditorInfo.IME_ACTION_SEARCH)) {
                getSearchResult()
            }
            return@setOnEditorActionListener false
        }
        mBinding.viewSearchHistory.setOnHistoryClearListener {
            SearchManager.clearSearchHistory()
        }
        mBinding.viewSearchHistory.setOnCheckChangeListener(clickCallBack)
        mBinding.viewSearchRecommend.setOnCheckChangeListener(clickCallBack)
    }

    private fun initRecyclerView() {
        mBinding.refreshLayout.apply {
            setEnableRefresh(false)
            setEnableLoadMore(true)
            setOnLoadMoreListener(this@SearchActivity)
            autoRefresh()
        }
        mAdapter = SearchResultAdapter()
        mBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = mAdapter
        }
        mAdapter.onItemClickListener = { view: View, position: Int ->
            val item = mAdapter.getItem(position)
            if (item != null && !item.link.isNullOrEmpty()) {
                MainServiceProvider.toArticleDetail(
                    context = this,
                    url = item.link!!,
                    title = item.title ?: ""
                )
            }
        }
        mAdapter.onItemCollectListener = { _: View, position: Int ->
            if (LoginServiceProvider.isLogin()) {
                collectArticle(position)
            } else {
                LoginServiceProvider.login(this)
            }
        }
    }

    override fun initData() {
        mViewModel.getHotSearchData().observe(this) { hotList ->
            val list = hotList?.map { it.name ?: "" }?.toMutableList()
            mBinding.viewSearchRecommend.setHistoryData(list)
        }
        mBinding.viewSearchRecommend.getDeleteImageView().gone()

        setSearchHistory()

        mViewModel.searchResultLiveData.observe(this) {
            if (page == 0) {
                mAdapter.setData(it)
                if (it.isNullOrEmpty()) {
                    //空视图
                    mBinding.viewEmptyData.visible()
                } else {
                    mBinding.viewEmptyData.gone()
                }
            } else {
                mAdapter.addAll(it)
                mBinding.refreshLayout.finishLoadMore()
            }
        }
    }

    /**
     * 设置搜索历史
     */
    private fun setSearchHistory() {
        val historyList = SearchManager.getSearchHistory()?.reversed()?.toMutableList()
        mBinding.viewSearchHistory.setHistoryData(historyList)
    }

    /**
     * 搜索结果
     */
    private fun getSearchResult() {
        val keyWord = mBinding.etSearch.text.toString()
        mViewModel.searchResult(page, keyWord)
        if (page == 0 && keyWord.isNotEmpty()) {
            SearchManager.addSearchHistory(keyWord)
            setSearchHistory()
            mBinding.clSearchResult.visible()
            mBinding.viewEmptyData.visible()
        }
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        page++
        getSearchResult()
    }

    /**
     * 收藏 or 取消收藏
     */
    private fun collectArticle(position: Int) {
        val item = mAdapter.getItem(position)

        if (item != null) {
            showLoading()
            val collect = item.collect ?: false
            mViewModel.collectArticle(item.id, collect).observe(this) {
                dismissLoading()
                it?.let {
                    val tipsRes = if (collect) R.string.collect_cancel else R.string.collect_success
                    TipsToast.showSuccessTips(tipsRes)
                    item.collect = !collect
                    mAdapter.updateItem(position, item)
                }
            }
        }
    }
}
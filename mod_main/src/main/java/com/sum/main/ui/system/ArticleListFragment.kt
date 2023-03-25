package com.sum.main.ui.system

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.sum.common.constant.KEY_ID
import com.sum.framework.base.BaseMvvmFragment
import com.sum.framework.decoration.NormalItemDecoration
import com.sum.framework.utils.dpToPx
import com.sum.main.R
import com.sum.main.databinding.FragmentArticleListBinding
import com.sum.main.ui.system.adapter.ArticleAdapter
import com.sum.main.ui.system.viewmodel.ArticleListViewModel

/**
 * @author mingyan.su
 * @date   2023/3/21 18:24
 * @desc   普通文件列表
 */
class ArticleListFragment : BaseMvvmFragment<FragmentArticleListBinding, ArticleListViewModel>(),
    OnRefreshListener, OnLoadMoreListener {
    private var page = 0
    private var cId = 0
    private lateinit var mAdapter: ArticleAdapter

    companion object {
        fun newInstance(id: Int): ArticleListFragment {
            val args = Bundle()
            args.putInt(KEY_ID, id)
            val fragment = ArticleListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        mBinding?.refreshLayout?.apply {
            setEnableRefresh(true)
            setEnableLoadMore(true)
            setOnRefreshListener(this@ArticleListFragment)
            setOnLoadMoreListener(this@ArticleListFragment)
            autoRefresh()
        }
        mAdapter = ArticleAdapter()
        val dp12 = dpToPx(12)
        mBinding?.recyclerView?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter
            addItemDecoration(NormalItemDecoration().apply {
                setBounds(left = dp12, top = dp12, right = dp12, bottom = dp12)
                setLastBottom(true)
            })
        }
        mAdapter.onItemClickListener = { view: View, position: Int ->

        }
    }

    override fun initData() {
        cId = arguments?.getInt(KEY_ID, 0) ?: 0
        mViewModel.articleListLiveData.observe(this) {

            if (page == 0) {
                mAdapter.setData(it)
                if (it.isNullOrEmpty()) {
                    //空视图

                }
                mBinding?.refreshLayout?.finishRefresh()
            } else {
                mAdapter.addAll(it)
                mBinding?.refreshLayout?.finishLoadMore()
            }
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        page = 0
        getArticleList()
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        page++
        getArticleList()
    }

    /**
     * 获取文章列表数据
     */
    private fun getArticleList() {
        mViewModel.getArticleList(page, cId)
    }

}
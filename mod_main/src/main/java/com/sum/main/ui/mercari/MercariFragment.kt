package com.sum.main.ui.mercari;

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.sum.framework.base.BaseMvvmFragment
import com.sum.framework.decoration.NormalItemDecoration
import com.sum.framework.utils.dpToPx
import com.sum.main.databinding.FragmentMercariBinding
import com.sum.main.ui.mercari.adapter.MercariAdapter
import com.sum.main.ui.mercari.viewmodel.MercariViewModel

class MercariFragment: BaseMvvmFragment<FragmentMercariBinding, MercariViewModel>(),
        OnRefreshListener,
        OnLoadMoreListener {
        private lateinit var mercariAdapter: MercariAdapter
        override fun initView(view: View, savedInstanceState: Bundle?) {
                mBinding?.refreshLayout?.apply {
                        setEnableRefresh(true)
                        setEnableLoadMore(true)
                        setOnRefreshListener(this@MercariFragment)
                        setOnLoadMoreListener(this@MercariFragment)
                        autoRefresh()
                }
                mercariAdapter = MercariAdapter()

                val dp12 = dpToPx(12)
                mBinding?.recyclerView?.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        adapter = mercariAdapter
                        addItemDecoration(NormalItemDecoration().apply {
                                setBounds(left = dp12, top = dp12, right = dp12, bottom = dp12)
                                setLastBottom(true)
                        })
                }
        }

        override fun onRefresh(refreshLayout: RefreshLayout) {
                getFeeds()
        }

        override fun onLoadMore(refreshLayout: RefreshLayout) {
        }

        private fun getFeeds() {
                mViewModel.getArticleList()
        }

        override fun initData() {
                mViewModel.mercicaListLiveData.observe(this) {
                        mercariAdapter.setData(it)
                        if (it.isNullOrEmpty()) {
                                //空视图

                        }
                        mBinding?.refreshLayout?.finishRefresh()

                }
        }
}

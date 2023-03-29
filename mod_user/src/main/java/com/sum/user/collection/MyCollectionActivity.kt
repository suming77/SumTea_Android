package com.sum.user.collection

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.sum.common.constant.USER_ACTIVITY_COLLECTION
import com.sum.framework.base.BaseMvvmActivity
import com.sum.framework.decoration.NormalItemDecoration
import com.sum.framework.toast.TipsToast
import com.sum.framework.utils.dpToPx
import com.sum.user.databinding.ActivityMyCollectListBinding

/**
 * @author mingyan.su
 * @date   2023/3/24 18:26
 * @desc   我的收藏
 */
@Route(path = USER_ACTIVITY_COLLECTION)
class MyCollectionActivity : BaseMvvmActivity<ActivityMyCollectListBinding, MyCollectViewModel>(),
    OnRefreshListener, OnLoadMoreListener {
    private var mPage = 0
    private lateinit var mAdapter: MyCollectListAdapter
    override fun initView(savedInstanceState: Bundle?) {
        mBinding?.refreshLayout?.apply {
            setEnableRefresh(true)
            setEnableLoadMore(true)
            setOnRefreshListener(this@MyCollectionActivity)
            setOnLoadMoreListener(this@MyCollectionActivity)
            autoRefresh()
        }
        mAdapter = MyCollectListAdapter()
        val dp12 = dpToPx(12)
        mBinding?.recyclerView?.apply {
            layoutManager = LinearLayoutManager(this@MyCollectionActivity)
            adapter = mAdapter
            addItemDecoration(NormalItemDecoration().apply {
                setBounds(left = dp12, top = dp12, right = dp12, bottom = dp12)
                setLastBottom(true)
            })
        }
        mAdapter.onItemClickListener = { view: View, position: Int ->

        }

        mAdapter.onItemCancelCollectListener = { view: View, position: Int ->
            val item = mAdapter.getItem(position)
            item?.let {
                showLoading()
                mViewModel.collectArticle(it.id, it.originId).observe(this) {
                    mAdapter.removeAt(position)
                    dismissLoading()
                    TipsToast.showTips(com.sum.common.R.string.collect_cancel)
                }
            }
        }
    }

    override fun initData() {
        getMyCollectList()
        mViewModel.collectListLiveData.observe(this) {
            if (mPage == 0) {
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

    private fun getMyCollectList() {
        mViewModel.getMyCollectList(mPage)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mPage = 0
        getMyCollectList()
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mPage++
        getMyCollectList()
    }

}
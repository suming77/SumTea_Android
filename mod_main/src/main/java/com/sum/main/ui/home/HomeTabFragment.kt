package com.sum.main.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.sum.common.constant.KEY_ID
import com.sum.framework.base.BaseMvvmFragment
import com.sum.framework.log.LogUtil
import com.sum.main.R
import com.sum.main.databinding.FragmentHomeVideoBinding
import com.sum.main.databinding.LayoutHomeTabItemBinding
import com.sum.main.ui.home.adapter.HomeTabAdapter
import com.sum.main.ui.home.viewmodel.HomeViewModel

/**
 * @author mingyan.su
 * @date   2023/3/5 20:11
 * @desc   首页资讯列表
 */
class HomeTabFragment : BaseMvvmFragment<FragmentHomeVideoBinding, HomeViewModel>(), OnLoadMoreListener {

    companion object {
        fun newInstance(id: Int): HomeTabFragment {
            val args = Bundle()
            args.putInt(KEY_ID, id)
            val fragment = HomeTabFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutResId(): Int = R.layout.fragment_home_video

    override fun initView(view: View, savedInstanceState: Bundle?) {
        mBinding?.refreshLayout?.apply {
            setEnableRefresh(false)
            setEnableLoadMore(false)
            setOnLoadMoreListener(this@HomeTabFragment)
        }
        val id = arguments?.getInt(KEY_ID, 0) ?: 0
        mBinding?.recyclerView?.layoutManager = LinearLayoutManager(context)
        val mAdapter = HomeTabAdapter()

        val view = LayoutHomeTabItemBinding.inflate(LayoutInflater.from(requireContext())).root
        val view2 = LayoutHomeTabItemBinding.inflate(LayoutInflater.from(requireContext())).root
        mAdapter.addHeadView(view, 0)
        mBinding?.recyclerView?.adapter = mAdapter

        mViewModel.getProjectList(1, id).observe(this) {
            mAdapter.setData(it?.datas)
            mAdapter.addFootView(view2, 10)
            LogUtil.e("${mAdapter.hasFooterView()}", tag = "smy")
        }

    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {

    }
}
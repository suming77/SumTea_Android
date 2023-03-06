package com.sum.main.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.sum.framework.base.BaseMvvmFragment
import com.sum.framework.log.LogUtil
import com.sum.main.R
import com.sum.main.databinding.FragmentHomeBinding

/**
 * @author mingyan.su
 * @date   2023/3/3 8:16
 * @desc   首页
 */
class HomeFragment : BaseMvvmFragment<FragmentHomeBinding, HomeViewModel>(), OnRefreshListener {

    override fun getLayoutResId(): Int = R.layout.fragment_home

    override fun initView(view: View, savedInstanceState: Bundle?) {
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
//        homeViewModel.getBannerList().observe(viewLifecycleOwner) {
//            binding.refreshLayout.finishRefresh()
//        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogUtil.e("onViewCreated")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        LogUtil.e("onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        LogUtil.e("onResume")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.e("onDestroy")
    }
}
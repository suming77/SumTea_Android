package com.sum.main.ui.home

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sum.common.decoration.StaggeredItemDecoration
import com.sum.common.model.VideoInfo
import com.sum.framework.base.BaseMvvmFragment
import com.sum.framework.utils.dpToPx
import com.sum.main.R
import com.sum.main.databinding.FragmentHomeVideoBinding
import com.sum.main.ui.home.adapter.HomeVideoAdapter
import com.sum.main.ui.home.viewmodel.HomeViewModel

/**
 * @author mingyan.su
 * @date   2023/3/5 20:11
 * @desc   首页视频列表
 */
class HomeVideoFragment : BaseMvvmFragment<FragmentHomeVideoBinding, HomeViewModel>() {

    override fun getLayoutResId(): Int = R.layout.fragment_home_video

    override fun initView(view: View, savedInstanceState: Bundle?) {

        val list = mutableListOf<VideoInfo>()
        for (i in 0..20) {
            list.add(VideoInfo("", "", "", "", "", ""))
        }

        val spanCount = 2
        val manager = StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL)
        val videoAdapter = HomeVideoAdapter(requireContext())
        mBinding?.recyclerView?.apply {
            layoutManager = manager
            addItemDecoration(StaggeredItemDecoration(dpToPx(10)))
            adapter = videoAdapter
        }
        videoAdapter.setDataList(list)
    }


}
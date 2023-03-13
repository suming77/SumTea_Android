package com.sum.main.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.sum.common.model.ProjectSubInfo
import com.sum.framework.adapter.BaseBindViewHolder
import com.sum.framework.adapter.BaseRecyclerViewAdapter
import com.sum.main.databinding.LayoutHomeTabItemBinding

/**
 * @author mingyan.su
 * @date   2023/3/13 17:53
 * @desc   首页列表信息
 */
class HomeTabAdapter : BaseRecyclerViewAdapter<ProjectSubInfo, LayoutHomeTabItemBinding>() {

    override fun getViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): LayoutHomeTabItemBinding {
        return LayoutHomeTabItemBinding.inflate(layoutInflater, parent, false)
    }

    override fun onBindDefViewHolder(
        holder: BaseBindViewHolder<LayoutHomeTabItemBinding>,
        item: ProjectSubInfo?,
        position: Int
    ) {
        holder.binding.tvTitle.text = item?.title
    }
}
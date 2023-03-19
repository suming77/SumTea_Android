package com.sum.main.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.sum.common.model.ProjectSubInfo
import com.sum.framework.adapter.BaseBindViewHolder
import com.sum.framework.adapter.BaseRecyclerViewAdapter
import com.sum.framework.utils.ViewUtils
import com.sum.framework.utils.dpToPx
import com.sum.glide.setUrl
import com.sum.main.databinding.LayoutHomeTabItemBinding

/**
 * @author mingyan.su
 * @date   2023/3/13 17:53
 * @desc   首页列表信息
 */
class HomeTabItemAdapter : BaseRecyclerViewAdapter<ProjectSubInfo, LayoutHomeTabItemBinding>() {

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
        if (item == null) return
        holder.binding.apply {
            ivMainIcon.setUrl(item.envelopePic)
            tvTitle.text = item.title
            tvSubTitle.text = item.desc
            tvAuthorName.text = item.author
            tvTime.text = item.niceDate
            ViewUtils.setClipViewCornerRadius(holder.itemView, dpToPx(8))
        }
    }
}
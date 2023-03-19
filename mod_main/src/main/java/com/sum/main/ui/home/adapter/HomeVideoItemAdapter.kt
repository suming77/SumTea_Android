package com.sum.main.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sum.common.model.VideoInfo
import com.sum.framework.adapter.BaseBindViewHolder
import com.sum.framework.adapter.BaseRecyclerViewAdapter
import com.sum.glide.setUrl
import com.sum.main.databinding.LayoutHomeVideoItemBinding

/**
 * @author mingyan.su
 * @date   2023/3/8 23:10
 * @desc   首页视频列表适配器
 */
class HomeVideoItemAdapter(val context: Context) : BaseRecyclerViewAdapter<VideoInfo, LayoutHomeVideoItemBinding>() {

    override fun getViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): LayoutHomeVideoItemBinding {
        return LayoutHomeVideoItemBinding.inflate(layoutInflater, parent, false)
    }

    override fun onBindDefViewHolder(
        holder: BaseBindViewHolder<LayoutHomeVideoItemBinding>,
        item: VideoInfo?,
        position: Int
    ) {
        item?.apply {
            holder.binding.tvTitle.text = title
            holder.binding.ivVideoCover.setUrl(imageUrl)
            holder.binding.tvCollectCount.text = collectionCount
        }
    }
}
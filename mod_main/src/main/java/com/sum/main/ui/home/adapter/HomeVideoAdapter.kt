package com.sum.main.ui.home.adapter

import android.content.Context
import com.sum.common.model.VideoInfo
import com.sum.framework.adapter.BaseListAdapter
import com.sum.framework.base.BaseViewHolder
import com.sum.main.R
import com.sum.main.databinding.LayoutHomeVideoItemBinding

/**
 * @author mingyan.su
 * @date   2023/3/8 23:10
 * @desc   首页视频列表适配器
 */
class HomeVideoAdapter(val context: Context) : BaseListAdapter<VideoInfo>(context ) {

    override val layoutId: Int = R.layout.layout_home_video_item

    override fun onBindItemHolder(holder: BaseViewHolder, position: Int) {
        holder.setImageResource(R.id.iv_video_cover, R.mipmap.ic_navi_home)
                .setText(R.id.tv_title, "shahhahaha")
    }
}
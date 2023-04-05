package com.sum.video.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.sum.common.model.VideoInfo
import com.sum.framework.adapter.BaseBindViewHolder
import com.sum.framework.adapter.BaseRecyclerViewAdapter
import com.sum.framework.ext.onClick
import com.sum.framework.toast.TipsToast
import com.sum.video.databinding.LayoutVideoItemBinding

/**
 * @author mingyan.su
 * @date   2023/4/3 12:41
 * @desc   视频Adapter
 */
class VideoAdapter : BaseRecyclerViewAdapter<VideoInfo, LayoutVideoItemBinding>() {

    override fun getViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): LayoutVideoItemBinding {
        return LayoutVideoItemBinding.inflate(layoutInflater, parent, false)
    }

    override fun onBindDefViewHolder(
        holder: BaseBindViewHolder<LayoutVideoItemBinding>,
        item: VideoInfo?,
        position: Int
    ) {
        holder.binding.rotateNoteView.initAnimator()
        holder.binding.includeVideoAction.tvLike.text = "10"
        holder.binding.includeVideoAction.tvComment.text = "24"
        holder.binding.includeVideoAction.tvShare.text = "0"

        holder.binding.includeVideoAction.tvCommentOpen.onClick { showToast() }
        holder.binding.includeVideoAction.tvLike.onClick { showToast() }
        holder.binding.includeVideoAction.tvComment.onClick { showToast() }
        holder.binding.includeVideoAction.tvShare.onClick { showToast() }
    }

    private fun showToast() {
        TipsToast.showTips(com.sum.common.R.string.default_developing)
    }
}
package com.sum.user.collection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sum.common.model.ArticleInfo
import com.sum.framework.adapter.BaseBindViewHolder
import com.sum.framework.adapter.BaseRecyclerViewAdapter
import com.sum.framework.ext.onClick
import com.sum.framework.utils.getStringFromResource
import java.text.SimpleDateFormat
import java.util.Locale
import com.sum.common.R
import com.sum.framework.ext.Bold
import com.sum.framework.ext.compoundDrawable
import com.sum.framework.ext.gone
import com.sum.framework.ext.visible
import com.sum.framework.utils.ViewUtils
import com.sum.framework.utils.dpToPx
import com.sum.user.databinding.LayoutMyCollectItemBinding

/**
 * @author mingyan.su
 * @date   2023/3/21 22:50
 * @desc   文章列表Item
 */
class MyCollectListAdapter : BaseRecyclerViewAdapter<ArticleInfo, LayoutMyCollectItemBinding>() {
    var onItemCancelCollectListener: ((view: View, position: Int) -> Unit?)? = null
    private val format = SimpleDateFormat("yyyy-MM-dd:HH:mm", Locale.CHINA)

    override fun getViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): LayoutMyCollectItemBinding {
        return LayoutMyCollectItemBinding.inflate(layoutInflater, parent, false)
    }

    override fun onBindDefViewHolder(
        holder: BaseBindViewHolder<LayoutMyCollectItemBinding>,
        item: ArticleInfo?,
        position: Int
    ) {
        if (item == null) return
        val name = if (item.author.isNullOrEmpty()) item.shareUser else item.author
        val authorName = String.format(getStringFromResource(R.string.author_name), name ?: "")
        holder.binding.apply {
            tvTitle.text = item.title
            tvTitle.Bold()
            tvDesc.text = item.desc
            if (item.desc.isNullOrEmpty()) {
                tvDesc.gone()
            } else {
                tvDesc.visible()
            }
            tvTime.text = if (!item.niceDate.isNullOrEmpty()) item.niceDate else format.format(item.publishTime)
            tvFrom.text = "form:${item.chapterName}"
            tvAuthorName.text = authorName
            tvCollect.onClick {
                onItemCancelCollectListener?.invoke(it, position)
            }
            ViewUtils.setClipViewCornerRadius(tvCollect, dpToPx(4))
        }
    }


}
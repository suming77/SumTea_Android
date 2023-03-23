package com.sum.main.ui.system.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.sum.common.model.ArticleInfo
import com.sum.framework.adapter.BaseBindViewHolder
import com.sum.framework.adapter.BaseRecyclerViewAdapter
import com.sum.framework.ext.onClick
import com.sum.framework.utils.getStringFromResource
import com.sum.main.databinding.LayoutArticleItemBinding
import java.text.SimpleDateFormat
import java.util.Locale
import com.sum.common.R
import com.sum.framework.ext.Bold
import com.sum.framework.ext.bold
import com.sum.framework.ext.gone
import com.sum.framework.ext.visible

/**
 * @author mingyan.su
 * @date   2023/3/21 22:50
 * @desc   文章列表Item
 */
class ArticleAdapter : BaseRecyclerViewAdapter<ArticleInfo, LayoutArticleItemBinding>() {
    private val format = SimpleDateFormat("yyyy-MM-dd:HH:mm", Locale.CHINA)
    override fun getViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): LayoutArticleItemBinding {
        return LayoutArticleItemBinding.inflate(layoutInflater, parent, false)
    }

    override fun onBindDefViewHolder(
        holder: BaseBindViewHolder<LayoutArticleItemBinding>,
        item: ArticleInfo?,
        position: Int
    ) {
        if (item == null) return
        val name = if (item.author.isNullOrEmpty()) item.shareUser else item.author
        val authorName = String.format(getStringFromResource(R.string.author_name), name)
        holder.binding.apply {
            tvTitle.text = item.title
            tvTitle.Bold()
            tvDesc.text = item.desc
            if (item.desc.isNullOrEmpty()) {
                tvDesc.gone()
            } else {
                tvDesc.visible()
            }
            tvTime.text = format.format(item.publishTime)
            tvFrom.text = "${item.superChapterName}/${item.chapterName}"
            tvAuthorName.text = authorName
            tvCollect.text = "${item.zan ?: "0"}"
            tvCollect.onClick {

            }
        }
    }


}
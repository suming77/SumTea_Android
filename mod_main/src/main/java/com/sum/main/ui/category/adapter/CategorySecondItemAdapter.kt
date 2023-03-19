package com.sum.main.ui.category.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.sum.common.model.CategorySecondItem
import com.sum.framework.adapter.BaseBindViewHolder
import com.sum.framework.adapter.BaseRecyclerViewAdapter
import com.sum.framework.utils.ViewUtils
import com.sum.framework.utils.dpToPx
import com.sum.main.databinding.LayoutCategorySecondItemBinding

/**
 * @author mingyan.su
 * @date   2023/3/19 22:45
 * @desc   分类二级Adapter
 */
class CategorySecondItemAdapter : BaseRecyclerViewAdapter<CategorySecondItem, LayoutCategorySecondItemBinding>() {
    override fun getViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): LayoutCategorySecondItemBinding {
        return LayoutCategorySecondItemBinding.inflate(layoutInflater, parent, false)
    }

    override fun onBindDefViewHolder(
        holder: BaseBindViewHolder<LayoutCategorySecondItemBinding>,
        item: CategorySecondItem?,
        position: Int
    ) {
        holder.binding?.apply {
            tvTitle.text = item?.title
            ViewUtils.setClipViewCornerRadius(tvTitle, dpToPx(8))
        }

    }
}
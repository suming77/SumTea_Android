package com.sum.main.ui.category.adapter

import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorRes
import com.sum.common.model.CategoryItem
import com.sum.framework.adapter.BaseBindViewHolder
import com.sum.framework.adapter.BaseRecyclerViewAdapter
import com.sum.framework.ext.gone
import com.sum.framework.ext.visible
import com.sum.framework.utils.dpToPx
import com.sum.framework.utils.getColorFromResource
import com.sum.main.R
import com.sum.main.databinding.LayoutCategoryTabItemBinding

/**
 * @author mingyan.su
 * @date   2023/3/19 18:11
 * @desc   分类tab Adapter
 */
class CategoryTabAdapter : BaseRecyclerViewAdapter<CategoryItem, LayoutCategoryTabItemBinding>() {
    /**
     * 背景圆角
     */
    private val mRadius = dpToPx(8f)

    /**
     * 当前选中条目
     */
    private var currPosition = 0

    fun setCurrentPosition(position: Int) {
        this.currPosition = position
    }

    override fun getViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): LayoutCategoryTabItemBinding {
        return LayoutCategoryTabItemBinding.inflate(layoutInflater, parent, false)
    }

    override fun onBindDefViewHolder(
        holder: BaseBindViewHolder<LayoutCategoryTabItemBinding>,
        item: CategoryItem?,
        position: Int
    ) {
        if (item == null) return

        holder.binding.apply {
            tvTitle.text = item.name
            if (item.isSelected) {
                viewTag.visible()
                tvTitle.background = getBgDrawable(R.color.white)
                tvTitle.typeface = Typeface.DEFAULT_BOLD
            } else {
                when (position) {
                    //前一个Item背景
                    currPosition - 1 -> {
                        tvTitle.background =
                            getBgDrawable(color = com.sum.common.R.color.color_f0f2f4, rightBottomRadius = mRadius)
                    }
                    //后一个Item背景
                    currPosition + 1 -> {
                        tvTitle.background =
                            getBgDrawable(color = com.sum.common.R.color.color_f0f2f4, rightTopRadius = mRadius)
                    }
                    else -> {
                        tvTitle.background = getBgDrawable(color = com.sum.common.R.color.color_f0f2f4)
                    }
                }
                viewTag.gone()
                tvTitle.typeface = Typeface.DEFAULT
            }
        }
    }

    /**
     * 设置背景
     * @param color 背景颜色
     * @param leftTopRadius top-left
     * @param rightTopRadius top-right
     * @param leftBottomRadius bottom-right
     * @param rightBottomRadius bottom-left
     */
    private fun getBgDrawable(
        @ColorRes color: Int,
        leftTopRadius: Float = 0.0f,
        rightTopRadius: Float = 0.0f,
        rightBottomRadius: Float = 0.0f,
        leftBottomRadius: Float = 0.0f
    ): GradientDrawable {
        return GradientDrawable().apply {
            setColor(getColorFromResource(color))
            shape = GradientDrawable.RECTANGLE
            cornerRadii = floatArrayOf(
                leftTopRadius,
                leftTopRadius,
                rightTopRadius,
                rightTopRadius,
                rightBottomRadius,
                rightBottomRadius,
                leftBottomRadius,
                leftBottomRadius
            )
        }
    }
}
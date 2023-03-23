package com.sum.framework.decoration

import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView

/**
 * 普通条目间距
 * @author mingyan.su
 * @date   2023/3/21 8:18
 */
class NormalItemDecoration : RecyclerView.ItemDecoration() {

    private val mDivider = ColorDrawable()
    private var mLastBottom = false
    private var mIsFirstHead = false

    fun setBounds(left: Int = 0, top: Int = 0, right: Int = 0, bottom: Int = 0) {
        mDivider.bounds.left = left
        mDivider.bounds.top = top
        mDivider.bounds.right = right
        mDivider.bounds.bottom = bottom
    }

    fun setColor(@ColorInt color: Int) {
        mDivider.color = color
    }

    /**
     * 是否显示最后一条底部间隔
     */
    fun setLastBottom(lastBottom: Boolean) {
        mLastBottom = lastBottom
    }

    /**
     * 是否显示第一条的所有间隔
     * 用于头部局
     */
    fun setFirstHeadMargin(isFirstHead: Boolean) {
        mIsFirstHead = isFirstHead
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val lastPosition = state.itemCount - 1
        val position = parent.getChildAdapterPosition(view)

        outRect.top = if (position == 0 && mIsFirstHead) 0 else mDivider.bounds.top
        outRect.left = if (position == 0 && mIsFirstHead) 0 else mDivider.bounds.left
        outRect.right = if (position == 0 && mIsFirstHead) 0 else mDivider.bounds.right
        outRect.bottom = if (position == lastPosition && mLastBottom) mDivider.bounds.bottom else 0
    }
}
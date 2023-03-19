package com.sum.framework.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * @author mingyan.su
 * @date   2023/3/8 22:57
 * @desc   瀑布流分割线
 */
class StaggeredItemDecoration(private val space: Int) : ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = space
        //瀑布流专属分割线
        val params = view.layoutParams as StaggeredGridLayoutManager.LayoutParams

        //根据params.getSpanIndex()来判断左右边确定分割线,第一列设置左边距为space，右边距为space/2（第二列反之）
        if (params.spanIndex % 2 == 0) {
            outRect.left = space
            outRect.right = space / 2
        } else {
            outRect.left = space / 2
            outRect.right = space
        }
    }
}
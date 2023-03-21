package com.sum.framework.decoration

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sum.framework.utils.dpToPx

/**
 * 普通条目间距
 * @author mingyan.su
 * @date   2023/3/21 8:18
 * @param space 条目间距 单位dp
 */
class NormalItemDecoration(var space: Int = 12) : RecyclerView.ItemDecoration() {
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
//        outRect.top = dpToPx(space)
        outRect.bottom = dpToPx(space)
        outRect.left = dpToPx(space)
        outRect.right = dpToPx(space)
    }
}
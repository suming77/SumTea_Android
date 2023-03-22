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
 * @param topBottom 条目上下间距 单位dp
 * @param topBottom 条目左右间距 单位dp
 */
class NormalItemDecoration(var topBottom: Int = 12, var leftRight: Int = 12) : RecyclerView.ItemDecoration() {
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = dpToPx(topBottom)
//        outRect.bottom = dpToPx(topBottom)
        outRect.left = dpToPx(leftRight)
        outRect.right = dpToPx(leftRight)
    }
}
package com.sum.framework.ext

import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewConfiguration
import androidx.recyclerview.widget.*
import com.scwang.smart.refresh.layout.util.SmartUtil.dp2px
import com.sum.framework.decoration.SumDividerItemDecoration

private var isLock = true

fun RecyclerView.addOnVerticalScrollListener(
    onScrolledUp: (recyclerView: RecyclerView) -> Unit,
    onScrolledDown: (recyclerView: RecyclerView) -> Unit,
    onScrolledToTop: (recyclerView: RecyclerView) -> Unit,
    onScrolledToBottom: (recyclerView: RecyclerView) -> Unit,
    once: Boolean = true
) {//上下滑动仅触发一次
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (!recyclerView.canScrollVertically(-1)) {
                //顶部
                onScrolledUp.invoke(recyclerView)
            } else if (!recyclerView.canScrollVertically(1)) {
                //底部
                onScrolledDown.invoke(recyclerView)
            } else if (dy < 0 && Math.abs(dy) > ViewConfiguration.get(context).scaledTouchSlop) {
                //往上滑
                if (once && !isLock) {
                    isLock = true
                    onScrolledToTop.invoke(recyclerView)
                } else if (!once) {
                    onScrolledToTop.invoke(recyclerView)
                }
            } else if (dy > 0 && Math.abs(dy) > ViewConfiguration.get(context).scaledTouchSlop) {
                //往下滑
                if (once && isLock) {
                    isLock = false
                    onScrolledToBottom.invoke(recyclerView)
                } else if (!once) {
                    onScrolledToBottom.invoke(recyclerView)
                }
            }
        }
    })

}

/**
 * 设置分割线
 * @param color 分割线的颜色，默认是#DEDEDE
 * @param size 分割线的大小，默认是1px
 * @param includeLast 最后一条是否绘制
 *
 */
fun RecyclerView.divider(
    color: Int = Color.parseColor("#DEDEDE"),
    size: Int = 1,
    includeLast: Boolean = true
): RecyclerView {
    val decoration = SumDividerItemDecoration(context, orientation)
    decoration.setDrawable(GradientDrawable().apply {
        setColor(color)
        shape = GradientDrawable.RECTANGLE
        setSize(size, size)
    })
    decoration.isDrawLastPositionDivider(includeLast)
    if (itemDecorationCount > 0) {
        removeItemDecorationAt(0)
    }
    addItemDecoration(decoration)
    return this
}

/**
 * 设置网格分割线间隔
 * @param spanCount 网格的列数
 * @param spacingDp 分割线的大小
 * @param includeEdge 是否包含边缘
 *
 */
fun RecyclerView.dividerGridSpace(
    spanCount: Int,
    spacingDp: Float,
    includeEdge: Boolean
): RecyclerView {
    addItemDecoration(object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view) // item position
            val column = position % spanCount // item column
            val targetSpacing = dp2px(spacingDp)
            if (includeEdge) {
                outRect.left =
                    targetSpacing - column * targetSpacing / spanCount // targetSpacing - column * ((1f / spanCount) * targetSpacing)
                outRect.right =
                    (column + 1) * targetSpacing / spanCount // (column + 1) * ((1f / spanCount) * targetSpacing)

                if (position < spanCount) { // top edge
                    outRect.top = targetSpacing
                }
                outRect.bottom = targetSpacing // item bottom
            } else {
                outRect.left =
                    column * targetSpacing / spanCount // column * ((1f / spanCount) * targetSpacing)
                outRect.right =
                    targetSpacing - (column + 1) * targetSpacing / spanCount // targetSpacing - (column + 1) * ((1f /    spanCount) * targetSpacing)
                if (position >= spanCount) {
                    outRect.top = targetSpacing // item top
                }
            }
        }
    })
    return this
}

fun RecyclerView.vertical(spanCount: Int = 0, isStaggered: Boolean = false): RecyclerView {
    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    if (spanCount != 0) {
        layoutManager = GridLayoutManager(context, spanCount)
    }
    if (isStaggered) {
        layoutManager = StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL)
    }
    return this
}

fun RecyclerView.itemAnimator(animator: RecyclerView.ItemAnimator): RecyclerView {
    itemAnimator = animator
    return this
}

fun RecyclerView.noItemAnim(){
    itemAnimator = null
}

fun RecyclerView.horizontal(spanCount: Int = 0, isStaggered: Boolean = false): RecyclerView {
    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    if (spanCount != 0) {
        layoutManager = GridLayoutManager(context, spanCount, GridLayoutManager.HORIZONTAL, false)
    }
    if (isStaggered) {
        layoutManager = StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.HORIZONTAL)
    }
    return this
}

inline val RecyclerView.orientation
    get() = if (layoutManager == null) -1 else layoutManager.run {
        when (this) {
            is LinearLayoutManager -> orientation
            is GridLayoutManager -> orientation
            is StaggeredGridLayoutManager -> orientation
            else -> -1
        }
    }



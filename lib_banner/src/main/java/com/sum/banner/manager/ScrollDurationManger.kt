package com.sum.banner.manager

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.sum.banner.utils.BannerUtils
import java.lang.reflect.InvocationTargetException

/**
 * ScrollDurationManger
 */
class ScrollDurationManger(
    viewPager2: ViewPager2, scrollDuration: Int,
    linearLayoutManager: LinearLayoutManager,
) :
    LinearLayoutManager(viewPager2.context, linearLayoutManager.orientation, false) {
    private var mParent: LinearLayoutManager? = null
    private var scrollDuration = 0

    init {
        this.scrollDuration = scrollDuration
        mParent = linearLayoutManager
    }

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView, state: RecyclerView.State?,
        position: Int
    ) {
        val linearSmoothScroller: LinearSmoothScroller =
            object : LinearSmoothScroller(recyclerView.context) {
                override fun calculateTimeForDeceleration(dx: Int): Int {
                    return scrollDuration
                }
            }
        linearSmoothScroller.targetPosition = position
        startSmoothScroll(linearSmoothScroller)
    }

    override fun performAccessibilityAction(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State, action: Int, args: Bundle?
    ): Boolean {
        return mParent?.performAccessibilityAction(recycler, state, action, args) ?: false
    }

    override fun onInitializeAccessibilityNodeInfo(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State, info: AccessibilityNodeInfoCompat
    ) {
        mParent?.onInitializeAccessibilityNodeInfo(recycler, state, info)
    }

    override fun calculateExtraLayoutSpace(
        state: RecyclerView.State,
        extraLayoutSpace: IntArray
    ) {
        try {
            val method = mParent?.javaClass
                    ?.getDeclaredMethod(
                        "calculateExtraLayoutSpace",
                        state.javaClass, extraLayoutSpace.javaClass
                    )
            method?.isAccessible = true
            method?.invoke(mParent, state, extraLayoutSpace)
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
            BannerUtils.log(e.message)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
            BannerUtils.log(e.message)
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
            BannerUtils.log(e.message)
        }
    }

    override fun requestChildRectangleOnScreen(
        parent: RecyclerView,
        child: View, rect: Rect, immediate: Boolean,
        focusedChildVisible: Boolean
    ): Boolean {
        return false // users should use setCurrentItem instead
    }
}
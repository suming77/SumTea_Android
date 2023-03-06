package com.sum.banner.manager

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

/**
 * 通过反射修改页面滑动的时间
 * Thanks:https://github.com/zguop/banner/blob/master/pager2banner/src/main/java/com/to/aboomy/pager2banner/Banner.java
 *
 */
object ReflectLayoutManager {
    fun reflectLayoutManager(viewPager2: ViewPager2, scrollDuration: Int) {
        try {
            val recyclerView = viewPager2.getChildAt(0) as RecyclerView
            recyclerView.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                ?: return
            val scrollDurationManger =
                ScrollDurationManger(viewPager2, scrollDuration, linearLayoutManager)
            recyclerView.layoutManager = scrollDurationManger
            val mRecyclerField =
                RecyclerView.LayoutManager::class.java.getDeclaredField("mRecyclerView")
            mRecyclerField.isAccessible = true
            mRecyclerField[linearLayoutManager] = recyclerView
            val layoutMangerField = ViewPager2::class.java.getDeclaredField("mLayoutManager")
            layoutMangerField.isAccessible = true
            layoutMangerField[viewPager2] = scrollDurationManger
            val pageTransformerAdapterField =
                ViewPager2::class.java.getDeclaredField("mPageTransformerAdapter")
            pageTransformerAdapterField.isAccessible = true
            val mPageTransformerAdapter = pageTransformerAdapterField[viewPager2]
            if (mPageTransformerAdapter != null) {
                val aClass: Class<*> = mPageTransformerAdapter.javaClass
                val layoutManager = aClass.getDeclaredField("mLayoutManager")
                layoutManager.isAccessible = true
                layoutManager[mPageTransformerAdapter] = scrollDurationManger
            }
            val scrollEventAdapterField =
                ViewPager2::class.java.getDeclaredField("mScrollEventAdapter")
            scrollEventAdapterField.isAccessible = true
            val mScrollEventAdapter = scrollEventAdapterField[viewPager2]
            if (mScrollEventAdapter != null) {
                val aClass: Class<*> = mScrollEventAdapter.javaClass
                val layoutManager = aClass.getDeclaredField("mLayoutManager")
                layoutManager.isAccessible = true
                layoutManager[mScrollEventAdapter] = scrollDurationManger
            }
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }
}
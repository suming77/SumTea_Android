package com.sum.video.listener

import android.view.View

/**
 * itemView选中回调
 */
interface OnViewPagerListener {
    /**
     * 初始化
     */
    fun onInitComplete(view: View?)

    /**
     * 释放
     */
    fun onPageRelease(isNext: Boolean, position: Int, view: View?)

    /**
     * 选中
     */
    fun onPageSelected(position: Int, isBottom: Boolean, view: View?)
}
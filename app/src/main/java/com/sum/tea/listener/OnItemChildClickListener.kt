package com.sum.tea.listener

import android.view.View

/**
 * @创建者 mingyan.su
 * @创建时间 2023/2/19 10:20
 * @类描述 item child view点击监听
 */
interface OnItemChildClickListener {
    /**
     * 子类View点击事件
     *
     * @param view 目标View
     * @param position 父控件位置
     */
    fun onItemChildClick(view: View?, position: Int)
}

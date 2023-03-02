package com.sum.tea.listener

import android.view.View

/**
 * @创建者 mingyan.su
 * @创建时间 2023/2/19 10:12
 * @类描述 item点击监听
 */
interface OnItemClickListener {
    /**
     * 条目点击监听
     *
     * @param view item
     * @param position item位置
     */
    fun onItemClick(view: View?, position: Int)
}

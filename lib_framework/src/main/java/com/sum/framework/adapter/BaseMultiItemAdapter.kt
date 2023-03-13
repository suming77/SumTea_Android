package com.sum.framework.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.sum.framework.listener.MultiItemEntity

/**
 * @author mingyan.su
 * @date   2023/3/13 18:33
 * @desc   多种类目Adapter
 */
abstract class BaseMultiItemAdapter<T : MultiItemEntity> :
    BaseRecyclerViewAdapter<T, ViewBinding>() {

    /**
     * model需要实现MultiItemEntity接口
     */
    override fun getDefItemViewType(position: Int): Int {
        return getData()[position].itemType
    }

    /**
     * 如果需要实现不同传递不同类型子类ViewHolder，可以重写该方法
     */
    override fun onCreateDefViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ViewBinding> {
        return BaseBindViewHolder(getViewBinding(layoutInflater, parent, viewType))
    }
}
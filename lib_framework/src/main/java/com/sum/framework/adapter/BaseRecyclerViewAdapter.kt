package com.sum.framework.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * @author mingyan.su
 * @date   2023/3/9 08:13
 * @desc   adapter基类
 * 提供创建ViewHolder能力，提供添加头尾布局能力
 */
abstract class BaseRecyclerViewAdapter<T, B : ViewBinding> : RecyclerView.Adapter<BaseViewHolder<B>>() {
    /**
     * 数据列表
     */
    private var data = mutableListOf<T>()

    /**
     * Item点击事件监听
     */
    val onItemClickListener: ((view: View, position: Int) -> Unit?)? = null

    /**
     * Item长按事件监听
     */
    val onItemLongClickListener: ((view: View, position: Int) -> Boolean) = { view, position -> false }

    /**
     * 子类不可重载，子类可重写createViewHolder()实现自定义ViewHolder
     * 或者重写getViewBinding()传入布局，不需要创建ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<B> {
        val layoutInflater = LayoutInflater.from(parent.context)
        return createViewHolder(layoutInflater, parent, viewType)
    }

    /**
     * 这类可以选择重载该方法，如果需要点击事件调用super即可
     */
    override fun onBindViewHolder(holder: BaseViewHolder<B>, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(holder.itemView, position)
        }
        holder.itemView.setOnLongClickListener {
            onItemLongClickListener.invoke(holder.itemView, position)
        }
    }

    override fun getItemCount(): Int {
        return  data.size
    }

    /**
     * 子类实现创建自定义ViewHolder，父类提供了LayoutInflater
     */
    open fun createViewHolder(
        layoutInflater: LayoutInflater?,
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<B> {
        return BaseViewHolder(getViewBinding(layoutInflater, parent, viewType))
    }

    /**
     * 子类实现ViewBinding，父类提供了LayoutInflater
     */
    abstract fun getViewBinding(layoutInflater: LayoutInflater?, parent: ViewGroup, viewType: Int): B

}
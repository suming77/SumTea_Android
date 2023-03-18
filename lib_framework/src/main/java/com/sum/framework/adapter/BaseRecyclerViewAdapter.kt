package com.sum.framework.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewParent
import android.widget.LinearLayout
import androidx.annotation.IntRange
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * @author mingyan.su
 * @date   2023/3/9 08:13
 * @desc   adapter基类
 * 提供创建ViewHolder能力，提供添加头尾布局能力，dataBinding能力
 */
abstract class BaseRecyclerViewAdapter<T, B : ViewBinding> : RecyclerView.Adapter<BaseViewHolder>() {
    /**
     * 数据列表
     */
    private var data: MutableList<T> = mutableListOf()

    private lateinit var mHeaderLayout: LinearLayout
    private lateinit var mFooterLayout: LinearLayout

    companion object {
        const val HEADER_VIEW = 0x10000111
        const val FOOTER_VIEW = 0x10000222
    }

    /**
     * Item点击事件监听
     */
    val onItemClickListener: ((view: View, position: Int) -> Unit?)? = null

    /**
     * Item长按事件监听
     */
    val onItemLongClickListener: ((view: View, position: Int) -> Boolean) = { view, position -> false }

    /**
     * 子类不可重载，如果有需要请重写[onCreateDefViewHolder]实现自定义ViewHolder
     * 或者重写[getViewBinding]传入布局，不需要创建ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val baseViewHolder: BaseViewHolder
        when (viewType) {
            HEADER_VIEW -> {
                val headerParent: ViewParent? = mHeaderLayout.parent
                if (headerParent is ViewGroup) {
                    headerParent.removeView(mHeaderLayout)
                }
                baseViewHolder = BaseViewHolder(mHeaderLayout)
            }
            FOOTER_VIEW -> {
                val headerParent: ViewParent? = mFooterLayout.parent
                if (headerParent is ViewGroup) {
                    headerParent.removeView(mFooterLayout)
                }
                baseViewHolder = BaseViewHolder(mFooterLayout)
            }
            else -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                baseViewHolder = onCreateDefViewHolder(layoutInflater, parent, viewType)
            }
        }
        return baseViewHolder
    }

    /**
     * 子类可以选择重载该方法，如果有需要可重写[onBindDefViewHolder]，点击事件调用super即可
     */
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(holder.itemView, position)
        }
        holder.itemView.setOnLongClickListener {
            onItemLongClickListener.invoke(holder.itemView, position)
        }
        when (holder.itemViewType) {
            HEADER_VIEW, FOOTER_VIEW -> {
                return
            }
            else -> {
                //holder必须继承自BaseBindViewHolder
                if (holder is BaseBindViewHolder<*>) {
                    holder as BaseBindViewHolder<B>
                    val item = getItem(position - headerLayoutCount)
                    item?.let {
                        onBindDefViewHolder(holder, it, position)
                    }
                }
            }
        }
    }

    /**
     * 子类重写该方法绑定数据
     * 重写[onCreateDefViewHolder]即可实现不同ViewHolder传递
     */
    protected abstract fun onBindDefViewHolder(holder: BaseBindViewHolder<B>, item: T?, position: Int)

    /**
     * 子类不可重载该方法，如有需要请重写[getDefItemViewType]
     */
    override fun getItemViewType(position: Int): Int {
        return if (hasHeaderView() && position == headerViewPosition) {
            HEADER_VIEW
        } else if (hasFooterView() && position == footerViewPosition) {
            FOOTER_VIEW
        } else {
            val realPosition = if (hasHeaderView()) {
                position - 1
            } else {
                position
            }
            getDefItemViewType(realPosition)
        }
    }

    /**
     * 重写此方法，返回你的ViewType
     */
    protected open fun getDefItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    /**
     * 不要重写此方法，如果有需要请重写[getDefItemCount]
     */
    override fun getItemCount(): Int {
        return headerLayoutCount + getDefItemCount() + footerLayoutCount
    }

    /**
     * 重写此方法，返回你的数据量
     */
    protected open fun getDefItemCount(): Int {
        return data.size
    }

    /**
     * 子类实现创建自定义ViewHolder，父类提供了LayoutInflater
     */
    protected open fun onCreateDefViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder {
        return BaseBindViewHolder(getViewBinding(layoutInflater, parent, viewType))
    }

    /**
     * 子类实现ViewBinding，父类提供了LayoutInflater
     * 可以根据不同的viewType传递不同的viewBinding
     */
    abstract fun getViewBinding(layoutInflater: LayoutInflater, parent: ViewGroup, viewType: Int): B

    /**
     * 添加HeaderView
     * @param view
     * @param index view在HeaderView中的位置
     * @return Int
     */
    fun addHeadView(view: View, index: Int = -1): Int {
        if (!this::mHeaderLayout.isInitialized) {
            mHeaderLayout = LinearLayout(view.context)
            mHeaderLayout.orientation = LinearLayout.VERTICAL
            mHeaderLayout.layoutParams = RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        }
        val childCount = mHeaderLayout.childCount
        var mIndex = index
        if (index < 0 || index > childCount) {
            mIndex = childCount
        }
        mHeaderLayout.addView(view, mIndex)
        if (mHeaderLayout.childCount == 1) {
            notifyItemInserted(headerViewPosition)
        }
        return mIndex
    }

    /**
     * 移除头布局
     * @param header
     */
    fun removeHeadView(header: View) {
        if (hasHeaderView()) {
            mHeaderLayout.removeView(header)
            if (mHeaderLayout.childCount == 0) {
                notifyItemRemoved(headerViewPosition)
            }
        }
    }

    /**
     * 移除所有头布局
     */
    fun removeAllHeadView() {
        if (hasHeaderView()) {
            mHeaderLayout.removeAllViews()
            notifyItemRemoved(headerViewPosition)
        }
    }

    /**
     * Heater位置
     */
    val headerViewPosition: Int = 0

    /**
     * 有HeaderView
     * @return Boolean
     */
    fun hasHeaderView(): Boolean {
        return this::mHeaderLayout.isInitialized && mHeaderLayout.childCount > 0
    }

    /**
     * headerView数量
     */
    val headerLayoutCount: Int
        get() {
            return if (hasHeaderView()) {
                1
            } else {
                0
            }
        }

    /**
     * headerView的子View数量
     */
    val headerViewCount: Int
        get() {
            return if (hasHeaderView()) {
                mHeaderLayout.childCount
            } else {
                0
            }
        }

    /**
     * 获取头布局
     */
    val headerBinding: LinearLayout?
        get() {
            return if (this::mHeaderLayout.isInitialized) {
                mHeaderLayout
            } else {
                null
            }
        }

    /**
     * 添加FooterView
     * @param view
     * @param index view在FooterView中的位置
     * @return Int
     */
    fun addFootView(view: View, index: Int = -1): Int {
        if (!this::mFooterLayout.isInitialized) {
            mFooterLayout = LinearLayout(view.context)
            mFooterLayout.orientation = LinearLayout.VERTICAL
            mFooterLayout.layoutParams = RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        }
        val childCount = mFooterLayout.childCount
        var mIndex = index
        if (index < 0 || index > childCount) {
            mIndex = childCount
        }
        mFooterLayout.addView(view, mIndex)
        if (mFooterLayout.childCount == 1) {
            notifyItemInserted(footerViewPosition)
        }
        return mIndex
    }

    /**
     * 移除尾布局
     * @param header
     */
    fun removeFootView(header: View) {
        if (hasFooterView()) {
            mFooterLayout.removeView(header)
            if (mFooterLayout.childCount == 0) {
                notifyItemRemoved(footerViewPosition)
            }
        }
    }

    /**
     * 移除所有尾布局
     */
    fun removeAllFootView() {
        if (hasFooterView()) {
            mFooterLayout.removeAllViews()
            notifyItemRemoved(footerViewPosition)
        }
    }

    /**
     * 有FooterView
     * @return Boolean
     */
    fun hasFooterView(): Boolean {
        return this::mFooterLayout.isInitialized && mFooterLayout.childCount > 0
    }

    /**
     * footer位置
     */
    val footerViewPosition: Int
        get() = headerLayoutCount + data.size

    /**
     * footerLayout数量
     */
    val footerLayoutCount: Int
        get() {
            return if (hasFooterView()) {
                1
            } else {
                0
            }
        }

    /**
     * footerView的子View数量
     */
    val footerViewCount: Int
        get() {
            return if (hasFooterView()) {
                mFooterLayout.childCount
            } else {
                0
            }
        }

    /**
     * 获取尾布局
     */
    val footerBinding: LinearLayout?
        get() {
            return if (this::mFooterLayout.isInitialized) {
                mFooterLayout
            } else {
                null
            }
        }

    /**
     * 获取data
     */
    fun getData(): MutableList<T> {
        return data
    }

    /**
     * 设置数据
     */
    fun setData(list: Collection<T>?) {
        this.data.clear()
        if (!list.isNullOrEmpty()) {
            this.data.addAll(list)
        }

        notifyDataSetChanged()
    }

    /**
     * 添加数据
     *
     * @param newList 添加的数据
     */
    fun addAll(newList: Collection<T>?) {
        if (newList.isNullOrEmpty()) {
            return
        }
        val lastIndex = data.size + headerLayoutCount
        if (this.data.addAll(newList)) {
            notifyItemRangeInserted(lastIndex, newList.size)
        }
    }

    /**
     * 清空数据
     */
    fun clear() {
        this.data.clear()
        notifyDataSetChanged()
    }

    /**
     * 获取指定item
     * @param position
     * @return T
     */
    fun getItem(@IntRange(from = 0) position: Int): T? {
        if (position >= data.size) {
            return null
        }
        return data[position]
    }

    /**
     * 如果返回-1，表示不存在
     * @param item
     * @return Int
     */
    fun getItemPosition(item: T?): Int {
        return if ((item != null && data.isNotEmpty())) {
            data.indexOf(item)
        } else {
            -1
        }
    }

    /**
     * 更新某一个位置上的数据
     * @param position
     * @param data
     */
    fun updateItem(@IntRange(from = 0) position: Int, data: T) {
        if (position >= this.data.size) {
            return
        }
        this.data[position] = data
        notifyItemChanged(position + headerLayoutCount)
    }

    /**
     * 在某一个位置上添加一条数据
     * @param position
     * @param data
     */
    fun setItem(@IntRange(from = 0) position: Int, data: T) {
        //如果超出则添加到最后
        val realPosition = if (position > this.data.size) {
            this.data.size
        } else {
            position
        }
        this.data.add(realPosition, data)
        notifyItemInserted(realPosition + headerLayoutCount)
    }

    /**
     * 增加一条数据到末尾
     * @param data
     */
    fun addItem(data: T) {
        this.data.add(data)
        notifyItemInserted(this.data.size - 1 + headerLayoutCount)
    }

    /**
     * 移除某个位置上的数据
     * @param position
     * @return T?
     */
    fun removeAt(@IntRange(from = 0) position: Int): T? {
        if (position >= data.size) {
            return null
        }
        val remove = this.data.removeAt(position)
        notifyItemRemoved(position + headerLayoutCount)
        return remove
    }

    /**
     * 移除某个item数据
     * @param data
     * @return -1表示不存在该条数据
     */
    fun remove(data: T): Int {
        val index = this.data.indexOf(data)
        if (index != -1) {
            removeAt(index)
        }
        return index
    }

}
package com.sum.tea.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sum.tea.listener.OnItemClickListener
import java.util.*

/**
 * @创建者 mingyan.su
 * @创建时间 2023/2/19 16:12
 * @类描述 封装adapter
 */
abstract class BaseListAdapter<T>(protected var mContext: Context?) :
    RecyclerView.Adapter<BaseViewHolder>() {

    protected var mInflater: LayoutInflater? = null
    protected var mLastPosition = -1

    /**
     * 开启动画
     */
    private var mOpenAnimationEnable = true
    protected var mDataList: MutableList<T> = ArrayList()

    init {
        mInflater =
            mContext?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return if (mContext != null && mInflater != null) {
            BaseViewHolder(mInflater!!.inflate(layoutId, parent, false))
        } else {
            BaseViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        onBindItemHolder(holder, position)
        holder.itemView.setOnClickListener { v ->
            onItemClickListener?.onItemClick(v, position)
        }
    }

    /**
     * 局部刷新关键：带payload的这个onBindViewHolder方法必须实现
     */
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int, payloads: List<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            onBindItemHolder(holder, position, payloads)
        }
    }

    /**
     * 动画开关
     *
     * @param enabled true 开启 ，false 关闭
     */
    fun setOpenAnimationEnable(enabled: Boolean) {
        mOpenAnimationEnable = enabled
    }

    /**
     * 添加动画
     *
     * @param holder   ViewHolder
     * @param position item 位置
     */
    private fun addAnimate(holder: BaseViewHolder, position: Int) {
        if (mOpenAnimationEnable && mLastPosition < position) {
            holder.itemView.alpha = 0f
            holder.itemView.animate().alpha(1f).start()
            mLastPosition = position
        }
    }

    /**
     * 获取布局id
     *
     * @return 布局id
     */
    abstract val layoutId: Int

    /**
     * 绑定控件
     *
     * @param holder   ViewHolder
     * @param position item 位置
     */
    abstract fun onBindItemHolder(holder: BaseViewHolder, position: Int)

    /**
     * 绑定控件
     *
     * @param holder   ViewHolder
     * @param position item 位置
     */
    fun onBindItemHolder(holder: BaseViewHolder?, position: Int, payloads: List<Any>?) {}

    /**
     * 获取总数
     *
     * @return item数目
     */
    override fun getItemCount(): Int {
        return mDataList.size
    }

    /**
     * 获取List列表数据
     *
     * @return 列表数据
     */
    val dataList: List<T>
        get() = mDataList

    /**
     * 设置数据
     *
     * @param list 要设置的数据
     */
    fun setDataList(list: MutableList<T>) {
        mLastPosition = -1
        mDataList = list
        notifyDataSetChanged()
    }

    /**
     * 添加数据
     *
     * @param list 添加的数据
     */
    fun addAll(mLastPosition: Int, list: Collection<T>?) {
        if (mDataList.addAll(mLastPosition, list!!)) {
            notifyDataSetChanged()
        }
    }

    /**
     * 添加数据
     *
     * @param list 添加的数据
     */
    fun addAll(list: Collection<T>) {
        val lastIndex = mDataList.size
        if (mDataList.addAll(list)) {
            notifyItemRangeInserted(lastIndex, list.size)
        }
    }

    /**
     * 移除某条数据
     *
     * @param position 移除数据的位置
     */
    fun remove(position: Int) {
        if (position < 0 || position >= mDataList.size) {
            return
        }
        mDataList.removeAt(position)
        notifyItemRemoved(position)

        // 如果移除的是最后一个，忽略
        if (position != dataList.size) {
            notifyItemRangeChanged(position, mDataList.size - position)
        }
    }

    /**
     * 清空列表
     */
    fun clear() {
        mDataList.clear()
        notifyDataSetChanged()
    }

    var onItemClickListener: OnItemClickListener? = null

    /**
     * item点击监听
     *
     * @param listener 监听回调
     */
    fun setonItemClickListener(listener: OnItemClickListener?) {
        onItemClickListener = listener
    }
}

package com.sum.banner.base

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sum.banner.BannerViewPager
import com.sum.banner.utils.BannerUtils
import java.util.ArrayList

/**
 * BannerAdapter基类
 */
abstract class BaseBannerAdapter<T, H : BaseViewHolder<T>> : RecyclerView.Adapter<H>() {
    companion object {
        const val MAX_VALUE = Int.MAX_VALUE / 2
    }

    protected var mList: MutableList<T> = ArrayList()
    private var isCanLoop = false

    private var mPageClickListener: BannerViewPager.OnPageClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): H {
        val viewHolder = onCreateHolder(parent, viewType)
        viewHolder.itemView.setOnClickListener { clickedView: View? ->
            val adapterPosition = viewHolder.adapterPosition
            if (mPageClickListener != null && adapterPosition != RecyclerView.NO_POSITION) {
                val realPosition: Int =
                    BannerUtils.getRealPosition(viewHolder.adapterPosition, getListSize())
                mPageClickListener?.onPageClick(clickedView, realPosition)
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: H, position: Int) {
        val realPosition: Int = BannerUtils.getRealPosition(position, getListSize())
        onBindView(holder, mList[realPosition], realPosition, getListSize())
    }

    override fun getItemViewType(position: Int): Int {
        val realPosition: Int = BannerUtils.getRealPosition(position, getListSize())
        return getViewType(realPosition)
    }

    override fun getItemCount(): Int {
        return if (isCanLoop && getListSize() > 1) {
            MAX_VALUE
        } else {
            getListSize()
        }
    }

    fun getData(): MutableList<T> {
        return mList
    }

    fun setData(list: List<T>?) {
        if (null != list) {
            mList.clear()
            mList.addAll(list)
        }
        notifyDataSetChanged()
    }

    fun addData(list: List<T>?) {
        list?.let {
            mList.addAll(it)
        }
        notifyDataSetChanged()
    }

    fun setCanLoop(canLoop: Boolean) {
        isCanLoop = canLoop
    }

    fun setPageClickListener(pageClickListener: BannerViewPager.OnPageClickListener?) {
        mPageClickListener = pageClickListener
    }

    fun getListSize(): Int {
        return mList.size
    }

    protected fun getViewType(position: Int): Int {
        return 0
    }

    fun isCanLoop(): Boolean {
        return isCanLoop
    }

    /**
     * Generally,there is no need to override this method in subclasses.
     *
     * This method called by [.onCreateViewHolder] to create a default [ ]
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     * an adapter position.
     * @param itemView Item View.
     * @param viewType The view type of the new View.
     * @return ViewHolder extends [BaseViewHolder].
     */
    abstract fun onCreateHolder(
        parent: ViewGroup,
        viewType: Int
    ): H

    /**
     * @param holder The ViewHolder which should be updated to represent the contents of the
     * item at the given position in the data set.
     * @param data Current item data.
     * @param position Current item position.
     * @param pageSize Page size of BVP,equals [BaseBannerAdapter.getListSize].
     */
    abstract fun onBindView(
        holder: H,
        data: T,
        position: Int,
        pageSize: Int
    )
}
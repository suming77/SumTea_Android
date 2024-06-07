package com.sum.video.manager

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.text.span.TextAnnotation.Position
import com.sum.framework.log.LogUtil
import com.sum.video.listener.OnViewPagerListener

/**
 * 单页滑动LinearLayoutManager
 */
class PagerLayoutManager : LinearLayoutManager {
    private var mPagerSnapHelper: PagerSnapHelper? = null
    private var mOnViewPagerListener: OnViewPagerListener? = null
    private var mRecyclerView: RecyclerView? = null
    private var mDrift = 0 //位移，用来判断移动方向
    private var mPosition = 0;

    companion object {
        private const val TAG = "PagerLayoutManager"
    }

    constructor(context: Context?, orientation: Int) : this(context, orientation, false,0)

    constructor(context: Context?, orientation: Int, reverseLayout: Boolean, position: Int) : super(context, orientation, reverseLayout) {
        init()
        this.mPosition = position
    }

    private fun init() {
        mPagerSnapHelper = PagerSnapHelper()
    }

    override fun onAttachedToWindow(view: RecyclerView) {
        super.onAttachedToWindow(view)
        mPagerSnapHelper?.attachToRecyclerView(view)
        this.mRecyclerView = view
        mRecyclerView?.addOnChildAttachStateChangeListener(mChildAttachStateChangeListener)
        mRecyclerView?.scrollToPosition(mPosition)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        super.onLayoutChildren(recycler, state)
    }

    /**
     * 滑动状态的改变
     * 缓慢拖拽-> SCROLL_STATE_DRAGGING
     * 快速滚动-> SCROLL_STATE_SETTLING
     * 空闲状态-> SCROLL_STATE_IDLE
     *
     * @param state
     */
    override fun onScrollStateChanged(state: Int) {
        when (state) {
            RecyclerView.SCROLL_STATE_IDLE -> {
                LogUtil.i("空闲=====", tag = TAG)
                val viewIdle = mPagerSnapHelper?.findSnapView(this)
                if (viewIdle != null) {
                    val positionIdle = getPosition(viewIdle)
                    if (childCount == 1) {
                        mOnViewPagerListener?.onPageSelected(positionIdle, positionIdle == itemCount - 1, viewIdle)
                    }
                }
            }
            RecyclerView.SCROLL_STATE_DRAGGING -> {
                LogUtil.i("缓慢拖拽=====", tag = TAG)
                val viewDrag = mPagerSnapHelper?.findSnapView(this)
//                if (viewDrag != null) {
//                    val positionDrag = getPosition(viewDrag)
//                }
            }
            RecyclerView.SCROLL_STATE_SETTLING -> {
                LogUtil.i("快速滚动=====", tag = TAG)
                val viewSettling = mPagerSnapHelper?.findSnapView(this)
//                if (viewSettling != null) {
//                    val positionSettling = getPosition(viewSettling)
//                }
            }
        }
    }

    /**
     * 监听竖直方向的相对偏移量
     *
     * @param dy
     * @param recycler
     * @param state
     * @return
     */
    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State): Int {
        mDrift = dy
        return super.scrollVerticallyBy(dy, recycler, state)
    }

    /**
     * 监听水平方向的相对偏移量
     *
     * @param dx
     * @param recycler
     * @param state
     * @return
     */
    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State): Int {
        mDrift = dx
        return super.scrollHorizontallyBy(dx, recycler, state)
    }

    /**
     * 设置监听
     *
     * @param listener
     */
    fun setOnViewPagerListener(listener: OnViewPagerListener?) {
        mOnViewPagerListener = listener
    }

    private val mChildAttachStateChangeListener: RecyclerView.OnChildAttachStateChangeListener =
        object : RecyclerView.OnChildAttachStateChangeListener {
            /**
             * itemView依赖Window
             */
            override fun onChildViewAttachedToWindow(view: View) {
                if (childCount == 1) {
                    mOnViewPagerListener?.onInitComplete(view)
                }
            }

            /**
             * itemView脱离Window
             */
            override fun onChildViewDetachedFromWindow(view: View) {
                if (mDrift >= 0) {
                    LogUtil.i("onPageRelease========true", tag = TAG)
                    mOnViewPagerListener?.onPageRelease(
                        true,
                        getPosition(view),
                        view
                    )
                } else {
                    LogUtil.i("onPageRelease========false", tag = TAG)
                    mOnViewPagerListener?.onPageRelease(
                        false,
                        getPosition(view),
                        view
                    )
                }
            }
        }
}
package com.sum.framework.base

import android.content.Context
import com.sum.framework.log.LogUtil.e
import com.sum.framework.R
import androidx.appcompat.app.AppCompatDialog
import android.content.DialogInterface
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.SparseArray
import android.util.SparseIntArray
import android.view.Gravity
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.os.SystemClock
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import java.lang.Exception
import java.util.ArrayList

/**
 * BaseDialog
 */
class BaseDialog @JvmOverloads constructor(
    context: Context?,
    themeResId: Int = R.style.BaseDialogStyle
) : AppCompatDialog(context, if (themeResId > 0) themeResId else R.style.BaseDialogStyle),
    DialogInterface.OnShowListener,
    DialogInterface.OnCancelListener,
    DialogInterface.OnDismissListener {


    companion object {
        private val HANDLER = Handler(Looper.getMainLooper())
    }

    private var mOnShowListeners: MutableList<OnShowListener?>? = null
    private var mOnCancelListeners: MutableList<OnCancelListener?>? = null
    private var mOnDismissListeners: MutableList<OnDismissListener?>? = null

    /**
     * 添加一个取消监听器
     *
     * @param listener 监听器对象
     */
    fun addOnShowListener(listener: OnShowListener?) {
        if (mOnShowListeners == null) {
            mOnShowListeners = ArrayList()
            super.setOnShowListener(this)
        }
        mOnShowListeners?.add(listener)
    }

    /**
     * 添加一个取消监听器
     *
     * @param listener 监听器对象
     */
    fun addOnCancelListener(listener: OnCancelListener?) {
        if (mOnCancelListeners == null) {
            mOnCancelListeners = ArrayList()
            super.setOnCancelListener(this)
        }
        mOnCancelListeners?.add(listener)
    }

    /**
     * 添加一个销毁监听器
     *
     * @param listener 监听器对象
     */
    fun addOnDismissListener(listener: OnDismissListener?) {
        if (mOnDismissListeners == null) {
            mOnDismissListeners = ArrayList()
            super.setOnDismissListener(this)
        }
        mOnDismissListeners?.add(listener)
    }

    /**
     * 设置显示监听器集合
     */
    private fun setOnShowListeners(listeners: MutableList<OnShowListener?>?) {
        super.setOnShowListener(this)
        mOnShowListeners = listeners
    }

    /**
     * 设置取消监听器集合
     */
    private fun setOnCancelListeners(listeners: MutableList<OnCancelListener?>?) {
        super.setOnCancelListener(this)
        mOnCancelListeners = listeners
    }

    /**
     * 设置销毁监听器集合
     */
    private fun setOnDismissListeners(listeners: MutableList<OnDismissListener?>?) {
        super.setOnDismissListener(this)
        mOnDismissListeners = listeners
    }

    /**
     * [DialogInterface.OnShowListener]
     */
    override fun onShow(dialog: DialogInterface) {
        try {
            mOnShowListeners?.let {
                val objects: Array<*> = it.toTypedArray()
                for (obj in objects) {
                    if (obj is OnShowListener) {
                        obj.onShow(this)
                    }
                }
            }
        } catch (e: Exception) {
            e(e)
        }
    }

    /**
     * [DialogInterface.OnCancelListener]
     */
    override fun onCancel(dialog: DialogInterface) {
        try {
            mOnCancelListeners?.let {
                val objects: Array<*> = it.toTypedArray()
                for (obj in objects) {
                    if (obj is OnCancelListener) {
                        obj.onCancel(this)
                    }
                }
            }
        } catch (e: Exception) {
            e(e)
        }
    }

    /**
     * [DialogInterface.OnDismissListener]
     */
    override fun onDismiss(dialog: DialogInterface) {
        try {
            // 移除和这个 Dialog 相关的消息回调
            HANDLER.removeCallbacksAndMessages(this)
            mOnDismissListeners?.let {
                val objects: Array<*> = it.toTypedArray()
                for (obj in objects) {
                    if (obj is OnDismissListener) {
                        obj.onDismiss(this)
                    }
                }
            }
        } catch (e: Exception) {
            e(e)
        }
    }

    /**
     * 延迟执行
     */
    fun post(r: Runnable): Boolean {
        return postDelayed(r, 0)
    }

    /**
     * 延迟一段时间执行
     */
    fun postDelayed(r: Runnable, delayMillis: Long): Boolean {
        var millis = delayMillis
        if (millis < 0) {
            millis = 0
        }
        return postAtTime(r, SystemClock.uptimeMillis() + millis)
    }

    /**
     * 在指定的时间执行
     */
    fun postAtTime(r: Runnable, uptimeMillis: Long): Boolean {
        return HANDLER.postAtTime(r, this, uptimeMillis)
    }

    interface OnClickListener {
        fun onClick(dialog: BaseDialog?, view: View)
    }

    interface OnShowListener {
        fun onShow(dialog: BaseDialog?)
    }

    interface OnCancelListener {
        fun onCancel(dialog: BaseDialog?)
    }

    interface OnDismissListener {
        fun onDismiss(dialog: BaseDialog?)
    }

    /**
     * Dialog 动画样式
     */
    object AnimStyle {
        // 缩放动画
        val SCALE = R.style.ScaleAnimStyle

        // IOS 动画
        val IOS = R.style.IOSAnimStyle

        // 吐司动画
        const val TOAST = android.R.style.Animation_Toast

        // 顶部弹出动画
        val TOP = R.style.TopAnimStyle

        // 底部弹出动画
        val BOTTOM = R.style.BottomAnimStyle

        // 左边弹出动画
        val LEFT = R.style.LeftAnimStyle

        // 右边弹出动画
        val RIGHT = R.style.RightAnimStyle

        // 默认动画效果
        val DEFAULT = R.style.ScaleAnimStyle
    }

    open class Builder<B : Builder<B>>(
        /**
         * 获取上下文对象（仅供子类调用）
         */
        // Context 对象
        protected val context: Context
    ) {
        // Dialog 布局
        protected var mContentView: View? = null

        /**
         * 获取当前 Dialog 对象（仅供子类调用）
         */
        protected var dialog: BaseDialog? = null
            private set

        // Dialog Show 监听
        private var mOnShowListeners: MutableList<OnShowListener?>? = null

        // Dialog Cancel 监听
        private var mOnCancelListeners: MutableList<OnCancelListener?>? = null

        // Dialog Dismiss 监听
        private var mOnDismissListeners: MutableList<OnDismissListener?>? = null

        // Dialog Key 监听
        private var mOnKeyListener: DialogInterface.OnKeyListener? = null

        /**
         * 是否设置了取消（仅供子类调用）
         */
        // 点击空白是否能够取消  默认点击阴影可以取消
        protected var isCancelable = true
            private set

        //点击返回键是否能够取消
        private var mTouchOutside = true
        private val mTextArray = SparseArray<CharSequence>()
        private val mVisibilityArray = SparseIntArray()
        private val mBackgroundArray = SparseArray<Drawable>()
        private val mImageArray = SparseArray<Drawable>()
        private val mClickArray = SparseArray<OnClickListener>()

        // 主题
        private var mThemeResId = -1

        // 动画
        private var mAnimations = -1

        /**
         * 获取 Dialog 重心（仅供子类调用）
         */
        // 位置
        protected var gravity = Gravity.CENTER

        // 宽度和高度
        private var mWidth = WRAP_CONTENT
        private var mHeight = WRAP_CONTENT

        // 垂直和水平边距
        private var mVerticalMargin = 0
        private var mHorizontalMargin = 0

        /**
         * 延迟执行，一定要在创建了Dialog之后调用（供子类调用）
         */
        protected fun post(r: Runnable): Boolean {
            return dialog?.post(r) ?: false
        }

        /**
         * 延迟一段时间执行，一定要在创建了Dialog之后调用（仅供子类调用）
         */
        protected fun postDelayed(r: Runnable, delayMillis: Long): Boolean {
            return dialog?.postDelayed(r, delayMillis) ?: false
        }

        /**
         * 在指定的时间执行，一定要在创建了Dialog之后调用（仅供子类调用）
         */
        protected fun postAtTime(r: Runnable, uptimeMillis: Long): Boolean {
            return dialog?.postAtTime(r, uptimeMillis) ?: false
        }

        /**
         * 是否可以取消
         */
        fun setCancelable(cancelable: Boolean): B {
            isCancelable = cancelable
            return this as B
        }

        /**
         * 设置重心位置
         */
        fun setGravity(gravity: Int): B {
            // 适配 Android 4.2 新特性，布局反方向（开发者选项 - 强制使用从右到左的布局方向）
            var gravity = gravity
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                gravity = Gravity
                        .getAbsoluteGravity(gravity, context.resources.configuration.layoutDirection)
            }
            this.gravity = gravity
            if (mAnimations == -1) {
                when (this.gravity) {
                    Gravity.TOP -> mAnimations = AnimStyle.TOP
                    Gravity.BOTTOM -> mAnimations = AnimStyle.BOTTOM
                    Gravity.LEFT -> mAnimations = AnimStyle.LEFT
                    Gravity.RIGHT -> mAnimations = AnimStyle.RIGHT
                    else -> {}
                }
            }
            return this as B
        }

        /**
         * 获取资源对象（仅供子类调用）
         */
        protected val resources: Resources
            protected get() = context.resources

        /**
         * 根据 id 获取一个文本（仅供子类调用）
         */
        protected fun getText(@StringRes resId: Int): CharSequence {
            return context.getText(resId)
        }

        /**
         * 根据 id 获取一个 String（仅供子类调用）
         */
        protected fun getString(@StringRes resId: Int): String {
            return context.getString(resId)
        }

        /**
         * 根据 id 获取一个颜色（仅供子类调用）
         */
        protected fun getColor(@ColorRes id: Int): Int {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                context.getColor(id)
            } else {
                context.resources.getColor(id)
            }
        }

        /**
         * 根据 id 获取一个 Drawable（仅供子类调用）
         */
        protected fun getDrawable(@DrawableRes id: Int): Drawable? {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                context.getDrawable(id)
            } else {
                context.resources.getDrawable(id)
            }
        }

        /**
         * 根据 id 查找 View（仅供子类调用）
         */
        protected fun <T : View?> findViewById(@IdRes id: Int): T {
            return mContentView!!.findViewById(id)
        }

        /**
         * 销毁当前 Dialog（仅供子类调用）
         */
        protected fun dismiss() {
            dialog?.dismiss()
        }

        /**
         * 设置主题 id
         */
        fun setThemeStyle(@StyleRes themeResId: Int): B {
            mThemeResId = themeResId
            return this as B
        }

        /**
         * 设置布局
         */
        fun setContentView(@LayoutRes layoutId: Int): B {
            return setContentView(LayoutInflater.from(context).inflate(layoutId, null))
        }

        fun setContentView(view: View): B {
            mContentView = view
            return this as B
        }

        /**
         * 设置宽度
         */
        fun setWidth(width: Int): B {
            mWidth = width
            return this as B
        }

        /**
         * 设置高度
         */
        fun setHeight(height: Int): B {
            mHeight = height
            return this as B
        }

        /**
         * 点击返回按键是否可以取消
         */
        fun setCanceledOnTouchOutside(cancelable: Boolean): B {
            mTouchOutside = cancelable
            return this as B
        }

        /**
         * 设置动画，已经封装好几种样式，具体可见[AnimStyle]类
         */
        fun setAnimStyle(@StyleRes resId: Int): B {
            mAnimations = resId
            return this as B
        }

        /**
         * 设置垂直间距
         */
        fun setVerticalMargin(margin: Int): B {
            mVerticalMargin = margin
            return this as B
        }

        /**
         * 设置水平间距
         */
        fun setHorizontalMargin(margin: Int): B {
            mHorizontalMargin = margin
            return this as B
        }

        /**
         * 添加显示监听
         */
        fun addOnShowListener(listener: OnShowListener): B {
            if (mOnShowListeners == null) {
                mOnShowListeners = ArrayList()
            }
            mOnShowListeners!!.add(listener)
            return this as B
        }

        /**
         * 添加取消监听
         */
        fun addOnCancelListener(listener: OnCancelListener): B {
            if (mOnCancelListeners == null) {
                mOnCancelListeners = ArrayList()
            }
            mOnCancelListeners!!.add(listener)
            return this as B
        }

        /**
         * 添加销毁监听
         */
        fun addOnDismissListener(listener: OnDismissListener): B {
            if (mOnDismissListeners == null) {
                mOnDismissListeners = ArrayList()
            }
            mOnDismissListeners!!.add(listener)
            return this as B
        }

        /**
         * 设置按键监听
         */
        fun setOnKeyListener(onKeyListener: DialogInterface.OnKeyListener): B {
            mOnKeyListener = onKeyListener
            return this as B
        }

        /**
         * 设置文本
         */
        fun setText(@IdRes id: Int, @StringRes resId: Int): B {
            return setText(id, context.resources.getString(resId))
        }

        fun setText(@IdRes id: Int, text: CharSequence): B {
            mTextArray.put(id, text)
            return this as B
        }

        /**
         * 设置可见状态
         */
        fun setVisibility(@IdRes id: Int, visibility: Int): B {
            mVisibilityArray.put(id, visibility)
            return this as B
        }

        /**
         * 设置背景
         */
        fun setBackground(@IdRes id: Int, @DrawableRes resId: Int): B {
            return setBackground(id, context.resources.getDrawable(resId))
        }

        fun setBackground(@IdRes id: Int, drawable: Drawable): B {
            mBackgroundArray.put(id, drawable)
            return this as B
        }

        /**
         * 设置图片
         */
        fun setImageDrawable(@IdRes id: Int, @DrawableRes resId: Int): B {
            return setBackground(id, context.resources.getDrawable(resId))
        }

        fun setImageDrawable(@IdRes id: Int, drawable: Drawable): B {
            mImageArray.put(id, drawable)
            return this as B
        }

        /**
         * 设置点击事件
         */
        fun setOnClickListener(@IdRes id: Int, listener: OnClickListener): B {
            mClickArray.put(id, listener)
            return this as B
        }

        /**
         * 创建
         */
        open fun create(): BaseDialog {

            // 判断布局是否为空
            requireNotNull(mContentView) { "Dialog layout cannot be empty" }
            val layoutParams = mContentView?.layoutParams
            layoutParams?.let {
                if (mWidth == ViewGroup.LayoutParams.WRAP_CONTENT) {
                    mWidth = layoutParams.width
                }
                if (mHeight == ViewGroup.LayoutParams.WRAP_CONTENT) {
                    mHeight = layoutParams.height
                }
            }

//            // 判断有没有设置主题
//            if (mThemeResId == -1) {
//                mDialog = new BaseDialog(mContext);
//            } else {
//                mDialog = new BaseDialog(mContext, mThemeResId);
//            }
            dialog = createDialog(context, mThemeResId)
            dialog?.setContentView(mContentView!!)
            dialog?.setCancelable(isCancelable)
            dialog?.setCanceledOnTouchOutside(mTouchOutside)
            if (mOnShowListeners != null) {
                dialog?.setOnShowListeners(mOnShowListeners)
            }
            if (mOnCancelListeners != null) {
                dialog?.setOnCancelListeners(mOnCancelListeners)
            }
            if (mOnDismissListeners != null) {
                dialog?.setOnDismissListeners(mOnDismissListeners)
            }
            if (mOnKeyListener != null) {
                dialog?.setOnKeyListener(mOnKeyListener)
            }

            // 判断有没有设置动画
            if (mAnimations == -1) {
                // 没有的话就设置默认的动画
                mAnimations = AnimStyle.DEFAULT
            }

            // 设置参数
            val params = dialog?.window?.attributes
            params?.width = mWidth
            params?.height = mHeight
            params?.gravity = gravity
            params?.windowAnimations = mAnimations
            params?.horizontalMargin = mHorizontalMargin.toFloat()
            params?.verticalMargin = mVerticalMargin.toFloat()
            dialog?.window?.attributes = params

            // 设置文本
            for (i in 0 until mTextArray.size()) {
                (mContentView?.findViewById<View>(mTextArray.keyAt(i)) as? TextView)?.text = mTextArray.valueAt(i)
            }

            // 设置可见状态
            for (i in 0 until mVisibilityArray.size()) {
                mContentView?.findViewById<View>(mVisibilityArray.keyAt(i))?.visibility = mVisibilityArray.valueAt(i)
            }

            // 设置背景
            for (i in 0 until mBackgroundArray.size()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mContentView?.findViewById<View>(mBackgroundArray.keyAt(i))?.background =
                        mBackgroundArray.valueAt(i)
                } else {
                    mContentView?.findViewById<View>(mBackgroundArray.keyAt(i))?.background = mBackgroundArray.valueAt(i)
                }
            }

            // 设置图片
            for (i in 0 until mImageArray.size()) {
                (mContentView?.findViewById<View>(mImageArray.keyAt(i)) as ImageView).setImageDrawable(
                    mImageArray.valueAt(
                        i
                    )
                )
            }

            // 设置点击事件
            for (i in 0 until mClickArray.size()) {
                mContentView?.findViewById<View>(mClickArray.keyAt(i))
                        ?.setOnClickListener(ViewClickWrapper(dialog, mClickArray.valueAt(i)))
            }
            return dialog!!
        }

        /**
         * 创建对话框对象（子类可以重写此方法来改变 Dialog 类型）
         */
        protected fun createDialog(context: Context?, themeResId: Int): BaseDialog {
            return BaseDialog(context, themeResId)
        }

        /**
         * 显示
         */
        open fun show(): BaseDialog? {
            val dialog = create()
            dialog?.show()
            return dialog
        }

        companion object {
            protected const val MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT
            protected const val WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT
        }
    }

    /**
     * 点击事件包装类
     */
    private class ViewClickWrapper(private val mDialog: BaseDialog?, listener: OnClickListener) :
        View.OnClickListener {
        private val mListener: OnClickListener
        override fun onClick(v: View) {
            mListener?.onClick(mDialog, v)
        }

        init {
            mListener = listener
        }
    }

    /**
     * 显示监听包装类
     */
    class ShowListenerWrapper(private val mListener: DialogInterface.OnShowListener?) :
        OnShowListener {
        override fun onShow(dialog: BaseDialog?) {
            mListener?.onShow(dialog)
        }
    }

    /**
     * 取消监听包装类
     */
    class CancelListenerWrapper private constructor(private val mListener: DialogInterface.OnCancelListener?) :
        OnCancelListener {
        override fun onCancel(dialog: BaseDialog?) {
            mListener?.onCancel(dialog)
        }
    }

    /**
     * 销毁监听包装类
     */
    class DismissListenerWrapper private constructor(private val mListener: DialogInterface.OnDismissListener?) :
        OnDismissListener {
        override fun onDismiss(dialog: BaseDialog?) {
            mListener?.onDismiss(dialog)
        }
    }
}
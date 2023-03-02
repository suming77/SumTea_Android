package com.sum.framework.base

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.TextUtils
import android.text.util.Linkify
import android.view.View
import android.view.View.OnLongClickListener
import android.view.View.OnTouchListener
import android.view.animation.AlphaAnimation
import android.widget.*
import androidx.annotation.IdRes
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.RecyclerView

/**
 * ViewHolder基类
 */
open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val mConvertView: View = itemView
    private val mViews: SparseArrayCompat<View?> = SparseArrayCompat()

    fun <T : View?> getView(@IdRes viewId: Int): T? {
        var view = mViews[viewId]
        if (view == null) {
            view = mConvertView.findViewById(viewId)
            mViews.put(viewId, view)
        }
        return view as T?
    }

    fun getImageView(@IdRes viewId: Int): ImageView {
        return getView<ImageView>(viewId)!!
    }

    fun getTextView(@IdRes viewId: Int): TextView {
        return getView<TextView>(viewId)!!
    }

    fun setVisibility(@IdRes viewId: Int, visibility: Int): BaseViewHolder {
        val view = getView<View>(viewId)!!
        view.visibility = visibility
        return this
    }

    fun setText(@IdRes viewId: Int, str: String?): BaseViewHolder {
        val textView = getTextView(viewId)
        if (TextUtils.isEmpty(str)) {
            textView.text = ""
        } else {
            textView.text = str
        }
        textView.text = str
        return this
    }

    fun setOnClickListener(@IdRes viewId: Int, listener: View.OnClickListener?): BaseViewHolder {
        getView<View>(viewId)!!.setOnClickListener(listener)
        return this
    }

    /**
     * 设置imageView图片
     */
    fun setImageResource(viewId: Int, resId: Int): BaseViewHolder {
        val view = getView<ImageView>(viewId)!!
        view.setImageResource(resId)
        return this
    }

    /**
     * 设置imageView图片
     */
    fun setImageBitmap(viewId: Int, bitmap: Bitmap?): BaseViewHolder {
        val view = getView<ImageView>(viewId)!!
        view.setImageBitmap(bitmap)
        return this
    }

    /**
     * 设置imageView图片
     */
    fun setImageDrawable(viewId: Int, drawable: Drawable?): BaseViewHolder {
        val view = getView<ImageView>(viewId)!!
        view.setImageDrawable(drawable)
        return this
    }

    /**
     * 设置背景颜色
     */
    fun setBackgroundColor(viewId: Int, color: Int): BaseViewHolder {
        val view = getView<View>(viewId)!!
        view.setBackgroundColor(color)
        return this
    }

    /**
     * 设置背景颜色
     */
    fun setBackgroundRes(viewId: Int, backgroundRes: Int): BaseViewHolder {
        val view = getView<View>(viewId)!!
        view.setBackgroundResource(backgroundRes)
        return this
    }

    /**
     * 设置text颜色
     */
    fun setTextColor(viewId: Int, textColor: Int): BaseViewHolder {
        val view = getView<TextView>(viewId)!!
        view.setTextColor(textColor)
        return this
    }

    /**
     * 设置透明度
     */
    @SuppressLint("NewApi")
    fun setAlpha(viewId: Int, value: Float): BaseViewHolder {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView<View>(viewId)!!.alpha = value
        } else {
            // Pre-honeycomb hack to set Alpha value
            val alpha = AlphaAnimation(value, value)
            alpha.duration = 0
            alpha.fillAfter = true
            getView<View>(viewId)!!.startAnimation(alpha)
        }
        return this
    }

    fun linkify(viewId: Int): BaseViewHolder {
        val view = getView<TextView>(viewId)!!
        Linkify.addLinks(view, Linkify.ALL)
        return this
    }

    fun setTypeface(typeface: Typeface?, vararg viewIds: Int): BaseViewHolder {
        for (viewId in viewIds) {
            val view = getView<TextView>(viewId)!!
            view.typeface = typeface
            view.paintFlags = view.paintFlags or Paint.SUBPIXEL_TEXT_FLAG
        }
        return this
    }

    fun setProgress(viewId: Int, progress: Int): BaseViewHolder {
        val view = getView<ProgressBar>(viewId)!!
        view.progress = progress
        return this
    }

    fun setProgress(viewId: Int, progress: Int, max: Int): BaseViewHolder {
        val view = getView<ProgressBar>(viewId)!!
        view.max = max
        view.progress = progress
        return this
    }

    fun setMax(viewId: Int, max: Int): BaseViewHolder {
        val view = getView<ProgressBar>(viewId)!!
        view.max = max
        return this
    }

    fun setRating(viewId: Int, rating: Float): BaseViewHolder {
        val view = getView<RatingBar>(viewId)!!
        view.rating = rating
        return this
    }

    fun setRating(viewId: Int, rating: Float, max: Int): BaseViewHolder {
        val view = getView<RatingBar>(viewId)!!
        view.max = max
        view.rating = rating
        return this
    }

    fun setTag(viewId: Int, tag: Any?): BaseViewHolder {
        val view = getView<View>(viewId)!!
        view.tag = tag
        return this
    }

    fun setTag(viewId: Int, key: Int, tag: Any?): BaseViewHolder {
        val view = getView<View>(viewId)!!
        view.setTag(key, tag)
        return this
    }

    fun setChecked(viewId: Int, checked: Boolean): BaseViewHolder {
        val view = getView<View>(viewId) as Checkable
        view.isChecked = checked
        return this
    }

    fun setSelected(viewId: Int, isSelect: Boolean): BaseViewHolder {
        val view = getView<View>(viewId)!!
        view.isSelected = isSelect
        return this
    }

    fun setOnTouchListener(viewId: Int, listener: OnTouchListener?): BaseViewHolder {
        val view = getView<View>(viewId)!!
        view.setOnTouchListener(listener)
        return this
    }

    fun setOnLongClickListener(viewId: Int, listener: OnLongClickListener?): BaseViewHolder {
        val view = getView<View>(viewId)!!
        view.setOnLongClickListener(listener)
        return this
    }
}

package com.sum.framework.ext

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.sum.framework.helper.SumAppHelper
import com.sum.framework.manager.AppManager
import java.math.BigDecimal
import java.text.DecimalFormat

/**
 * Description: 资源操作相关
 */

fun Context.color(id: Int) = ContextCompat.getColor(this, id)

fun Context.string(id: Int) = resources.getString(id)

fun Context.stringArray(id: Int) = resources.getStringArray(id)

fun Context.drawable(id: Int) = ContextCompat.getDrawable(this, id)

fun Context.dimenPx(id: Int) = resources.getDimensionPixelSize(id)

fun Context.integer(id: Int) = resources.getInteger(id)


fun View.color(id: Int) = context.color(id)

fun View.string(id: Int) = context.string(id)

fun View.stringArray(id: Int) = context.stringArray(id)

fun View.drawable(id: Int) = context.drawable(id)

fun View.dimenPx(id: Int) = context.dimenPx(id)

fun View.integer(id: Int) = context.integer(id)


fun Fragment.color(id: Int) = requireContext().color(id)

fun Fragment.string(id: Int) = requireContext().string(id)

fun Fragment.stringArray(id: Int) = requireContext().stringArray(id)

fun Fragment.drawable(id: Int) = requireContext().drawable(id)

fun Fragment.dimenPx(id: Int) = requireContext().dimenPx(id)

fun Fragment.integer(id: Int) = requireContext().integer(id)


fun RecyclerView.ViewHolder.color(id: Int) = itemView.color(id)

fun RecyclerView.ViewHolder.string(id: Int) = itemView.string(id)

fun RecyclerView.ViewHolder.stringArray(id: Int) = itemView.stringArray(id)

fun RecyclerView.ViewHolder.drawable(id: Int) = itemView.drawable(id)

fun RecyclerView.ViewHolder.dimenPx(id: Int) = itemView.dimenPx(id)

fun RecyclerView.integer(id: Int) = context.integer(id)

inline val Context.layoutInflater: android.view.LayoutInflater
    get() = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as android.view.LayoutInflater

fun inflateLayout(
    @LayoutRes layoutId: Int,
    parent: ViewGroup? = null,
    attachToParent: Boolean = false
): View {
    return context.layoutInflater.inflate(layoutId, parent, attachToParent)
}


fun dp2px(dpValue: Float): Int {
    return (dpValue * res.displayMetrics.density + 0.5f).toInt()
}

fun px2dp(pxValue: Float): Int {
    return (pxValue / res.displayMetrics.density + 0.5f).toInt()
}

fun sp2px(spValue: Float): Int {
    return (spValue * res.displayMetrics.scaledDensity + 0.5f).toInt()
}

fun px2sp(pxValue: Float): Int {
    return (pxValue / res.displayMetrics.scaledDensity + 0.5f).toInt()
}

fun Number.dp(): Int = dp2px(toFloat())

fun Number.sp(): Int = sp2px(toFloat())

val screenWidth: Int
    get() {
        return AppManager.getScreenWidthPx()
    }

val screenHeight: Int
    get() {
        return AppManager.getScreenHeightPx()
    }

private val res: Resources
    get() = context.resources

private val context: Context
    get() = SumAppHelper.getApplication()

inline val Context.asActivity
    get() = this as FragmentActivity

//数字转百分比 ，0.50->50%
inline val String.formatWithPercent: String
    get() = run {
        return runCatching {
            BigDecimal(this).multiply(BigDecimal("100")).stripTrailingZeros()
                .toPlainString() + "%"
        }.getOrDefault(this)
    }

//数字格式化，100000->100,000
inline val String.formatWithComma: String
    get() = run {
        return runCatching {
            val df = DecimalFormat()
            df.applyPattern(",###.########")
            df.format(toDouble())
        }.getOrDefault(this)
    }

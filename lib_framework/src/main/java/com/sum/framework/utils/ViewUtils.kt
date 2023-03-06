package com.sum.framework.utils

import android.app.Activity
import android.graphics.Color
import android.graphics.Outline
import android.os.Build
import android.view.View
import android.view.ViewOutlineProvider
import android.view.WindowManager

/**
 * View相关处理工具类
 */
object ViewUtils {

    /**
     * 设置View圆角
     */
    fun setClipViewCornerRadius(view: View?, radius: Int) {
        if (view == null) {
            return
        }
        if (radius > 0) {
            view.outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View?, outline: Outline?) {
                    outline?.setRoundRect(0, 0, view?.width ?: 0, view?.height ?: 0, radius.toFloat())
                }
            }
            view.clipToOutline = true
        } else {
            view.clipToOutline = false
        }
    }

    /**
     * 设置View顶部圆角
     */
    fun setClipViewCornerTopRadius(view: View?, radius: Int) {
        if (view == null) {
            return
        }
        if (radius > 0) {
            view.outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View?, outline: Outline?) {
                    outline?.setRoundRect(0, 0, view?.width ?: 0, view?.height?.plus(radius) ?: 0, radius.toFloat())
                }
            }
            view.clipToOutline = true
        } else {
            view.clipToOutline = false
        }
    }

    /**
     * 设置View底部圆角
     */
    fun setClipViewCornerBottomRadius(view: View?, radius: Int) {
        if (view == null) {
            return
        }
        if (radius > 0) {
            view.outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View?, outline: Outline?) {
                    outline?.setRoundRect(0, -radius, view?.width ?: 0, view?.height ?: 0, radius.toFloat())
                }
            }
            view.clipToOutline = true
        } else {
            view.clipToOutline = false
        }
    }

    /**
     * 设置透明沉浸式状态栏
     * @param activity 页面
     * @param isDarkText 是否状态栏文字显示深色
     */
    fun transparentBar(activity: Activity, isDarkText: Boolean = false) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity?.window
            window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window?.statusBarColor = Color.TRANSPARENT
            var systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            if (isDarkText && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
            window?.decorView?.systemUiVisibility = systemUiVisibility
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val window = activity?.window
            window?.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            )
        }
    }
}
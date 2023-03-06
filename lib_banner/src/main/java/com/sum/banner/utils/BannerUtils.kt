package com.sum.banner.utils

import android.content.res.Resources
import android.util.Log
import com.sum.banner.base.BaseBannerAdapter.Companion.MAX_VALUE

/**
 * Banner工具类
 */
object BannerUtils {
    private var debugMode = false

    private val TAG = "BVP"

    fun setDebugMode(isDebug: Boolean) {
        debugMode = isDebug
    }

    fun isDebugMode(): Boolean {
        return debugMode
    }

    fun dp2px(dpValue: Float): Int {
        return (0.5f + dpValue * Resources.getSystem().displayMetrics.density).toInt()
    }

    fun log(tag: String?, msg: String?) {
        if (isDebugMode()) {
            Log.e(tag, msg ?: "")
        }
    }

    fun log(msg: String?) {
        if (isDebugMode()) {
            log(TAG, msg)
        }
    }

    /**
     * 在循环模式下[BannerViewPager]会初始化一个item为
     * [BaseBannerAdapter.MAX_VALUE]的ViewPager2,并将当前position设置为ViewPager2
     * 的中间位置，因此，此时的position需要通过该方法进行转换为真实的position。
     *
     * @param position  当前position
     * @param pageSize  轮播图页面数
     * @return 真实的position
     */
    fun getRealPosition(position: Int, pageSize: Int): Int {
        return if (pageSize == 0) {
            0
        } else (position + pageSize) % pageSize
    }

    /**
     * @param pageSize 轮播图页面数
     * @return 轮播图初始位置
     */
    fun getOriginalPosition(pageSize: Int): Int {
        return MAX_VALUE / 2 - MAX_VALUE / 2 % pageSize
    }
}
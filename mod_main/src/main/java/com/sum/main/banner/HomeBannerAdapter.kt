package com.sum.main.banner

import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.sum.banner.base.BaseBannerAdapter
import com.sum.common.holder.BannerImageHolder
import com.sum.common.model.Banner
import com.sum.glide.setUrl

/**
 * @author mingyan.su
 * @date   2023/3/6 19:23
 * @desc   BannerAdapter
 */
class HomeBannerAdapter : BaseBannerAdapter<Banner, BannerImageHolder>() {
    var isRecord = false

    // 首帧绘制监听
    var onFirstFrameTimeCall: (() -> Unit)? = null
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerImageHolder {
        val imageView = AppCompatImageView(parent.context).apply {
            scaleType = ImageView.ScaleType.CENTER_CROP
            layoutParams =
                ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }.also {
//            ViewUtils.setClipViewCornerRadius(it, dpToPx(12))
        }
        return BannerImageHolder(imageView)
    }

    override fun onBindView(holder: BannerImageHolder, data: Banner, position: Int, pageSize: Int) {
        data.imagePath?.let {
            holder.imageView.setUrl(it)
        }
        // 第一个item，并且没有没注册监听
        if (position == 0 && !isRecord) {
            isRecord = true
            holder.itemView.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    onFirstFrameTimeCall?.invoke()
                    // 移除监听
                    holder.itemView.viewTreeObserver.removeOnPreDrawListener(this)
                    return false
                }
            })
        }
    }
}
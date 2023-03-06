package com.sum.main.banner

import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.sum.banner.base.BaseBannerAdapter
import com.sum.common.holder.BannerImageHolder
import com.sum.framework.utils.ViewUtils
import com.sum.framework.utils.dpToPx
import com.sum.main.R

/**
 * @author mingyan.su
 * @date   2023/3/6 19:23
 * @desc   BannerAdapter
 */
class HomeBannerAdapter : BaseBannerAdapter<String, BannerImageHolder>() {

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerImageHolder {
        val imageView = AppCompatImageView(parent.context).apply {
            scaleType = ImageView.ScaleType.CENTER_CROP
            layoutParams =
                ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }.also {
            ViewUtils.setClipViewCornerRadius(it, dpToPx(12))
        }
        return BannerImageHolder(imageView)
    }

    override fun onBindView(holder: BannerImageHolder, data: String, position: Int, pageSize: Int) {
        data?.let {
            holder.imageView.setImageResource(if (position % 2 == 0) R.mipmap.ic_navi_home else R.mipmap.ic_navi_categories)
        }
    }

}
package com.sum.glide

import android.graphics.Bitmap

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.sum.framework.manager.ActivityManager
import com.sum.framework.manager.AppManager
import com.sum.glide.transformation.BlurTransformation
import com.sum.glide.transformation.CircleBorderTransform
import java.io.File

/**
 * @author mingyan.su
 * @date   2023/3/6 19:02
 * @desc   Glide加载ImageView扩展方法
 */

/**
 * 加载图片，开启缓存
 * @param url 图片地址
 */
fun ImageView.setUrl(url: String?) {
    if (ActivityManager.isActivityDestroy(context)) {
        return
    }
    Glide.with(context).load(url)
            .placeholder(R.mipmap.default_img) // 占位符，异常时显示的图片
            .error(R.mipmap.default_img) // 错误时显示的图片
            .skipMemoryCache(false) //启用内存缓存
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE) //磁盘缓存策略
            .into(this)
}

/**
 * 加载图片，开启缓存
 * @param url 图片地址
 */
fun ImageView.loadFile(file: File?) {
    if (ActivityManager.isActivityDestroy(context)) {
        return
    }
    //请求配置
    val options = RequestOptions.circleCropTransform()
    Glide.with(context).load(file)
            .placeholder(R.mipmap.default_head) // 占位符，异常时显示的图片
            .error(R.mipmap.default_head) // 错误时显示的图片
            .skipMemoryCache(false) //启用内存缓存
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE) //磁盘缓存策略
            .apply(options) // 圆形
            .into(this)
}

/**
 * 设置图片，不开启缓存
 * @param url
 */
fun ImageView.setUrlNoCache(url: String?) {
    if (ActivityManager.isActivityDestroy(context)) return
    Glide.with(context).load(url)
            .placeholder(R.mipmap.default_img)
            .error(R.mipmap.default_img)
            .priority(Priority.HIGH)
            .skipMemoryCache(true) //不启动缓存
            .diskCacheStrategy(DiskCacheStrategy.NONE) //不启用磁盘策略
            .into(this)
}

/**
 * 加载圆形图片
 * @param url
 */
fun ImageView.setUrlCircle(url: String?) {
    if (ActivityManager.isActivityDestroy(context)) return
    //请求配置
    val options = RequestOptions.circleCropTransform()
    Glide.with(context).load(url)
            .placeholder(R.mipmap.default_head)
            .error(R.mipmap.default_head)
            .skipMemoryCache(false) //启用内存缓存
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .apply(options)// 圆形
            .into(this)
}

/**
 * 加载边框圆形图片
 * @param url
 * @param borderWidth 边框宽度
 * @param borderColor 边框颜色
 */
fun ImageView.setUrlCircleBorder(url: String?, borderWidth: Float, borderColor: Int) {
    if (ActivityManager.isActivityDestroy(context)) return
    Glide.with(context).load(url)
            .placeholder(R.mipmap.default_head)
            .error(R.mipmap.default_head)
            .skipMemoryCache(false) //启用内存缓存
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .transform(CircleBorderTransform(borderWidth, borderColor)) // 圆形
            .into(this)
}

/**
 * 加载圆角图片
 * @param url
 * @param radius dp
 *
 * 注意：glide的图片裁剪和ImageView  scaleType有冲突，
 * bitmap会先圆角裁剪，再加载到ImageView中，如果bitmap图片尺寸大于ImageView尺寸，则会看不到
 * 使用CenterCrop()重载，会先将bitmap居中裁剪，再进行圆角处理，这样就能看到了。
 */
fun ImageView.setUrlRound(url: String?, radius: Int = 10) {
    if (ActivityManager.isActivityDestroy(context)) return
    Glide.with(context).load(url)
            .placeholder(R.mipmap.default_img)
            .error(R.mipmap.default_img)
            .skipMemoryCache(false) // 启用内存缓存
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .transform(CenterCrop(), RoundedCorners(radius))
            .into(this)
}

/**
 * 设置图片
 * @param url
 * @param errorRes 错误图片
 */
fun ImageView.setUrlErrorIcon(url: String?, @DrawableRes errorRes: Int) {
    if (ActivityManager.isActivityDestroy(context)) return
    Glide.with(context).load(url)
            .placeholder(errorRes)
            .error(errorRes)
            .priority(Priority.HIGH)
            .skipMemoryCache(true) //不启动缓存
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(this)
}

/**
 * 带资源回调
 * @param url
 * @param callBack 根据资源回调，做一些处理
 */
fun ImageView.setUrlAsBitmap(url: String?, callBack: (Bitmap) -> Unit) {
    if (ActivityManager.isActivityDestroy(context)) return
    Glide.with(context).asBitmap().load(url)
            .placeholder(R.mipmap.default_img)
            .error(R.mipmap.default_img)
            .into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap>?) {
                    setImageBitmap(bitmap)
                    callBack(bitmap)
                }
            })
}

/**
 * 加载Gif图片
 * @param url
 */
fun ImageView.setUrlGif(url: String?) {
    if (ActivityManager.isActivityDestroy(context)) return
    Glide.with(context).asGif().load(url)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .placeholder(R.mipmap.default_img)
            .error(R.mipmap.default_img)
            .into(this)
}

/**
 * 设置图片高斯模糊
 * @param radius 设置模糊度(在0.0到25.0之间)，默认25
 * @param sampling  图片缩放比例，默认1
 */
fun ImageView.setBlurView(url: String?, radius: Int = 25, sampling: Int = 1) {
    if (ActivityManager.isActivityDestroy(context)) return
    //请求配置
    val options = RequestOptions.bitmapTransform(BlurTransformation(radius, sampling))
    Glide.with(context)
            .load(url)
            .placeholder(R.mipmap.default_img)
            .error(R.mipmap.default_img)
            .apply(options)
            .into(this)
}

/**
 * 适配屏幕宽度，高度自适应
 */
fun ImageView.setScanImage(url: String?) {
    if (ActivityManager.isActivityDestroy(context)) return
    //获取bitmap根据Drawable设定图片显示尺寸
    Glide.with(context).asDrawable().load(url)
            .placeholder(R.mipmap.default_img)
            .skipMemoryCache(false)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .error(R.mipmap.default_img)
            .into(object : CustomTarget<Drawable?>() {
                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable?>?) {
                    val width = resource.intrinsicWidth
                    val height = resource.intrinsicHeight
                    val lp = layoutParams
                    lp.width = AppManager.getScreenWidthPx()
                    val tempHeight = height * (lp.width.toFloat() / width)
                    lp.height = tempHeight.toInt()
                    layoutParams = lp
                    layoutParams = lp
                    setImageDrawable(resource)
                    //Glide.with(context).load(drawable).override(lp.width, lp.height).into(this)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
}


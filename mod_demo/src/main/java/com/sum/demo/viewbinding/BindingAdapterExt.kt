package com.sum.demo.viewbinding

import android.widget.Button
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.sum.glide.setUrl
import com.sum.glide.setUrlRound


/**
 * @author mingyan.su
 * @date   2023/12/15 23:32
 * @desc   BindingAdapter扩展类
 */

/**
 * 1.需要定义成public static ,使用BindingAdapter注解并标记
 * 2.value中的字段根据需要添加，与方法参数一一对应
 * 3.requireAll代表是否以下两个属性在xml中同时使用才会调用到该方法，为false的话，只要有一个属性被使用就能调用该方法
 */
@BindingAdapter(value = ["imageUrl", "radius"], requireAll = false)
fun setImageUrl(view: ImageView, imageUrl: String, radius: Int) {
    if (radius > 0) {
        view.setUrlRound(imageUrl, radius)
    } else {
        view.setUrl(imageUrl)
    }
}

@BindingAdapter("android:text")
fun setText(view: Button, text: String) {
    view.text = "$text-改变原生控件属性"
}
package com.sum.framework.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * @author mingyan.su
 * @date   2023/3/9 08:11
 * @desc   基本ViewHolder
 */
open class BaseViewHolder<B : ViewBinding>(rootView: View) : RecyclerView.ViewHolder(rootView)

open class BaseBindViewHolder<B : ViewBinding>(val binding: B) : BaseViewHolder<B>(binding.root)
package com.sum.framework.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * @author mingyan.su
 * @date   2023/3/9 08:11
 * @desc   基本ViewHolder
 */
open class BaseViewHolder<B : ViewBinding>(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)
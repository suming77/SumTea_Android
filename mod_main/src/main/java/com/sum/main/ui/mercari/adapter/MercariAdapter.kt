package com.sum.main.ui.mercari.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sum.common.model.MercariModel
import com.sum.framework.adapter.BaseBindViewHolder
import com.sum.framework.adapter.BaseRecyclerViewAdapter
import com.sum.glide.setUrl
import com.sum.main.databinding.LayoutHomeMercariItemBinding
import java.lang.String
import java.util.Locale

class MercariAdapter : BaseRecyclerViewAdapter<MercariModel, LayoutHomeMercariItemBinding>() {
    override fun onBindDefViewHolder(
        holder: BaseBindViewHolder<LayoutHomeMercariItemBinding>,
        data: MercariModel?,
        position: Int
    ) {
        holder.binding.apply {
            name.text = data!!.name
            numComments.text = String.format(Locale.US, "%d", data.numComments)
            numLikes.text = String.format(Locale.US, "%d", data.numLikes)
            price.text = String.format(Locale.US, "$%d", data.price)
            photo.setUrl(data.photo)

            when (data.status) {
                "sold_out" -> soldOut.visibility = View.VISIBLE
                else -> soldOut.visibility = View.GONE
            }
        }

    }

    override fun getViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): LayoutHomeMercariItemBinding {
        return LayoutHomeMercariItemBinding.inflate(layoutInflater, parent, false)
    }

}

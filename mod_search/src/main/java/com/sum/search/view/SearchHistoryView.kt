package com.sum.search.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.chip.Chip
import com.sum.common.model.HotSearch
import com.sum.common.model.KeyWord
import com.sum.framework.ext.onClick
import com.sum.search.R
import com.sum.search.databinding.LayoutHistorySearchBinding
import com.sum.search.databinding.LayoutHistorySearchChipItemBinding

/**
 * @author mingyan.su
 * @date   2023/3/29 22:48
 * @desc   历史搜索
 */
class SearchHistoryView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val mKeyWords = mutableListOf<String>()
    private var mBinding: LayoutHistorySearchBinding

    init {
        mBinding = LayoutHistorySearchBinding.inflate(LayoutInflater.from(context), this, true)
        val array = context.obtainStyledAttributes(attrs, R.styleable.SearchHistoryView)
        val title = array.getString(R.styleable.SearchHistoryView_title)
        mBinding.tvSearch.text = title
        array.recycle()
    }

    /**
     * 设置数据
     */
    fun setHistoryData(histories: MutableList<String>?) {
        if (histories == null) return
        mKeyWords.clear()
        mKeyWords.addAll(histories)
        for (index in 0 until histories.size) {
            var chipItem: Chip
            //这里也要需要判断一下复用的情况
            val childCount = mBinding.chipGroup.childCount
            if (index < childCount) {
                chipItem = mBinding.chipGroup.getChildAt(index) as Chip
            } else {
                chipItem = createChipItem()
                mBinding.chipGroup.addView(chipItem)
            }
            chipItem.text = histories[index]
        }
    }

    /**
     * 创建Chip
     */
    private fun createChipItem(): Chip {
        val chipItem: Chip =
            LayoutHistorySearchChipItemBinding.inflate(LayoutInflater.from(context), mBinding.chipGroup, false).root

        chipItem.isChecked = false //刚创建的时候是没有选中的
        chipItem.isCheckable = true
        //还需要去改变每个chip的id，这个id在布局文件的时候是相同的，但是我们添加到chipGroup的时候是不同的
//        chipItem.id = View.generateViewId()
        chipItem.id = mBinding.chipGroup.childCount ?: 0
        return chipItem
    }

    /**
     * 需要暴露方法，点击item的时候把事件暴露出去
     */
    fun setOnCheckChangeListener(callBack: (String) -> Unit) {
        mBinding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            for (index in 0 until mBinding.chipGroup.childCount) {
                if (mBinding.chipGroup.getChildAt(index)?.id == checkedId) {
                    callBack.invoke(mKeyWords[index])
                    break
                }
            }
        }
    }

    /**
     * 清除历史记录监听
     */
    fun setOnHistoryClearListener(callBack: () -> Unit) {
        mBinding.ivDelete.onClick {
            callBack()
            mBinding.chipGroup.removeAllViews()
            mKeyWords.clear()
        }
    }

    fun getDeleteImageView(): ImageView {
        return mBinding.ivDelete
    }

}
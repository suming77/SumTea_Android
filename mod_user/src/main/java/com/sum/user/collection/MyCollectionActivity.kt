package com.sum.user.collection

import android.os.Bundle
import com.sum.common.databinding.LayoutNormalRecyclerviewBinding
import com.sum.framework.base.BaseMvvmActivity
import com.sum.user.R

/**
 * @author mingyan.su
 * @date   2023/3/24 18:26
 * @desc   我的收藏
 */
class MyCollectionActivity : BaseMvvmActivity<LayoutNormalRecyclerviewBinding, MyCollectViewModel>() {

    override fun getLayoutResId(): Int = com.sum.common.R.layout.layout_normal_recyclerview

    override fun initView(savedInstanceState: Bundle?) {

    }
}
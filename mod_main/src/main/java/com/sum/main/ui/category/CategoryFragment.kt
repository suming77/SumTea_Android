package com.sum.main.ui.category

import android.os.Bundle
import android.view.View
import com.sum.framework.base.BaseMvvmFragment
import com.sum.main.R
import com.sum.main.databinding.FragmentCategoryBinding
/**
 * @author mingyan.su
 * @date   2023/3/3 8:10
 * @desc   分类
 */
class CategoryFragment : BaseMvvmFragment<FragmentCategoryBinding, CategoryViewModel>() {

    override fun getLayoutResId(): Int = R.layout.fragment_category

    override fun initView(view: View, savedInstanceState: Bundle?) {

    }

}
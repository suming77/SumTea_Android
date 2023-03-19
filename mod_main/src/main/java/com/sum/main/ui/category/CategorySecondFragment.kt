package com.sum.main.ui.category

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.sum.common.constant.KEY_LIST
import com.sum.common.model.CategorySecondItem
import com.sum.framework.base.BaseMvvmFragment
import com.sum.framework.ext.dividerGridSpace
import com.sum.framework.ext.toBeanOrNull
import com.sum.framework.toast.TipsToast
import com.sum.main.R
import com.sum.main.databinding.FragmentCategorySecondBinding
import com.sum.main.ui.category.adapter.CategorySecondItemAdapter
import com.sum.main.ui.category.viewmodel.CategoryViewModel

/**
 * @author mingyan.su
 * @date   2023/3/19 22:31
 * @desc   分类item
 */
class CategorySecondFragment : BaseMvvmFragment<FragmentCategorySecondBinding, CategoryViewModel>() {
    private lateinit var mAdapter: CategorySecondItemAdapter

    companion object {
        fun newInstance(jsonStr: String): CategorySecondFragment {
            val fragment = CategorySecondFragment()
            val bundle = Bundle()
            bundle.putString(KEY_LIST, jsonStr)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutResId(): Int = R.layout.fragment_category_second

    override fun initView(view: View, savedInstanceState: Bundle?) {
        mAdapter = CategorySecondItemAdapter()
        mBinding?.recyclerView?.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = mAdapter
            dividerGridSpace(2, 8.0f, true)
        }
        mAdapter.onItemClickListener = { view: View, position: Int ->
            TipsToast.showTips("点击事件")
        }
    }

    override fun initData() {
        val listJson = arguments?.getString(KEY_LIST, "")
        val list = listJson?.toBeanOrNull<MutableList<CategorySecondItem>>()
        mAdapter.setData(list)
    }
}
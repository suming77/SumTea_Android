package com.sum.main.ui.category

import android.os.Bundle
import android.util.SparseArray
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.sum.framework.adapter.ViewPage2FragmentAdapter
import com.sum.framework.base.BaseMvvmFragment
import com.sum.framework.ext.gone
import com.sum.framework.ext.toJson
import com.sum.framework.ext.visible
import com.sum.main.R
import com.sum.main.databinding.FragmentCategoryBinding
import com.sum.main.ui.category.adapter.CategoryTabAdapter
import com.sum.main.ui.category.viewmodel.CategoryViewModel

/**
 * @author mingyan.su
 * @date   2023/3/3 8:10
 * @desc   分类
 */
class CategoryFragment : BaseMvvmFragment<FragmentCategoryBinding, CategoryViewModel>() {
    //当前选中的position
    private var mCurrentSelectPosition = 0
    private var fragments = SparseArray<Fragment>()
    private lateinit var mTabAdapter: CategoryTabAdapter
    private var mViewPagerAdapter: ViewPage2FragmentAdapter? = null

    override fun initView(view: View, savedInstanceState: Bundle?) {
        initTabRecyclerView()
        initViewPager2()
    }

    private fun initTabRecyclerView() {
        mTabAdapter = CategoryTabAdapter()
        mBinding?.tabRecyclerView?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mTabAdapter
        }
        mTabAdapter.onItemClickListener = { view: View, position: Int ->
            updateTabItem(position)
            mBinding?.viewPager2?.setCurrentItem(position, false)
        }
    }

    private fun initViewPager2() {
        activity?.let {
            mViewPagerAdapter = ViewPage2FragmentAdapter(it, fragments)
        }
        mBinding?.viewPager2?.apply {
            offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
            orientation = ViewPager2.ORIENTATION_VERTICAL
            registerOnPageChangeCallback(viewPager2Callback)
            adapter = mViewPagerAdapter
        }
    }

    override fun initData() {
        mViewModel.getCategoryData()
        mViewModel.categoryItemLiveData.observe(this) {
            it?.let {
                mBinding?.viewEmptyData?.gone()
                //默认第一条选中
                it.firstOrNull()?.isSelected = true
                mTabAdapter.setData(it)
                it.forEachIndexed { index, item ->
                    val fragment = CategorySecondFragment.newInstance(item.articles?.toJson(true) ?: "")
                    fragments.append(index, fragment)
                }
                mViewPagerAdapter?.notifyItemRangeChanged(0, it.size)
            } ?: kotlin.run {
                mBinding?.viewEmptyData?.visible()
            }
        }
    }

    /**
     * 更新Tab状态
     * @param position 选择的item
     */
    private fun updateTabItem(position: Int) {
        mTabAdapter.setCurrentPosition(position)

        if (mCurrentSelectPosition != position) {
            //更新上一条item
            val selectedItem = mTabAdapter.getItem(mCurrentSelectPosition)
            selectedItem?.let { it.isSelected = false }
            //更新当前item
            val newItem = mTabAdapter.getItem(position)
            newItem?.let { it.isSelected = true }

            mCurrentSelectPosition = position
            mTabAdapter.notifyDataSetChanged()
            mBinding?.tabRecyclerView?.smoothScrollToPosition(position)
        }
    }

    /**
     * VIewPager2选择回调
     */
    private val viewPager2Callback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            updateTabItem(position)
        }
    }

    override fun onDestroyView() {
        mBinding?.viewPager2?.unregisterOnPageChangeCallback(viewPager2Callback)
        super.onDestroyView()
    }

}
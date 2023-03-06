package com.sum.main.ui.home

import android.graphics.Typeface
import android.os.Bundle
import android.util.SparseArray
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.sum.framework.adapter.ViewPage2FragmentAdapter
import com.sum.framework.base.BaseMvvmFragment
import com.sum.framework.log.LogUtil
import com.sum.main.R
import com.sum.main.databinding.FragmentHomeBinding


/**
 * @author mingyan.su
 * @date   2023/3/3 8:16
 * @desc   首页
 */
class HomeFragment : BaseMvvmFragment<FragmentHomeBinding, HomeViewModel>(), OnRefreshListener {
    private var tabLayoutMediator: TabLayoutMediator? = null
    override fun getLayoutResId(): Int = R.layout.fragment_home

    override fun initView(view: View, savedInstanceState: Bundle?) {
        initBanner()
        initTab()
    }

    private fun initBanner() {
        val bannerList = mutableListOf<String>()
        bannerList.add("")
        bannerList.add("")
        bannerList.add("")
        bannerList.add("")
        bannerList.add("")
        bannerList.add("")
        bannerList.add("")
        mBinding?.bannerHome?.setData(bannerList)
    }

    private fun initTab() {
        val arrayFragments = SparseArray<Fragment>()
        arrayFragments.append(0, HomeVideoFragment())
        for (i in 1..6) {
            arrayFragments.append(i, HomeTabFragment())
        }
        val adapter = ViewPage2FragmentAdapter(this, arrayFragments)

        mBinding?.let {
            it.viewPager.adapter = adapter
            //可左右滑动
            it.viewPager.isUserInputEnabled = true
            //禁用预加载
            it.viewPager.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT

            tabLayoutMediator = TabLayoutMediator(it.tabHome, it.viewPager) { tab: TabLayout.Tab, position: Int ->
                //                tab.text = titles[position]
                if (position == 0) {
                    tab.setText(R.string.home_tab_video_title)
                } else {
                    tab.text = "资讯快读"
                }
            }
            //tabLayout和viewPager2关联起来
            tabLayoutMediator?.attach()

            //增加tab选择监听
            it.tabHome.addOnTabSelectedListener(tabSelectedCall)

            //设置第一个tab效果
            val tabFirst = it.tabHome.getTabAt(0)
            setTabTextSize(tabFirst)
        }
    }

    /**
     * 设置tab大小加粗效果
     */
    private fun setTabTextSize(tabFirst: TabLayout.Tab?) {
        TextView(requireContext()).apply {
            typeface = Typeface.DEFAULT_BOLD
            setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15f)
            setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        }.also {
            it.text = tabFirst?.text
            tabFirst?.customView = it
        }
    }

    private val tabSelectedCall = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            setTabTextSize(tab)
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
            //非选中效果在xml中设置
            tab?.customView = null
        }

        override fun onTabReselected(tab: TabLayout.Tab?) {
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
//        homeViewModel.getBannerList().observe(viewLifecycleOwner) {
//            binding.refreshLayout.finishRefresh()
//        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogUtil.e("onViewCreated")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        LogUtil.e("onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        LogUtil.e("onResume")
    }

    override fun onDestroy() {
        super.onDestroy()
        tabLayoutMediator?.detach()
        LogUtil.e("onDestroy")
    }
}
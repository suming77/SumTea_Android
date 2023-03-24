package com.sum.main.ui.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.sum.common.constant.USER_ACTIVITY_SETTING
import com.sum.framework.base.BaseMvvmFragment
import com.sum.framework.decoration.NormalItemDecoration
import com.sum.framework.ext.onClick
import com.sum.framework.utils.dpToPx
import com.sum.main.R
import com.sum.main.databinding.FragmentMineBinding
import com.sum.main.databinding.FragmentMineHeadBinding
import com.sum.main.ui.mine.viewmodel.MineViewModel
import com.sum.main.ui.system.adapter.ArticleAdapter

/**
 * @author mingyan.su
 * @date   2023/3/3 8:22
 * @desc   我的
 */
class MineFragment : BaseMvvmFragment<FragmentMineBinding, MineViewModel>(), OnRefreshListener,
    OnLoadMoreListener {

    private var mPage = 0
    private lateinit var mHeadBinding: FragmentMineHeadBinding
    private lateinit var mAdapter: ArticleAdapter
    override fun getLayoutResId(): Int = R.layout.fragment_mine

    override fun initView(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        initHeadView()
        initListener()
    }

    override fun initData() {

    }

    private fun initListener() {
        mHeadBinding?.apply {
            ivHead.onClick {

            }
            ivSetting.onClick {
                ARouter.getInstance().build(USER_ACTIVITY_SETTING).navigation()
            }
            tvVideo.onClick {

            }
            tvWorkTitle.onClick {

            }
            tvLikeTitle.onClick {

            }
            tvNavigation.onClick {

            }
            tvLifeCycle.onClick {

            }
            tvDataBinging.onClick {

            }
            tvLivedata.onClick {

            }
            tvViewModel.onClick {

            }
            tvPaging.onClick {

            }
            tvRoom.onClick {

            }
            tvHilt.onClick {

            }
        }

    }

    private fun initRecyclerView() {
        mBinding?.refreshLayout?.apply {
            autoRefresh()
            setEnableRefresh(true)
            setEnableLoadMore(true)
            setOnRefreshListener(this@MineFragment)
            setOnLoadMoreListener(this@MineFragment)
            autoRefresh()
        }
        mAdapter = ArticleAdapter()
        val dp12 = dpToPx(12)
        mBinding?.recyclerView?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(NormalItemDecoration().apply {
                setBounds(left = dp12, top = dp12, right = dp12, bottom = dp12)
                setLastBottom(true)
                setFirstHeadMargin(true)
            })
            adapter = mAdapter
        }
        mAdapter.onItemClickListener = { view: View, position: Int ->

        }
    }

    private fun initHeadView() {
        mHeadBinding = FragmentMineHeadBinding.inflate(LayoutInflater.from(requireContext()))
        mHeadBinding?.tvName?.text = "苏火火~"
        mHeadBinding?.tvDesc?.text = "天苍苍，野茫茫，风水草低见牛羊！"
        mAdapter.addHeadView(mHeadBinding.root)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mPage = 0
        getRecommendList()
    }

    /**
     * 获取推荐列表数据
     */
    private fun getRecommendList() {
        mViewModel.getRecommendList(count = mPage).observe(this) {
            if (mPage == 0) {
                mAdapter.setData(it)
                if (it.isNullOrEmpty()) {
                    //空视图

                }
                mBinding?.refreshLayout?.finishRefresh()
            } else {
                mAdapter.addAll(it)
                mBinding?.refreshLayout?.finishLoadMore()
            }
        }
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mPage++
        getRecommendList()
    }
}
package com.sum.main.ui.mine

import android.os.Bundle
import android.view.View
import com.sum.framework.base.BaseMvvmFragment
import com.sum.main.R
import com.sum.main.databinding.FragmentMineBinding

/**
 * @author mingyan.su
 * @date   2023/3/3 8:22
 * @desc   我的
 */
class MineFragment : BaseMvvmFragment<FragmentMineBinding, MineViewModel>() {

    override fun getLayoutResId(): Int = R.layout.fragment_mine

    override fun initView(view: View, savedInstanceState: Bundle?) {

    }


}
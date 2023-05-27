package com.sum.demo.jetpack.navigation

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.sum.demo.R
import com.sum.demo.databinding.FragmentHomeNavBinding
import com.sum.framework.base.BaseDataBindFragment

/**
 * @author mingyan.su
 * @date   2023/5/12 16:46
 * @desc   FindNavFragment
 */
class FindNavFragment : BaseDataBindFragment<FragmentHomeNavBinding>() {
    override fun initView(view: View, savedInstanceState: Bundle?) {
        findNavController().navigate(R.id.nav_find)
    }
}
package com.sum.demo.viewmodel

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.sum.demo.databinding.FragmentViewmodelVideoBinding
import com.sum.framework.base.BaseDataBindFragment
import com.sum.framework.ext.onClick

/**
 * @author mingyan.su
 * @date   2023/6/17 08:28
 * @desc
 */
class VideoFragment : BaseDataBindFragment<FragmentViewmodelVideoBinding>() {
    override fun initView(view: View, savedInstanceState: Bundle?) {
        // 获取ViewModel，注意传入的是宿主Activity
        val viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        mBinding?.tvSaveInfo?.onClick {
            viewModel.shareLiveData.value = "数据共享：VideoFragment中的数据"
        }
    }
}
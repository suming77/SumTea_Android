package com.sum.demo.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.sum.demo.databinding.DialogNavBinding

/**
 * @author mingyan.su
 * @date   2023/6/5 15:38
 * @desc
 */
class NavDialog : DialogFragment() {
    lateinit var binding: DialogNavBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogNavBinding.inflate(layoutInflater)
        return binding.root
    }
}
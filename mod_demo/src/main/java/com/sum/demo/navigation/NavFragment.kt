package com.sum.demo.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sum.demo.databinding.FragmentNavBinding

/**
 * @author mingyan.su
 * @date   2023/5/12 16:46
 * @desc   HomeNavFragment
 */
class NavFragment : Fragment() {

    lateinit var binding: FragmentNavBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        findNavController().navigate(R.id.nav_find)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNavBinding.inflate(layoutInflater)
        return binding.root
    }
}
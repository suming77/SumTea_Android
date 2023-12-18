package com.sum.demo.viewbinding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.sum.common.constant.DEMO_ACTIVITY_VIEWBINDING
import com.sum.demo.databinding.ActivityViewBindingBinding

/**
 * @author mingyan.su
 * @date   2023/7/5 07:21
 * @desc   ViewBindingDemo
 */
@Route(path = DEMO_ACTIVITY_VIEWBINDING)
class ViewBindingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 解析ActivityViewBindingBinding
        val binding = ActivityViewBindingBinding.inflate(layoutInflater)
        val contentView = binding.root
        setContentView(contentView)

        // 通过binding对象直接获取到xml中的控件
        binding.tvName.text = "苏火火苏火火"
        binding.tvName.setOnClickListener {

        }
    }
}
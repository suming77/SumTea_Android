package com.sum.demo.viewbinding

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.sum.common.constant.DEMO_ACTIVITY_DATABINDING
import com.sum.common.model.Student
import com.sum.demo.R
import com.sum.demo.databinding.ActivityDataBindingBinding
import com.sum.demo.databinding.LayoutBindingViewstubBinding
import com.sum.demo.viewmodel.MainViewModel

/**
 * @author mingyan.su
 * @date   2023/12/14 07:16
 * @desc   DataBindingDemo
 */
@Route(path = DEMO_ACTIVITY_DATABINDING)
class DataBindingActivity : AppCompatActivity() {
    var mStudent: Student? = null
    lateinit var binding: ActivityDataBindingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        // 1.解析ActivityViewBindingBinding
//        val binding = ActivityDataBindingBinding.inflate(layoutInflater)
//        val contentView = binding.root
//        setContentView(contentView)

        // 2.绑定View
        val layoutView = LayoutInflater.from(this).inflate(R.layout.activity_data_binding, null)
        val bindingView = DataBindingUtil.bind<ActivityDataBindingBinding>(layoutView)

        // 3.Activity中使用setContentView
        binding = DataBindingUtil.setContentView<ActivityDataBindingBinding>(this, R.layout.activity_data_binding)

        // 4.Fragment中onCreateView()使用
        //val binding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater, R.layout.fragment_home, container, false)


        // 通过binding对象直接获取到xml中的控件
        binding.tvName.text = "苏火火苏火火"

        mStudent = Student("name").apply {
            name = "姓名"
            age = null
        }
        // 设置student数据
        binding.student = mStudent
        binding.activity = this

        bindLiveData()
    }

    /**
     * 更新Student数据
     */
    fun updateStudentName() {
        mStudent?.setStuName("苏火火~")
    }

    /**
     * 更新Student数据
     */
    fun getDoubleBindName() {
        val updateData = binding.tvEdit.text
        binding.tvName.text = updateData
    }

    /**
     * 获取图片地址
     */
    fun getImageUrl(): String = "https://www.wanandroid.com/blogimgs/62c1bd68-b5f3-4a3c-a649-7ca8c7dfabe6.png"

    /**
     * viewStub
     */
    fun viewStub() {
        val viewStub = binding.layoutBindingViewstub.viewStub
        viewStub?.setOnInflateListener { stub, inflated -> //如果在 xml 中没有使用 bind:userInfo="@{userInf}" 对 viewStub 进行数据绑定
            //那么可以在此处进行手动绑定
            val viewStubBinding: LayoutBindingViewstubBinding? = DataBindingUtil.bind(stub)
            viewStubBinding?.student = mStudent
        }
    }

    /**
     * 结合DataBinding、ViewModel、LiveData
     */
    private fun bindLiveData() {
        binding.lifecycleOwner = this
        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.vm = viewModel
        // 请求数据
        viewModel.getUserInfo()
    }

}
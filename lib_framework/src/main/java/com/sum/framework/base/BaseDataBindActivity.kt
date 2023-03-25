package com.sum.framework.base

import android.view.LayoutInflater
import androidx.databinding.ViewDataBinding
import com.sum.framework.ext.saveAs
import com.sum.framework.ext.saveAsUnChecked
import java.lang.reflect.ParameterizedType

/**
 * @author mingyan.su
 * @date   2023/2/26 11:48
 * @desc   dataBinding Activity基类
 */
abstract class BaseDataBindActivity<DB : ViewDataBinding> : BaseActivity() {
    lateinit var mBinding: DB

    override fun setContentLayout() {
//      mBinding = DataBindingUtil.setContentView(this, getLayoutResId())
        val type = javaClass.genericSuperclass
        val vbClass: Class<DB> = type!!.saveAs<ParameterizedType>().actualTypeArguments[0].saveAs()
        val method = vbClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        mBinding = method.invoke(this, layoutInflater)!!.saveAsUnChecked()
        setContentView(mBinding.root)
    }

    override fun getLayoutResId(): Int = 0
}
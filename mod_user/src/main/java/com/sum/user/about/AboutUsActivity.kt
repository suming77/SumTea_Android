package com.sum.user.about

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.sum.framework.base.BaseDataBindActivity
import com.sum.user.R
import com.sum.user.databinding.ActivityAboutUsBinding

/**
 * @author mingyan.su
 * @date   2023/3/24 08:21
 * @desc   关于我们
 */
class AboutUsActivity : BaseDataBindActivity<ActivityAboutUsBinding>() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, AboutUsActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getLayoutResId(): Int = R.layout.activity_about_us

    override fun initView(savedInstanceState: Bundle?) {
        setSupportActionBar(mBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
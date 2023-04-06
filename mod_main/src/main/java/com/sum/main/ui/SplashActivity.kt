package com.sum.main.ui

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.sum.common.provider.MainServiceProvider
import com.sum.framework.base.BaseDataBindActivity
import com.sum.framework.ext.countDownCoroutines
import com.sum.framework.ext.onClick
import com.sum.framework.utils.StatusBarSettingHelper
import com.sum.main.R
import com.sum.main.databinding.ActivitySplashBinding

/**
 * @author mingyan.su
 * @date   2023/3/29 14:25
 * @desc   启动页
 */
class SplashActivity : BaseDataBindActivity<ActivitySplashBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        StatusBarSettingHelper.setStatusBarTranslucent(this)
        mBinding.tvSkip.onClick {
            MainServiceProvider.toMain(this)
        }
        //倒计时
        countDownCoroutines(2, lifecycleScope, onTick = {
            mBinding.tvSkip.text = getString(R.string.splash_time, it.plus(1).toString())
        }) {
            MainServiceProvider.toMain(this)
            finish()
        }
    }
}
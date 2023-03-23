package com.sum.user.setting

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.sum.common.constant.USER_ACTIVITY_SETTING
import com.sum.framework.base.BaseDataBindActivity
import com.sum.framework.ext.onClick
import com.sum.framework.toast.TipsToast
import com.sum.framework.utils.ViewUtils
import com.sum.framework.utils.dpToPx
import com.sum.user.R
import com.sum.user.databinding.ActivitySettingBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author mingyan.su
 * @date   2023/3/23 12:43
 * @desc   设置
 */
//@Route(path = USER_ACTIVITY_SETTING)
class SettingActivity : BaseDataBindActivity<ActivitySettingBinding>() {
    override fun getLayoutResId(): Int = R.layout.activity_setting

    override fun initView(savedInstanceState: Bundle?) {
        ViewUtils.setClipViewCornerRadius(mBinding.tvLogout, dpToPx(8))
        initListener()
    }

    private fun initListener() {
        mBinding.clUserInfo.onClick {

        }
        mBinding.clAccountSafe.onClick {
            TipsToast.showWarningTips(com.sum.common.R.string.default_developing)
        }
        mBinding.clCurrentVersion.onClick {
            TipsToast.showWarningTips(R.string.setting_newest_version)
        }
        mBinding.clClearCache.onClick {
            showLoading("正在清理...")
            lifecycleScope.launch {
                delay(1000)
                dismissLoading()
                mBinding.tvCache.text = "0MB"
            }
        }
        mBinding.clAboutUs.onClick {

        }
        mBinding.tvLogout.onClick {

        }
    }
}
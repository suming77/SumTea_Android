package com.sum.user.info

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.sum.framework.base.BaseDataBindActivity
import com.sum.framework.ext.onClick
import com.sum.framework.toast.TipsToast
import com.sum.framework.utils.ViewUtils
import com.sum.framework.utils.dpToPx
import com.sum.user.R
import com.sum.user.databinding.ActivityUserInfoBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author mingyan.su
 * @date   2023/3/23 18:43
 * @desc   用户信息
 */
class UserInfoActivity : BaseDataBindActivity<ActivityUserInfoBinding>() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, UserInfoActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        ViewUtils.setClipViewCornerRadius(mBinding.tvSave, dpToPx(20))
        initListener()
    }

    private fun initListener() {
        mBinding.clHead.onClick {

        }
        mBinding.clSex.onClick {
            TipsToast.showWarningTips(com.sum.common.R.string.default_developing)
        }
        mBinding.clBirthday.onClick {
        }
        mBinding.tvSave.onClick {
            showLoading("正在保存...")
            lifecycleScope.launch {
                delay(1000)
                dismissLoading()

            }
        }
    }
}
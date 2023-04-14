package com.sum.user.info

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.sum.common.constant.USER_ACTIVITY_INFO
import com.sum.common.provider.LoginServiceProvider
import com.sum.common.provider.UserServiceProvider
import com.sum.framework.base.BaseDataBindActivity
import com.sum.framework.ext.onClick
import com.sum.framework.toast.TipsToast
import com.sum.framework.utils.ViewUtils
import com.sum.framework.utils.dpToPx
import com.sum.user.databinding.ActivityUserInfoBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author mingyan.su
 * @date   2023/3/23 18:43
 * @desc   用户信息
 */
@Route(path = USER_ACTIVITY_INFO)
class UserInfoActivity : BaseDataBindActivity<ActivityUserInfoBinding>() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, UserInfoActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        ViewUtils.setClipViewCornerRadius(mBinding.tvSave, dpToPx(20))
        initUserInfo()
        initListener()
    }

    /**
     * 设置用户信息
     */
    private fun initUserInfo() {
        val user = UserServiceProvider.getUserInfo() ?: return
        mBinding.user = user
        mBinding.activity = this

//        user.apply {
//            val name = if (!nickname.isNullOrEmpty()) {
//                nickname
//            } else {
//                username
//            }
//            mBinding.etName.setText(name)
//            mBinding.tvPhone.text = username
//            mBinding.tvSex.text = if (sex.isNullOrEmpty()) "--" else sex
//            mBinding.etSignature.setText(signature)
//        }
    }

    private fun initListener() {
        mBinding.clHead.onClick {

        }
//        mBinding.clSex.onClick {
//            TipsToast.showWarningTips(com.sum.common.R.string.default_developing)
//        }
        mBinding.clBirthday.onClick {
        }
        mBinding.tvSave.onClick {
            val user = UserServiceProvider.getUserInfo()
            user?.let {
                user.nickname = mBinding.etName.text.toString()
                user.sex = mBinding.tvSex.text.toString()
                user.signature = mBinding.etSignature.text.toString()
                showLoading()
                lifecycleScope.launch {
                    UserServiceProvider.saveUserInfo(user)
                    delay(1000)
                    dismissLoading()
                    TipsToast.showTips(com.sum.common.R.string.default_save_success)
                }
            } ?: kotlin.run {
                LoginServiceProvider.login(this)
            }
        }
    }

    /**
     * 性别选择弹框
     */
    fun showSelectSexDialog() {
        TipsToast.showWarningTips(com.sum.common.R.string.default_developing)
    }
}
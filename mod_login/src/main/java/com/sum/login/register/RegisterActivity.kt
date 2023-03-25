package com.sum.login.register

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.alibaba.android.arouter.launcher.ARouter
import com.sum.common.constant.MAIN_ACTIVITY_HOME
import com.sum.common.constant.USER_INFO_DATA
import com.sum.common.constant.USER_PHONE
import com.sum.framework.base.BaseMvvmActivity
import com.sum.framework.ext.onClick
import com.sum.framework.ext.toJson
import com.sum.framework.log.LogUtil
import com.sum.framework.toast.TipsToast
import com.sum.framework.utils.getColorFromResource
import com.sum.framework.utils.getStringFromResource
import com.sum.login.login.LoginViewModel
import com.sum.login.R
import com.sum.login.databinding.ActivityRegisterBinding
import com.tencent.mmkv.MMKV

/**
 * @author mingyan.su
 * @date   2023/3/24 18:24
 * @desc   注册
 */
class RegisterActivity : BaseMvvmActivity<ActivityRegisterBinding, LoginViewModel>() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, RegisterActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        initAgreement()
        initListener()
    }

    private fun initListener() {
        mBinding.tvRegister.onClick {
            val userName = mBinding.etPhone.text?.trim()?.toString()
            val password = mBinding.etPassword.text?.trim()?.toString()
            val repassword = mBinding.etRepassword.text?.trim()?.toString()

            if (userName.isNullOrEmpty() || userName.length < 11) {
                TipsToast.showTips(getStringFromResource(R.string.error_phone_number))
                return@onClick
            }
            if (password.isNullOrEmpty() || repassword.isNullOrEmpty() || password != repassword) {
                TipsToast.showTips(R.string.error_double_password)
                return@onClick
            }
            if (!mBinding.cbAgreement.isChecked) {
                TipsToast.showTips(R.string.tips_read_user_agreement)
                return@onClick
            }
            showLoading(com.sum.common.R.string.default_loading)
            mViewModel.register(userName, password, repassword).observe(this) { user ->
                dismissLoading()
                user?.let {
                    TipsToast.showTips(R.string.success_register)
                    //保存用户信息
                    MMKV.defaultMMKV().encode(USER_INFO_DATA, it.toJson(false))
                    MMKV.defaultMMKV().encode(USER_PHONE, it.username)
                    LogUtil.e("user:$it", tag = "smy")
                    ARouter.getInstance().build(MAIN_ACTIVITY_HOME).navigation()
                    finish()
                } ?: kotlin.run {

                }
            }
        }
    }

    /**
     * 初始化协议点击
     */
    private fun initAgreement() {
        val agreement = getStringFromResource(R.string.login_agreement)
        try {
            mBinding.cbAgreement.movementMethod = LinkMovementMethod.getInstance()
            val spaBuilder = SpannableStringBuilder(agreement)
            val privacySpan = getStringFromResource(R.string.login_privacy_agreement)
            val serviceSpan = getStringFromResource(R.string.login_user_agreement)
            spaBuilder.setSpan(
                object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        (widget as TextView).highlightColor = getColorFromResource(com.sum.common.R.color.transparent)
                    }

                    @RequiresApi(Build.VERSION_CODES.M)
                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.color = getColorFromResource(com.sum.common.R.color.color_0165b8)
                        ds.isUnderlineText = false
                        ds.clearShadowLayer()
                    }
                },
                spaBuilder.indexOf(privacySpan),
                spaBuilder.indexOf(privacySpan) + privacySpan.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spaBuilder.setSpan(
                object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        (widget as TextView).highlightColor = getColorFromResource(com.sum.common.R.color.transparent)
                    }

                    @RequiresApi(Build.VERSION_CODES.M)
                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.color = getColorFromResource(com.sum.common.R.color.color_0165b8)
                        ds.isUnderlineText = false
                        ds.clearShadowLayer()
                    }
                },
                spaBuilder.indexOf(serviceSpan),
                spaBuilder.indexOf(serviceSpan) + serviceSpan.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            mBinding.cbAgreement.setText(spaBuilder, TextView.BufferType.SPANNABLE)
        } catch (e: Exception) {
            LogUtil.e(e)
            mBinding.cbAgreement.text = agreement
        }
    }

}
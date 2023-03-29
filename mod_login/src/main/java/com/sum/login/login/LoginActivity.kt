package com.sum.login.login

import android.os.Build
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.HideReturnsTransformationMethod
import android.text.method.LinkMovementMethod
import android.text.method.PasswordTransformationMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.alibaba.android.arouter.facade.annotation.Route
import com.sum.common.constant.LOGIN_ACTIVITY_LOGIN
import com.sum.common.provider.MainServiceProvider
import com.sum.common.provider.UserServiceProvider
import com.sum.framework.base.BaseMvvmActivity
import com.sum.framework.ext.onClick
import com.sum.framework.log.LogUtil
import com.sum.framework.toast.TipsToast
import com.sum.framework.utils.getColorFromResource
import com.sum.framework.utils.getStringFromResource
import com.sum.login.R
import com.sum.login.databinding.ActivityLoginBinding
import com.sum.login.register.RegisterActivity

/**
 * @author mingyan.su
 * @date   2023/3/25 8:11
 * @desc   登录
 */
@Route(path = LOGIN_ACTIVITY_LOGIN)
class LoginActivity : BaseMvvmActivity<ActivityLoginBinding, LoginViewModel>() {
    private var isShowPassword = true

    override fun initView(savedInstanceState: Bundle?) {
        initAgreement()
        initListener()
        mBinding.etPhone.setText(UserServiceProvider.getUserPhone())
        mBinding.etPhone.setSelection(mBinding.etPhone.length())
        mBinding.etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
    }

    override fun initData() {
        mViewModel.loginLiveData.observe(this) { user ->
            //登录成功
            dismissLoading()
            user?.let {
                UserServiceProvider.saveUserInfo(user)
                UserServiceProvider.saveUserPhone(user.username)
                TipsToast.showTips(R.string.success_login)
//                MainServiceProvider.toMain(context = this)
                finish()
            } ?: kotlin.run {

            }
        }
    }

    private fun initListener() {
        mBinding.ivPasswordToggle.onClick {
            setPasswordHide()
        }
        mBinding.tvForgetPassword.onClick {
            TipsToast.showTips(R.string.login_forget_password)
        }
        mBinding.tvLogin.onClick {
            toLogin()
        }
        mBinding.tvRegister.onClick {
            RegisterActivity.start(this)
        }
    }

    /**
     * 去登录
     */
    private fun toLogin() {
        val userName = mBinding.etPhone.text?.trim()?.toString()
        val password = mBinding.etPassword.text?.trim()?.toString()

        if (userName.isNullOrEmpty() || userName.length < 11) {
            TipsToast.showTips(getStringFromResource(R.string.error_phone_number))
            return
        }
        if (password.isNullOrEmpty()) {
            TipsToast.showTips(R.string.error_input_password)
            return
        }
        if (!mBinding.cbAgreement.isChecked) {
            TipsToast.showTips(R.string.tips_read_user_agreement)
            return
        }
        showLoading()
        mViewModel.login(userName, password)
    }

    /**
     * 密码是否可见
     */
    private fun setPasswordHide() {
        isShowPassword = !isShowPassword
        if (isShowPassword) {
            mBinding.ivPasswordToggle.setImageResource(R.mipmap.ic_psw_invisible)
            mBinding.etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
        } else {
            mBinding.etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            mBinding.ivPasswordToggle.setImageResource(R.mipmap.ic_psw_visible)
        }
        mBinding.etPassword.setSelection(mBinding.etPassword.length())
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
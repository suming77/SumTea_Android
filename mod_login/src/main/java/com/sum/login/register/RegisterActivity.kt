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
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.sum.common.constant.LOGIN_ACTIVITY_REGISTER
import com.sum.common.provider.MainServiceProvider
import com.sum.common.provider.UserServiceProvider
import com.sum.framework.base.BaseMvvmActivity
import com.sum.framework.ext.onClick
import com.sum.framework.ext.textChangeFlow
import com.sum.framework.log.LogUtil
import com.sum.framework.toast.TipsToast
import com.sum.framework.utils.getColorFromResource
import com.sum.framework.utils.getStringFromResource
import com.sum.login.login.LoginViewModel
import com.sum.login.R
import com.sum.login.databinding.ActivityRegisterBinding
import com.sum.login.policy.PrivacyPolicyActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * @author mingyan.su
 * @date   2023/3/24 18:24
 * @desc   注册
 */
@Route(path = LOGIN_ACTIVITY_REGISTER)
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
            toRegister()
        }

        setEditTextChange(mBinding.etPhone)
        setEditTextChange(mBinding.etPassword)
        setEditTextChange(mBinding.etRepassword)
        mBinding.cbAgreement.setOnCheckedChangeListener { _, _ ->
            updateLoginState()
        }
    }

    /**
     * 注册
     */
    private fun toRegister() {
        val userName = mBinding.etPhone.text?.trim()?.toString()
        val password = mBinding.etPassword.text?.trim()?.toString()
        val repassword = mBinding.etRepassword.text?.trim()?.toString()

        if (userName.isNullOrEmpty() || userName.length < 11) {
            TipsToast.showTips(getStringFromResource(R.string.error_phone_number))
            return
        }
        if (password.isNullOrEmpty() || repassword.isNullOrEmpty() || password != repassword) {
            TipsToast.showTips(R.string.error_double_password)
            return
        }
        if (!mBinding.cbAgreement.isChecked) {
            TipsToast.showTips(R.string.tips_read_user_agreement)
            return
        }
        showLoading(com.sum.common.R.string.default_loading)
        mViewModel.register(userName, password, repassword).observe(this) { user ->
            dismissLoading()
            user?.let {
                TipsToast.showTips(R.string.success_register)
                //保存用户信息
                UserServiceProvider.saveUserInfo(user)
                UserServiceProvider.saveUserPhone(user.username)
                LogUtil.e("user:$it", tag = "smy")
                MainServiceProvider.toMain(context = this)
                finish()
            } ?: kotlin.run {

            }
        }
    }

    /**
     * 更新登录按钮状态
     */
    private fun updateLoginState() {
        val phone = mBinding.etPhone.text.toString()
        val phoneEnable = !phone.isNullOrEmpty() && phone.length == 11
        val password = mBinding.etPassword.text.toString()
        val repassword = mBinding.etRepassword.text.toString()
        val passwordEnable = !password.isNullOrEmpty() && !repassword.isNullOrEmpty()
        val agreementEnable = mBinding.cbAgreement.isChecked

        mBinding.tvRegister.isSelected = phoneEnable && passwordEnable && agreementEnable
    }

    /**
     * 监听EditText文本变化
     */
    private fun setEditTextChange(editText: EditText) {
        editText.textChangeFlow()
//                .filter { it.isNotEmpty() }
                .debounce(300)
//                .flatMapLatest { searchFlow(it.toString()) }
                .flowOn(Dispatchers.IO)
                .onEach {
                    updateLoginState()
                }
                .launchIn(lifecycleScope)
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
                        PrivacyPolicyActivity.start(this@RegisterActivity)
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
                        PrivacyPolicyActivity.start(this@RegisterActivity)
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
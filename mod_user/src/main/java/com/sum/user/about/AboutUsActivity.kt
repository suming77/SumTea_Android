package com.sum.user.about

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.sum.common.constant.AUTHOR_GITHUB_LINK
import com.sum.common.constant.AUTHOR_MAIN_LINK
import com.sum.common.provider.MainServiceProvider
import com.sum.framework.base.BaseDataBindActivity
import com.sum.framework.ext.onClick
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

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.tvMainLink.text = AUTHOR_MAIN_LINK
        mBinding.tvGithub.text = AUTHOR_GITHUB_LINK

        mBinding.clMainLink.onClick {
            MainServiceProvider.toArticleDetail(
                context = this,
                url = AUTHOR_MAIN_LINK,
                title = mBinding.tvMainLinkTitle.text.toString()
            )
        }
        mBinding.clGithub.onClick {
            MainServiceProvider.toArticleDetail(
                context = this,
                url = AUTHOR_GITHUB_LINK,
                title = mBinding.tvGithubTitle.text.toString()
            )
        }
    }
}
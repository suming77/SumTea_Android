package com.sum.user.collection

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.sum.common.constant.USER_ACTIVITY_COLLECTION
import com.sum.common.databinding.LayoutNormalRecyclerviewBinding
import com.sum.framework.base.BaseMvvmActivity
import com.sum.user.R

/**
 * @author mingyan.su
 * @date   2023/3/24 18:26
 * @desc   我的收藏
 */
@Route(path = USER_ACTIVITY_COLLECTION)
class MyCollectionActivity : BaseMvvmActivity<LayoutNormalRecyclerviewBinding, MyCollectViewModel>() {

    override fun initView(savedInstanceState: Bundle?) {

    }
}
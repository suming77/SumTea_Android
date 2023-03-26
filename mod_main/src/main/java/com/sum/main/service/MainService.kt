package com.sum.main.service

import android.content.Context
import android.content.Intent
import com.alibaba.android.arouter.facade.annotation.Route
import com.sum.common.constant.KEY_INDEX
import com.sum.common.constant.MAIN_SERVICE_HOME
import com.sum.common.service.IMainService
import com.sum.main.MainActivity

/**
 * @author mingyan.su
 * @date   2023/3/26 18:23
 * @desc   主页服务
 * 提供对IMainService接口的具体实现
 */
@Route(path = MAIN_SERVICE_HOME)
class MainService : IMainService {
    /**
     * 跳转主页
     * @param context
     * @param index tab位置
     */
    override fun toMain(context: Context, index: Int) {
        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra(KEY_INDEX, index)
        context.startActivity(intent)
    }

    override fun init(context: Context?) {
    }
}
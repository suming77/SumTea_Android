package com.sum.demo.lifecycle

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.sum.common.constant.DEMO_ACTIVITY_LIFECYCLE
import com.sum.demo.databinding.ActivityLifecycleBinding
import com.sum.framework.base.BaseDataBindActivity

/**
 * @author mingyan.su
 * @date 2023/6/9 18:32
 * @desc Lifecycle三种使用方式
 */
@Route(path = DEMO_ACTIVITY_LIFECYCLE)
class DemoLifecycleActivity : BaseDataBindActivity<ActivityLifecycleBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        //第一种.注册观察者，观察宿主生命周期状态变化
        val observer = MyLifecycleObserver()
        lifecycle.addObserver(observer)

        //第二种
        val observer2 = MyFullLifeObserver()
        lifecycle.addObserver(observer2)

        //第三种
        val observer3 = MyLifecycleEventObserver()
        lifecycle.addObserver(observer3)
    }
}
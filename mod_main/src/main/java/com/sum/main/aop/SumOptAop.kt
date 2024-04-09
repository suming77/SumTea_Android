package com.sum.main.aop

import com.sum.framework.log.LogUtil
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import java.lang.Exception

/**
 * @author mingyan.su
 * @date   2024/4/10 23:18
 * @desc   AOP切面编程
 */
// 注解产生关联
@Aspect
class SumOptAop {

    // @Around每个函数前后都插入代码
    // 具体类+**+(..)表示什么参数都可以
    @Around("call(* com.sum.tea.SumApplication**(..))")
    fun getApplicationTime(joinPoint: ProceedingJoinPoint) {
        // 在函数内要加入的代码
        // 获取函数名称
        val signature = joinPoint.signature
        val curTime = System.currentTimeMillis()
        try {
            // 前后执行一次
            joinPoint.proceed()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        LogUtil.e("${signature.name} | time：${System.currentTimeMillis() - curTime}")
    }

    @Before("execution(* android.app.Activity.on**(..))")
    fun onActivityCalled(joinPoint: JoinPoint) {
        // 这里可以插入任意代码段
        LogUtil.e("所有:${joinPoint.signature.name}", tag = "onActivityCalled")
    }

//    @Before("execution(* set*(..))")
//    @After("execution(public * *(..))")
//    @Around("execution(android.app.Activity. *(..))")
//    @Around("execution(void android.view.View.OnClickListener.onClick(..))")
}
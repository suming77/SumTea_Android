package com.sum.tea.network

// 定义网络请求状态(密封类扩展性更好)
sealed class StateActionEvent

/**
 * 加载中
 */
object LoadState : StateActionEvent()

/**
 * 完成
 */
object SuccessState : StateActionEvent()

/**
 * 出错
 */
class ErrorState(val message: String?) : StateActionEvent()

package com.sum.tea.base

import androidx.lifecycle.*
import com.sum.tea.network.ErrorState
import com.sum.tea.network.LoadState
import com.sum.tea.network.ResultNet
import com.sum.tea.network.StateActionEvent
import com.sum.tea.network.SuccessState
import com.sum.tea.network.error.ExceptionHandle
import com.sum.tea.network.error.ResponseThrowable
import kotlinx.coroutines.*
import java.util.concurrent.CancellationException

/**
 * @创建者 mingyan.su
 * @创建时间 2023/2/20 10:28
 * @类描述 ViewModel基类
 */
open class BaseViewModel : ViewModel(), LifecycleObserver {
    // 通用事件模型驱动(如：显示对话框、取消对话框、错误提示)
    val mStateLiveData = MutableLiveData<StateActionEvent>()
    fun emitUiState(event: StateActionEvent) {
        mStateLiveData.value = event
    }

    /**
     * ========统一api调用使用协程===========
     * @bloack api调用
     * @success 成功
     * @erro 失败
     * @complete 完成
     * @isShowDialog 是否显示loading 默认显示
     */
    fun <T : Any> launchOnUIStateResult(
        block: suspend CoroutineScope.() -> ResultNet<T>,
        success: (T?) -> Unit,
        error: (ResponseThrowable) -> Unit = {
            emitUiState(ErrorState(it.errMsg))
        },
        complete: () -> Unit = {},
        isShowDialog: Boolean = false
    ) {
        if (isShowDialog) emitUiState(LoadState)
        launchUI {
            handleException(
                {
                    withContext(Dispatchers.IO) {
                        block().let {
                            if (it is ResultNet.Success) {
                                // 执行成功
                                it.data
                            } else {
                                // 执行失败
                                it as ResultNet.Error
                                throw ResponseThrowable(it.errCode, it.errMsg)
                            }
                        }
                    }.also { success(it) }
                },
                { error(it) },
                {
                    // 执行完成
                    if (isShowDialog) emitUiState(SuccessState)
                    complete()
                }
            )
        }
    }

    /**
     * 在UI线程运行
     */
    fun launchUI(block: suspend CoroutineScope.() -> Unit) {
        // 会在页面销毁的时候自动取消请求，不过必须要使用AndroidX,
        viewModelScope.launch {
            block()
        }
    }

    /**
     * 在子线程运行
     */
    fun launchIO(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            block()
        }
    }

    suspend fun <T> launchIO(block: suspend CoroutineScope.() -> T) {
        withContext(Dispatchers.IO) {
            block
        }
    }

    /**
     * 在UI线程 Try
     */
    fun launchTry(tryBlock: suspend CoroutineScope.() -> Unit) {
        launchUI {
            tryCatch(tryBlock, {}, {}, true)
        }
    }

    /**
     * 在UI线程 Try Catch
     */
    fun launchOnUITryCatch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
        finallyBlock: suspend CoroutineScope.() -> Unit,
        handleCancellationExceptionManually: Boolean
    ) {
        launchUI {
            tryCatch(tryBlock, catchBlock, finallyBlock, handleCancellationExceptionManually)
        }
    }

    /**
     * LiveData发射数据
     */
    fun <T> emit(block: suspend LiveDataScope<T>.() -> T): LiveData<T> = liveData {
        try {
            emit(block())
        } catch (e: Exception) {
            emitUiState(ErrorState(e.message))
        }
    }

    /**
     * LiveData发射数据-带UI状态
     */
    fun <T> emitOnState(block: suspend LiveDataScope<T>.() -> T): LiveData<T> = liveData {
        try {
            emitUiState(LoadState)
            emit(block())
            emitUiState(SuccessState)
        } catch (e: Exception) {
            var erro = ExceptionHandle.handleException(e)
            var erroMsg = erro.message
            emitUiState(ErrorState(erroMsg))
        }
    }

    /**
     * 异常统一处理
     */
    private suspend fun handleException(
        block: suspend CoroutineScope.() -> Unit,
        error: suspend CoroutineScope.(ResponseThrowable) -> Unit,
        complete: suspend CoroutineScope.() -> Unit
    ) {
        coroutineScope {
            try {
                block()
            } catch (e: Throwable) {
                error(ExceptionHandle.handleException(e))
            } finally {
                complete()
            }
        }
    }

    private suspend fun tryCatch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
        finallyBlock: suspend CoroutineScope.() -> Unit,
        handleCancellationExceptionManually: Boolean = false
    ) {
        coroutineScope {
            try {
                tryBlock()
            } catch (e: Throwable) {
                if (e !is CancellationException || handleCancellationExceptionManually) {
                    emitUiState(ErrorState(e.message))
                    catchBlock(e)
                } else {
                    throw e
                }
            } finally {
                finallyBlock()
            }
        }
    }
}

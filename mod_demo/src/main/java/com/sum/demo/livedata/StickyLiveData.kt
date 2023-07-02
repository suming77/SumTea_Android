package com.sum.demo.livedata

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.ConcurrentHashMap

/**
 * 扩展LiveData，支持粘性事件的订阅，分发的StickyLiveData
 *
 * @param <T>
 */
class StickyLiveData<T>(private val mEventName: String) : LiveData<T>() {
    var mStickyData: T? = null
    // 版本标记
    var mVersion = 0
    // 事件存储集合
    var mHashMap: ConcurrentHashMap<String, StickyLiveData<T>>? = null

    /**
     * 调用mVersion++
     * 注册一个Observer的时候，把它包装一下，目的是为了让Observer的version和LiveData的version对齐
     * 但是LiveData的version字段拿不到，所以需要管理version，在对齐的时候
     *
     * @param value
     */
    override fun setValue(value: T?) {
        mVersion++
        super.setValue(value)
    }

    override fun postValue(value: T?) {
        mVersion++
        super.postValue(value)
    }

    /**
     * 发送粘性事件
     * 只能在主线程发送数据
     *
     * @param stickyData
     */
    fun setStickData(stickyData: T) {
        mStickyData = stickyData
        value = stickyData
    }

    /**
     * 发送粘性事件，不受线程限制
     *
     * @param stickyData
     */
    fun postStickData(stickyData: T) {
        mStickyData = stickyData
        postValue(stickyData)
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in T?>) {
        observeSticky(owner, observer, false)
    }

    /**
     * 暴露方法，是否关心之前发送的数据,再往宿主上面添加一个addObserver监听生命周期事件，如果是DESTORYED则主动移除LiveData
     *
     * @param owner
     * @param observer
     * @param sticky   是否为粘性事件，sticky=true,如果之前存在已经发送数据，那么Observer就会收到之前的粘性事件消息
     */
    fun observeSticky(owner: LifecycleOwner, observer: Observer<in T>, sticky: Boolean) {
        owner.lifecycle.addObserver(LifecycleEventObserver { source, event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                mHashMap?.remove(mEventName)
            }
        })
        super.observe(owner, StickyObserver(this, observer as Observer<T>, sticky))
    }
}
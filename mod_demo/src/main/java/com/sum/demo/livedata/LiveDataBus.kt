package com.sum.demo.livedata

import androidx.lifecycle.*
import java.util.concurrent.ConcurrentHashMap

/**
 * 跨页面事件总线
 * object单例，实现共享
 */
object LiveDataBus {
    private var mEventMap = ConcurrentHashMap<String, StickyLiveData<*>>()

    /**
     * 消息总线，是需要名称的
     */
    fun <T> with(eventName: String): StickyLiveData<T> {
        //基于事件名称 订阅、分发消息，为什么要返回StickyLiveData对象，因为每一种对象对应的消息体都是不一样的，
        //由于一个LiveData只能发送一种数据类型，所以不同的Event事件，需要使用不同的LiveData实例去分发

        var liveData = mEventMap[eventName]
        if (liveData == null) {
            liveData = StickyLiveData<T>(eventName)
            mEventMap[eventName] = liveData
        }
        return liveData as StickyLiveData<T>
    }

    class StickyLiveData<T>(private val mEventName: String) : LiveData<T>() {
        var mStickyData: T? = null
        var mVersion = 0

        /**
         * 调用mVersion++
         * 在我们注册一个Observer的时候，我们需要把它包装一下，目的是为了让Observer的version和LiveData的version对齐
         * 但是LiveData的version字段拿不到，所以需要自己管理version,在对齐的时候使用这个就可以了
         *
         * @param value
         */
        override fun setValue(value: T) {
            mVersion++
            super.setValue(value)
        }

        override fun postValue(value: T) {
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
            setValue(stickyData)
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

        override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
            observeSticky(owner, observer, false)
        }

        /**
         * 暴露方法，是否关心之前发送的数据,再往宿主上面添加一个addObserver监听生命周期事件，如果是DESTORY则
         * 主动移除LiveData
         *
         * @param owner
         * @param observer
         * @param sticky   是否为粘性事件，sticky=true,如果之前存在已经发送数据，那么者Observer就会收到之前的粘性事件消息
         */
        fun observeSticky(owner: LifecycleOwner, observer: Observer<in T>, sticky: Boolean) {
            owner.lifecycle.addObserver(LifecycleEventObserver { source, event ->
                if (event == Lifecycle.Event.ON_DESTROY) {
                    mEventMap?.remove(mEventName)
                }
            })
            super.observe(owner, StickyObserver(this, observer as Observer<T>, sticky))
        }
    }

    /**
     * 包装StickyObserver，有新的消息会回调onChanged方法，从这里判断是否要分发这条消息
     * 这只是完成StickyObserver的包装，用于控制事件的分发与否，但是事件的发送还是依靠LiveData来完成的
     * @param <T>
     */
    internal class StickyObserver<T>(
        val stickyLiveData: StickyLiveData<T>,
        val observer: Observer<in T>,
        val sticky: Boolean//是否开启粘性事件,为false则只能接受到注册之后发送的消息，如果需要接受粘性事件则传true
    ) : Observer<T> {
        //标记该Observer已经接收几次数据了，过滤老数据防止重复接收
        private var mLastVersion = 0
        override fun onChanged(t: T) {
            if (mLastVersion >= stickyLiveData.mVersion) { //如果相等则说明没有更新的数据要发送
                //但是如果当前Observer是关系粘性事件的，则分发给他
                if (sticky && stickyLiveData.mStickyData != null) {
                    observer.onChanged(stickyLiveData.mStickyData)
                }
                return
            }
            mLastVersion = stickyLiveData.mVersion
            observer.onChanged(t)
        }
    }

}
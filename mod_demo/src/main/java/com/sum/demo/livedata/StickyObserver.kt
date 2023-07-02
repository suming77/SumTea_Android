package com.sum.demo.livedata

import androidx.lifecycle.Observer

/**
 * 包装StickyObserver，有新的消息会回调onChanged方法，从这里判断是否要分发这条消息
 * 这只是完成StickyObserver的包装，用于控制事件的分发，但是事件的发送还是依靠LiveData来完成的
 *
 * @param <T>
 */
internal class StickyObserver<T>(
    liveData: StickyLiveData<T>,
    observer: Observer<T>,
    sticky: Boolean
) : Observer<T> {
    private val mLiveData: StickyLiveData<T>
    private val mObserver: Observer<T>

    //是否开启粘性事件,为false则只能接受到注册之后发送的消息，如果需要接受粘性事件则传true
    private val mSticky: Boolean

    //标记该Observer已经接收几次数据了，过滤老数据防止重复接收
    private var mLastVersion = 0

    init {
        //比如先使用StickLiveData发送了一条数据，StickLiveData#version=1
        //那么当创建WrapperObserver注册进去的时候，需要把它的version和StickLiveData的version保持一致
        mLastVersion = liveData.mVersion
        mLiveData = liveData
        mSticky = sticky
        mObserver = observer
    }

    override fun onChanged(t: T) {
        if (mLastVersion >= mLiveData.mVersion) { //如果相等则说明没有更新的数据要发送
            //但是如果当前Observer是关系粘性事件的，则分发给他
            if (mSticky && mLiveData.mStickyData != null) {
                mObserver.onChanged(mLiveData.mStickyData)
            }
            return
        }
        mLastVersion = mLiveData.mVersion
        mObserver.onChanged(t)
    }
}
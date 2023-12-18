package com.sum.common.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.sum.common.BR

/**
 * @author mingyan.su
 * @date   2023/12/14 12:10
 * @desc   Databinding单双向绑定数据类
 */
data class Student(var name: String) : BaseObservable() {

    //当使用name字段发生变更后，若想UI自动刷新，
    //要求方法名必须以get开头并且标记Bindable注解
    //注解才会自动在build目录BR类中生成entry
    @Bindable
    fun getStuName(): String {
        return name
    }

    fun setStuName(name: String) {
        this.name = name
        // 手动刷新
        notifyPropertyChanged(BR.stuName)
    }

    var age: String? = null
}
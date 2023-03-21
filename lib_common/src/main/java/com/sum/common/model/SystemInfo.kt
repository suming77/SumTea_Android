package com.sum.common.model

/**
 * @author mingyan.su
 * @date   2023/3/21 7:52
 * @desc   体系
 */
data class SystemList(
    val id: Int,
    val courseId: Int,
    val name: String,       //一级名称
    val children: MutableList<SystemSecondList>,
    val visible: Int
)

/**
 * 二级列表
 */
data class SystemSecondList(
    val id: Int,
    val name: String,
    val visible: Int
)
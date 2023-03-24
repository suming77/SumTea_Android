package com.sum.common.constant

/**
 * @author mingyan.su
 * @date   2023/3/23 18:35
 * @desc   路由路径
 * 命名规则：/开头并且必须大于两级，/模块/分类/具体名称
 * 比如: /模块名称/组件[activity]/组件名称
 *       /模块名称/服务[service]/服务名称
 */

/**
 * 用户模块-设置界面
 */
const val USER_ACTIVITY_SETTING = "/user/activity/setting"

/**
 * 搜索模块-搜索页面
 */
const val SEARCH_ACTIVITY_SEARCH = "/search/activity/search"

/**
 * 登录模块-登录页面
 */
const val LOGIN_ACTIVITY_LOGIN = "/login/activity/login"

/**
 * 登录模块-注册页面
 */
const val LOGIN_ACTIVITY_REGISTER = "/login/activity/register"

/**
 * 首页模块-首页
 */
const val MAIN_ACTIVITY_HOME = "/main/activity/home"

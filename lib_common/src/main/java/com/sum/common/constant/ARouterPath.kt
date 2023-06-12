package com.sum.common.constant

/**
 * @author mingyan.su
 * @date   2023/3/23 18:35
 * @desc   路由路径
 * 命名规则：/开头并且必须大于两级，/模块/分类/具体名称
 * 比如: /模块名称/组件[activity]/组件名称
 *       /模块名称/服务[service]/服务名称
 */

//**********************跳转相关**********************

/**
 * 登录模块
 */
//登录页面
const val LOGIN_ACTIVITY_LOGIN = "/login/activity/login"
//注册页面
const val LOGIN_ACTIVITY_REGISTER = "/login/activity/register"
//隐私协议界面
const val Login_ACTIVITY_POLICY = "/login/activity/policy"

/**
 * 首页模块
 */
//首页
const val MAIN_ACTIVITY_HOME = "/main/activity/home"

/**
 * 用户模块
 */
//设置界面
const val USER_ACTIVITY_SETTING = "/user/activity/setting"
//设置用户信息
const val USER_ACTIVITY_INFO = "/user/activity/info"
//我的收藏界面
const val USER_ACTIVITY_COLLECTION = "/user/activity/collection"

/**
 * 搜索模块-搜索页面
 */
const val SEARCH_ACTIVITY_SEARCH = "/search/activity/search"

/**
 * 视频模块
 */
const val VIDEO_ACTIVITY_PLAYER = "/video/activity/player"

/**
 * Demo模块
 */
//Navigation
const val DEMO_ACTIVITY_NAVIGATION = "/demo/activity/navigation"
//Lifecycle
const val DEMO_ACTIVITY_LIFECYCLE = "/demo/activity/lifecycle"

//**********************服务相关**********************

/**
 * 登录模块-登录服务
 */
const val LOGIN_SERVICE_LOGIN = "/login/service/login"

/**
 * 用户模块-用户服务
 */
const val USER_SERVICE_USER = "/user/service/user"

/**
 * 主页模块-主页
 */
const val MAIN_SERVICE_HOME = "/main/service/home"

/**
 * 搜索模块模块-搜索
 */
const val SEARCH_SERVICE_SEARCH = "/search/service/search"
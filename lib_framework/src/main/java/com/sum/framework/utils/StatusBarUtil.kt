package com.sum.framework.utils

import android.R
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.core.view.ViewCompat
import com.sum.framework.ext.gone
import com.sum.framework.ext.visible
import com.sum.framework.log.LogUtil

/**
 * 状态栏工具类(状态栏文字颜色)
 */
object StatusBarUtil {
    const val STATUS_BAR_TYPE_DEFAULT = 0
    const val STATUS_BAR_TYPE_MI_UI = 1
    const val STATUS_BAR_TYPE_FLY_ME = 2
    const val STATUS_BAR_TYPE_ANDROID_M = 3
    const val STATUS_BAR_TYPE_OPP = 4
    const val SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT = 0x00000010

    /**
     * 设置状态栏浅色模式--黑色字体图标，
     *
     * @param activity
     * @return
     */
    fun setStatusBarLightMode(activity: Activity?): Int {
        var result = STATUS_BAR_TYPE_DEFAULT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && activity != null) { //MIUI 9版本开始状态栏文字颜色恢复为系统原生方案-为防止反复修改先进行6.0方案
            if (setStatusBarModeForAndroidM(activity.window, true)) {
                result = STATUS_BAR_TYPE_ANDROID_M
            }
            if (setStatusBarModeForMIUI(activity.window, true)) {
                result = STATUS_BAR_TYPE_MI_UI
            } else if (setStatusBarModeForFlyMe(activity.window, true)) {
                result = STATUS_BAR_TYPE_FLY_ME
            }
        }
        return result
    }

    /**
     * 设置状态栏深色模式--白色字体图标，
     *
     * @param activity
     * @return
     */
    fun setStatusBarDarkMode(activity: Activity?): Int {
        var result = STATUS_BAR_TYPE_DEFAULT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && activity != null) { //MIUI 9版本开始状态栏文字颜色恢复为系统原生方案-为防止反复修改先进行6.0方案
            if (setStatusBarModeForAndroidM(activity.window, false)) {
                result = STATUS_BAR_TYPE_ANDROID_M
            }
            if (setStatusBarModeForMIUI(activity.window, false)) {
                result = STATUS_BAR_TYPE_MI_UI
            } else if (setStatusBarModeForFlyMe(activity.window, false)) {
                result = STATUS_BAR_TYPE_FLY_ME
            }
        }
        return result
    }

    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window   需要设置的窗口
     * @param darkText 是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private fun setStatusBarModeForMIUI(
        window: Window?,
        darkText: Boolean
    ): Boolean {
        var result = false
        if (window != null) {
            val clazz: Class<*> = window.javaClass
            try {
                var darkModeFlag = 0
                val layoutParams =
                    Class.forName("android.view.MiuiWindowManager\$LayoutParams")
                val field =
                    layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                darkModeFlag = field.getInt(layoutParams)
                val extraFlagField = clazz.getMethod(
                    "setExtraFlags",
                    Int::class.javaPrimitiveType,
                    Int::class.javaPrimitiveType
                )
                if (darkText) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag) //状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag) //清除黑色字体
                }
                var vis = window.decorView.systemUiVisibility
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                vis =
                    if (darkText) {
                        //window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                        //window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        vis or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    } else {
                        //window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        //window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                        vis and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                    }
                window.decorView.systemUiVisibility = vis
                // 部分机型的status bar会有半透明的黑色背景
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    // SDK21
                    window.statusBarColor = Color.TRANSPARENT
                }
                result = true
            } catch (e: Exception) {
                LogUtil.w(e)
            }
        }
        return result
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window   需要设置的窗口
     * @param darkText 是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private fun setStatusBarModeForFlyMe(
        window: Window?,
        darkText: Boolean
    ): Boolean {
        var result = false
        if (window != null) {
            try {
                val lp = window.attributes
                val darkFlag = WindowManager.LayoutParams::class.java
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
                val meizuFlags = WindowManager.LayoutParams::class.java
                        .getDeclaredField("meizuFlags")
                darkFlag.isAccessible = true
                meizuFlags.isAccessible = true
                val bit = darkFlag.getInt(null)
                var value = meizuFlags.getInt(lp)
                value = if (darkText) {
                    value or bit
                } else {
                    value and bit.inv()
                }
                meizuFlags.setInt(lp, value)
                window.attributes = lp
                result = true
            } catch (e: Exception) {
                LogUtil.w(e)
            }
        }
        return result
    }

    /**
     * 设置原生Android 6.0以上系统状态栏
     *
     * @param window
     * @param darkText 是否把状态栏字体及图标颜色设置为深色
     * @return
     */
    private fun setStatusBarModeForAndroidM(
        window: Window?,
        darkText: Boolean
    ): Boolean {
        var result = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && window != null) {
            window.decorView.systemUiVisibility =
                if (darkText) View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or 0x00002000 else View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_VISIBLE
            result = true
        }
        return result
    }

    fun setStatusBarModeForOpp(
        window: Window,
        darkText: Boolean
    ): Boolean {
        var result = false
        try {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            var vis = window.decorView.systemUiVisibility
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                vis = if (darkText) {
                    vis or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else {
                    vis and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                vis = if (darkText) {
                    vis or SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT
                } else {
                    vis and SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT.inv()
                }
            }
            window.decorView.systemUiVisibility = vis
            result = true
        } catch (e: Exception) {
            LogUtil.w(e)
        }
        return result
    }

    /**
     * 设置状态栏颜色
     */
    fun setColor(
        activity: Activity, @ColorInt color: Int,
        statusBarAlpha: Int
    ) { //先设置的全屏模式
        setFullScreen(activity)
        //在透明状态栏的垂直下方放置一个和状态栏同样高宽的view
        addStatusBarBehind(activity, color, statusBarAlpha)
    }

    /**
     * 添加了一个状态栏(实际上是个view)，放在了状态栏的垂直下方
     */
    fun addStatusBarBehind(
        activity: Activity, @ColorInt color: Int,
        statusBarAlpha: Int
    ) { //获取windowphone下的decorView
        val decorView = activity.window.decorView as ViewGroup
        val count = decorView.childCount
        //判断是否已经添加了statusBarView
        if (count > 0 && decorView.getChildAt(count - 1) is StatusBarView) {
            decorView.getChildAt(count - 1)
                    .setBackgroundColor(calculateStatusColor(color, statusBarAlpha))
        } else { //新建一个和状态栏高宽的view
            val statusView = createStatusBarView(activity, color, statusBarAlpha)
            decorView.addView(statusView)
        }
        setRootView(activity)
    }

    fun setTranslucentImageHeader(
        activity: Activity,
        alpha: Int,
        needOffsetView: View?,
        intArray: IntArray = intArrayOf(0, 0, 0),
        hasOffset: Boolean = true
    ) {
        setFullScreen(activity)
        //获取windowphone下的decorView
        val decorView = activity.window.decorView as ViewGroup
        val count = decorView.childCount
        //判断是否已经添加了statusBarView
        if (count > 0 && decorView.getChildAt(count - 1) is StatusBarView) {
            decorView.getChildAt(count - 1)
                    .setBackgroundColor(Color.argb(alpha, intArray[0], intArray[1], intArray[2]))
        } else { //新建一个和状态栏高宽的view
            val statusView = createTranslucentStatusBarView(activity, alpha)
            decorView.addView(statusView)
        }
        if (needOffsetView != null && hasOffset) {
            val layoutParams =
                needOffsetView.layoutParams as MarginLayoutParams
            layoutParams.setMargins(0, getStatusBarHeight(activity), 0, 0)
        }
    }

    /**
     * 隐藏添加的StatusBarView
     */
    fun hideStatusBarView(activity: Activity) {
        val decorView = activity.window.decorView as ViewGroup
        val count = decorView.childCount
        if (count > 0 && decorView.getChildAt(count - 1) is StatusBarView) {
            decorView.getChildAt(count - 1).gone()
        }
    }

    /**
     * 显示添加的StatusBarView
     */
    fun showStatusBarView(activity: Activity) {
        val decorView = activity.window.decorView as ViewGroup
        val count = decorView.childCount
        if (count > 0 && decorView.getChildAt(count - 1) is StatusBarView) {
            decorView.getChildAt(count - 1).visible()
        }
    }

    private fun createTranslucentStatusBarView(
        activity: Activity,
        alpha: Int
    ): StatusBarView { // 绘制一个和状态栏一样高的矩形
        val statusBarView = StatusBarView(activity)
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            getStatusBarHeight(activity)
        )
        statusBarView.layoutParams = params
        statusBarView.setBackgroundColor(Color.argb(alpha, 0, 0, 0))
        return statusBarView
    }

    /**
     * 设置根布局参数
     */
    private fun setRootView(activity: Activity) {
        val rootView =
            (activity.findViewById<View>(R.id.content) as ViewGroup).getChildAt(
                0
            ) as ViewGroup
        //rootview不会为状态栏流出状态栏空间
        ViewCompat.setFitsSystemWindows(rootView, true)
        rootView.clipToPadding = true
    }

    private fun createStatusBarView(
        activity: Activity,
        color: Int,
        alpha: Int
    ): StatusBarView { // 绘制一个和状态栏一样高的矩形
        val statusBarView = StatusBarView(activity)
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            getStatusBarHeight(activity)
        )
        statusBarView.layoutParams = params
        statusBarView.setBackgroundColor(calculateStatusColor(color, alpha))
        return statusBarView
    }

    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度
     */
    private fun getStatusBarHeight(context: Context): Int { // 获得状态栏高度
        val resourceId =
            context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return context.resources.getDimensionPixelSize(resourceId)
    }

    /**
     * 计算状态栏颜色
     *
     * @param color color值
     * @param alpha alpha值
     * @return 最终的状态栏颜色
     */
    private fun calculateStatusColor(color: Int, alpha: Int): Int {
        val a = 1 - alpha / 255f
        var red = color shr 16 and 0xff
        var green = color shr 8 and 0xff
        var blue = color and 0xff
        red = (red * a + 0.5).toInt()
        green = (green * a + 0.5).toInt()
        blue = (blue * a + 0.5).toInt()
        return 0xff shl 24 or (red shl 16) or (green shl 8) or blue
    }

    fun setFullScreen(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity.window
            window.clearFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
            )
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 设置透明状态栏,这样才能让 ContentView 向上
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

    class StatusBarView : View {
        constructor(context: Context?) : super(context)
        constructor(context: Context?, attrs: AttributeSet?) : super(
            context,
            attrs
        )

        constructor(
            context: Context?,
            attrs: AttributeSet?,
            defStyleAttr: Int
        ) : super(context, attrs, defStyleAttr)
    }


    /**
     * @param darkContent true:白底黑字
     * @param statusBarColor 状态栏背景色
     * @param translucent 沉浸式效果，页面布局延伸到状态栏里面
     */
    fun setStatusBar(
        activity: Activity,
        darkContent: Boolean,
        statusBarColor: Int = Color.WHITE,
        translucent: Boolean
    ) {

        //所有参数都是作用在window
        val window = activity.window
        //根布局
        val decorView = window.decorView
        //代表一些行为
        var visibility = decorView.systemUiVisibility

        //大于5.0才去改变状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //为了兼容不同的平台，不同的系统版本，让statusBarColor都能生效，需要为window增加一个flag
            //FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS请求系统绘制状态栏背景色，但是不能与FLAG_TRANSLUCENT_STATUS同时出现
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = statusBarColor
        }

        //设置浅色能力，需要6.0以上的能力
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            visibility = if (darkContent) {
                //白底黑字，浅色主题
                //需要给visibility增加SYSTEM_UI_FLAG_LIGHT_STATUS_BAR,能使状态栏字体颜色变黑色，背景变白色，同时更新visibility的值
                visibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                //深色主题，黑体白字 需要除去SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                //java写法：visibility &=亦或 View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR，kotlin不支持这种写法
                visibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
        }

        if (translucent) {
            // View.SYSTEM_UI_FLAG_FULLSCREEN：能使页面延伸到状态栏里面，但是状态栏的图标(信号，时间)也看不见了，即全屏模式
            //所以会搭配另外一个flag:SYSTEM_UI_FLAG_LAYOUT_STABLE恢复状态栏图标
            visibility =
                visibility or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }

        decorView.systemUiVisibility = visibility
    }
}
package com.sum.video.animatorpath

import java.util.ArrayList

/**
 * 客户端使用类,记录一系列的不同移动轨迹
 */
class AnimatorPath {
    //一系列的轨迹记录动作
    private val mPoints: MutableList<PathPoint> = ArrayList()

    /**
     * 移动位置到:
     * @param x
     * @param y
     */
    fun moveTo(x: Float, y: Float) {
        mPoints.add(PathPoint.moveTo(x, y))
    }

    /**
     * 直线移动
     * @param x
     * @param y
     */
    fun lineTo(x: Float, y: Float) {
        mPoints.add(PathPoint.lineTo(x, y))
    }

    /**
     * 二阶贝塞尔曲线移动
     * @param c0X
     * @param c0Y
     * @param x
     * @param y
     */
    fun secondBesselCurveTo(c0X: Float, c0Y: Float, x: Float, y: Float) {
        mPoints.add(PathPoint.secondBesselCurveTo(c0X, c0Y, x, y))
    }

    /**
     * 三阶贝塞尔曲线移动
     * @param c0X
     * @param c0Y
     * @param c1X
     * @param c1Y
     * @param x
     * @param y
     */
    fun thirdBesselCurveTo(c0X: Float, c0Y: Float, c1X: Float, c1Y: Float, x: Float, y: Float) {
        mPoints.add(PathPoint.thirdBesselCurveTo(c0X, c0Y, c1X, c1Y, x, y))
    }

    /**
     *
     * @return  返回移动动作集合
     */
    val points: Collection<PathPoint>
        get() = mPoints
}
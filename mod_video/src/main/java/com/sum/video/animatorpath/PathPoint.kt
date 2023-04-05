package com.sum.video.animatorpath

/**
 * 记录view移动动作的坐标点
 */
class PathPoint {
    /**
     * View移动到的最终位置
     */
    var mX: Float
    var mY: Float

    /**
     * 控制点
     */
    var mContorl0X = 0f
    var mContorl0Y = 0f
    var mContorl1X = 0f
    var mContorl1Y = 0f

    //操作符
    var mOperation: Int

    /**
     * Line/Move都通过该构造函数来创建
     */
    constructor(mOperation: Int, mX: Float, mY: Float) {
        this.mX = mX
        this.mY = mY
        this.mOperation = mOperation
    }

    /**
     * 二阶贝塞尔曲线
     * @param mX
     * @param mY
     * @param mContorl0X
     * @param mContorl0Y
     */
    constructor(mContorl0X: Float, mContorl0Y: Float, mX: Float, mY: Float) {
        this.mX = mX
        this.mY = mY
        this.mContorl0X = mContorl0X
        this.mContorl0Y = mContorl0Y
        mOperation = SECOND_CURVE
    }

    /**
     * 三阶贝塞尔曲线
     * @param mContorl0x
     * @param mContorl0Y
     * @param mContorl1x
     * @param mContorl1Y
     * @param mX
     * @param mY
     */
    constructor(mContorl0x: Float, mContorl0Y: Float, mContorl1x: Float, mContorl1Y: Float, mX: Float, mY: Float) {
        this.mX = mX
        this.mY = mY
        mContorl0X = mContorl0x
        this.mContorl0Y = mContorl0Y
        mContorl1X = mContorl1x
        this.mContorl1Y = mContorl1Y
        mOperation = THIRD_CURVE
    }

    companion object {
        /**
         * 起始点操作
         */
        const val MOVE = 0

        /**
         * 直线路径操作
         */
        const val LINE = 1

        /**
         * 二阶贝塞尔曲线操作
         */
        const val SECOND_CURVE = 2

        /**
         * 三阶贝塞尔曲线操作
         */
        const val THIRD_CURVE = 3

        /**
         * 为了方便使用都用静态的方法来返回路径点
         */
        @JvmStatic
        fun moveTo(x: Float, y: Float): PathPoint {
            return PathPoint(MOVE, x, y)
        }

        @JvmStatic
        fun lineTo(x: Float, y: Float): PathPoint {
            return PathPoint(LINE, x, y)
        }

        @JvmStatic
        fun secondBesselCurveTo(c0X: Float, c0Y: Float, x: Float, y: Float): PathPoint {
            return PathPoint(c0X, c0Y, x, y)
        }

        @JvmStatic
        fun thirdBesselCurveTo(c0X: Float, c0Y: Float, c1X: Float, c1Y: Float, x: Float, y: Float): PathPoint {
            return PathPoint(c0X, c0Y, c1X, c1Y, x, y)
        }
    }
}
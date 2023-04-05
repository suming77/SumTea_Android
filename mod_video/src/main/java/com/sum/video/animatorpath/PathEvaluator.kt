package com.sum.video.animatorpath

import android.animation.TypeEvaluator

/**
 * 估值器类,实现坐标点的计算
 */
class PathEvaluator : TypeEvaluator<PathPoint> {
    /**
     * @param t          :执行的百分比
     * @param startValue : 起点
     * @param endValue   : 终点
     * @return
     */
    override fun evaluate(t: Float, startValue: PathPoint, endValue: PathPoint): PathPoint {
        val x: Float
        val y: Float
        val oneMiunsT = 1 - t
        //三阶贝塞尔曲线
        if (endValue.mOperation == PathPoint.THIRD_CURVE) {
            x =
                startValue.mX * oneMiunsT * oneMiunsT * oneMiunsT + 3 * endValue.mContorl0X * t * oneMiunsT * oneMiunsT + 3 * endValue.mContorl1X * t * t * oneMiunsT + endValue.mX * t * t * t
            y =
                startValue.mY * oneMiunsT * oneMiunsT * oneMiunsT + 3 * endValue.mContorl0Y * t * oneMiunsT * oneMiunsT + 3 * endValue.mContorl1Y * t * t * oneMiunsT + endValue.mY * t * t * t
            //二阶贝塞尔曲线
        } else if (endValue.mOperation == PathPoint.SECOND_CURVE) {
            x = oneMiunsT * oneMiunsT * startValue.mX + 2 * t * oneMiunsT * endValue.mContorl0X + t * t * endValue.mX
            y = oneMiunsT * oneMiunsT * startValue.mY + 2 * t * oneMiunsT * endValue.mContorl0Y + t * t * endValue.mY
            //直线
        } else if (endValue.mOperation == PathPoint.LINE) {
            //x起始点+t*起始点和终点的距离
            x = startValue.mX + t * (endValue.mX - startValue.mX)
            y = startValue.mY + t * (endValue.mY - startValue.mY)
        } else {
            x = endValue.mX
            y = endValue.mY
        }
        return PathPoint.moveTo(x, y)
    }
}
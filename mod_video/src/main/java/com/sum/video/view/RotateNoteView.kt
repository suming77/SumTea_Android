package com.sum.video.view

import android.widget.FrameLayout
import android.animation.ObjectAnimator
import com.sum.video.animatorpath.AnimatorPath
import android.view.animation.LinearInterpolator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import com.sum.video.animatorpath.PathEvaluator
import com.sum.video.animatorpath.PathPoint
import com.sum.video.databinding.LayoutRotateNoteViewBinding
import java.util.ArrayList

/**
 * 旋转音盒
 */
class RotateNoteView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var objectAnimator: ObjectAnimator? = null
    var state = 0

    //声明动画集合
    private var path: AnimatorPath? = null
    private var mObjectAnimators: ArrayList<ObjectAnimator?>? = ArrayList()
    private var outSideCL__rect: Rect? = null
    private var note_quaver_1_rect: Rect? = null
    private var isAnimPlaying = false

    //private Animation rotate;
    private val mOnClickListener: OnClickListener? = null

    private val mBinding = LayoutRotateNoteViewBinding.inflate(LayoutInflater.from(context), this)

    /**
     * 清理动画
     */
    fun initAnimator() {
        mBinding.musicButtonMbRl.clearAnimation()
        mBinding.musicInfoIv.clearAnimation()
        mBinding.noteQuaver1.clearAnimation()
        mBinding.noteQuaver2.clearAnimation()
        mBinding.noteQuaver3.clearAnimation()
        mBinding.noteQuaver4.clearAnimation()
        state = STATE_STOP
        objectAnimator = ObjectAnimator.ofFloat(mBinding.musicButtonMbRl, "rotation", 0f, 360f)
        //添加旋转动画，旋转中心默认为控件中点
        objectAnimator?.duration = DURATION_TIME.toLong()
        //设置动画时间
        objectAnimator?.interpolator = LinearInterpolator()
        //动画时间线性渐变
        objectAnimator?.repeatCount = ObjectAnimator.INFINITE
        objectAnimator?.repeatMode = ObjectAnimator.RESTART
    }

    fun setBgmOnClickListener(clickListener: OnClickListener?) {
        mBinding.musicButtonMbRl.setOnClickListener { view -> clickListener?.onClick(view) }
    }

    /**
     * 设置动画路径
     */
    fun setPath() {
        path = AnimatorPath()
        path?.moveTo(
            (note_quaver_1_rect!!.left - outSideCL__rect!!.left).toFloat(),
            (note_quaver_1_rect!!.top - outSideCL__rect!!.top).toFloat()
        )
        path!!.secondBesselCurveTo(
            0f,
            (outSideCL__rect!!.centerY() - outSideCL__rect!!.top).toFloat(),
            (outSideCL__rect!!.centerX() - outSideCL__rect!!.left shr 1).toFloat(),
            0f
        )
    }

    fun stopAnim() {
        //mBinding.musicButtonMbRl.clearAnimation();
        objectAnimator?.end() //动画结束
        if (!mObjectAnimators.isNullOrEmpty()) {
            for (mObjectAnimator in mObjectAnimators!!) {
                mObjectAnimator?.end()
            }
            mBinding.noteQuaver1.visibility = INVISIBLE
            mBinding.noteQuaver2.visibility = INVISIBLE
            mBinding.noteQuaver3.visibility = INVISIBLE
            mBinding.noteQuaver4.visibility = INVISIBLE
        }
        isAnimPlaying = false
        state = STATE_STOP
    }

    /**
     * 暂停
     */
    fun pauseAnim() {
        if (objectAnimator != null) {
            if (state == STATE_PAUSE) {
                objectAnimator?.end()
            } else {
                objectAnimator?.pause()
            }
        }
        if (!mObjectAnimators.isNullOrEmpty()) {
            for (mObjectAnimator in mObjectAnimators!!) {
                mObjectAnimator?.end()
            }
            mBinding.noteQuaver1.visibility = VISIBLE
            mBinding.noteQuaver2.visibility = VISIBLE
            mBinding.noteQuaver3.visibility = VISIBLE
            mBinding.noteQuaver4.visibility = VISIBLE
        }
        isAnimPlaying = false
        state = STATE_PAUSE
    }

    /**
     * 设置音乐作者封面
     *
     * @param url
     */
    fun setMusicInfoAvatar(url: String?) {
//        ImageLoadUtils.loadHead(getContext(), url, mBinding.musicInfoIv);
    }

    fun startAnim() {
        if (isAnimPlaying) {
            stopAnim()
            //return;
        }
        if (state == STATE_STOP) {
            objectAnimator?.start()
            //动画开始
            state = STATE_PLAYING
        } else if (state == STATE_PAUSE) {
            objectAnimator?.resume()
            //动画重新开始
            state = STATE_PLAYING
        } else if (state == STATE_PLAYING) {
            objectAnimator?.pause() //动画暂停
            state = STATE_PAUSE
        }

//        if (rotate == null) {
//            rotate = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_anim);
//
//        }
//        //设置匀速
//        LinearInterpolator interpolator = new LinearInterpolator();
//        rotate.setInterpolator(interpolator);
//        if (rotate != null) {
//            mBinding.musicButtonMbRl.setAnimation(rotate);
//            mBinding.musicButtonMbRl.startAnimation(rotate);
//        } else {
//            mBinding.musicButtonMbRl.setAnimation(rotate);
//        }
        if (null != mObjectAnimators && mObjectAnimators!!.size > 0) {
            for (mObjectAnimator in mObjectAnimators!!) {
                mObjectAnimator!!.start()
            }
            mBinding.noteQuaver1.visibility = VISIBLE
            mBinding.noteQuaver2.visibility = VISIBLE
            mBinding.noteQuaver3.visibility = VISIBLE
            mBinding.noteQuaver4.visibility = VISIBLE
            isAnimPlaying = true
        } else {
            outSideCL__rect = Rect()
            //            mBinding.musicButtonMbRl.getGlobalVisibleRect(outSideCL__rect);
            outSideCL__rect?.bottom = 1625
            outSideCL__rect?.left = 750
            outSideCL__rect?.top = 1430
            outSideCL__rect?.right = 1050
            note_quaver_1_rect = Rect()
            //            note_quaver_1.getGlobalVisibleRect(note_quaver_1_rect);
            note_quaver_1_rect?.bottom = 1625
            note_quaver_1_rect?.left = 960
            note_quaver_1_rect?.top = 1580
            note_quaver_1_rect?.right = 1005
            setPath()
            val rotationHolder = PropertyValuesHolder.ofFloat("Rotation", -90f, 90f)
            val alphaHolder = PropertyValuesHolder.ofFloat(
                "alpha",
                0f,
                0.1f,
                0.2f,
                0.3f,
                0.4f,
                0.5f,
                0.6f,
                0.7f,
                0.8f,
                0.9f,
                1f,
                0.6f,
                0.2f,
                0f
            )
            val scaleXHolder = PropertyValuesHolder.ofFloat("ScaleX", 0f, 1f)
            val scaleYHolder = PropertyValuesHolder.ofFloat("ScaleY", 0f, 1f)
            mObjectAnimators = ArrayList()
            mObjectAnimators?.add(
                startObjectAnim(
                    mBinding.noteQuaver1,
                    DURATION_TIME.toLong(),
                    0,
                    rotationHolder,
                    alphaHolder,
                    scaleXHolder,
                    scaleYHolder
                )
            )
            mObjectAnimators?.add(startPathAnim("note_quaver_1", DURATION_TIME.toLong(), 0, path))
            mObjectAnimators?.add(
                startObjectAnim(
                    mBinding.noteQuaver2,
                    DURATION_TIME.toLong(),
                    NOTE_DELAY_TIME.toLong(),
                    rotationHolder,
                    alphaHolder,
                    scaleXHolder,
                    scaleYHolder
                )
            )
            mObjectAnimators?.add(
                startPathAnim(
                    "note_quaver_2",
                    DURATION_TIME.toLong(),
                    NOTE_DELAY_TIME.toLong(),
                    path
                )
            )
            mObjectAnimators?.add(
                startObjectAnim(
                    mBinding.noteQuaver3,
                    DURATION_TIME.toLong(),
                    (NOTE_DELAY_TIME * 2).toLong(),
                    rotationHolder,
                    alphaHolder,
                    scaleXHolder,
                    scaleYHolder
                )
            )
            mObjectAnimators?.add(
                startPathAnim(
                    "note_quaver_3",
                    DURATION_TIME.toLong(),
                    (NOTE_DELAY_TIME * 2).toLong(),
                    path
                )
            )
            mObjectAnimators?.add(
                startObjectAnim(
                    mBinding.noteQuaver4,
                    DURATION_TIME.toLong(),
                    (NOTE_DELAY_TIME * 3).toLong(),
                    rotationHolder,
                    alphaHolder,
                    scaleXHolder,
                    scaleYHolder
                )
            )
            mObjectAnimators?.add(
                startPathAnim(
                    "note_quaver_4",
                    DURATION_TIME.toLong(),
                    (NOTE_DELAY_TIME * 3).toLong(),
                    path
                )
            )
            isAnimPlaying = true
        }
    }

    private fun startPathAnim(
        note_quaver: String, durationTime: Long,
        delayTime: Long, path: AnimatorPath?
    ): ObjectAnimator {
        val anim4 = ObjectAnimator.ofObject(this, note_quaver, PathEvaluator(), *path!!.points.toTypedArray())
        anim4.startDelay = delayTime
        anim4.duration = durationTime
        anim4.repeatMode = ObjectAnimator.RESTART
        anim4.repeatCount = ObjectAnimator.INFINITE
        anim4.start()
        return anim4
    }

    private fun startObjectAnim(
        note_quaver: ImageView?, durationTime: Long,
        delayTime: Long, vararg mPropertyValuesHolders: PropertyValuesHolder
    ): ObjectAnimator {
        val anim_note_quaver_4 = ObjectAnimator.ofPropertyValuesHolder(note_quaver, *mPropertyValuesHolders)
        anim_note_quaver_4.interpolator = DecelerateInterpolator()
        anim_note_quaver_4.startDelay = delayTime
        anim_note_quaver_4.duration = durationTime
        anim_note_quaver_4.repeatMode = ObjectAnimator.RESTART
        anim_note_quaver_4.repeatCount = ObjectAnimator.INFINITE
        anim_note_quaver_4.start()
        return anim_note_quaver_4
    }

    /**
     * 设置View的属性通过ObjectAnimator.ofObject()的反射机制来调用
     */
    fun setNote_quaver_1(newLoc: PathPoint) {
        if (INVISIBLE == mBinding.noteQuaver1.visibility) {
            mBinding.noteQuaver1.visibility = VISIBLE
        }
        mBinding.noteQuaver1.x = newLoc.mX
        mBinding.noteQuaver1.y = newLoc.mY
    }

    fun setNote_quaver_2(newLoc: PathPoint) {
        if (INVISIBLE == mBinding.noteQuaver2.visibility) {
            mBinding.noteQuaver2.visibility = VISIBLE
        }
        mBinding.noteQuaver2.x = newLoc.mX
        mBinding.noteQuaver2.y = newLoc.mY
    }

    fun setNote_quaver_3(newLoc: PathPoint) {
        if (INVISIBLE == mBinding.noteQuaver3.visibility) {
            mBinding.noteQuaver3.visibility = VISIBLE
        }
        mBinding.noteQuaver3.x = newLoc.mX
        mBinding.noteQuaver3.y = newLoc.mY
    }

    fun setNote_quaver_4(newLoc: PathPoint) {
        if (INVISIBLE == mBinding.noteQuaver4.visibility) {
            mBinding.noteQuaver4.visibility = VISIBLE
        }
        mBinding.noteQuaver4.x = newLoc.mX
        mBinding.noteQuaver4.y = newLoc.mY
    }

    companion object {
        const val STATE_PLAYING = 1 //正在播放
        const val STATE_PAUSE = 2 //暂停
        const val STATE_STOP = 3 //停止
        const val DURATION_TIME = (3.5 * 1000).toInt()
        const val NOTE_COUNT = 4
        const val NOTE_DELAY_TIME = DURATION_TIME / NOTE_COUNT
        private const val TAG = "AiBan_RotateNoteView"
    }
}
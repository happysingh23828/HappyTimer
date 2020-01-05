package com.androchef.happytimer.ui

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator

class CircleProgressBar(
    context: Context,
    attrs: AttributeSet
) : View(context, attrs) {
    /**
     * ProgressBar's line thickness
     */

    private var strokeWidthForGroundProgress = 4f

    private var strokeWidthBackgroundProgress = 4f

    private var progress = 0f

    private var min = 0

    private var max = 100

    private var colorForGroundProgress = Color.DKGRAY

    private var colorBackgroundProgress: Int? = null

    private var rectF: RectF? = null

    private var backgroundPaint: Paint? = null

    private var foregroundPaint: Paint? = null

    private var progressFlow: ProgressFlow = ProgressFlow.RIGHT_LEFT

    fun setStrokeWidth(strokeWidthForeground: Float, strokeWidthBackground: Float? = null) {
        this.strokeWidthForGroundProgress = strokeWidthForeground
        this.strokeWidthBackgroundProgress = strokeWidthBackground ?: strokeWidthForeground
        backgroundPaint!!.strokeWidth = strokeWidthBackground ?: strokeWidthForeground
        foregroundPaint!!.strokeWidth = strokeWidthForeground
        invalidate()
        requestLayout() //Because it should recalculate its bounds
    }

    fun getProgress(): Float {
        return progress
    }

    fun setProgress(progress: Float) {
        this.progress = progress
        invalidate()
    }

    fun setMin(min: Int) {
        this.min = min
        invalidate()
    }

    fun setMax(max: Int) {
        this.max = max
        invalidate()
    }

    fun setProgressFlow(progressFlow: ProgressFlow) {
        this.progressFlow = progressFlow
    }

    fun setColor(colorForeground: Int, colorBackGround: Int? = null) {
        this.colorForGroundProgress = colorForeground
        this.colorBackgroundProgress = colorBackGround
        backgroundPaint!!.color = colorBackGround ?: adjustAlpha(colorForeground, 0.3f)
        foregroundPaint!!.color = colorForeground
        invalidate()
        requestLayout()
    }

    private fun init(
        context: Context,
        attrs: AttributeSet
    ) {
        rectF = RectF()
        backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        backgroundPaint!!.color =
            colorBackgroundProgress ?: adjustAlpha(colorForGroundProgress, 0.3f)
        backgroundPaint!!.style = Paint.Style.STROKE
        backgroundPaint!!.strokeWidth = strokeWidthBackgroundProgress
        foregroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        foregroundPaint!!.color = colorForGroundProgress
        foregroundPaint!!.style = Paint.Style.STROKE
        foregroundPaint!!.strokeWidth = strokeWidthForGroundProgress
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawOval(rectF!!, backgroundPaint!!)
        val angle = getAngleForProgressFlow()
        /**
         * Start the progress at 12 o'clock
         */
        val startAngle = -90
        canvas.drawArc(rectF!!, startAngle.toFloat(), angle, false, foregroundPaint!!)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val height =
            getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)
        val width =
            getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        val min = Math.min(width, height)
        setMeasuredDimension(min, min)
        val hightStrokeWidth = if(strokeWidthBackgroundProgress < strokeWidthForGroundProgress) strokeWidthBackgroundProgress else strokeWidthForGroundProgress
        rectF!![0 + hightStrokeWidth / 2, 0 + hightStrokeWidth / 2, min - hightStrokeWidth / 2] =
            min - hightStrokeWidth / 2
    }

    /**
     * Lighten the given color by the factor
     *
     * @param color  The color to lighten
     * @param factor 0 to 4
     * @return A brighter color
     */
    fun lightenColor(color: Int, factor: Float): Int {
        val r = Color.red(color) * factor
        val g = Color.green(color) * factor
        val b = Color.blue(color) * factor
        val ir = Math.min(255, r.toInt())
        val ig = Math.min(255, g.toInt())
        val ib = Math.min(255, b.toInt())
        val ia = Color.alpha(color)
        return Color.argb(ia, ir, ig, ib)
    }

    /**
     * Transparent the given color by the factor
     * The more the factor closer to zero the more the color gets transparent
     *
     * @param color  The color to transparent
     * @param factor 1.0f to 0.0f
     * @return int - A transplanted color
     */
    fun adjustAlpha(color: Int, factor: Float): Int {
        val alpha = Math.round(Color.alpha(color) * factor)
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        return Color.argb(alpha, red, green, blue)
    }

    /**
     * Set the progress with an animation.
     * Note that the [android.animation.ObjectAnimator] Class automatically set the progress
     * so don't call the [CircleProgressBar.setProgress] directly within this method.
     *
     * @param progress The progress it should animate to it.
     */
    fun setProgressWithAnimation(progress: Float) {
        val objectAnimator = ObjectAnimator.ofFloat(this, "progress", progress)
        objectAnimator.duration = 1500
        objectAnimator.interpolator = DecelerateInterpolator()
        objectAnimator.start()
    }

    private fun getAngleForProgressFlow(): Float {
        return when (progressFlow) {
            ProgressFlow.LEFT_RIGHT -> 360 * progress / max
            ProgressFlow.RIGHT_LEFT -> 360 * progress / max
        }
    }

    init {
        init(context, attrs)
    }

    enum class ProgressFlow {
        LEFT_RIGHT, RIGHT_LEFT
    }
}
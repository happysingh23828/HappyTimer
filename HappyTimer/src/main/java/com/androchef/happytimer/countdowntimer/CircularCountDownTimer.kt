package com.androchef.happytimer.countdowntimer

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.androchef.happytimer.R
import com.androchef.happytimer.utils.DateTimeUtils
import kotlinx.android.synthetic.main.layout_circular_count_down_timer.view.*

class CircularCountDownTimer(context: Context, attributeSet: AttributeSet) :
    ConstraintLayout(context, attributeSet) {

    var strokeThicknessForeground: Float = resources.getDimension(R.dimen.default_stroke_thickness)
        set(value) {
            field = value.dpToPx()
            circleProgressBar.setStrokeWidth(field,strokeThicknessBackground)
            invalidate()
        }

    var strokeThicknessBackground: Float = resources.getDimension(R.dimen.default_stroke_thickness)
        set(value) {
            field = value.dpToPx()
            circleProgressBar.setStrokeWidth(strokeThicknessForeground,field)
            invalidate()
        }

    var strokeColorForeground: Int = Color.GRAY
        set(value) {
            field = value
            circleProgressBar.setColor(field,strokeColorBackground)
            invalidate()
        }

    var strokeColorBackground: Int = strokeColorForeground
        set(value) {
            field = value
            circleProgressBar.setColor(strokeColorForeground,field)
            invalidate()
        }

    var timerTextColor: Int = Color.BLACK
        set(value) {
            field = value
            tvTimerText.setTextColor(value)
            invalidate()
        }

    var timerTextSize: Float = resources.getDimension(R.dimen.default_timer_text_size)
        set(value) {
            field = value.dpToPx()
            tvTimerText.textSize = field
            invalidate()
        }

    var timerTextIsBold: Boolean = resources.getBoolean(R.bool.default_timer_text_is_bold)
        set(value) {
            field = value
            tvTimerText.setTextAppearance(context, Typeface.BOLD)
            invalidate()
        }

    private var timerTotalSeconds: Int = resources.getInteger(R.integer.default_timer_total_seconds)

    private var happyTimer: HappyTimer? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_circular_count_down_timer, this)
        val typedArray = context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.CircularCountDownTimer,
            0, 0
        )
        //Reading values from the XML layout
        try {
            strokeColorForeground = typedArray.getColor(
                R.styleable.CircularCountDownTimer_stroke_foreground_color,
                strokeColorForeground
            )

            strokeColorBackground = typedArray.getColor(
                R.styleable.CircularCountDownTimer_stroke_background_color,
                strokeColorBackground
            )

            strokeThicknessForeground = typedArray.getDimension(
                R.styleable.CircularCountDownTimer_stroke_foreground_thickness,
                strokeThicknessForeground
            )

            strokeThicknessBackground = typedArray.getDimension(
                R.styleable.CircularCountDownTimer_stroke_background_thickness,
                strokeThicknessBackground
            )

            timerTextColor = typedArray.getColor(
                R.styleable.CircularCountDownTimer_timer_text_color,
                timerTextColor
            )

            timerTextIsBold = typedArray.getBoolean(
                R.styleable.CircularCountDownTimer_timer_text_isBold,
                timerTextIsBold
            )

            timerTextSize = typedArray.getDimension(
                R.styleable.CircularCountDownTimer_timer_text_size,
                timerTextSize
            )

            timerTotalSeconds =
                typedArray.getInt(
                    R.styleable.CircularCountDownTimer_timer_total_seconds,
                    timerTotalSeconds
                )

        } finally {
            typedArray.recycle()
        }
    }

    fun initTimer(totalTimeInSeconds: Int, type: HappyTimer.Type = HappyTimer.Type.COUNT_DOWN) {
        this.timerTotalSeconds = totalTimeInSeconds
        stopTimer()
        resetTimer()
        happyTimer = HappyTimer(totalTimeInSeconds, type, 3000)
        setOnTickListener()
        setOnStateChangeListener()
        onInitTimerState()
    }

    fun startTimer() {
        happyTimer?.start()
    }

    fun stopTimer() {
        happyTimer?.stop()
    }

    fun pauseTimer() {
        happyTimer?.pause()
    }

    fun resumeTimer() {
        happyTimer?.resume()
    }

    fun resetTimer() {
        happyTimer?.resetTimer()
    }

    private fun setOnTickListener() {
        happyTimer?.setOnTickListener(object : HappyTimer.OnTickListener {
            override fun onTick(completedSeconds: Int, remainingSeconds: Int) {
                tvTimerText.text = DateTimeUtils.getMinutesSecondsFormat(remainingSeconds)
                circleProgressBar.setProgressWithAnimation(remainingSeconds.toFloat())
            }

            override fun onTimeUp() {
                onTimeUpState()
            }
        })
    }

    private fun setOnStateChangeListener() {
        happyTimer?.setOnStateChangeListener(object : HappyTimer.OnStateChangeListener {
            override fun onStateChange(
                state: HappyTimer.State,
                completedSeconds: Int,
                remainingSeconds: Int
            ) {
                when (state) {
                    HappyTimer.State.RUNNING -> {
                        onRunningState()
                    }
                    HappyTimer.State.FINISHED -> {
                        onFinishedState()
                    }
                    HappyTimer.State.PAUSED -> {
                        onPausedState()
                    }
                    HappyTimer.State.RESUMED -> {
                        onResumedState()
                    }
                    HappyTimer.State.UNKNOWN -> {
                        onUnknownState()
                    }
                    HappyTimer.State.RESET -> {
                        onResetState()
                    }
                    HappyTimer.State.STOPPED -> {
                        onStopState()
                    }
                }
            }
        })
    }

    private fun onStopState() {

    }

    private fun onResetState() {

    }

    private fun onUnknownState() {

    }

    private fun onResumedState() {

    }

    private fun onPausedState() {

    }

    private fun onFinishedState() {

    }

    private fun onRunningState() {

    }

    private fun onInitTimerState() {
        tvTimerText.text = DateTimeUtils.getMinutesSecondsFormat(timerTotalSeconds)
        circleProgressBar.setMin(0)
        circleProgressBar.setMax(timerTotalSeconds)
        circleProgressBar.setProgressWithAnimation(timerTotalSeconds.toFloat())
        circleProgressBar.invalidate()
    }

    private fun onTimeUpState() {

    }


    //region Extensions Utils
    private fun Float.dpToPx(): Float =
        this * Resources.getSystem().displayMetrics.density

    private fun Float.pxToDp(): Float =
        this / Resources.getSystem().displayMetrics.density

}
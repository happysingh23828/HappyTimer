package com.androchef.happytimer.countdowntimer

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.androchef.happytimer.R
import com.androchef.happytimer.countdowntimer.pojo.NormalCountDownTime
import com.androchef.happytimer.utils.DateTimeUtils
import com.androchef.happytimer.utils.extensions.gone
import com.androchef.happytimer.utils.extensions.visible
import kotlinx.android.synthetic.main.layout_normal_countdown_timer.view.*

class NormalCountDownView(context: Context, attributeSet: AttributeSet) :
    ConstraintLayout(context, attributeSet) {

    var timerTextColor: Int = Color.BLACK
        set(value) {
            field = value
            setTimerTextViewColor(value)
            invalidate()
        }

    var timerTextLabelColor: Int = Color.BLACK
        set(value) {
            field = value
            setTimerLabelTextViewColor(value)
            invalidate()
        }

    var timerTextSize: Float = 30f.spToPx()
        set(value) {
            field = value
            setTimerTextViewSize(field)
            invalidate()
        }

    var timerTextLabelSize: Float = 18f.spToPx()
        set(value) {
            field = value
            setTimerLabelTextViewSize(field)
            invalidate()
        }

    var timerTextIsBold: Boolean = false
        set(value) {
            field = value
            if(value)
                setTimerTextViewToBold()
             else
                setTimerTextViewToNormal()
            invalidate()
        }

    var timerTextLabelIsBold: Boolean = false
        set(value) {
            field = value
            if(value)
                setTimerTextViewLabelToBold()
            else
                setTimerTextViewLabelToNormal()
            invalidate()
        }

    var showLabels: Boolean = true
        set(value) {
            field = value
            if (value)
                showLabel()
            else
                hideLabel()
            invalidate()
        }

    var showHour: Boolean = true
        set(value) {
            field = value
            if (value)
                showHour()
            else
                hideHour()
            invalidate()
        }

    var showMinutes: Boolean = true
        set(value) {
            field = value
            if (value)
                showMinutes()
            else
                hideMinutes()
            invalidate()
        }

    var showSeconds: Boolean = true
        set(value) {
            field = value
            if (value)
                showSeconds()
            else
                hideSeconds()
            invalidate()
        }

    var timerType: HappyTimer.Type = HappyTimer.Type.COUNT_DOWN
        set(value) {
            field = value
            invalidate()
        }

    private var timerTotalSeconds: Int = resources.getInteger(R.integer.default_timer_total_seconds)

    private var happyTimer: HappyTimer? = null

    private var onTickListener: HappyTimer.OnTickListener? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_normal_countdown_timer, this)

        val typedArray = context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.NormalCountDownView,
            0, 0
        )
        //Reading values from the XML layout
        try {
            timerTextColor = typedArray.getColor(
                R.styleable.NormalCountDownView_normal_timer_text_color,
                timerTextColor
            )

            timerTextLabelColor = typedArray.getColor(
                R.styleable.NormalCountDownView_normal_timer_text_label_color,
                timerTextLabelColor
            )

            timerTextSize = typedArray.getDimension(
                R.styleable.NormalCountDownView_normal_timer_text_size,
                timerTextSize
            ).pxToSp()

            timerTextLabelSize = typedArray.getDimension(
                R.styleable.NormalCountDownView_normal_timer_label_text_size,
                timerTextLabelSize
            ).pxToSp()

            timerTextIsBold = typedArray.getBoolean(
                R.styleable.CircularCountDownView_timer_text_isBold,
                timerTextIsBold
            )

            timerTextLabelIsBold = typedArray.getBoolean(
                R.styleable.NormalCountDownView_normal_timer_text__label_isBold,
                timerTextLabelIsBold
            )

            showLabels =
                typedArray.getBoolean(
                    R.styleable.NormalCountDownView_show_labels,
                    showLabels
                )

            showHour =
                typedArray.getBoolean(
                    R.styleable.NormalCountDownView_show_hour,
                    showHour
                )

            showMinutes =
                typedArray.getBoolean(
                    R.styleable.NormalCountDownView_show_minutes,
                    showMinutes
                )

            showSeconds =
                typedArray.getBoolean(
                    R.styleable.NormalCountDownView_show_seconds,
                    showSeconds
                )

        } finally {
            typedArray.recycle()
        }

    }

    fun initTimer(totalTimeInSeconds: Int, type: HappyTimer.Type = HappyTimer.Type.COUNT_DOWN) {
        this.timerTotalSeconds = totalTimeInSeconds
        stopTimer()
        happyTimer = HappyTimer(totalTimeInSeconds, 3000)
        setOnTickListener()
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
                onTickListener?.onTick(completedSeconds ,remainingSeconds)
                setTimerText(completedSeconds, remainingSeconds)
            }

            override fun onTimeUp() {
                onTickListener?.onTimeUp()
            }
        })
    }

    private fun setTimerText(completedSeconds: Int, remainingSeconds: Int) {
        val countDownTime: NormalCountDownTime = when (timerType) {
            HappyTimer.Type.COUNT_UP -> {
                DateTimeUtils.getNormalCountDownTime(completedSeconds)
            }
            HappyTimer.Type.COUNT_DOWN -> {
                DateTimeUtils.getNormalCountDownTime(remainingSeconds)
            }
        }
        tvHour.text = countDownTime.hour.toString().padStart(2, '0')
        tvMinutes.text = countDownTime.minutes.toString().padStart(2, '0')
        tvSeconds.text = countDownTime.seconds.toString().padStart(2, '0')
    }

    private fun onInitTimerState() {
        setTimerTextInitial()
    }

    private fun setTimerTextInitial() {
        when (timerType) {
            HappyTimer.Type.COUNT_UP -> {
                setTimerText(0, 0)
            }
            HappyTimer.Type.COUNT_DOWN -> {
                setTimerText(0, timerTotalSeconds)
            }
        }
    }


    private fun setTimerTextViewSize(textSize: Float) {
        tvHour.textSize = textSize
        tvMinutes.textSize = textSize
        tvSeconds.textSize = textSize
    }

    private fun setTimerTextViewColor(color: Int) {
        tvHour.setTextColor(color)
        tvMinutes.setTextColor(color)
        tvSeconds.setTextColor(color)
    }


    private fun setTimerLabelTextViewSize(textSize: Float) {
        tvHourLabel.textSize = textSize
        tvMinutesLabel.textSize = textSize
        tvSecondsLabel.textSize = textSize
    }

    private fun setTimerLabelTextViewColor(color: Int) {
        tvHourLabel.setTextColor(color)
        tvMinutesLabel.setTextColor(color)
        tvSecondsLabel.setTextColor(color)
    }

    private fun setTimerTextViewToBold() {
        tvHour.setTypeface(null, Typeface.BOLD)
        tvMinutes.setTypeface(null, Typeface.BOLD)
        tvSeconds.setTypeface(null, Typeface.BOLD)
    }

    private fun setTimerTextViewLabelToBold() {
        tvHourLabel.setTypeface(null, Typeface.BOLD)
        tvMinutesLabel.setTypeface(null, Typeface.BOLD)
        tvSecondsLabel.setTypeface(null, Typeface.BOLD)
    }

    private fun setTimerTextViewToNormal() {
        tvHour.setTypeface(null, Typeface.NORMAL)
        tvMinutes.setTypeface(null, Typeface.NORMAL)
        tvSeconds.setTypeface(null, Typeface.NORMAL)
    }

    private fun setTimerTextViewLabelToNormal() {
        tvHourLabel.setTypeface(null, Typeface.NORMAL)
        tvMinutesLabel.setTypeface(null, Typeface.NORMAL)
        tvSecondsLabel.setTypeface(null, Typeface.NORMAL)
    }

    private fun hideLabel() {
        tvHourLabel.gone()
        tvMinutesLabel.gone()
        tvSecondsLabel.gone()
    }

    private fun showLabel() {
        tvHourLabel.visible()
        tvMinutesLabel.visible()
        tvSecondsLabel.visible()
    }

    private fun showHour() {
        tvHour.visible()
        if (showLabels)
            tvHourLabel.visible()
    }

    private fun hideHour() {
        tvHour.gone()
        tvHourLabel.gone()
    }

    private fun showMinutes() {
        tvMinutes.visible()
        if (showLabels)
            tvMinutesLabel.visible()
    }

    private fun hideMinutes() {
        tvMinutes.gone()
        tvMinutesLabel.gone()
    }

    private fun showSeconds() {
        tvSeconds.visible()
        if (showLabels)
            tvSecondsLabel.visible()
    }

    private fun hideSeconds() {
        tvSeconds.gone()
        tvSecondsLabel.gone()
    }

    fun setStateChangeListener(stateChangeListener: HappyTimer.OnStateChangeListener) {
        happyTimer?.setOnStateChangeListener(stateChangeListener)
    }

    fun setOnTickListener(onTickListener: HappyTimer.OnTickListener) {
        this.onTickListener = onTickListener
    }

    //region Extensions Utils
    private fun Float.dpToPx(): Float =
        this * Resources.getSystem().displayMetrics.density

    private fun Float.pxToDp(): Float =
        this / Resources.getSystem().displayMetrics.density

    //region Extensions Utils
    private fun Float.spToPx(): Float =
        this * Resources.getSystem().displayMetrics.scaledDensity

    private fun Float.pxToSp(): Float =
        this / Resources.getSystem().displayMetrics.scaledDensity

    private fun View.isToShow(boolean: Boolean) {
        if (boolean)
            this.visible()
        else
            this.gone()
    }

}
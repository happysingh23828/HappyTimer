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
import com.androchef.happytimer.utils.extensions.gone
import com.androchef.happytimer.utils.extensions.visible
import kotlinx.android.synthetic.main.layout_normal_countdown_timer.view.*

class NormalCountDownTextView(context: Context, attributeSet: AttributeSet) :
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

    var timerTextSize: Float = resources.getDimension(R.dimen.default_normal_timer_text_size)
        set(value) {
            field = value
            setTimerTextViewSize(value)
            invalidate()
        }

    var timerTextLabelSize: Float =
        resources.getDimension(R.dimen.default_normal_timer_text_label_size)
        set(value) {
            field = value
            setTimerLabelTextViewSize(value)
            invalidate()
        }

    var timerTextIsBold: Boolean = resources.getBoolean(R.bool.default_timer_text_is_bold)
        set(value) {
            field = value
            setTimerTextViewToBold()
            invalidate()
        }

    var timerTextLabelIsBold: Boolean = resources.getBoolean(R.bool.default_timer_text_is_bold)
        set(value) {
            field = value
            setTimerTextViewLabelToBold()
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

    private var timerTotalSeconds: Int = resources.getInteger(R.integer.default_timer_total_seconds)

    private var happyTimer: HappyTimer? = null


    init {
        LayoutInflater.from(context).inflate(R.layout.layout_normal_countdown_timer, this)

        val typedArray = context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.NormalCountDownTextView,
            0, 0
        )
        //Reading values from the XML layout
        try {
            timerTextColor = typedArray.getColor(
                R.styleable.NormalCountDownTextView_normal_timer_text_color,
                timerTextColor
            )

            timerTextLabelColor = typedArray.getColor(
                R.styleable.NormalCountDownTextView_normal_timer_text_label_color,
                timerTextLabelColor
            )

            timerTextSize = typedArray.getDimension(
                R.styleable.NormalCountDownTextView_normal_timer_text_size,
                timerTextSize
            )

            timerTextLabelSize = typedArray.getDimension(
                R.styleable.NormalCountDownTextView_normal_timer_label_text_size,
                timerTextLabelSize
            )

            timerTextIsBold = typedArray.getBoolean(
                R.styleable.CircularCountDownTimer_timer_text_isBold,
                timerTextIsBold
            )

            timerTextLabelIsBold = typedArray.getBoolean(
                R.styleable.NormalCountDownTextView_normal_timer_text__label_isBold,
                timerTextLabelIsBold
            )

            showLabels =
                typedArray.getBoolean(
                    R.styleable.NormalCountDownTextView_show_labels,
                    showLabels
                )

            showHour =
                typedArray.getBoolean(
                    R.styleable.NormalCountDownTextView_show_hour,
                    showHour
                )

            showMinutes =
                typedArray.getBoolean(
                    R.styleable.NormalCountDownTextView_show_minutes,
                    showMinutes
                )

            showSeconds =
                typedArray.getBoolean(
                    R.styleable.NormalCountDownTextView_show_seconds,
                    showSeconds
                )

        } finally {
            typedArray.recycle()
        }

    }

    fun initTimer(totalTimeInSeconds: Int, type: HappyTimer.Type = HappyTimer.Type.COUNT_DOWN) {
        this.timerTotalSeconds = totalTimeInSeconds
        this.timerType = type
        stopTimer()
        resetTimer()
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
                setTimerText(completedSeconds, remainingSeconds)

            }

            override fun onTimeUp() {

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
        val sizeInPX = textSize.dpToPx()
        tvHour.textSize = sizeInPX
        tvMinutes.textSize = sizeInPX
        tvSeconds.textSize = sizeInPX
    }

    private fun setTimerTextViewColor(color: Int) {
        tvHour.setTextColor(color)
        tvMinutes.setTextColor(color)
        tvSeconds.setTextColor(color)
    }


    private fun setTimerLabelTextViewSize(textSize: Float) {
        val sizeInPX = textSize.dpToPx()
        tvHourLabel.textSize = sizeInPX
        tvMinutesLabel.textSize = sizeInPX
        tvSecondsLabel.textSize = sizeInPX
    }

    private fun setTimerLabelTextViewColor(color: Int) {
        tvHourLabel.setTextColor(color)
        tvMinutesLabel.setTextColor(color)
        tvSecondsLabel.setTextColor(color)
    }

    private fun setTimerTextViewToBold() {
        tvHour.setTextAppearance(context, Typeface.BOLD)
        tvMinutes.setTextAppearance(context, Typeface.BOLD)
        tvSeconds.setTextAppearance(context, Typeface.BOLD)
    }

    private fun setTimerTextViewLabelToBold() {
        tvHourLabel.setTextAppearance(context, Typeface.BOLD)
        tvMinutesLabel.setTextAppearance(context, Typeface.BOLD)
        tvSecondsLabel.setTextAppearance(context, Typeface.BOLD)
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

    data class NormalCountDownTime(
        val hour: Int = 0,
        val minutes: Int = 0,
        val seconds: Int = 0
    )

    //region Extensions Utils
    private fun Float.dpToPx(): Float =
        this * Resources.getSystem().displayMetrics.density

    private fun Float.pxToDp(): Float =
        this / Resources.getSystem().displayMetrics.density
}
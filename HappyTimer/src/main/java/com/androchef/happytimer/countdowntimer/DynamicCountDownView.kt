package com.androchef.happytimer.countdowntimer

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.androchef.happytimer.R
import com.androchef.happytimer.countdowntimer.pojo.NormalCountDownTime
import com.androchef.happytimer.utils.DateTimeUtils
import com.androchef.happytimer.utils.extensions.gone
import com.androchef.happytimer.utils.extensions.invisible
import com.androchef.happytimer.utils.extensions.visible
import kotlinx.android.synthetic.main.layout_dynamic_countdown_timer.view.*

class DynamicCountDownView(context: Context, attributeSet: AttributeSet) :
    ConstraintLayout(context, attributeSet) {


    var timerTextColor: Int = Color.BLACK
        set(value) {
            field = value
            setTimerTextViewColor(value)
            invalidate()
        }

    var timerTextSeparatorColor: Int = Color.BLACK
        set(value) {
            field = value
            setTimerSeparatorTextViewColor(value)
            invalidate()
        }

    var timerTextSize: Float = 30f.spToPx()
        set(value) {
            field = value
            setTimerTextViewSize(value)
            invalidate()
            requestLayout()
        }

    var timerTextSeparatorSize: Float = 12f.spToPx()
        set(value) {
            field = value
            setTimerSeparatorTextViewSize(value)
            invalidate()
            requestLayout()
        }

    var timerTextIsBold: Boolean = false
        set(value) {
            field = value
            if(field)
                setTimerTextViewToBold()
            else
                setTimerTextViewToNormal()
            invalidate()
        }

    var timerTextSeparatorIsBold: Boolean = false
        set(value) {
            field = value
            if(field)
                setTimerTextViewSeparatorToBold()
            else
                setTimerTextViewSepartorToNormal()
            invalidate()
        }

    var showSeparators: Boolean = true
        set(value) {
            field = value
            if (value)
                showSeparator()
            else
                hideSeparator()
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

    var separatorString: String = resources.getString(R.string.default_separator_dynamic)
        set(value) {
            field = value
            setSeparatorText(value)
            invalidate()
        }

    var customBackgroundDrawable: Drawable? =
        resources.getDrawable(R.drawable.bg_textview_countdown_rectangle)
        set(value) {
            field = value
            value?.let {
                setTimerTextCustomBackground(value)
                invalidate()
                requestLayout()
            }
        }

    var timerTextBackgroundTintColor: Int = resources.getColor(R.color.colorLightBlue)
        set(value) {
            field = value
            setTimeTextBackgroundTintList(value)
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
        LayoutInflater.from(context).inflate(R.layout.layout_dynamic_countdown_timer, this)

        val typedArray = context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.DynamicCountDownView,
            0, 0
        )
        //Reading values from the XML layout
        try {
            timerTextColor = typedArray.getColor(
                R.styleable.DynamicCountDownView_dynamic_timer_text_color,
                timerTextColor
            )

            timerTextSeparatorColor = typedArray.getColor(
                R.styleable.DynamicCountDownView_dynamic_timer_text_separator_color,
                timerTextSeparatorColor
            )

            timerTextSize = typedArray.getDimension(
                R.styleable.DynamicCountDownView_dynamic_timer_text_size,
                timerTextSize
            ).pxToSp()

            timerTextSeparatorSize = typedArray.getDimension(
                R.styleable.DynamicCountDownView_dynamic_timer_separator_text_size,
                timerTextSeparatorSize
            ).pxToSp()

            timerTextIsBold = typedArray.getBoolean(
                R.styleable.DynamicCountDownView_dynamic_timer_text_isBold,
                timerTextIsBold
            )

            timerTextSeparatorIsBold = typedArray.getBoolean(
                R.styleable.DynamicCountDownView_dynamic_timer_text__separator_isBold,
                timerTextSeparatorIsBold
            )

            separatorString =
                typedArray.getString(R.styleable.DynamicCountDownView_dynamic_timer_text_separator)
                    ?: separatorString

            showSeparators =
                typedArray.getBoolean(
                    R.styleable.DynamicCountDownView_dynamic_show_labels,
                    showSeparators
                )

            showHour =
                typedArray.getBoolean(
                    R.styleable.DynamicCountDownView_dynamic_show_hour,
                    showHour
                )

            showMinutes =
                typedArray.getBoolean(
                    R.styleable.DynamicCountDownView_dynamic_show_minutes,
                    showMinutes
                )

            showSeconds =
                typedArray.getBoolean(
                    R.styleable.DynamicCountDownView_dynamic_show_minutes,
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

    private fun setTimerTextCustomBackground(backgroundDrawable: Drawable) {
        tvHour.setBackgroundDrawable(backgroundDrawable)
        tvMinutes.setBackgroundDrawable(backgroundDrawable)
        tvSeconds.setBackgroundDrawable(backgroundDrawable)
    }

    fun setRectangularBackground() {
        val rectDrawable = resources.getDrawable(R.drawable.bg_textview_countdown_rectangle)
        customBackgroundDrawable = rectDrawable
    }

    fun setRoundedBackground() {
        val rectDrawable = resources.getDrawable(R.drawable.bg_textview_count_down_circle)
        customBackgroundDrawable = rectDrawable
    }

    private fun setTimeTextBackgroundTintList(color: Int) {
        tvHour.background.setTint(color)
        tvMinutes.background.setTint(color)
        tvSeconds.background.setTint(color)
    }

    private fun setSeparatorText(separator: String) {
        tvHourSeparator.text = separator
        tvMinutesSeparator.text = separator
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


    private fun setTimerSeparatorTextViewSize(textSize: Float) {
        tvHourSeparator.textSize = textSize
        tvMinutesSeparator.textSize = textSize
    }

    private fun setTimerSeparatorTextViewColor(color: Int) {
        tvHourSeparator.setTextColor(color)
        tvMinutesSeparator.setTextColor(color)
    }

    private fun setTimerTextViewToBold() {
        tvHour.setTypeface(null, Typeface.BOLD)
        tvMinutes.setTypeface(null, Typeface.BOLD)
        tvSeconds.setTypeface(null, Typeface.BOLD)
    }

    private fun setTimerTextViewSeparatorToBold() {
        tvHourSeparator.setTypeface(null, Typeface.BOLD)
        tvMinutesSeparator.setTypeface(null, Typeface.BOLD)
    }

    private fun setTimerTextViewToNormal() {
        tvHour.setTypeface(null, Typeface.NORMAL)
        tvMinutes.setTypeface(null, Typeface.NORMAL)
        tvSeconds.setTypeface(null, Typeface.NORMAL)
    }

    private fun setTimerTextViewSepartorToNormal() {
        tvHourSeparator.setTypeface(null, Typeface.NORMAL)
        tvMinutesSeparator.setTypeface(null, Typeface.NORMAL)
    }



    private fun hideSeparator() {
        tvHourSeparator.invisible()
        tvMinutesSeparator.invisible()
    }

    private fun showSeparator() {
        tvHourSeparator.visible()
        tvMinutesSeparator.visible()
    }

    private fun showHour() {
        tvHour.visible()
        if (showSeparators)
            tvHourSeparator.visible()
    }

    private fun hideHour() {
        tvHour.gone()
        tvHourSeparator.gone()
    }

    private fun showMinutes() {
        tvMinutes.visible()
        if (showSeparators)
            tvMinutesSeparator.visible()
    }

    private fun hideMinutes() {
        tvMinutes.gone()
        tvMinutesSeparator.gone()
    }

    private fun showSeconds() {
        tvSeconds.visible()
    }

    private fun hideSeconds() {
        tvSeconds.gone()
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
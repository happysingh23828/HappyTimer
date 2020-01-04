package com.androchef.happytimer.countdowntimer

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.androchef.happytimer.R
import kotlinx.android.synthetic.main.layout_circular_count_down_timer.view.*

class CircularCountDownTimer(context: Context, attributeSet: AttributeSet) :
    ConstraintLayout(context, attributeSet) {

    var strokeThickness: Float = resources.getDimension(R.dimen.default_stroke_thickness)
        set(value) {
            field = value.dpToPx()
            circleProgressBar.setStrokeWidth(field)
            invalidate()
        }

    var strokeColor: Int = Color.BLACK
        set(value) {
            field = value
            circleProgressBar.setColor(field)
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


    init {
        LayoutInflater.from(context).inflate(R.layout.layout_circular_count_down_timer, this)
        val typedArray = context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.CircularCountDownTimer,
            0, 0
        )
        //Reading values from the XML layout
        try {
            strokeThickness = typedArray.getDimension(
                R.styleable.CircularCountDownTimer_stroke_thickness,
                strokeThickness
            )

            strokeColor =
                typedArray.getInt(R.styleable.CircularCountDownTimer_stroke_color, strokeColor)

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

        } finally {
            typedArray.recycle()
        }
    }




    //region Extensions Utils
    private fun Float.dpToPx(): Float =
        this * Resources.getSystem().displayMetrics.density

    private fun Float.pxToDp(): Float =
        this / Resources.getSystem().displayMetrics.density

}
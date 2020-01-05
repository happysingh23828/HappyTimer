package com.androchef.happytimersample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.androchef.happytimer.countdowntimer.HappyTimer
import kotlinx.android.synthetic.main.activity_dynamic_count_down.*
import kotlinx.android.synthetic.main.activity_dynamic_count_down.btnPause
import kotlinx.android.synthetic.main.activity_dynamic_count_down.btnResume
import kotlinx.android.synthetic.main.activity_dynamic_count_down.btnSetTime
import kotlinx.android.synthetic.main.activity_dynamic_count_down.btnStart
import kotlinx.android.synthetic.main.activity_dynamic_count_down.btnStop
import kotlinx.android.synthetic.main.activity_dynamic_count_down.dynamicCountDownTimer
import kotlinx.android.synthetic.main.activity_dynamic_count_down.edtTimeInSeconds
import kotlinx.android.synthetic.main.activity_dynamic_count_down.rdCountDown
import kotlinx.android.synthetic.main.activity_dynamic_count_down.rdCountUp
import kotlinx.android.synthetic.main.activity_text_view_count_down.*

class DynamicCountDownActivity : AppCompatActivity() {

    private var timerType : HappyTimer.Type = HappyTimer.Type.COUNT_DOWN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic_count_down)
        initTimerConfiguration(30)
        onClicks()
    }

    private fun onClicks() {
        btnStart.setOnClickListener {
            dynamicCountDownTimer.startTimer()
        }

        btnStop.setOnClickListener {
            dynamicCountDownTimer.stopTimer()
        }

        btnPause.setOnClickListener {
            dynamicCountDownTimer.pauseTimer()
        }

        btnResume.setOnClickListener {
            dynamicCountDownTimer.resumeTimer()
        }

        btnSetTime.setOnClickListener {
            initTimerConfiguration(edtTimeInSeconds.text?.toString()?.toInt() ?: 30)
        }

        rdCountDown.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                timerType = HappyTimer.Type.COUNT_DOWN
            }
        }

        rdCountUp.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                timerType = HappyTimer.Type.COUNT_UP
            }
        }

        btnIncSize.setOnClickListener {
            dynamicCountDownTimer.timerTextSize = dynamicCountDownTimer.timerTextSize.plus(1)
        }

        btnDecSize.setOnClickListener {
            dynamicCountDownTimer.timerTextSize = dynamicCountDownTimer.timerTextSize.minus(1)
        }


    }

    private fun initTimerConfiguration(totalSeconds: Int) {
        dynamicCountDownTimer.timerTextIsBold = false
        dynamicCountDownTimer.timerTextSize = 30f
        dynamicCountDownTimer.separatorString = "-"
        dynamicCountDownTimer.timerTextColor = ContextCompat.getColor(this,android.R.color.white)
        dynamicCountDownTimer.setRectangularBackground()
        dynamicCountDownTimer.timerTextBackgroundTintColor = ContextCompat.getColor(this,android.R.color.holo_red_light)
        dynamicCountDownTimer.initTimer(totalSeconds, timerType)
    }
}

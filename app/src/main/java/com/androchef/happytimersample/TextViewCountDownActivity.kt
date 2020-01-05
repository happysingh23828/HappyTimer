package com.androchef.happytimersample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androchef.happytimer.countdowntimer.HappyTimer
import kotlinx.android.synthetic.main.activity_text_view_count_down.*

class TextViewCountDownActivity : AppCompatActivity() {

    private var timerType : HappyTimer.Type = HappyTimer.Type.COUNT_DOWN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_view_count_down)
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
    }

    private fun initTimerConfiguration(totalSeconds: Int) {
        dynamicCountDownTimer.timerTextIsBold = false
        dynamicCountDownTimer.timerTextSize = 18F
        dynamicCountDownTimer.timerTextLabelSize = 14F
        dynamicCountDownTimer.initTimer(totalSeconds, timerType)
    }
}

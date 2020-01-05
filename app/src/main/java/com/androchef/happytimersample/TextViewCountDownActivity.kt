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
            normalCountDownTimer.startTimer()
        }

        btnStop.setOnClickListener {
            normalCountDownTimer.stopTimer()
        }

        btnPause.setOnClickListener {
            normalCountDownTimer.pauseTimer()
        }

        btnResume.setOnClickListener {
            normalCountDownTimer.resumeTimer()
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
        normalCountDownTimer.timerTextIsBold = false
        normalCountDownTimer.timerTextSize = 18F
        normalCountDownTimer.timerTextLabelSize = 14F
        normalCountDownTimer.initTimer(totalSeconds, timerType)
    }
}

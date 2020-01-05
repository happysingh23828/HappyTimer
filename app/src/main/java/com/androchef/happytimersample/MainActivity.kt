package com.androchef.happytimersample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.androchef.happytimer.countdowntimer.HappyTimer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initTimerConfiguration(30)
        onClicks()
    }

    private fun onClicks() {
        btnStart.setOnClickListener {
            countDownTimer.startTimer()
        }

        btnStop.setOnClickListener {
            countDownTimer.stopTimer()
        }

        btnPause.setOnClickListener {
            countDownTimer.pauseTimer()
        }

        btnResume.setOnClickListener {
            countDownTimer.resumeTimer()
        }

        btnSetTime.setOnClickListener {
            initTimerConfiguration(edtTimeInSeconds.text?.toString()?.toInt() ?: 30)
        }
    }

    private fun initTimerConfiguration(totalSeconds: Int) {
        countDownTimer.timerTextIsBold = true
        countDownTimer.timerTextSize = 12F
        countDownTimer.initTimer(totalSeconds, HappyTimer.Type.COUNT_DOWN)
    }
}

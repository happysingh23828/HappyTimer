package com.androchef.happytimersample.circular_countdown

import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.androchef.happytimer.countdowntimer.CircularCountDownView
import com.androchef.happytimer.countdowntimer.HappyTimer
import com.androchef.happytimersample.R
import com.androchef.happytimersample.utils.toast
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import kotlinx.android.synthetic.main.activity_demo_circular_count_down.*


class DemoCircularCountDownActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_circular_count_down)
        onClicksForColor()
        onClicksForSizeChange()
        setCheckBoxAndRadioChangeListener()
        setEditTextListeners()
        setBtnActionsListeners()
        setTimerSecondsListeners()
    }

    private fun setEditTextListeners() {
        btnInitTimer.setOnClickListener {
            if (edtTime.text.toString().isBlank().not()) {
                circularCountDownView.initTimer(edtTime.text.toString().toInt())
                setTickInitialText()
            } else {
                toast("Enter Seconds")
            }
        }
    }

    private fun setTickInitialText() {
        tvCurrentTimerCompletedSeconds.text = "0"
        tvCurrentTimerTotalSeconds.text = edtTime.text.toString()
        tvCurrentTimerState.text = getString(R.string.unknown)
    }

    private fun setBtnActionsListeners() {
        btnStartTimer.setOnClickListener {
            circularCountDownView.startTimer()
        }

        btnPauseTimer.setOnClickListener {
            circularCountDownView.pauseTimer()
        }

        btnResumeTimer.setOnClickListener {
            circularCountDownView.resumeTimer()
        }

        btnStopTimer.setOnClickListener {
            circularCountDownView.stopTimer()
        }

        btnResetTimer.setOnClickListener {
            circularCountDownView.resetTimer()
        }
    }

    private fun setTimerSecondsListeners() {
        circularCountDownView.setOnTickListener(object : HappyTimer.OnTickListener {
            override fun onTick(completedSeconds: Int, remainingSeconds: Int) {
                tvCurrentTimerCompletedSeconds.text = completedSeconds.toString()
            }

            override fun onTimeUp() {

            }
        })

        circularCountDownView.setStateChangeListener(object : HappyTimer.OnStateChangeListener {
            override fun onStateChange(
                state: HappyTimer.State,
                completedSeconds: Int,
                remainingSeconds: Int
            ) {
                tvCurrentTimerState.text = state.name
            }
        })
    }

    private fun onClicksForColor() {

        btnChooseColorBackgroundStroke.setOnClickListener {
            showPicker(ColorEnvelopeListener { envelop, _ ->
                circularCountDownView.strokeColorBackground = envelop.color
            })
        }

        btnChooseColorForegroundStroke.setOnClickListener {
            showPicker(ColorEnvelopeListener { envelop, _ ->
                circularCountDownView.strokeColorForeground = envelop.color
            })
        }

        btnChooseColorTimerText.setOnClickListener {
            showPicker(ColorEnvelopeListener { envelop, _ ->
                circularCountDownView.timerTextColor = envelop.color
            })
        }

    }

    private fun onClicksForSizeChange() {
        btnPlusThicknessBackground.setOnClickListener {
            circularCountDownView.strokeThicknessBackground+=1
        }

        btnMinusThicknessBackground.setOnClickListener {
            circularCountDownView.strokeThicknessBackground-=1
        }

        btnPlusThicknessForeground.setOnClickListener {
            circularCountDownView.strokeThicknessForeground+=1
        }

        btnMinusThicknessForeground.setOnClickListener {
            circularCountDownView.strokeThicknessForeground-=1
        }

        btnPlusTimerTextSize.setOnClickListener {
            circularCountDownView.timerTextSize+=1
        }

        btnMinusTimerTextSize.setOnClickListener {
            circularCountDownView.timerTextSize-=1
        }
    }

    private fun setCheckBoxAndRadioChangeListener() {
        checkBoxIsTimerTextBold.setOnClickListener {
            circularCountDownView.timerTextIsBold = checkBoxIsTimerTextBold.isChecked
        }

        rgTimerType.setOnCheckedChangeListener { group, checkedId ->
            circularCountDownView.timerType = when (checkedId) {
                R.id.timerTypeCountUp -> HappyTimer.Type.COUNT_UP
                R.id.timerTypeCountDown -> HappyTimer.Type.COUNT_DOWN
                else -> HappyTimer.Type.COUNT_DOWN
            }
        }

        rgTimerTextFormat.setOnCheckedChangeListener { group, checkedId ->
            circularCountDownView.timerTextFormat = when (checkedId) {
                R.id.timerTextFormatHMS -> CircularCountDownView.TextFormat.HOUR_MINUTE_SECOND
                R.id.timerTextFormatMS -> CircularCountDownView.TextFormat.MINUTE_SECOND
                R.id.timerTextFormatS -> CircularCountDownView.TextFormat.SECOND
                else -> CircularCountDownView.TextFormat.MINUTE_SECOND
            }
        }
    }


    private fun showPicker(colorEnvelopeListener: ColorEnvelopeListener) {
        ColorPickerDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
            .setTitle("ColorPicker Dialog")
            .setPositiveButton("Select", colorEnvelopeListener)
            .setNegativeButton(
                "Cancel"
            ) { dialogInterface, _ -> dialogInterface.dismiss() }
            .attachAlphaSlideBar(true) // default is true. If false, do not show the AlphaSlideBar.
            .attachBrightnessSlideBar(true) // default is true. If false, do not show the BrightnessSlideBar.
            .show()

    }
}

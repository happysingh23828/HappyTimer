package com.androchef.happytimersample.normal_countdown

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androchef.happytimer.countdowntimer.HappyTimer
import com.androchef.happytimersample.R
import com.androchef.happytimersample.utils.toast
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import kotlinx.android.synthetic.main.activity_normal_count_down.*

class NormalCountDownActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_normal_count_down)
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
                normalCountDownView.initTimer(edtTime.text.toString().toInt())
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
            normalCountDownView.startTimer()
        }

        btnPauseTimer.setOnClickListener {
            normalCountDownView.pauseTimer()
        }

        btnResumeTimer.setOnClickListener {
            normalCountDownView.resumeTimer()
        }

        btnStopTimer.setOnClickListener {
            normalCountDownView.stopTimer()
        }

        btnResetTimer.setOnClickListener {
            normalCountDownView.resetTimer()
        }
    }

    private fun setTimerSecondsListeners() {
        normalCountDownView.setOnTickListener(object : HappyTimer.OnTickListener {
            override fun onTick(completedSeconds: Int, remainingSeconds: Int) {
                tvCurrentTimerCompletedSeconds.text = completedSeconds.toString()
            }

            override fun onTimeUp() {

            }
        })

        normalCountDownView.setStateChangeListener(object : HappyTimer.OnStateChangeListener {
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

        btnChooseTextColor.setOnClickListener {
            showPicker(ColorEnvelopeListener { envelop, _ ->
                normalCountDownView.timerTextColor = envelop.color
            })
        }

        btnChooseTextLabelColor.setOnClickListener {
            showPicker(ColorEnvelopeListener { envelop, _ ->
                normalCountDownView.timerTextLabelColor = envelop.color
            })
        }

    }

    private fun onClicksForSizeChange() {
        btnPlusTimerTextSize.setOnClickListener {
            normalCountDownView.timerTextSize+=1
        }

        btnMinusTimerTextSize.setOnClickListener {
            normalCountDownView.timerTextSize-=1
        }

        btnPlusTimerLabelTextSize.setOnClickListener {
            normalCountDownView.timerTextLabelSize+=1
        }

        btnMinusTimerLabelTextSize.setOnClickListener {
            normalCountDownView.timerTextLabelSize-=1
        }


    }

    private fun setCheckBoxAndRadioChangeListener() {

        rgTimerType.setOnCheckedChangeListener { group, checkedId ->
            normalCountDownView.timerType = when (checkedId) {
                R.id.timerTypeCountUp -> HappyTimer.Type.COUNT_UP
                R.id.timerTypeCountDown -> HappyTimer.Type.COUNT_DOWN
                else -> HappyTimer.Type.COUNT_DOWN
            }
        }

        showHours.setOnClickListener {
            normalCountDownView.showHour = showHours.isChecked
        }

        showMinutes.setOnClickListener {
            normalCountDownView.showMinutes = showMinutes.isChecked
        }

        showSeconds.setOnClickListener {
            normalCountDownView.showSeconds = showSeconds.isChecked
        }

        showLabels.setOnClickListener {
            normalCountDownView.showLabels = showLabels.isChecked
        }

        checkBoxIsTimerTextBold.setOnClickListener {
            normalCountDownView.timerTextIsBold = checkBoxIsTimerTextBold.isChecked
        }

        checkBoxIsTimerTextLabelBold.setOnClickListener {
            normalCountDownView.timerTextLabelIsBold = checkBoxIsTimerTextLabelBold.isChecked
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

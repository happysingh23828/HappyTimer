package com.androchef.happytimersample.dynamic_countdown

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androchef.happytimer.countdowntimer.HappyTimer
import com.androchef.happytimersample.R
import com.androchef.happytimersample.utils.toast
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import kotlinx.android.synthetic.main.activity_demo_dynamic_count_down.*

class DemoDynamicCountDownActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_dynamic_count_down)
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
                dynamicCountDownView.initTimer(edtTime.text.toString().toInt())
                setTickInitialText()
                setTimerSecondsListeners()
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
            dynamicCountDownView.startTimer()
        }

        btnPauseTimer.setOnClickListener {
            dynamicCountDownView.pauseTimer()
        }

        btnResumeTimer.setOnClickListener {
            dynamicCountDownView.resumeTimer()
        }

        btnStopTimer.setOnClickListener {
            dynamicCountDownView.stopTimer()
        }

        btnResetTimer.setOnClickListener {
            dynamicCountDownView.resetTimer()
        }
    }

    private fun setTimerSecondsListeners() {
        dynamicCountDownView.setOnTickListener(object : HappyTimer.OnTickListener {
            override fun onTick(completedSeconds: Int, remainingSeconds: Int) {
                tvCurrentTimerCompletedSeconds.text = completedSeconds.toString()
            }

            override fun onTimeUp() {

            }
        })

        dynamicCountDownView.setStateChangeListener(object : HappyTimer.OnStateChangeListener {
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
                dynamicCountDownView.timerTextColor = envelop.color
            })
        }

        btnChooseTextLabelColor.setOnClickListener {
            showPicker(ColorEnvelopeListener { envelop, _ ->
                dynamicCountDownView.timerTextSeparatorColor = envelop.color
            })
        }

    }

    private fun onClicksForSizeChange() {
        btnPlusTimerTextSize.setOnClickListener {
            dynamicCountDownView.timerTextSize+=1
        }

        btnMinusTimerTextSize.setOnClickListener {
            dynamicCountDownView.timerTextSize-=1
        }

        btnPlusTimerLabelTextSize.setOnClickListener {
            dynamicCountDownView.timerTextSeparatorSize+=1
        }

        btnMinusTimerLabelTextSize.setOnClickListener {
            dynamicCountDownView.timerTextSeparatorSize-=1
        }


    }

    private fun setCheckBoxAndRadioChangeListener() {

        rgTimerType.setOnCheckedChangeListener { group, checkedId ->
            dynamicCountDownView.timerType = when (checkedId) {
                R.id.timerTypeCountUp -> HappyTimer.Type.COUNT_UP
                R.id.timerTypeCountDown -> HappyTimer.Type.COUNT_DOWN
                else -> HappyTimer.Type.COUNT_DOWN
            }
        }

        showHours.setOnClickListener {
            dynamicCountDownView.showHour = showHours.isChecked
        }

        showMinutes.setOnClickListener {
            dynamicCountDownView.showMinutes = showMinutes.isChecked
        }

        showSeconds.setOnClickListener {
            dynamicCountDownView.showSeconds = showSeconds.isChecked
        }

        showSeparator.setOnClickListener {
            dynamicCountDownView.showSeparators = showSeparator.isChecked
        }

        checkBoxIsTimerTextBold.setOnClickListener {
            dynamicCountDownView.timerTextIsBold = checkBoxIsTimerTextBold.isChecked
        }

        checkBoxIsTimerTextSeparatorBold.setOnClickListener {
            dynamicCountDownView.timerTextSeparatorIsBold = checkBoxIsTimerTextSeparatorBold.isChecked
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

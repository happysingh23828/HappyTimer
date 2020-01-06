package com.androchef.happytimer.countdowntimer

import android.os.CountDownTimer

class HappyTimer(seconds: Int,timeElapsedIntervalInSeconds: Int = 0) {

    private var totalSecondsInMills = DEFAULT_MILL_SECONDS

    private var currentCompletedSecondsInMillis: Long = 0

    private var remainingSecondsInMillis: Long = 0

    private var countDownTimer: CountDownTimer? = null

    private var onTickListener: OnTickListener? = null

    private var onStateChangeListener: OnStateChangeListener? = null

    private var timeElapsedIntervalInMillis: Int = timeElapsedIntervalInSeconds.times(1000)

    private var timerState: State = State.UNKNOWN
        set(value) {
            field = value
            onStateChange(field)
        }

    init {
        totalSecondsInMills = seconds.times(1000L)
        initialize()
    }

    private fun initialize() {
        countDownTimer = object : CountDownTimer(totalSecondsInMills, COUNT_DOWN_INTERVAL) {
            override fun onFinish() {
                if (timerState == State.FINISHED || timerState == State.RUNNING || timerState == State.RESUMED) {
                    onTickListener?.onTimeUp()
                    resetTimer()
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                onTimerTick(millisUntilFinished)
            }
        }
    }

    private fun initializeWithNewTime(leftMilliSeconds: Long) {
        countDownTimer = object : CountDownTimer(leftMilliSeconds, COUNT_DOWN_INTERVAL) {
            override fun onFinish() {
                if (timerState == State.FINISHED || timerState == State.RUNNING || timerState == State.RESUMED) {
                    onTickListener?.onTimeUp()
                    resetTimer()
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                onTimerTick(millisUntilFinished)
            }
        }
    }

    fun onTimerTick(millisUntilFinished: Long) {
        //removing the remainders from milliseconds
        val seconds = millisUntilFinished / 1000.0.toLong()
        val millis = seconds * 1000L
        remainingSecondsInMillis = millis
        currentCompletedSecondsInMillis = totalSecondsInMills.minus(millis)
        onTickListener?.onTick(
            currentCompletedSecondsInMillis.div(COUNT_DOWN_INTERVAL).toInt()
            , remainingSecondsInMillis.div(COUNT_DOWN_INTERVAL).toInt()
        )
    }

    private fun onStateChange(state: State) {
        onStateChangeListener?.onStateChange(
            state,
            currentCompletedSecondsInMillis.div(COUNT_DOWN_INTERVAL).toInt(),
            remainingSecondsInMillis.div(COUNT_DOWN_INTERVAL).toInt()
        )
    }

    fun start() {
        if (timerState == State.RESET) {
            initialize()
        }
        if (timerState == State.UNKNOWN) {
            countDownTimer?.start()
            timerState = State.RUNNING
        }
    }


    fun stop() {
        if (timerState == State.RESUMED || timerState == State.RUNNING || timerState == State.PAUSED) {
            timerState = State.STOPPED
            countDownTimer?.cancel()
            currentCompletedSecondsInMillis = 0
            remainingSecondsInMillis = 0
        }
    }

    fun pause() {
        if (timerState == State.RUNNING || timerState == State.RESUMED) {
            countDownTimer?.cancel()
            //Creating new Timer
            initializeWithNewTime(remainingSecondsInMillis)
            timerState = State.PAUSED
        }
    }

    fun resume() {
        if (timerState == State.RESET) {
            initialize()
        }
        if (timerState == State.PAUSED) {
            timerState = State.RESUMED
            countDownTimer?.start()
        } else if (timerState == State.UNKNOWN) {
            timerState = State.RUNNING
            countDownTimer?.start()
        }
    }

    fun resetTimer() {
        currentCompletedSecondsInMillis = 0
        remainingSecondsInMillis = 0
        timerState = State.RESET
    }

    fun setOnTickListener(onTickListener: OnTickListener) {
        this.onTickListener = onTickListener
    }

    fun setOnStateChangeListener(onStateChangeListener: OnStateChangeListener) {
        this.onStateChangeListener = onStateChangeListener
    }

    enum class Type {
        COUNT_UP, COUNT_DOWN
    }

    enum class State {
        RUNNING, FINISHED, PAUSED, RESUMED, UNKNOWN, RESET, STOPPED
    }

    interface OnTickListener {
        fun onTick(completedSeconds: Int, remainingSeconds: Int)
        fun onTimeUp()
    }

    interface OnStateChangeListener {
        fun onStateChange(state: State, completedSeconds: Int, remainingSeconds: Int)
    }

    companion object {
        private const val DEFAULT_MILL_SECONDS = 10000L
        private const val COUNT_DOWN_INTERVAL = 1000L
        private const val TIME_ELAPSED_INTERVAL = 5000L
    }
}
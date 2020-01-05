package com.androchef.happytimer.utils

import com.androchef.happytimer.countdowntimer.pojo.NormalCountDownTime


object DateTimeUtils {

    fun getMinutesSecondsFormat(seconds: Int): String {
        return "${(seconds / 60).toString()
            .padStart(2, '0')}:${(seconds % 60).toString()
            .padStart(2, '0')}"
    }

    fun getHourMinutesSecondsFormat(seconds: Int): String {
        val hours = seconds / 3600
        return "${(hours).toString()
            .padStart(2, '0')}:${(seconds.rem(3600) / 60).toString()
            .padStart(2, '0')}:${(seconds % 60).toString()
            .padStart(2, '0')}"
    }

    fun getNormalCountDownTime(seconds: Int): NormalCountDownTime {
        return NormalCountDownTime(
            hour = seconds / 3600,
            minutes = seconds.rem(3600) / 60,
            seconds = seconds % 60
        )
    }
}
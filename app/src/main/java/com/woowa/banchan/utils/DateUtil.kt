package com.woowa.banchan.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    private val format: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss")
        .apply { timeZone = TimeZone.getTimeZone("Aisa/Seoul") }

    fun getUpdateDate(date: Date): String {
        val currentTime = System.currentTimeMillis()

        val diff: Long = currentTime - date.time
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        val years = days / 365

        return if (years > 0) "${years}년 전"
        else if (days > 0) "${days}일 전"
        else if (hours > 0) "${hours}시간 전"
        else if (minutes > 0) "${minutes}분 전"
        else "${seconds}초 전"
    }

    fun getUpdateDate(date: String): String =
        getUpdateDate(format.parse(date) as Date)
}
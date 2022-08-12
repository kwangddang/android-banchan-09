package com.woowa.banchan.data.local

import androidx.room.TypeConverter
import java.util.*

object DBConverter {

    @TypeConverter
    fun toDate(timestamp: Long): Date {
        return Date(timestamp)
    }

    @TypeConverter
    fun toTimestamp(date: Date): Long {
        return date.time
    }
}
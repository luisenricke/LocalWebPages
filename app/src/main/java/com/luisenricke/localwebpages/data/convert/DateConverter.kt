package com.luisenricke.localwebpages.data.convert

import android.annotation.SuppressLint
import androidx.room.TypeConverter
import java.util.Date

@Suppress("unused")
@SuppressLint("SimpleDateFormat")
object DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = if (value == null) null else Date(value)

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time
}
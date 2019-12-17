package com.ocean.ishareeconomy_android.database.converters

import androidx.room.TypeConverter
import java.sql.Date


object DateConverter {

    @TypeConverter
    @JvmStatic
    fun toDate(dateLong: Long): Date {
        return Date(dateLong)
    }

    @TypeConverter
    @JvmStatic
    fun fromDate(date: Date): Long {
        return date.getTime()
    }
}
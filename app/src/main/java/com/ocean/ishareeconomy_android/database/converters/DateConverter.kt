package com.ocean.ishareeconomy_android.database.converters

import androidx.room.TypeConverter
import java.sql.Date

/**
 * Part of *database.converters*.
 *
 * Converter that automatically handles conversion of [Date] objects into and out of the DB
 */
object DateConverter {

    @TypeConverter
    @JvmStatic
    fun toDate(dateLong: Long): Date {
        return Date(dateLong)
    }

    @TypeConverter
    @JvmStatic
    fun fromDate(date: Date): Long {
        return date.time
    }
}
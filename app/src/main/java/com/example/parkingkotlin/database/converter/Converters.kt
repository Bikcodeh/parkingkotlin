package com.example.parkingkotlin.database.converter

import androidx.room.TypeConverter
import java.util.*

class Converters {
    companion object{

        @TypeConverter
        @JvmStatic
        fun fromDate(value: Long): Date? {
            return if (value == null) null else Date(value)
        }

        @TypeConverter
        @JvmStatic
        fun dateToTimestamp(date: Date?): Long? {
            return date?.time
        }
    }
}
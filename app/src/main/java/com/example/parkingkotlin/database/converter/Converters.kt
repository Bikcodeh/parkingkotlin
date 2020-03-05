package com.example.parkingkotlin.database.converter

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class Converters {

    companion object{

        private val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)

        @TypeConverter
        @JvmStatic
        fun fromTimestamp(value: String): Date? {
            return simpleDateFormat.parse(value)
        }

        @TypeConverter
        @JvmStatic
        fun dateToTimestamp(date: Date): String? {
            return simpleDateFormat.format(date)
        }
    }
}
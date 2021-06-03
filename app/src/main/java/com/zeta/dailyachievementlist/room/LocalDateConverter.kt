package com.zeta.dailyachievementlist.room

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.ZoneId

object LocalDateConverter {
    @TypeConverter
    fun toLocalDate(value: Long?): LocalDate? = value?.let { LocalDate.ofEpochDay(it) }

    @TypeConverter
    fun fromLocalDate(date: LocalDate?): Long? =
        date?.toEpochDay()
}
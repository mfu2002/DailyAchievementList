package com.zeta.dailyachievementlist.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.zeta.dailyachievementlist.room.LocalDateConverter
import java.time.LocalDate
import java.util.*

@Entity
@TypeConverters(LocalDateConverter::class)
data class Achievement (
    @PrimaryKey(autoGenerate = true) val id: Long,
    val achievement: String,
    val accomplishedDate: LocalDate
)
package com.zeta.dailyachievementlist.room

import androidx.room.TypeConverters
import java.time.LocalDate



@TypeConverters(LocalDateConverter::class)
data class StatisticsInfo(
    val accomplishedDate: LocalDate
)
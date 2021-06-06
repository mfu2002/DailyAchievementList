package com.zeta.dailyachievementlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeta.dailyachievementlist.room.AchievementDao
import com.zeta.dailyachievementlist.room.LocalDateConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.TemporalQueries.localTime

@HiltViewModel
class StatisticsFragmentViewModel(val achievementDao: AchievementDao): ViewModel() {


    val achievementYearCount by lazy {
        viewModelScope.launch {
            val currentDate = LocalDate.now()
            achievementDao.getAchievementCount(
                LocalDateConverter.fromLocalDate(LocalDate.of(currentDate.year,1,1))!!,
                LocalDateConverter.fromLocalDate(LocalDate.of(currentDate.year,12,31))!!
            )
        }
    }

    val achievementMonthCount by lazy {
        viewModelScope.launch {
            val currentDate = LocalDate.now()
            achievementDao.getAchievementCount(
                LocalDateConverter.fromLocalDate(LocalDate.of(currentDate.year,currentDate.monthValue,1))!!,
                LocalDateConverter.fromLocalDate(LocalDate.of(currentDate.year,currentDate.monthValue,currentDate.month.length(currentDate.isLeapYear)))!!
            )
        }
    }


    val achievementWeekCount by lazy {
        viewModelScope.launch {
            val currentDate = LocalDate.now()
            val startOfTheWeek = currentDate.minusDays(currentDate.dayOfWeek.value.toLong())
            val endOfTheWeek = currentDate.plusDays(7 - currentDate.dayOfWeek.value.toLong())

            achievementDao.getAchievementCount(
                LocalDateConverter.fromLocalDate(startOfTheWeek)!!,
                LocalDateConverter.fromLocalDate(endOfTheWeek)!!
            )
        }
    }

    val daysFulfilledGoalYear by lazy {
        viewModelScope.launch {
            val currentDate = LocalDate.now()
            achievementDao.fulfilledGoalCount(
                5,
                LocalDateConverter.fromLocalDate(LocalDate.of(currentDate.year, 1, 1))!!,
                LocalDateConverter.fromLocalDate(LocalDate.of(currentDate.year, 12, 31))!!
            )
        }
    }

    val daysFulfilledGoalMonth by lazy {
        viewModelScope.launch {
            val currentDate = LocalDate.now()
            achievementDao.fulfilledGoalCount(
                5,
                LocalDateConverter.fromLocalDate(LocalDate.of(currentDate.year,currentDate.monthValue,1))!!,
                LocalDateConverter.fromLocalDate(LocalDate.of(currentDate.year,currentDate.monthValue,currentDate.month.length(currentDate.isLeapYear)))!!
            )
        }
    }


}
package com.zeta.dailyachievementlist.viewmodel

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.*
import androidx.preference.PreferenceManager
import com.zeta.dailyachievementlist.MyApplication
import com.zeta.dailyachievementlist.R
import com.zeta.dailyachievementlist.room.AchievementDao
import com.zeta.dailyachievementlist.room.LocalDateConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.TemporalQueries.localTime
import javax.inject.Inject

@HiltViewModel
class StatisticsFragmentViewModel @Inject constructor(
    val achievementDao: AchievementDao,
    app: Application
): AndroidViewModel(app) {

    private val dailyGoal by lazy {
        PreferenceManager.getDefaultSharedPreferences(getApplication<MyApplication>().applicationContext)
            .getString(
                getApplication<MyApplication>().getString(R.string.pref_goal),
                getApplication<MyApplication>().getString(R.string.def_goal)
            )!!.toInt()
    }

    val achievementYearCount = MutableLiveData(0)

    val achievementMonthCount = MutableLiveData(0)

    val achievementWeekCount = MutableLiveData(0)

    val daysFulfilledGoalYear = MutableLiveData(0)

    val daysFulfilledGoalMonth = MutableLiveData(0)


    private fun loadStats() {
        viewModelScope.launch {
            val currentDate = LocalDate.now()

            val accomplishmentDatesList = achievementDao.getAchievementsBetweenDates(
                LocalDateConverter.fromLocalDate(LocalDate.of(currentDate.year, 1, 1))!!,
                LocalDateConverter.fromLocalDate(LocalDate.of(currentDate.year, 12, 31))!!
            )
            if (accomplishmentDatesList.isEmpty()) {
                return@launch
            }
            var processingDate = accomplishmentDatesList[0].accomplishedDate.toEpochDay()
            var processingDateAchievementCount: Int = 0
            val yearAchievementCount: Int = accomplishmentDatesList.size
            var monthAchievementCount: Int = 0
            var weekAchievementCount: Int = 0
            var yearGoalAchievedCount: Int = 0
            var monthGoalAchievementCount: Int = 0

            val startOfTheWeek = currentDate.minusDays(currentDate.dayOfWeek.value.toLong())
            val endOfTheWeek = currentDate.plusDays(7 - currentDate.dayOfWeek.value.toLong())

            val startOfTheMonth = LocalDate.of(
                currentDate.year,
                currentDate.monthValue,
                1
            )
            val endOfTheMonth = LocalDate.of(
                currentDate.year,
                currentDate.monthValue,
                currentDate.month.length(currentDate.isLeapYear)
            )

            for (date in accomplishmentDatesList) {
                if (processingDate != date.accomplishedDate.toEpochDay()) {
                    processingDate = date.accomplishedDate.toEpochDay()
                    processingDateAchievementCount = 0
                }
                processingDateAchievementCount++
                if (processingDateAchievementCount == dailyGoal) {
                    yearGoalAchievedCount++
                }
                if (date.accomplishedDate >= startOfTheMonth && date.accomplishedDate <= endOfTheMonth) {
                    monthAchievementCount++
                    if (date.accomplishedDate >= startOfTheWeek && date.accomplishedDate <= endOfTheWeek) {
                        weekAchievementCount++
                    }
                    if (processingDateAchievementCount == dailyGoal) {
                        monthGoalAchievementCount++
                    }
                }


            }

            achievementYearCount.postValue(yearAchievementCount)
            achievementMonthCount.postValue(monthAchievementCount)
            achievementWeekCount.postValue(weekAchievementCount)
            daysFulfilledGoalYear.postValue(yearGoalAchievedCount)
            daysFulfilledGoalMonth.postValue(monthGoalAchievementCount)
        }
    }

    init {


        loadStats()


    }

}


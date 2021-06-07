package com.zeta.dailyachievementlist.viewmodel

import android.app.Application
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeta.dailyachievementlist.MyApplication
import com.zeta.dailyachievementlist.R
import com.zeta.dailyachievementlist.room.AchievementDao
import com.zeta.dailyachievementlist.room.LocalDateConverter
import com.zeta.dailyachievementlist.room.entity.Achievement
import com.zeta.dailyachievementlist.ui.calendarcomponenets.CalendarItemData
import com.zeta.dailyachievementlist.ui.calendarcomponenets.MonthPickerDialog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class CalendarFragmentViewModel @Inject constructor(
    app: Application,
    val sharedPref: SharedPreferences,
    val achievementDao: AchievementDao
    ): AndroidViewModel(app) {

    val currentViewCalendarData = ObservableArrayList<CalendarItemData>()
    private val achievementCache = Hashtable<Long, List<Achievement>>()
    val currentViewAchievementList = ObservableArrayList<Achievement>()

    val viewingMonthStr = MutableLiveData("")
    private var viewingMonth =  LocalDate.now()

    private val dailyGoal by lazy { sharedPref.getInt(getApplication<MyApplication>().getString(R.string.pref_goal), 5) }


    init {
        viewModelScope.launch {
            setMonthDataOnView(viewingMonth)
        }

    }

   private suspend fun getMonthAchievements(date: LocalDate) {
        //TODO: check Whether Leap Year
        val monthAchievements = achievementDao.getAchievementsBetweenDates(
                LocalDateConverter.fromLocalDate(
                        date.withDayOfMonth(1))!!,
                LocalDateConverter.fromLocalDate(
                        date.withDayOfMonth(date.month.length(date.isLeapYear)))!!
        )

        val achievementDict = Hashtable<Long, ArrayList<Achievement>>()

        for (achievement in monthAchievements) {
            val dayKey = achievement.accomplishedDate.toEpochDay()
            if (!achievementDict.containsKey(dayKey)) {
                achievementDict[dayKey] = ArrayList()
            }

            achievementDict[dayKey]!!.add(achievement)
        }

        for (dayKey in achievementDict.keys) {
            achievementCache[dayKey] = achievementDict[dayKey]
        }

    }

    private suspend fun  getAchievementForDate(date: LocalDate):List<Achievement>{
        val dayKey = date.toEpochDay()
        if(!achievementCache.containsKey(dayKey)){
            getMonthAchievements(date)
        }
        return if(achievementCache[dayKey] == null) listOf()
        else achievementCache[dayKey]!!
    }



    suspend fun setMonthDataOnView(month: LocalDate) {
        currentViewCalendarData.clear()
        addCalendarHeader()
        viewingMonth = month
        val firstDay = month.withDayOfMonth(1)
        viewingMonthStr.value="${month.month.name}, ${month.year}"

        for (filler in 1 until firstDay.dayOfWeek.value) {
            currentViewCalendarData.add(CalendarItemData(-1, "", getDrawableBackground(Color.TRANSPARENT), false))
        }

        for (day in 1..month.month.length(month.isLeapYear)) {
            val dayKey = firstDay.plusDays((day - 1).toLong())
            val dayAchievements = getAchievementForDate(dayKey)

            val backgroundColor =
                when {
                    dayAchievements.size >= dailyGoal -> {
                        Color.parseColor("#8800FF00")
                    }
                    dayAchievements.size >= dailyGoal / 2 -> {
                        Color.parseColor("#5500FF00")
                    }
                    else -> {
                        Color.TRANSPARENT
                    }
                }

            currentViewCalendarData.add(
                CalendarItemData(
                    dayKey.toEpochDay(),
                    day.toString(),
                    getDrawableBackground(backgroundColor),
                    true
                )
            )

        }
    }


    private fun addCalendarHeader(){
        val days = listOf("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su")
        for (day in days){
            currentViewCalendarData.add(CalendarItemData(-1, day, getDrawableBackground(Color.TRANSPARENT), false))
        }
    }

    private fun getDrawableBackground(backgroundColor: Int) : Drawable {
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.RECTANGLE
        shape.setColor(backgroundColor)
        return shape
    }

    fun setAchievementList(dateItemId: Long) {
        currentViewAchievementList.clear()
        viewModelScope.launch {
            currentViewAchievementList.addAll(getAchievementForDate(LocalDate.ofEpochDay(dateItemId)))
        }
    }

    fun showPrevMonth(v: View){
        viewModelScope.launch {
            setMonthDataOnView(viewingMonth.plusMonths(-1))
        }
    }

    fun showNextMonth(v: View){
        viewModelScope.launch {
            setMonthDataOnView(viewingMonth.plusMonths(1))
        }
    }

}
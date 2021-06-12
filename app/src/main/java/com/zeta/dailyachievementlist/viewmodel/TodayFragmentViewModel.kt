package com.zeta.dailyachievementlist.viewmodel

import android.util.Log
import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeta.dailyachievementlist.room.AchievementDao
import com.zeta.dailyachievementlist.room.LocalDateConverter
import com.zeta.dailyachievementlist.room.entity.Achievement
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TodayFragmentViewModel @Inject constructor(val achievementDao: AchievementDao) : ViewModel() {
    val achievementsList = ObservableArrayList<Achievement>()
    val newAchievement =  MutableLiveData("")


    init {

        val currentDate = LocalDate.now()
        val endDate = currentDate.plusDays(1)
        viewModelScope.launch {
            val achievementsToday = achievementDao.getAchievementsBetweenDates(LocalDateConverter.fromLocalDate(currentDate)!!, LocalDateConverter.fromLocalDate(endDate)!!)
            achievementsList.addAll(achievementsToday)
        }
    }

    fun addAchievement(v: View){
        if(newAchievement.value == "")return
        viewModelScope.launch {
           val id = achievementDao.addAchievement(Achievement(0, newAchievement.value.toString(), LocalDate.now()))
            achievementsList.add(0, Achievement(id, newAchievement.value.toString(),LocalDate.now()))
            newAchievement.value= ""

        }
    }

}
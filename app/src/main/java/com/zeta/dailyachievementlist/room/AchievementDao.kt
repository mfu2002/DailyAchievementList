package com.zeta.dailyachievementlist.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.zeta.dailyachievementlist.room.entity.Achievement
import java.time.LocalDate
import java.util.*

@Dao
interface AchievementDao {
    @Query("SELECT * FROM achievement")
    suspend fun getAll(): List<Achievement>

    @Query("SELECT * FROM achievement WHERE :startDate <= accomplishedDate AND :endDate >= accomplishedDate")
    suspend fun getAchievementsBetweenDates(startDate: Long, endDate: Long): List<Achievement>

    @Insert
    suspend fun addAchievement(achievement: Achievement): Long

    @Query("DELETE FROM Achievement WHERE id == :achievementId")
    suspend fun deleteAchievement(achievementId: Long)

    @Query("SELECT accomplishedDate FROM Achievement WHERE :startDate <= accomplishedDate and :endDate>= accomplishedDate")
    suspend fun getDatesOfAchievementsBetween(startDate: Long, endDate: Long): List<StatisticsInfo>

}
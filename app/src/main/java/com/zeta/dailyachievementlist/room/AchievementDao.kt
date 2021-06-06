package com.zeta.dailyachievementlist.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.zeta.dailyachievementlist.room.entity.Achievement
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

    @Query ("SELECT COUNT(*) FROM achievement WHERE :startDate <= accomplishedDate AND :endDate >= accomplishedDate")
    suspend fun  getAchievementCount(startDate: Long, endDate: Long) : Long

    @Query ("SELECT COUNT(*) FROM achievement WHERE :startDate <= accomplishedDate AND :endDate >= accomplishedDate GROUP BY accomplishedDate HAVING COUNT(*) >= :goal")
    suspend fun fulfilledGoalCount(goal: Int, startDate: Long, endDate: Long) : Long

}
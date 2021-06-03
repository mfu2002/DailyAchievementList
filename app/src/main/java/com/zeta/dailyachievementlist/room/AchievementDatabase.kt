package com.zeta.dailyachievementlist.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zeta.dailyachievementlist.room.entity.Achievement

@Database(entities = [Achievement::class], version = 1)
abstract class AchievementDatabase:RoomDatabase() {
    abstract fun achievementDao(): AchievementDao
}
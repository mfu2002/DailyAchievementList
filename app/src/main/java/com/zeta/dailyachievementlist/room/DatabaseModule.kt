package com.zeta.dailyachievementlist.room

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    fun provideAchievementDao(achievementDatabase: AchievementDatabase): AchievementDao =
        achievementDatabase.achievementDao()

    @Provides
    @Singleton
    fun providesAchievementDatabase(@ApplicationContext appContext: Context):AchievementDatabase{
        return Room.databaseBuilder(
            appContext,
            AchievementDatabase::class.java,
        "dbAchievement"
        ).build()
    }

}
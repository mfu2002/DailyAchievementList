package com.zeta.dailyachievementlist.room

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.zeta.dailyachievementlist.R
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

//    @Provides
//    fun provideSharedPreferences(@ApplicationContext appContext: Context): SharedPreferences {
//        return appContext.getSharedPreferences(
//            appContext.getString(R.string.pref_file),
//            Context.MODE_PRIVATE
//        )
//    }

}
package com.zeta.dailyachievementlist

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager

object Helper {

    fun refreshTheme(context: Context){
        val themeOptions = context.resources.getStringArray(R.array.theme_values)
        when (PreferenceManager.getDefaultSharedPreferences(context).getString(
            context.getString(R.string.pref_theme),
            themeOptions[2]
        )) {
            themeOptions[0] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            themeOptions[1] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            themeOptions[2] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

}
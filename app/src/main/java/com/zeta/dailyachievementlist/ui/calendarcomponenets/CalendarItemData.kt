package com.zeta.dailyachievementlist.ui.calendarcomponenets

import android.graphics.Color
import android.graphics.drawable.Drawable

data class CalendarItemData(
        val id: Long,
        val text: String,
        val background: Drawable,
        val selectable: Boolean
)

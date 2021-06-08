package com.zeta.dailyachievementlist.ui.customPref

import android.content.Context
import android.content.res.TypedArray
import android.text.TextUtils
import android.util.AttributeSet
import androidx.preference.DialogPreference
import com.zeta.dailyachievementlist.R
import java.time.LocalTime
import java.time.format.DateTimeFormatter


/**
 * A Preference to select a specific Time with a [android.widget.TimePicker].
 *
 * @author Jakob Ulbrich
 */
class TimePreference(
    context: Context?,
    attrs: AttributeSet? = null) : DialogPreference(context, attrs) {

    /**
     * In Minutes after midnight
     */
    var time = 0


    /**
     * Called when a Preference is being inflated and the default value attribute needs to be read
     */
    override fun onGetDefaultValue(a: TypedArray, index: Int): Any {
        // The type of this preference is Int, so we read the default value from the attributes
        // as Int. Fallback value is set to 0.
        return a.getInt(index, 0)
    }

    /**
     * Returns the layout resource that is used as the content View for the dialog
     */
    override fun getDialogLayoutResource() = R.layout.preference_dialog_time

    /**
     * Implement this to set the initial value of the Preference.
     */
    override fun onSetInitialValue(restorePersistedValue: Boolean, defaultValue: Any) {
        // If the value can be restored, do it. If not, use the default value.

    time = if (restorePersistedValue) getPersistedInt(time) else defaultValue as Int
    }

    override fun getSummary(): CharSequence {
        val prefix = if (TextUtils.isEmpty(super.getSummary())) "" else "${super.getSummary()}"
        return prefix + formatTime()
    }

    private fun formatTime(): String {
        return  LocalTime.of(time/60, time %60).format(DateTimeFormatter.ofPattern("HH:mm"))
    }
}
package com.zeta.dailyachievementlist.ui.customPref

import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.TimePicker
import androidx.preference.PreferenceDialogFragmentCompat
import com.zeta.dailyachievementlist.R

class TimePreferenceDialogFragmentCompat(key: String?): PreferenceDialogFragmentCompat() {


    /**
     * The TimePicker widget
     */
    private var mTimePicker: TimePicker? = null


    init {
        val b = Bundle(1)
        b.putString(ARG_KEY, key)
        arguments = b
    }


    override fun onBindDialogView(view: View) {
        super.onBindDialogView(view)
        mTimePicker = view.findViewById(R.id.time_picker)

        // Exception: There is no TimePicker with the id 'edit' in the dialog.
        checkNotNull(mTimePicker) { "Dialog view must contain a TimePicker with id 'edit'" }

        // Get the time from the related Preference
        var minutesAfterMidnight: Int? = null
        if (preference is TimePreference) {
            minutesAfterMidnight = (preference as TimePreference).time
        }

        // Set the time to the TimePicker
        if (minutesAfterMidnight != null) {
            val hours = minutesAfterMidnight / 60
            val minutes = minutesAfterMidnight % 60
            val is24hour = DateFormat.is24HourFormat(context)
            mTimePicker!!.setIs24HourView(is24hour)
            mTimePicker!!.currentHour = hours
            mTimePicker!!.currentMinute = minutes
        }
    }

    /**
     * Called when the Dialog is closed.
     *
     * @param positiveResult Whether the Dialog was accepted or canceled.
     */
    override fun onDialogClosed(positiveResult: Boolean) {
        if (positiveResult) {
            // Get the current values from the TimePicker
            val hours: Int
            val minutes: Int
            if (Build.VERSION.SDK_INT >= 23) {
                hours = mTimePicker!!.hour
                minutes = mTimePicker!!.minute
            } else {
                hours = mTimePicker!!.currentHour
                minutes = mTimePicker!!.currentMinute
            }

            // Generate value to save
            val minutesAfterMidnight = hours * 60 + minutes

            // Save the value
            if (preference is TimePreference) {
                // This allows the client to ignore the user value.
                if (preference.callChangeListener(minutesAfterMidnight)) {
                    // Save the value
                    (preference as TimePreference).time = minutesAfterMidnight
                }
            }
        }
    }

}
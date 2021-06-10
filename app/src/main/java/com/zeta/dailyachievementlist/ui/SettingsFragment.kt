package com.zeta.dailyachievementlist.ui

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.zeta.dailyachievementlist.Helper
import com.zeta.dailyachievementlist.R
import com.zeta.dailyachievementlist.ui.customPref.TimePreference
import com.zeta.dailyachievementlist.ui.customPref.TimePreferenceDialogFragmentCompat

class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val notificationTypePref = findPreference<ListPreference>(getString(R.string.pref_notification_type))
        notificationTypePref?.onPreferenceChangeListener = this
        setNotificationTypeDependentDisability(notificationTypePref?.value)


        findPreference<ListPreference>(getString(R.string.pref_theme))?.onPreferenceChangeListener = this


        findPreference<Preference>(getString(R.string.pref_reset))?.setOnPreferenceClickListener {
            preferenceManager.sharedPreferences.edit().clear().apply()
            findNavController().navigate(R.id.action_navigation_settings_to_navigation_settings)
            true
        }




    }

    private fun setNotificationTypeDependentDisability(typeValue: Any?){
        val notificationTypeOption = resources.getStringArray(R.array.notification_style_values)
        val notificationTimePref = findPreference<TimePreference>(getString(R.string.pref_notification_time))
        val notificationTimeStartPref = findPreference<TimePreference>(getString(R.string.pref_notification_start_time))
        val notificationTimeEndPref = findPreference<TimePreference>(getString(R.string.pref_notification_end_time))
        val notificationTimeIntervalPref = findPreference<ListPreference>(getString(R.string.pref_notification_interval))

        notificationTimePref?.isEnabled =  typeValue == notificationTypeOption[1]
        notificationTimeStartPref?.isEnabled = typeValue == notificationTypeOption[2]
        notificationTimeEndPref?.isEnabled = typeValue == notificationTypeOption[2]
        notificationTimeIntervalPref?.isEnabled = typeValue == notificationTypeOption[2]
    }



    override fun onDisplayPreferenceDialog(preference: Preference?) {

        var dialogFragment: DialogFragment? = null
        if (preference is TimePreference) {
            dialogFragment = TimePreferenceDialogFragmentCompat(preference.key)
        }
        if (dialogFragment != null && isAdded) {
            dialogFragment.setTargetFragment(this, 0)
            dialogFragment.show(
                this.parentFragmentManager, "android.support.v7.preference" +
                        ".PreferenceFragment.DIALOG"
            )
        } else {
            super.onDisplayPreferenceDialog(preference)
        }
    }

    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
        if(preference == null || newValue == null ) return true
        if(preference.key.equals(getString(R.string.pref_notification_type))){
           setNotificationTypeDependentDisability(newValue)
        }

        if(preference.key.equals(getString(R.string.pref_theme))){
            Helper.refreshTheme(requireContext())
            requireActivity().recreate()
        }
        return true
    }


}
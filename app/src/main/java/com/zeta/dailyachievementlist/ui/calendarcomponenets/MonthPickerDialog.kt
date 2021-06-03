package com.zeta.dailyachievementlist.ui.calendarcomponenets

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import com.zeta.dailyachievementlist.MainActivity
import com.zeta.dailyachievementlist.databinding.DialogMonthPickerBinding
import java.time.LocalDate

class MonthPickerDialog(private val monthPickerCallBack: MonthPickerCallBack): AppCompatDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater
        val binding = DialogMonthPickerBinding.inflate(inflater, null, false)

        binding.nbpMonth.minValue = 1
        binding.nbpMonth.maxValue = 12
        binding.nbpMonth.wrapSelectorWheel = false
        binding.nbpMonth.displayedValues = arrayOf(
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
        )
        val localDate = LocalDate.now()
        binding.nbpMonth.value = localDate.monthValue

        binding.nbpYear.minValue = 2000
        binding.nbpYear.maxValue = 2999
        binding.nbpYear.value = localDate.year
        binding.nbpYear.wrapSelectorWheel = false

        builder.setView(binding.root)
            .setTitle("Select Month")
            .setNegativeButton("Cancel"){ dialogInterface: DialogInterface, i: Int ->

            }.setPositiveButton("Ok"){ dialogInterface: DialogInterface, i: Int ->
               monthPickerCallBack.selectedMonth(binding.nbpMonth.value, binding.nbpYear.value)
            }


        return builder.create()
    }
}
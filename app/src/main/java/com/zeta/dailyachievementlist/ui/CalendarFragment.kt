package com.zeta.dailyachievementlist.ui

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zeta.dailyachievementlist.MyApplication
import com.zeta.dailyachievementlist.R
import com.zeta.dailyachievementlist.RecyclerViewListChangeNotifier
import com.zeta.dailyachievementlist.databinding.FragmentCalendarBinding
import com.zeta.dailyachievementlist.room.AchievementDao
import com.zeta.dailyachievementlist.ui.calendarcomponenets.CalendarAdapter
import com.zeta.dailyachievementlist.ui.calendarcomponenets.CalendarParent
import com.zeta.dailyachievementlist.ui.calendarcomponenets.MonthPickerCallBack
import com.zeta.dailyachievementlist.ui.calendarcomponenets.MonthPickerDialog
import com.zeta.dailyachievementlist.ui.todayfragmentcomponents.AchievementRecyclerViewAdapter
import com.zeta.dailyachievementlist.viewmodel.CalendarFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class CalendarFragment : Fragment(), CalendarParent, MonthPickerCallBack {

    private val TAG = "CalendarFragment"

    lateinit var binding: FragmentCalendarBinding
    @Inject
    lateinit var achievementDao: AchievementDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        val viewModel by viewModels<CalendarFragmentViewModel>()
        binding.viewModel =viewModel


        val outValue = TypedValue()
        requireActivity().theme.resolveAttribute(R.attr.colorSecondary,outValue, true)
        viewModel.primaryColor = outValue.data
        requireActivity().theme.resolveAttribute(R.attr.colorSecondaryVariant,outValue, true)
        viewModel.secondaryColor = outValue.data

        val calendarAdapter = CalendarAdapter(viewModel.currentViewCalendarData, this)
        viewModel.currentViewCalendarData.addOnListChangedCallback(
            RecyclerViewListChangeNotifier(
                calendarAdapter as RecyclerView.Adapter<RecyclerView.ViewHolder>
            )
        )


        binding.rcvCalendar.adapter = calendarAdapter
        binding.rcvCalendar.layoutManager = GridLayoutManager(requireContext(), 7)

        val achievementsAdapter =
            AchievementRecyclerViewAdapter(viewModel.currentViewAchievementList, null, false)

        viewModel.currentViewAchievementList.addOnListChangedCallback(
            RecyclerViewListChangeNotifier(achievementsAdapter as RecyclerView.Adapter<RecyclerView.ViewHolder>)
        )
        binding.rcvAchievements.adapter = achievementsAdapter
        binding.rcvAchievements.layoutManager = LinearLayoutManager(context)




        binding.txtMonth.setOnClickListener{
            val monthPickerDialog = MonthPickerDialog(this)
            monthPickerDialog.show(childFragmentManager, "")
        }


        return binding.root
    }

    override fun calendarItemClicked(itemId: Long) {
        binding.viewModel?.setAchievementList(itemId)
    }

    override fun selectedMonth(month: Int, year: Int) {
        lifecycleScope.launch {
            binding.viewModel?.setMonthDataOnView(LocalDate.of(year, month, 1))
        }
    }
}
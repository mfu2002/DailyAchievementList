package com.zeta.dailyachievementlist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.RecyclerView
import com.zeta.dailyachievementlist.databinding.FragmentTodayBinding
import com.zeta.dailyachievementlist.viewmodel.TodayFragmentViewModel
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.zeta.dailyachievementlist.room.AchievementDao
import com.zeta.dailyachievementlist.ui.todayfragmentcomponents.AchievementRecyclerViewAdapter
import com.zeta.dailyachievementlist.ui.todayfragmentcomponents.AchievementRecyclerViewParent
import com.zeta.dailyachievementlist.RecyclerViewListChangeNotifier
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TodayFragment : Fragment(), AchievementRecyclerViewParent {

    lateinit var binding: FragmentTodayBinding

    @Inject lateinit var achievementDao: AchievementDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val viewModel by viewModels<TodayFragmentViewModel>()

        binding = FragmentTodayBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val recyclerViewAdapter = AchievementRecyclerViewAdapter(viewModel.achievementsList, this)
        viewModel.achievementsList.addOnListChangedCallback(
            RecyclerViewListChangeNotifier(
                recyclerViewAdapter as RecyclerView.Adapter<RecyclerView.ViewHolder>
            )
        )
        binding.rcvAchievements.adapter = recyclerViewAdapter
        binding.rcvAchievements.layoutManager = LinearLayoutManager(requireContext())

        binding.txtAddAchievement.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_GO){
                binding.imgBtnAddAchievement.callOnClick()

            }
            false
        }

        return binding.root
    }

    override fun deleteAchievement(achievementId: Long) {
        val achievement = binding.viewModel!!.achievementsList.find { it.id == achievementId }
        binding.viewModel!!.achievementsList.remove(achievement)
        if(achievement != null) {
            lifecycleScope.launch {
                achievementDao.deleteAchievement(achievement.id)
            }
        }
    }
}
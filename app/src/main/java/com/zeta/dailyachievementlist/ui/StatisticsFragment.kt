package com.zeta.dailyachievementlist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentViewHolder
import com.zeta.dailyachievementlist.R
import com.zeta.dailyachievementlist.databinding.FragmentStatisticsBinding
import com.zeta.dailyachievementlist.databinding.FragmentTodayBinding
import com.zeta.dailyachievementlist.viewmodel.StatisticsFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticsFragment : Fragment() {

    lateinit var binding: FragmentStatisticsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val viewModel by viewModels<StatisticsFragmentViewModel>()
        binding = FragmentStatisticsBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        return binding.root
    }
}
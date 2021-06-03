package com.zeta.dailyachievementlist.ui.todayfragmentcomponents

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zeta.dailyachievementlist.databinding.DailyAchievementListItemBinding
import com.zeta.dailyachievementlist.room.entity.Achievement

class AchievementRecyclerViewAdapter(private val achievements: List<Achievement>,
                                     private val recyclerViewParent: AchievementRecyclerViewParent?,
                                     private val allowEdit: Boolean = true):
    RecyclerView.Adapter<AchievementRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(val binding: DailyAchievementListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            DailyAchievementListItemBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val achievement = achievements[position]
        viewHolder.binding.lblAchievement.text = achievement.achievement
        if (allowEdit) {
            viewHolder.binding.imgBtnDeleteAchievement.setOnClickListener {
                recyclerViewParent?.deleteAchievement(achievement.id)
            }
        } else {
            viewHolder.binding.imgBtnDeleteAchievement.visibility = View.INVISIBLE

        }

    }

    override fun getItemCount() = achievements.size

}
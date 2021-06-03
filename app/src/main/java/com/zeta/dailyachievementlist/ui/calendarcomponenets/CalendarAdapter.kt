package com.zeta.dailyachievementlist.ui.calendarcomponenets

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.zeta.dailyachievementlist.R

class CalendarAdapter(private val calendarItems: ObservableArrayList<CalendarItemData>, private val calendarParent: CalendarParent) : RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {

    var selectedView: View? = null

    class ViewHolder(val rootView: View) : RecyclerView.ViewHolder(rootView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(View.inflate(parent.context, R.layout.calendar_grid_item, null))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemData = calendarItems[position]
        holder.rootView.findViewById<TextView>(R.id.txtCalendarItemText).text = itemData.text
        holder.rootView.background = itemData.background
        if (itemData.selectable) {
            holder.rootView.setOnClickListener {
                calendarParent.calendarItemClicked(itemData.id)
                selectedView?.findViewById<TextView>(R.id.txtTopLeftAdorn)?.visibility = View.GONE
                selectedView = holder.rootView
                holder.rootView.findViewById<TextView>(R.id.txtTopLeftAdorn)?.visibility =
                    View.VISIBLE
            }
        }
    }



    override fun getItemCount(): Int =calendarItems.size
}
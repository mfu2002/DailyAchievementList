package com.zeta.dailyachievementlist

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.databinding.ObservableList.OnListChangedCallback
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewListChangeNotifier(private val recyclerViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>): OnListChangedCallback<ObservableArrayList<Any>>() {
    override fun onChanged(sender: ObservableArrayList<Any>?) {
        recyclerViewAdapter.notifyDataSetChanged()
    }

    override fun onItemRangeChanged(
        sender: ObservableArrayList<Any>?,
        positionStart: Int,
        itemCount: Int
    ) {
        recyclerViewAdapter.notifyItemRangeChanged(positionStart, itemCount)
    }

    override fun onItemRangeInserted(
        sender: ObservableArrayList<Any>?,
        positionStart: Int,
        itemCount: Int
    ) {
        recyclerViewAdapter.notifyItemRangeInserted(positionStart, itemCount)
    }

    override fun onItemRangeMoved(
        sender: ObservableArrayList<Any>?,
        fromPosition: Int,
        toPosition: Int,
        itemCount: Int
    ) {
        for (i in 0..itemCount) {
            recyclerViewAdapter.notifyItemMoved(fromPosition + i, toPosition + i)
        }
    }

    override fun onItemRangeRemoved(
        sender: ObservableArrayList<Any>?,
        positionStart: Int,
        itemCount: Int
    ) {
        recyclerViewAdapter.notifyItemRangeRemoved(positionStart, itemCount)
    }
}
package com.batanks.nextplan.notifications

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R

class NotificationsListAdapter (private val notificationsList : List<String>) : RecyclerView.Adapter<NotificationsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_notifications_item, parent, false)

        return ViewHolder(view)

    }

    override fun getItemCount() = /*notificationsList.size*/ 15

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

//        val txt_groups_list_item : TextView = itemView.txt_groups_list_item
    }
}
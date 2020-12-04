package com.batanks.nextplan.notifications

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.swagger.model.NotificationsList
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_notifications_item.view.*

class NotificationsListAdapter (private val notificationsList : ArrayList<NotificationsList>, private var context: Context) : RecyclerView.Adapter<NotificationsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_notifications_item, parent, false)

        return ViewHolder(view)

    }

    override fun getItemCount() = notificationsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.txt_followups_list_item.text = notificationsList[position].event.title
        Glide.with(context).load(notificationsList[position].event.category.picture).into(holder.img_followups_list_item)

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txt_followups_list_item : TextView = itemView.txt_followups_list_item
        val img_followups_list_item : ImageView = itemView.img_followups_list_item
    }
}
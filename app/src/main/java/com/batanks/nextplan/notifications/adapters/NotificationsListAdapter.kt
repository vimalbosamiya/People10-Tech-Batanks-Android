package com.batanks.nextplan.notifications.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.eventdetails.EventDetailView
import com.batanks.nextplan.eventdetailsadmin.EventDetailViewAdmin
import com.batanks.nextplan.home.HomePlanPreview
import com.batanks.nextplan.notifications.viewmodel.NotificationsViewModel
import com.batanks.nextplan.swagger.model.NotificationModel
import com.batanks.nextplan.swagger.model.NotificationRead
import com.batanks.nextplan.swagger.model.NotificationsList
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_notifications_item.view.*

class NotificationsListAdapter (private val notificationsList : ArrayList<NotificationModel>, private val notificationsViewModel: NotificationsViewModel)
                                : RecyclerView.Adapter<NotificationsListAdapter.ViewHolder>() {

    lateinit var context : Context
    val notificationId : ArrayList<Int> = arrayListOf()
    private var userId : Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        context = parent.context

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_notifications_item, parent, false)

        return ViewHolder(view)

    }

    override fun getItemCount() = notificationsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        userId  = context.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).getInt("ID",0)

        holder.txt_followups_list_item.text = notificationsList[position].message
        //Glide.with(context).load(notificationsList[position].event.category.picture).into(holder.img_followups_list_item)

        if (notificationsList[position].read == true){

            holder.notificationItem.setBackgroundColor(context.resources.getColor(R.color.colorPrimary))

        } else if(notificationsList[position].read == false){

            holder.notificationItem.setBackgroundColor(context.resources.getColor(R.color.lightDark))
        }

        holder.txt_followups_list_item.setOnClickListener {

            notificationId.add(notificationsList[position].id)

            notificationsViewModel.apiNotificationMarkAsRead(NotificationRead(notificationId))

            if (notificationsList.get(position).event_creator_id == userId){

                val intent = Intent(context, EventDetailViewAdmin::class.java)
                intent.putExtra("ID", notificationsList.get(position).event_id)
                ContextCompat.startActivity(context, intent, null)
                //(context as HomePlanPreview).finish()
                (context as Activity).finish()

            } else {

                val intent = Intent(context, EventDetailView::class.java)
                intent.putExtra("ID", notificationsList.get(position).event_id)
                ContextCompat.startActivity(context, intent, null)
                //(context as HomePlanPreview).finish()
                (context as Activity).finish()
            }

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txt_followups_list_item : TextView = itemView.txt_followups_list_item
        val notificationItem : RelativeLayout = itemView.notificationItem
        val img_followups_list_item : ImageView = itemView.img_followups_list_item
    }
}
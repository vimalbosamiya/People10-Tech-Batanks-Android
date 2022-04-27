package com.batanks.nextplan.eventdetailsadmin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.home.fragment.period.AddPeriodRecyclerView
import com.batanks.nextplan.swagger.model.ActivityParticipant
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_contact.view.*

class ActivityEverybodyComeListAdapterAdmin (/*private val callBack: ActivityEverybodyComeListAdapterAdmin,*/ val contactList: ArrayList<String>/*ArrayList<ActivityParticipant>*/, val context: Context) :
    RecyclerView.Adapter<ActivityEverybodyComeListAdapterAdmin.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_contact, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount() = contactList.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            holder.close.setOnClickListener {

                contactList.removeAt(position)
                //callBack.closeButtonAddPeriodItemListener(position)
            }

            holder.contactName.text = contactList?.get(position)
            holder.contactStatus.setImageResource(R.drawable.ic_publiceventcategoryiconaccepted)
            //holder.close.visibility = View.GONE
            //Glide.with(context).load(contactList[position].picture).circleCrop().into(holder.contactImage)
            //Glide.with(context).load(contactList[position].picture).circleCrop().into(holder.contactStatus)
            //holder.contactSettings.visibility = View.GONE
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val contactStatus : ImageView = itemView.contactStatus
            val contactName : TextView = itemView.contactName
            val close : ImageView = itemView.close
            //val contactImage : ImageView = itemView.contactImage
            //val contactImage : ImageView = itemView.contactImage
        }

    interface ActivityEverybodyComeListAdapterAdmin {
        fun closeButtonAddPeriodItemListener(pos: Int)
    }
}
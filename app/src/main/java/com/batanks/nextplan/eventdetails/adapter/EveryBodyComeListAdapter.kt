package com.batanks.nextplan.eventdetails.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.swagger.model.EventInvitation
import kotlinx.android.synthetic.main.layout_contact.view.*

class EveryBodyComeListAdapter (val contactList : ArrayList<EventInvitation>, val context: Context): RecyclerView.Adapter<EveryBodyComeListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_contact, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {

        return contactList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val contactStatus : ImageView = itemView.contactStatus
        val contactImage : ImageView = itemView.contactImage
        val contactName : TextView = itemView.contactName
        val contactSettings : ImageView = itemView.contactSettings
    }
}
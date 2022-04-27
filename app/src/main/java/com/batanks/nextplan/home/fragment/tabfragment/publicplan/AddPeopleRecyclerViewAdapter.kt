package com.batanks.nextplan.home.fragment.tabfragment.publicplan

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.swagger.model.ActivityParticipant
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_contact.view.*

class AddPeopleRecyclerViewAdapter (val eventParticipantsList: ArrayList<String>, private val callBack: AddPeopleRecyclerViewCallBack) : RecyclerView.Adapter<AddPeopleRecyclerViewAdapter.ViewHolder>() {

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        context = parent.context

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_contact, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = eventParticipantsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //val contact: ActivityParticipant = eventParticipantsList[position]
        val contact = eventParticipantsList[position]

        holder.contactName.text = contact
        holder.contactStatus.setBackgroundResource(R.drawable.ic_event_details_contact_confirmed)
        holder.close.setImageResource(R.drawable.ic_close_button)
        //context?.let { Glide.with(it).load(contact.picture).circleCrop().into(holder.contactStatus) }

        holder.close.setOnClickListener {

            callBack.closeButtonItemListener(position)
            //eventParticipantsList[position].selection = false
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val contactStatus : ImageView = itemView.contactStatus
        val contactName: TextView = itemView.contactName
        val close: ImageView = itemView.close

    }

    interface AddPeopleRecyclerViewCallBack {
        fun closeButtonItemListener(pos: Int)
    }
}
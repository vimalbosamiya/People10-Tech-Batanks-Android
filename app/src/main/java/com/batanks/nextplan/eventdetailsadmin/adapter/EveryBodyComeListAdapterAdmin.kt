package com.batanks.nextplan.eventdetailsadmin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.eventdetailsadmin.viewmodel.EventDetailViewModelAdmin
import com.batanks.nextplan.swagger.model.ContactsList
import com.batanks.nextplan.swagger.model.EventDate
import kotlinx.android.synthetic.main.layout_contact.view.*

class EveryBodyComeListAdapterAdmin (val contactsList: ArrayList<String>, val context: Context,
                                     private val eventDetailViewModelAdmin: EventDetailViewModelAdmin,
                                     private val callBack: AddPeopleRecyclerViewCallBack): RecyclerView.Adapter<EveryBodyComeListAdapterAdmin.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_contact, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = contactsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val contact : String = contactsList[position]

        holder.contactName.text =  contact

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val contactName : TextView = itemView.contactName

    }

    interface AddPeopleRecyclerViewCallBack {
        fun closeButtonAddPeriodItemListener(pos: Int)
    }
}
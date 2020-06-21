package com.batanks.nextplan.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.home.fragment.contacts.ContactsModel
import kotlinx.android.synthetic.main.layout_settings_contacts_item.view.*

class SearchContactsAdapter (private val contactsList: List<ContactsModel>) : RecyclerView.Adapter<SearchContactsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_settings_contacts_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = contactsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val contact = contactsList[position]

        holder.txt_contact_list_item.text = contact.contactname


    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        //val contactName: TextView = item.txt_contact_name
        val txt_contact_list_item : TextView = itemView.txt_contact_list_item
    }
}
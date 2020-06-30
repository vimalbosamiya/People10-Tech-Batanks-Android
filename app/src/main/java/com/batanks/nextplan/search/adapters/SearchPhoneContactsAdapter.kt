package com.batanks.nextplan.search.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.home.fragment.contacts.ContactsModel
import kotlinx.android.synthetic.main.contact_item.view.*

class SearchPhoneContactsAdapter (private val phoneContactsList: List<ContactsModel>) : RecyclerView.Adapter<SearchPhoneContactsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount() = phoneContactsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val phContact = phoneContactsList[position]

        holder.txt_contact_name.text = phContact.contactname

    }

    class ViewHolder(itemVIew: View) : RecyclerView.ViewHolder(itemVIew) {

        val txt_contact_name : TextView = itemVIew.txt_contact_name
    }
}
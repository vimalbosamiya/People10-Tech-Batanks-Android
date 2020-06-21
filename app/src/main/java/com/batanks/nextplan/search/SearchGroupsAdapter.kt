package com.batanks.nextplan.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.home.fragment.contacts.ContactsModel
import kotlinx.android.synthetic.main.layout_settings_groups_item.view.*


class SearchGroupsAdapter (private val groupsList: List<ContactsModel>) : RecyclerView.Adapter<SearchGroupsAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_settings_groups_item, parent, false)

        return ViewHolder(view)

    }

    override fun getItemCount() = groupsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    val group = groupsList[position]

        holder.txt_groups_list_item.text = group.contactname

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txt_groups_list_item : TextView = itemView.txt_groups_list_item
    }
}
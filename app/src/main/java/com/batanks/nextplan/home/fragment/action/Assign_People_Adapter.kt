package com.batanks.nextplan.home.fragment.action

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.home.fragment.contacts.ContactsModel
import kotlinx.android.synthetic.main.contact_item.view.*
import kotlinx.android.synthetic.main.item_assign_people.view.*

class Assign_People_Adapter (private val myList: List<ContactsModel>) : RecyclerView.Adapter<Assign_People_Adapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_assign_people, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = myList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        //val itemView = holder.itemView

        holder.txt_assign_people_name.text = myList.get(position).contactname
    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val txt_assign_people_name: TextView = item.txt_assign_people_name
    }
}
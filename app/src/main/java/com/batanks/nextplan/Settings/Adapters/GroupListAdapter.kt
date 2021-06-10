package com.batanks.nextplan.Settings.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.home.viewmodel.ContactsViewModel
import com.batanks.nextplan.swagger.model.Contact
import com.batanks.nextplan.swagger.model.ContactsList
import kotlinx.android.synthetic.main.contacts_in_group_item.view.*


public class GroupListAdapter (private val myList: ArrayList<ContactsList>, val closeButtonVisible : Boolean, val contactsViewModel: ContactsViewModel) : RecyclerView.Adapter<GroupListAdapter.MyViewHolder>() {

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.contacts_in_group_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount()= myList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.contactName.text = myList[position].username

        if (closeButtonVisible == false){

            holder.img_close.visibility = View.GONE

        }else if(closeButtonVisible == true) {

            holder.img_close.visibility = View.VISIBLE

        }

        holder.img_close.setOnClickListener {

            contactsViewModel.deleteContact(myList[position].id.toString())
        }

    }

     class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val contactName: TextView = item.txt_contact_list_item
        val img_close : ImageView = item.img_close
    }
}
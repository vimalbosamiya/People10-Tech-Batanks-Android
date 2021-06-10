package com.batanks.nextplan.home.fragment.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.swagger.model.ActivityParticipants
/*import com.batanks.nextplan.home.adapter.HomePlanPreviewAdapter*/
import com.batanks.nextplan.swagger.model.ContactsList
import com.batanks.nextplan.swagger.model.PhoneContacts
import com.google.android.material.checkbox.MaterialCheckBox
import kotlinx.android.synthetic.main.contact_item.view.*
import kotlinx.android.synthetic.main.item_event_name.view.*

class ContactsAdapter (private val listner : contactsAdapterRecyclerViewCallBack, private val myList: ArrayList</*ContactsModel*/ PhoneContacts>) : RecyclerView.Adapter<ContactsAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount() = myList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        //val itemView = holder.itemView

        holder.contactName.text = myList.get(position).username

        if (myList.get(position).selection){

            holder.cb_select_contact.isChecked = true

        } else {

            holder.cb_select_contact.isChecked = false
        }

        holder.cb_select_contact.setOnClickListener {

            if (holder.cb_select_contact.isChecked == false){

                myList[position].selection = false
                listner.contactUnchecked(myList)

            } else {

                myList[position].selection = true
                listner.contactUnchecked(myList)
            }
        }
    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val contactName: TextView = item.txt_contact_name
        val cb_select_contact: MaterialCheckBox = item.cb_select_contact
    }

    interface contactsAdapterRecyclerViewCallBack {
        //fun addSelectedPhoneContacts(contacts : ArrayList<ActivityParticipants>)
        //fun contactUnchecked(isUnchecked : Boolean)
        fun contactUnchecked(list: ArrayList<PhoneContacts>)
    }
}
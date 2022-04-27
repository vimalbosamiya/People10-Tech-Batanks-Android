package com.batanks.nextplan.home.fragment.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.swagger.model.ActivityParticipants
import com.batanks.nextplan.swagger.model.ContactsList
import com.google.android.material.checkbox.MaterialCheckBox
import kotlinx.android.synthetic.main.contact_item.view.*

class FriendsAdapter (private val listner : friendsAdapterRecyclerViewCallBack, private val myList: ArrayList<ContactsList>/*, private val participants : ArrayList<ActivityParticipants>*/) : RecyclerView.Adapter<FriendsAdapter.MyViewHolder>() {

    //var participantsFriends : ArrayList<ActivityParticipants> = arrayListOf()
    var participants : ArrayList<ContactsList> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = myList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.contactName.text = myList.get(position).username

        if (myList[position].selection){

            holder.cb_select_contact.isChecked = true
            participants.add(myList[position])
            listner.addSelectedContactsFriends(participants)

        } else {

            holder.cb_select_contact.isChecked = false
        }

        holder.cb_select_contact.setOnClickListener{

            if (holder.cb_select_contact.isChecked == true){

                myList[position].selection = true

                participants.add(myList[position])

                listner.addSelectedContactsFriends(participants)
                listner.addSelectedFriends(participants)

            } else if (holder.cb_select_contact.isChecked == false) {

                myList[position].selection = false

                val iterator = participants.iterator()

                if (participants.size > 0) {

                    while(iterator.hasNext()){

                        val item = iterator.next()

                        if (item.username == myList[position].username && item.id == myList[position].id){

                            iterator.remove()
                        }
                    }
                }

                listner.addSelectedContactsFriends(participants)
                listner.addSelectedFriends(participants)
            }
        }
    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val contactName: TextView = item.txt_contact_name
        val cb_select_contact : MaterialCheckBox = item.cb_select_contact
    }

    interface friendsAdapterRecyclerViewCallBack {
        fun addSelectedContactsFriends(contacts : ArrayList<ContactsList>)
        fun addSelectedFriends(friends : ArrayList<ContactsList>)
    }
}
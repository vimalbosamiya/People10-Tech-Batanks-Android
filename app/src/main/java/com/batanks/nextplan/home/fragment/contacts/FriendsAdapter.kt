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

class FriendsAdapter (private val listner : friendsAdapterRecyclerViewCallBack, private val myList: ArrayList<ContactsList>/*, private val participants : */) : RecyclerView.Adapter<FriendsAdapter.MyViewHolder>() {

    var participantsFriends : ArrayList<ActivityParticipants> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = myList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.contactName.text = myList.get(position).username

        if (myList[position].selection){

            holder.cb_select_contact.isChecked = true

        } else {

            holder.cb_select_contact.isChecked = false
        }

        holder.cb_select_contact.setOnClickListener{

            if (holder.cb_select_contact.isChecked == true){

                myList[position].selection = true

                participantsFriends.add(ActivityParticipants(myList[position].username,myList[position].id))

                /*if (participantsFriends.size > 0){

                    while(iterator.hasNext()){

                        val item = iterator.next()

                        if (item.participantName != myList[position].username && item.id != myList[position].id){

                            participantsFriends.add(ActivityParticipants(myList[position].username,myList[position].id))
                        }
                    }

                    *//*for (item in participantsFriends){

                        if (item.participantName != myList[position].username && item.id != myList[position].id){

                            participantsFriends.add(ActivityParticipants(myList[position].username,myList[position].id))
                        }
                    }*//*
                } else {

                    participantsFriends.add(ActivityParticipants(myList[position].username,myList[position].id))
                }*/

                listner.addSelectedContactsFriends(participantsFriends)

            } else if (holder.cb_select_contact.isChecked == false) {

                myList[position].selection = false

                val iterator = participantsFriends.iterator()

                if (participantsFriends.size > 0) {

                    while(iterator.hasNext()){

                        val item = iterator.next()

                        if (item.participantName == myList[position].username && item.id == myList[position].id){

                            iterator.remove()
                        }
                    }

                   /* for (item in participantsFriends) {

                        if (item.participantName != myList[position].username && item.id != myList[position].id){

                            participantsFriends.remove(ActivityParticipants(myList[position].username,myList[position].id))
                        }
                    }*/
                }

                listner.addSelectedContactsFriends(participantsFriends)
            }
        }

        /*holder.cb_select_contact.setOnClickListener {

            *//*if (holder.cb_select_contact.isChecked){

                participantsFriends.add(ActivityParticipants(myList[position].username,myList[position].id))

                if (!AddContactsFragment.participants.contains(ActivityParticipants(myList[position].username,myList[position].id))){

                    AddContactsFragment.participants.add(ActivityParticipants(myList[position].username,myList[position].id))
                }
            }

            listner.addSelectedContactsFriends(participantsFriends) *//*


        }*/
    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val contactName: TextView = item.txt_contact_name
        val cb_select_contact : MaterialCheckBox = item.cb_select_contact
    }

    interface friendsAdapterRecyclerViewCallBack {
        fun addSelectedContactsFriends(contacts : ArrayList<ActivityParticipants>)
    }
}
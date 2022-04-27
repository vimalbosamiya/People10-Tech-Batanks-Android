package com.batanks.nextplan.home.fragment.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.search.viewmodel.SearchViewModel
import com.batanks.nextplan.swagger.model.ContactsList
import com.google.android.material.checkbox.MaterialCheckBox
import kotlinx.android.synthetic.main.contact_item.view.*

class UsersAdapter (private val listner : usersAdapterRecyclerViewCallBack, private val myList: ArrayList<ContactsList>, private val searchViewModel: SearchViewModel) : RecyclerView.Adapter<UsersAdapter.MyViewHolder>() {

    //var participantsUsers : ArrayList<ContactsList> = arrayListOf()
    //var participants : ArrayList<ContactsList> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = myList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.contactName.text = myList[position].username

        if (myList[position].selection){

            holder.cb_select_contact.isChecked = true
            searchViewModel.participantsUsers.add(myList[position])
            listner.addSelectedContactsUsers(searchViewModel.participantsUsers)

        } else {

            holder.cb_select_contact.isChecked = false
        }

        holder.cb_select_contact.setOnClickListener{

            if (holder.cb_select_contact.isChecked == true){

                myList[position].selection = true

                searchViewModel.participantsUsers.add(myList[position])

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

                listner.addSelectedContactsUsers(searchViewModel.participantsUsers)

            } else if (holder.cb_select_contact.isChecked == false) {

                myList[position].selection = false

                val iterator = searchViewModel.participantsUsers.iterator()

                if (searchViewModel.participantsUsers.size > 0) {

                    while(iterator.hasNext()){

                        val item = iterator.next()

                        if (item.username == myList[position].username && item.id == myList[position].id){

                            iterator.remove()
                        }
                    }

                    /* for (item in participantsFriends) {

                         if (item.participantName != myList[position].username && item.id != myList[position].id){

                             participantsFriends.remove(ActivityParticipants(myList[position].username,myList[position].id))
                         }
                     }*/
                }

                listner.addSelectedContactsUsers(searchViewModel.participantsUsers)
            }
        }

        /*holder.cb_select_contact.setOnClickListener {

            if (holder.cb_select_contact.isChecked){

                *//*if (!AddContactsFragment.participants.contains(ActivityParticipants(myList[position].username,myList[position].id))){

                    AddContactsFragment.participants.add(ActivityParticipants(myList[position].username,myList[position].id))
                }*//*

                participantsUsers.add(ActivityParticipants(myList[position].username,myList[position].id))
            }

            listner.addSelectedContactsUsers(participantsUsers)
        }*/
    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val contactName: TextView = item.txt_contact_name
        val cb_select_contact : MaterialCheckBox = item.cb_select_contact
    }

    interface usersAdapterRecyclerViewCallBack {
        fun addSelectedContactsUsers(contacts : ArrayList<ContactsList>)
    }
}
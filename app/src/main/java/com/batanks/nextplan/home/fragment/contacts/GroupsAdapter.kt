package com.batanks.nextplan.home.fragment.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.swagger.model.ActivityParticipants
import com.batanks.nextplan.swagger.model.Group
import com.google.android.material.checkbox.MaterialCheckBox
import kotlinx.android.synthetic.main.contact_item.view.*

class GroupsAdapter (private val listner : groupsAdapterRecyclerViewCallBack,private val myList: List<Group>) : RecyclerView.Adapter<GroupsAdapter.MyViewHolder>() {

    var participantsGroups : ArrayList<Group> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = myList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.contactName.text = myList.get(position).name

        if (myList[position].selection){

            holder.cb_select_contact.isChecked = true

        } else {

            holder.cb_select_contact.isChecked = false
        }

        holder.cb_select_contact.setOnClickListener{

            if (holder.cb_select_contact.isChecked == true){

                myList[position].selection = true

                participantsGroups.add(Group(myList[position].id, myList[position].users,myList[position].name, myList[position].selection))

                /*for (item in myList[position].users){

                    participantsGroups.add(ActivityParticipants(item.first_name,item.id))
                }*/

                //participantsGroups.add(ActivityParticipants(myList[position].name,myList[position].id))

                listner.addSelectedContactsGroups(participantsGroups)

            } else if (holder.cb_select_contact.isChecked == false) {

                myList[position].selection = false

                val iterator = participantsGroups.iterator()

                if (participantsGroups.size > 0) {

                    while(iterator.hasNext()){

                        val item = iterator.next()

                        if (item.name == myList[position].name && item.id == myList[position].id){

                            iterator.remove()
                        }
                    }
                }

                listner.addSelectedContactsGroups(participantsGroups)
            }
        }

        /*holder.cb_select_contact.setOnClickListener {

            if (holder.cb_select_contact.isChecked){

                for (item in myList[position].users){

                    participantsGroups.add(ActivityParticipants(item.first_name,item.id))

                    *//*if (!AddContactsFragment.participants.contains(ActivityParticipants(item.first_name,item.id))){

                        AddContactsFragment.participants.add(ActivityParticipants(item.first_name,item.id))

                    }*//*
                }
            }

            listner.addSelectedContactsGroups(participantsGroups)
        }*/
    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val contactName: TextView = item.txt_contact_name
        val cb_select_contact: MaterialCheckBox = item.cb_select_contact
    }

    interface groupsAdapterRecyclerViewCallBack {
        fun addSelectedContactsGroups(contacts : ArrayList<Group>)
    }
}
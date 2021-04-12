package com.batanks.nextplan.home.fragment.tabfragment.publicplan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.swagger.model.ActivityParticipant
import com.batanks.nextplan.swagger.model.Guests
import kotlinx.android.synthetic.main.item_assign_people.view.*

class AssignActivityParticipantsAdapter(private val listner : assignPeopleActivityRecyclerViewCallBack, private val myList: ArrayList<Guests>,
                                        private var defaultSelectedGuests : ArrayList<ActivityParticipant>?) : RecyclerView.Adapter<AssignActivityParticipantsAdapter.MyViewHolder>() {

    private val selectedActivityParticipants : ArrayList<Guests> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_assign_people, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = myList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.txt_assign_people_name.text = myList.get(position).name

        if (myList[position].selection){

            holder.checkBox.isChecked = true

        } else {

            holder.checkBox.isChecked = false
        }

        if (defaultSelectedGuests != null){

            for (item in defaultSelectedGuests!!){

                if (item.id == myList[position].user_id){

                    myList[position].selection = true

                    holder.checkBox.isChecked = true

                    println("Box which is checked is : " + myList[position].name)

                    if (!selectedActivityParticipants.contains(myList[position])){

                        selectedActivityParticipants.add(myList[position])
                    }

                    listner.assignSelectedContactsActivity(selectedActivityParticipants)

                } else {

                    myList[position].selection = false

                    holder.checkBox.isChecked = false
                }
            }
        }

        holder.checkBox.setOnClickListener{

            if (holder.checkBox.isChecked){

                myList[position].selection = true

                if (!selectedActivityParticipants.contains(myList[position])){

                    selectedActivityParticipants.add(myList[position])
                }

                listner.assignSelectedContactsActivity(selectedActivityParticipants)

            }else {

                myList[position].selection = false

                selectedActivityParticipants.remove(myList[position])

                listner.assignSelectedContactsActivity(selectedActivityParticipants)
            }
        }
    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val txt_assign_people_name: TextView = item.txt_assign_people_name
        val checkBox : CheckBox = item.cb_assign_contact
    }

    interface assignPeopleActivityRecyclerViewCallBack {
        fun assignSelectedContactsActivity(selection : ArrayList<Guests>?)
    }
}
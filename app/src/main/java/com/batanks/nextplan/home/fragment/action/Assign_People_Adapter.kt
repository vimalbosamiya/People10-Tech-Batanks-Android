package com.batanks.nextplan.home.fragment.action

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.home.fragment.contacts.ContactsModel
import com.batanks.nextplan.swagger.model.ContactsList
import com.batanks.nextplan.swagger.model.Guests
import kotlinx.android.synthetic.main.item_assign_people.view.*


class Assign_People_Adapter (private val listner : assignPeopleRecyclerViewCallBack , private val myList: ArrayList<Guests>) : RecyclerView.Adapter<Assign_People_Adapter.MyViewHolder>() {

    private var lastChecked: CheckBox? = null
    private var lastCheckedPos = -1
    private var currentChecked: CheckBox? = null
    private var lastCheckedPosition = 0
    private var currentCheckedPosition = 0

    private var positionArray : ArrayList<Int> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_assign_people, parent, false)

        for(item in myList){

            positionArray.add(0)
        }

        return MyViewHolder(view)
    }

    override fun getItemCount() = myList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        //val itemView = holder.itemView

        holder.txt_assign_people_name.text = myList.get(position).name
        //println(myList.get(position).user_id)

        //myList.get(position).id

        //holder.checkBox.setChecked(myList.get(position).selection);
        //holder.checkBox.tag = position
        //holder.checkBox.setTag(new Integer(position));

        //for default check in first item
       /* if(position == 0 && myList.get(0).selection && holder.checkBox.isChecked()) {
            lastChecked = holder.checkBox;
            lastCheckedPos = 0;
        }*/



        if (holder.checkBox.isChecked){

            lastChecked = holder.checkBox
            lastCheckedPos = position
        }


        holder.checkBox.setOnClickListener { v ->
            val cb = v as CheckBox
            val clickedPos = /*(cb.tag as Int).toInt()*/ position

            /*if (cb.isChecked){
                if (lastChecked != null){

                    lastChecked!!.isChecked = false
                }
                currentChecked = cb
                currentCheckedPosition = (cb.tag as Int).toInt()
            }*/

            if (cb.isChecked) {
                if (lastChecked != null) {
                    lastChecked!!.isChecked = false
                    myList.get(lastCheckedPos)?.selection = false
                }
                lastChecked = cb
                lastCheckedPos = clickedPos

                myList.get(clickedPos).selection = (cb.isChecked)
                //Toast.makeText(v.context , "" + myList.get(clickedPos).contactname , Toast.LENGTH_SHORT).show()
                val selection = myList.get(clickedPos).name /*+ " " + myList.get(clickedPos).contactNumber;*/
                val selectedId = myList.get(position).user_id
                println(selectedId)
                listner.assignSelectedContact(myList.get(clickedPos), cb.isChecked, selectedId)

            }

            else{

                lastChecked = null
                listner.assignSelectedContact(null, cb.isChecked, null)
            }

            /*myList.get(clickedPos).selection = (cb.isChecked)
            //Toast.makeText(v.context , "" + myList.get(clickedPos).contactname , Toast.LENGTH_SHORT).show()
            val selection = myList.get(clickedPos).first_name *//*+ " " + myList.get(clickedPos).contactNumber;*//*
            val selectedId = myList.get(position).id
            listner.assignSelectedContact(selection, cb.isChecked, selectedId)*/
        }

       /* holder.checkBox.setOnClickListener {

            val clickedPos = (it.tag as Int).toInt()

            val selection = myList.get(clickedPos).first_name

            listner.assignSelectedContact(selection*//*, cb.isChecked*//*)
        }*/
    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val txt_assign_people_name: TextView = item.txt_assign_people_name
        val checkBox : CheckBox = item.cb_assign_contact
    }

    interface assignPeopleRecyclerViewCallBack {
        fun assignSelectedContact(selection : Guests?, selected : Boolean, id : Int?)
    }
}
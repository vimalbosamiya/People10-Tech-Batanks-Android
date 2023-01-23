package com.batanks.nextplan.home.fragment.action

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.home.fragment.contacts.ContactsModel
import com.batanks.nextplan.swagger.model.ContactsList
import com.batanks.nextplan.swagger.model.Guests
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_assign_people.view.*


class Assign_People_Adapter (private val listner : assignPeopleRecyclerViewCallBack , private val myList: ArrayList<Guests>, private var defaultSelectedAssigne : String?,private var context: Context) : RecyclerView.Adapter<Assign_People_Adapter.MyViewHolder>() {

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
        if (myList[position].status?.equals("AC") == true) {
            holder.txt_assign_people_name.text = myList[position].user.username
            Glide.with(context).load(myList[position].user.picture).circleCrop().into(holder.userImage)
            holder.userImageStatus.background = context.resources.getDrawable(R.drawable.ic_user_accepted)
        }else{
            holder.assignLayout.visibility = View.GONE
        }

        if (myList.get(position).user.username == defaultSelectedAssigne){

            holder.checkBox.isChecked = true
        }
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
                val selection = myList.get(clickedPos).user.username /*+ " " + myList.get(clickedPos).contactNumber;*/
                val selectedId = myList.get(position).user.id
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
        val userImageStatus:ImageView = item.img_assing_people_item
        val assignLayout:RelativeLayout =item.assign_layout
        val userImage:ImageView = item.userImage
        val checkBox : CheckBox = item.cb_assign_contact
    }

    interface assignPeopleRecyclerViewCallBack {
        fun assignSelectedContact(selection : Guests?, selected : Boolean, id : Int?)
    }
}
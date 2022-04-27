package com.batanks.nextplan.home.fragment.action

import android.app.Application
import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.eventdetailsadmin.adapter.ActivityEverybodyComeListAdapterAdmin
import com.batanks.nextplan.swagger.model.Event
import com.batanks.nextplan.swagger.model.PostTasks
import com.batanks.nextplan.swagger.model.Task
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_add_action_card.view.*

class AddActionRecyclerView (private val callBack: AddActionRecyclerViewCallBack, private val modelList: ArrayList<PostTasks>, private val editButtonClicked : Boolean,
                             private val deleteButtonClicked : Boolean, private val event : Event?) : RecyclerView.Adapter<AddActionRecyclerView.MyViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.fragment_add_action_card, parent, false)

        context = parent.context
        return MyViewHolder(view)
    }

    override fun getItemCount() = modelList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.actioncloseButtonIcon.setOnClickListener {
            //modelList.removeAt(position)
            callBack.closeButtonAddActionItemListener(position)
        }

        if(editButtonClicked == true){

            if (modelList != null) {

                if (modelList.size > 0){

                    if (modelList.get(position)?.assignee != null){

                        holder.contactBackground.visibility = View.VISIBLE
                        //holder.userImage.visibility = View.GONE

                        holder.contactName.setText(modelList.get(position).assigneeName)
                        //holder.contactName.setText(modelList[position].assigneeName)
                        //Glide.with(context).load(event!!.tasks?.get(position)?.assignee?.picture).circleCrop().into(holder.contactImage)

                    } else if (modelList.get(position).assignee == null) {

                        holder.contactBackground.visibility = View.GONE

                    }

                } else {}

            }else {}

        } else{}

        var costTitle : String? = null

        if (modelList[position].per_person == true){

            costTitle = "Cost Per Person"
            holder.txt_add_action_cost_title.setText(R.string.cost_per_person)
            holder.img_add_action_cost.setImageResource(R.drawable.ic_cost_perperson_icon)

        } else if (modelList[position].per_person == false) {

            costTitle = "Total Cost"
            holder.txt_add_action_cost_title.setText(R.string.total_cost)
            holder.img_add_action_cost.setImageResource(R.drawable.ic_action_cost_icon)}

            holder.txt_actionname.text = modelList[position].name

        if (!TextUtils.isEmpty(modelList[position].description)){

            holder.txt_description.visibility = View.VISIBLE
            holder.txt_description.text = modelList[position].description

        } else { holder.txt_description.visibility = View.GONE }

        if (modelList[position].price.isNotEmpty()) {
            val price = modelList[position].price.substringBefore(".")
            holder.txt_add_action_cost_value.text = price
                //if (modelList[position].price.toDouble() > 0){holder.txt_add_action_cost_value.text = String.format("%,d",modelList[position].price)}
        }else{ holder.txt_add_action_cost_value.text = "0" }

        val id: Int = context?.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE)?.getInt("ID", 0)!!
        val currency : String? = context?.getSharedPreferences("SAVED_CURREN", AppCompatActivity.MODE_PRIVATE)?.getString("SAVED_CURRENCY","USD")

        holder.txt_add_action_cost_symbol.text = currency

        if (editButtonClicked == true){

            holder.actionEditButtonIcon.visibility = View.VISIBLE
            holder.actioncloseButtonIcon.visibility = View.GONE

        }else if (deleteButtonClicked == true) {

            holder.actionEditButtonIcon.visibility = View.GONE
            holder.actioncloseButtonIcon.visibility = View.VISIBLE

        } else {}

        holder.actionEditButtonIcon.setOnClickListener { callBack.editButtonAddActionItemListener(position) }



        //holder.txt_add_action_assignee_name.text = modelList[position].assigneeName

        /*if (!TextUtils.isEmpty(modelList[position].assigneeName)){

            holder.rl_add_action_assignee.visibility = View.VISIBLE

        } else {

            holder.rl_add_action_assignee.visibility = View.GONE
        }*/

        /*if (holder.txt_add_action_assignee_name.text == null){

            holder.rl_add_action_assignee.visibility = View.GONE

            println(modelList[position].assignee)
        } else {

            holder.rl_add_action_assignee.visibility = View.VISIBLE
        }*/

        //holder.placeAddress.text = modelList[position].address

    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val txt_actionname: TextView = item.txt_actionname
        val txt_description :TextView = item.txt_description
        val txt_add_action_cost_value : TextView = item.txt_add_action_cost_value
        val txt_add_action_cost_symbol : TextView = item.txt_add_action_cost_symbol
        val txt_add_action_cost_title :TextView = item.txt_add_action_cost_title
        val txt_add_action_assignee_name : TextView = item.contactName
        val contactName : TextView = item.contactName
        val contactBackground : ConstraintLayout = item .contactBackground
        //val placeAddress: TextView = item.placeAddressTextView
        val actioncloseButtonIcon: ImageView = item.actioncloseButtonIcon
        val actionEditButtonIcon: ImageView = item.actionEditButtonIcon
        val img_add_action_cost: ImageView = item.img_add_action_cost
        val contactImage: ImageView = item.contactImage
        val userImage: ImageView = item.userImage

    }

    interface AddActionRecyclerViewCallBack {
        fun closeButtonAddActionItemListener(pos: Int)
        fun editButtonAddActionItemListener(pos: Int)
    }
}
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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.swagger.model.PostTasks
import com.batanks.nextplan.swagger.model.Task
import kotlinx.android.synthetic.main.fragment_add_action_card.view.*

class AddActionRecyclerView (private val callBack: AddActionRecyclerViewCallBack, private val modelList: ArrayList<PostTasks>) :
        RecyclerView.Adapter<AddActionRecyclerView.MyViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.fragment_add_action_card, parent, false)

        context = parent.context
        return MyViewHolder(view)
    }

    override fun getItemCount() = modelList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.closeButton.setOnClickListener {
            modelList.removeAt(position)
            callBack.closeButtonAddActionItemListener(position)
        }

        var costTitle : String? = null

        if (modelList[position].per_person == true){

            costTitle = "Cost Per Person"

        } else if (modelList[position].per_person == false) {

            costTitle = "Total Cost"
        }

        holder.txt_actionname.text = modelList[position].name

        if (!TextUtils.isEmpty(modelList[position].description)){

            holder.txt_description.visibility = View.VISIBLE
            holder.txt_description.text = modelList[position].description

        } else {

            holder.txt_description.visibility = View.GONE
        }


        //if (!TextUtils.isEmpty(modelList[position].price)){
        if (modelList[position].price > 0){

            holder.txt_add_action_cost_value.text = modelList[position].price.toString()
        }

        val id: Int = context?.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE)?.getInt("ID", 0)!!

        holder.txt_add_action_cost_title.text = costTitle
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
        val rl_add_action_assignee : ConstraintLayout = item .contactBackground
        //val placeAddress: TextView = item.placeAddressTextView
        val closeButton: ImageView = item.actioncloseButtonIcon
    }

    interface AddActionRecyclerViewCallBack {
        fun closeButtonAddActionItemListener(pos: Int)
    }
}
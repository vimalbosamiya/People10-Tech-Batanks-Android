package com.batanks.nextplan.home.fragment.action

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.swagger.model.Task
import kotlinx.android.synthetic.main.fragment_add_action_card.view.*

class AddActionRecyclerView (private val callBack: AddActionRecyclerViewCallBack, private val modelList: ArrayList<Task>) :
        RecyclerView.Adapter<AddActionRecyclerView.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.fragment_add_action_card, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = modelList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.closeButton.setOnClickListener {
            modelList.removeAt(position)
            callBack.closeButtonAddActionItemListener(position)
        }
        holder.txt_actionname.text = modelList[position].name
        holder.txt_description.text = modelList[position].description
        holder.txt_add_action_cost_value.text = modelList[position].price_currency
        holder.txt_add_action_cost_title.text = modelList[position].price
        holder.txt_add_action_assignee_name.text = modelList[position].assignee

        if (holder.txt_add_action_assignee_name.text == null){

            holder.rl_add_action_assignee.visibility = View.GONE

            println(modelList[position].assignee)
        } else {

            holder.rl_add_action_assignee.visibility = View.VISIBLE
        }

        //holder.placeAddress.text = modelList[position].address
    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val txt_actionname: TextView = item.txt_actionname
        val txt_description :TextView = item.txt_description
        val txt_add_action_cost_value : TextView = item.txt_add_action_cost_value
        val txt_add_action_cost_title :TextView = item.txt_add_action_cost_title
        val txt_add_action_assignee_name : TextView = item.txt_add_action_assignee_name
        val rl_add_action_assignee : ConstraintLayout = item .rl_add_action_assignee
        //val placeAddress: TextView = item.placeAddressTextView
        val closeButton: ImageView = item.actioncloseButtonIcon
    }

    interface AddActionRecyclerViewCallBack {
        fun closeButtonAddActionItemListener(pos: Int)
    }
}
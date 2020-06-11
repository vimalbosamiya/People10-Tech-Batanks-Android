package com.batanks.nextplan.eventdetailsadmin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.swagger.model.Task
import kotlinx.android.synthetic.main.layout_action_display.view.textViewEventName
import kotlinx.android.synthetic.main.layout_action_display.view.textViewTotalAmount
import kotlinx.android.synthetic.main.layout_action_display.view.totalCostBackground
import kotlinx.android.synthetic.main.layout_action_display.view.totalCostBackgroundFull
import kotlinx.android.synthetic.main.layout_action_display_admin.view.*

class EventActionListAdapterAdmin (val actionList : List<Task>, val context: Context): RecyclerView.Adapter<EventActionListAdapterAdmin.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_action_display_admin, parent, false)

        view.actionDropDown.setOnClickListener {

            view.totalCostBackground.visibility = View.GONE

            view.totalCostBackgroundFull.visibility = View.VISIBLE
        }

        view.actionDropDownMulti.setOnClickListener {

            view.totalCostBackgroundFull.visibility = View.GONE

            view.totalCostBackground.visibility = View.VISIBLE
        }

        return  ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return actionList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val action : Task = actionList[position]

        holder.textViewEventName.text = action.name
        holder.textViewEventNameMulti.text = action.name
        holder.textViewTotalAmount.text = action.price_currency
        holder.textViewTotalAmountMulti.text = action.price_currency
        holder.textViewEventDescription.text = action.description
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textViewEventName : TextView = itemView.textViewEventName
        val textViewEventNameMulti : TextView = itemView.textViewEventNameMulti
        val textViewTotalAmount : TextView = itemView.textViewTotalAmount
        val textViewTotalAmountMulti : TextView = itemView.textViewTotalAmountMulti
        val textViewEventDescription : TextView = itemView.textViewEventDescription
    }

    interface AddActionRecyclerViewCallBack {
        fun closeButtonAddActionItemListener(pos: Int)
    }
}
package com.batanks.nextplan.eventdetails.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.eventdetails.viewmodel.EventDetailViewModel
import com.batanks.nextplan.swagger.model.AsssignTask
import com.batanks.nextplan.swagger.model.Task
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_action_display.view.*

class EventActionListAdapter (val actionList : List<Task>, val context: Context, private val eventDetailViewModel: EventDetailViewModel,
                              private val eventId : Int) : RecyclerView.Adapter<EventActionListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_action_display, parent, false)

        view.actionDropDown.setOnClickListener {

            view.totalCostBackground.visibility = GONE

            view.totalCostBackgroundFull.visibility = VISIBLE
        }

        view.actionDropDownMulti.setOnClickListener {

            view.totalCostBackgroundFull.visibility = GONE

            view.totalCostBackground.visibility = VISIBLE
        }

        return  ViewHolder(view)
    }

    override fun getItemCount() = actionList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val action : Task = actionList[position]

        holder.contactSettings.visibility = View.GONE
        holder.textViewEventName.text = action.name
        holder.textViewEventNameMulti.text = action.name

        if (action.assignee != null){

            holder.contactBackground.visibility = View.VISIBLE
            holder.assignMeButton.visibility = View.GONE
            holder.contactName.text = action.assignee.username

            if (!action.assignee?.picture.isNullOrEmpty()){

                //Glide.with(context).load(action.assignee?.picture).circleCrop().into(holder.contactStatus)
                //holder.contactImage.visibility = GONE
                //holder.userImage.visibility = GONE
            }

        } else if (action.assignee == null){

            holder.contactBackground.visibility = View.GONE
            holder.assignMeButton.visibility = View.VISIBLE
        }

        if (action.per_person == true){

            holder.textViewTotalCost.setText(R.string.cost_per_person)
            holder.textViewTotalCostMulti.setText(R.string.cost_per_person)

        }else {

            holder.textViewTotalCost.setText(R.string.total_cost)
            holder.textViewTotalCostMulti.setText(R.string.total_cost)
        }

        holder.textViewTotalAmount.text = action.price.toString()
        holder.textViewTotalAmountMulti.text = action.price.toString()
        holder.textViewCurrencySymbol.text = action.price_currency
        holder.textViewTotalAmountSymbol.text = action.price_currency
        holder.textViewEventDescription.text = action.description

        holder.assignMeButton.setOnClickListener {

            eventDetailViewModel.assignAction(eventId.toString(), AsssignTask(action.id))
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textViewEventName : TextView = itemView.textViewEventName
        val textViewEventNameMulti : TextView = itemView.textViewEventNameMulti
        val textViewTotalCost : TextView = itemView.textViewTotalCost
        val textViewTotalAmount : TextView = itemView.textViewTotalAmount
        val textViewEventDescription : TextView = itemView.textViewEventDescription
        val contactName : TextView = itemView.contactName
        val textViewTotalCostMulti : TextView = itemView.textViewTotalCostMulti
        val textViewTotalAmountSymbol : TextView = itemView.textViewTotalAmountSymbol
        val textViewTotalAmountMulti : TextView = itemView.textViewTotalAmountMulti
        val textViewCurrencySymbol : TextView = itemView.textViewCurrencySymbol
        val contactStatus : ImageView = itemView.contactStatus
        val contactImage : ImageView = itemView.contactImage
        val userImage : ImageView = itemView.userImage
        val contactSettings : ImageView = itemView.contactSettings
        val contactBackground : ConstraintLayout = itemView.contactBackground
        val assignMeButton : ConstraintLayout = itemView.assignMeButton
    }
}
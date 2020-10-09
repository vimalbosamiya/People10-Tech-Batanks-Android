package com.batanks.nextplan.eventdetailsadmin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.eventdetails.viewmodel.EventDetailViewModel
import com.batanks.nextplan.eventdetailsadmin.viewmodel.EventDetailViewModelAdmin
import com.batanks.nextplan.home.fragment.period.AddPeriodRecyclerView
import com.batanks.nextplan.swagger.model.EventDate
import kotlinx.android.synthetic.main.layout_date_display.view.*

class VoteForDateMultipleListAdapterAdmin (val datesList: ArrayList<EventDate>, val context: Context,
                                           private val eventDetailViewModelAdmin: EventDetailViewModelAdmin,
                                           private val callBack: AddPeriodRecyclerViewCallBack): RecyclerView.Adapter<VoteForDateMultipleListAdapterAdmin.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_date_display, parent, false)

        return ViewHolder(view)



    }

    override fun getItemCount() = datesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemView = holder.itemView

        val date : EventDate = datesList[position]

        holder.close.setOnClickListener {

            datesList.forEach{

                it.visibility = false

            }
            //holder.close.visibility = GONE
            datesList.removeAt(position)
            callBack.closeButtonAddPeriodItemListener(position)
        }
        holder.dateDisplayCountTextView.text = date.id.toString()
        holder.dateTextView.text = date.start
        holder.fromTextview.text = date.start
        holder.toTextview.text = date.end
        holder.noOfVotesTextview.text = date.votes.size.toString()

        if (date.visibility){

            holder.close.visibility = VISIBLE
        }

        else{

            holder.close.visibility = GONE

        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val close : ImageView = itemView.closeButtonIcon
        val dateDisplayCountTextView : TextView = itemView.dateDisplayCountTextView
        val dateTextView : TextView = itemView.dateTextView
        val fromTextview : TextView = itemView.fromTextview
        val toTextview : TextView = itemView.toTextview
        val noOfVotesTextview : TextView = itemView.noOfVotesTextview
    }

    interface AddPeriodRecyclerViewCallBack {
        fun closeButtonAddPeriodItemListener(pos: Int)
    }

}
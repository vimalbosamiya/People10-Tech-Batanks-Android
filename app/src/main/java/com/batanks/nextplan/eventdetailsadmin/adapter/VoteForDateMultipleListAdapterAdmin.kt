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
import com.batanks.nextplan.swagger.model.VoteDate
import kotlinx.android.synthetic.main.layout_date_display.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class VoteForDateMultipleListAdapterAdmin (val datesList: ArrayList<EventDate>, val context: Context,
                                           private val eventDetailViewModel: EventDetailViewModel,
                                           private val callBack: AddPeriodRecyclerViewCallBack, private val eventId : String): RecyclerView.Adapter<VoteForDateMultipleListAdapterAdmin.ViewHolder>() {

    private var voteList : ArrayList<Int> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_date_display, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = datesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val date : EventDate = datesList[position]

        var endDate : Date?
        var formattedEndDate : String? = null

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss") /*"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"*/
        val outFormat = SimpleDateFormat("EEE, MMM d yyyy")
        val outputFormat = SimpleDateFormat("EEE, MMM d yyyy HH:mm")
        val startDate = inputFormat.parse(date?.start)
        val formattedStartDate = outFormat.format(startDate)
        val formattedFromDate = outputFormat.format(startDate)

        if (date.end != null){

            endDate = inputFormat.parse(date?.end)
            formattedEndDate = outputFormat.format(endDate)
        }

        holder.dateTextView.text = formattedStartDate
        holder.fromTextview.text = formattedFromDate
        holder.toTextview.text = formattedEndDate
        holder.dateDisplayCountTextView.text = date.number.toString()
        holder.dateDisplayTextCountTextView.text = date.string_value
        holder.noOfVotesTextview.text = date.total_votes.toString()

        holder.close.setOnClickListener {

            datesList.forEach{

                it.visibility = false

            }
            //holder.close.visibility = GONE
            datesList.removeAt(position)
            callBack.closeButtonAddPeriodItemListener(position)
        }

        if (date.visibility){

            holder.close.visibility = VISIBLE

        } else{

            holder.close.visibility = GONE

        }
        if (date.current_user_have_vote == true){

            holder.dateFavouriteIcon.setImageResource(R.drawable.ic_date_display_favourite)

        } else {

            holder.dateFavouriteIcon.setImageResource(R.drawable.ic_date_display_favourite_border)
        }


        holder.dateFavouriteIcon.setOnClickListener {

            voteList.add(date.id)

            eventDetailViewModel.dateVoteClicked(eventId, date.id.toString())
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val close : ImageView = itemView.closeButtonIcon
        val dateDisplayCountTextView : TextView = itemView.dateDisplayCountTextView
        val dateDisplayTextCountTextView : TextView = itemView.dateDisplayTextCountTextView
        val dateTextView : TextView = itemView.dateTextView
        val fromTextview : TextView = itemView.fromTextview
        val toTextview : TextView = itemView.toTextview
        val noOfVotesTextview : TextView = itemView.noOfVotesTextview
        val dateFavouriteIcon : ImageView = itemView.dateFavouriteIcon
    }

    interface AddPeriodRecyclerViewCallBack {
        fun closeButtonAddPeriodItemListener(pos: Int)
    }

}
package com.batanks.nextplan.eventdetails.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.response.ApiResponse
import com.batanks.nextplan.arch.response.Status
import com.batanks.nextplan.eventdetails.EventDetailView
import com.batanks.nextplan.eventdetails.dataclass.MultipleDateDisplay
import com.batanks.nextplan.eventdetails.viewmodel.EventDetailViewModel
import com.batanks.nextplan.swagger.api.EventAPI
import com.batanks.nextplan.swagger.model.EventDate
import com.batanks.nextplan.swagger.model.VoteDate
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.layout_date_display.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class VoteForDateMultipleListAdapter(val datesList: ArrayList<EventDate>, val context: Context, private val eventDetailViewModel: EventDetailViewModel,
                                     private val eventId : String) : RecyclerView.Adapter<VoteForDateMultipleListAdapter.ViewHolder>(), EventDetailView.VoteDateClickImplementation {

   /* private val disposables = CompositeDisposable()
    val responseLiveData: MutableLiveData<ApiResponse> = MutableLiveData()*/

    private var voteList : ArrayList<Int> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_date_display, parent, false)

        /*view.dateFavouriteIcon.setOnClickListener {

            Toast.makeText(context,"Fav Icon from date display clicked",Toast.LENGTH_SHORT).show()

            //eventDetailViewModel.dateVoteClicked()

            //voteIconClicked()
        }*/

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

        holder.dateDisplayCountTextView.text = date.number.toString()
        holder.dateDisplayTextCountTextView.text = date.string_value
        holder.dateTextView.text = formattedStartDate
        holder.fromTextview.text = formattedFromDate
        holder.toTextview.text = formattedEndDate
        holder.noOfVotesTextview.text = date.total_votes.toString()
        holder.closeButtonIcon.visibility = GONE

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

        val dateDisplayCountTextView : TextView = itemView.dateDisplayCountTextView
        val dateDisplayTextCountTextView : TextView = itemView.dateDisplayTextCountTextView
        val dateTextView : TextView = itemView.dateTextView
        val fromTextview : TextView = itemView.fromTextview
        val toTextview : TextView = itemView.toTextview
        val noOfVotesTextview : TextView = itemView.noOfVotesTextview
        val dateFavouriteIcon : ImageView = itemView.dateFavouriteIcon
        val closeButtonIcon : ImageView = itemView.closeButtonIcon
    }

    override fun voteIconClicked() {
    }


}
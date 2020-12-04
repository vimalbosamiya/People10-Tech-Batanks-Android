package com.batanks.nextplan.eventdetails.adapter

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
import com.batanks.nextplan.eventdetails.dataclass.MultipleDateDisplay
import com.batanks.nextplan.eventdetails.viewmodel.EventDetailViewModel
import com.batanks.nextplan.swagger.api.EventAPI
import com.batanks.nextplan.swagger.model.EventDate
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.layout_date_display.view.*


class VoteForDateMultipleListAdapter(val datesList: ArrayList<EventDate>, val context: Context, private val eventDetailViewModel: EventDetailViewModel): RecyclerView.Adapter<VoteForDateMultipleListAdapter.ViewHolder>(), VoteDateClickImplementation {

   /* private val disposables = CompositeDisposable()
    val responseLiveData: MutableLiveData<ApiResponse> = MutableLiveData()*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_date_display, parent, false)

        view.dateFavouriteIcon.setOnClickListener {

            Toast.makeText(context,"Fav Icon from date display clicked",Toast.LENGTH_SHORT).show()

            //eventDetailViewModel.dateVoteClicked()

            //voteIconClicked()
        }

        return ViewHolder(view)

    }

    override fun getItemCount(): Int {

        return datesList.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val date : EventDate = datesList[position]

        //holder.dateDisplayCountTextView.text = date.id.toString()
        //holder.dateDisplayTextCountTextView.text = dates.date
        holder.dateTextView.text = date.start
        holder.fromTextview.text = date.start
        holder.toTextview.text = date.end
        holder.noOfVotesTextview.text = date.votes.size.toString()
        holder.closeButtonIcon.visibility = GONE

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
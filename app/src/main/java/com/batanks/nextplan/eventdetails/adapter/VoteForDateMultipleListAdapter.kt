package com.batanks.nextplan.eventdetails.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.eventdetails.dataclass.MultipleDateDisplay
import kotlinx.android.synthetic.main.layout_date_display.view.*


class VoteForDateMultipleListAdapter(val datesList: ArrayList<MultipleDateDisplay>, val context: Context): RecyclerView.Adapter<VoteForDateMultipleListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_date_display, parent, false)

        return ViewHolder(view)

    }

    override fun getItemCount(): Int {

        return datesList.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val dates : MultipleDateDisplay = datesList[position]

        holder.dateDisplayCountTextView.text = dates.dateCount.toString()
        holder.dateDisplayTextCountTextView.text = dates.date
        holder.dateTextView.text = dates.fullDate
        holder.fromTextview.text = dates.fromDate
        holder.toTextview.text = dates.todate
        holder.noOfVotesTextview.text = dates.voteCount.toString()

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val dateDisplayCountTextView : TextView = itemView.dateDisplayCountTextView
        val dateDisplayTextCountTextView : TextView = itemView.dateDisplayTextCountTextView
        val dateTextView : TextView = itemView.dateTextView
        val fromTextview : TextView = itemView.fromTextview
        val toTextview : TextView = itemView.toTextview
        val noOfVotesTextview : TextView = itemView.noOfVotesTextview

    }
}
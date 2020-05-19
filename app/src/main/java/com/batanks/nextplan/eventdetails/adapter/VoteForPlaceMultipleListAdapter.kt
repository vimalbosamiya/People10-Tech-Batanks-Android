package com.batanks.nextplan.eventdetails.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.eventdetails.dataclass.MultipleDateDisplay
import com.batanks.nextplan.eventdetails.dataclass.MultiplePlaceDisplay
import kotlinx.android.synthetic.main.layout_date_display.view.*
import kotlinx.android.synthetic.main.layout_date_display.view.noOfVotesTextview
import kotlinx.android.synthetic.main.layout_place_display.view.*

class VoteForPlaceMultipleListAdapter (val placesList: ArrayList<MultiplePlaceDisplay>, val context: Context): RecyclerView.Adapter<VoteForPlaceMultipleListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_place_display, parent, false)

        return ViewHolder(view)

    }

    override fun getItemCount(): Int {

        return placesList.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val places : MultiplePlaceDisplay = placesList[position]

        holder.placeDisplayCountTextView.text = places.placeCount.toString()
        holder.placeDisplayTextCountTextView.text = places.place
        holder.placeTextView.text = places.placeName
        holder.address.text = places.fullAddress
        holder.noOfVotesPlaceTextview.text = places.voteCountPlace.toString()

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val placeDisplayCountTextView : TextView = itemView.placeDisplayCountTextView
        val placeDisplayTextCountTextView : TextView = itemView.placeDisplayTextCountTextView
        val placeTextView : TextView = itemView.placeTextView
        val address : TextView = itemView.address
        val noOfVotesPlaceTextview : TextView = itemView.noOfVotesPlaceTextview

    }


}
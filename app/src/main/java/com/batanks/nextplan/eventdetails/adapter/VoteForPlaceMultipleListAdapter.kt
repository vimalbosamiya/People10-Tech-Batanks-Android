package com.batanks.nextplan.eventdetails.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.eventdetails.viewmodel.EventDetailViewModel
import com.batanks.nextplan.swagger.model.EventPlace
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.layout_place_display.view.*

class VoteForPlaceMultipleListAdapter (val placesList: List<EventPlace>, val context: Context,private val eventDetailViewModel: EventDetailViewModel, private val eventId : String)
                                        : RecyclerView.Adapter<VoteForPlaceMultipleListAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_place_display, parent, false)

        view.seeOnMapIcon.setOnClickListener {

            view.seeOnMapLayout.visibility = View.GONE

            view.hideMapLayout.visibility = View.VISIBLE
        }

        view.hideMapIcon.setOnClickListener {

            view.hideMapLayout.visibility = View.GONE

            view.seeOnMapLayout.visibility = View.VISIBLE
        }

        return ViewHolder(view)
    }

    override fun getItemCount() = placesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val place : EventPlace = placesList[position]

        holder.placeDisplayCountTextView.text = place.number.toString()
        holder.placeDisplayTextCountTextView.text = place.string_value
        holder.placeTextView.text = place.place.name

        val stringBuilder = StringBuilder()
                .append(place.place?.address)
                .append("\n")
                .append(place.place?.zipcode)
                .append(" ")
                .append(place.place?.city)
                .append(" ")
                .append(place.place?.country)

        holder.address.text = stringBuilder.toString()
        holder.noOfVotesPlaceTextview.text = place.total_votes.toString()

        if (placesList.size > 1){

            holder.placeFavouriteIcon.visibility = View.VISIBLE
            holder.placePersonIcon.visibility = View.VISIBLE
            holder.noOfVotesPlaceTextview.visibility = View.VISIBLE

        }else {

            holder.placeFavouriteIcon.visibility = View.GONE
            holder.placePersonIcon.visibility = View.GONE
            holder.noOfVotesPlaceTextview.visibility = View.GONE
        }

        if (place.current_user_have_vote == true){

            holder.placeFavouriteIcon.setImageResource(R.drawable.ic_date_display_favourite)

        } else {

            holder.placeFavouriteIcon.setImageResource(R.drawable.ic_date_display_favourite_border)
        }

        holder.placeCloseButton.visibility = GONE

        if (place.place.map == true){

            holder.placeMapView.apply {

                onCreate(null)
                getMapAsync{

                    val LATLNG = LatLng(place.place.latitude,place.place.longitude)

                    with(it){

                        onResume()
                        moveCamera(CameraUpdateFactory.newLatLngZoom(LATLNG, 13f))
                        addMarker(MarkerOptions().position(LATLNG))
                    }
                }
            }

        }else {

            holder.mapViewHolder.visibility = GONE
        }

        holder.placeFavouriteIcon.setOnClickListener {

            eventDetailViewModel.placeVoteClicked(eventId, place.id.toString())
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val placeDisplayCountTextView : TextView = itemView.placeDisplayCountTextView
        val placeDisplayTextCountTextView : TextView = itemView.placeDisplayTextCountTextView
        val placeTextView : TextView = itemView.placeTextView
        val address : TextView = itemView.address
        val seeOnMapLayout : ConstraintLayout = itemView.seeOnMapLayout
        val hideMapLayout : ConstraintLayout = itemView.hideMapLayout
        val seeOnMapLoader : ImageView = itemView.seeOnMapLoader
        val placeMapView : MapView = itemView.placeMapView
        val placeCloseButton : ImageView = itemView.placeCloseButton
        val mapViewHolder : ConstraintLayout = itemView.mapViewHolder
        val placeFavouriteIcon : ImageView = itemView.placeFavouriteIcon
        val placePersonIcon : ImageView = itemView.placePersonIcon
        val noOfVotesPlaceTextview : TextView = itemView.noOfVotesPlaceTextview
    }

}
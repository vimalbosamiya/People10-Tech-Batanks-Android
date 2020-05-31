package com.batanks.nextplan.eventdetails.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.eventdetails.dataclass.MultipleDateDisplay
import com.batanks.nextplan.eventdetails.dataclass.MultiplePlaceDisplay
import com.batanks.nextplan.swagger.model.EventPlace
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.layout_date_display.view.*
import kotlinx.android.synthetic.main.layout_date_display.view.noOfVotesTextview
import kotlinx.android.synthetic.main.layout_place_display.view.*

class VoteForPlaceMultipleListAdapter (val placesList: List<EventPlace>, val context: Context): RecyclerView.Adapter<VoteForPlaceMultipleListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_place_display, parent, false)

        view.seeOnMapLayout.setOnClickListener {

            //Toast.makeText(context,"Constraint Layout Clicked",Toast.LENGTH_SHORT).show()

            view.seeOnMapLayout.visibility = View.GONE

            view.hideMapLayout.visibility = View.VISIBLE
        }

        view.seeOnMapLoader.setOnClickListener {

            view.hideMapLayout.visibility = View.GONE

            view.seeOnMapLayout.visibility = View.VISIBLE
        }


        return ViewHolder(view)

    }

    override fun getItemCount(): Int {

        return placesList.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val places : EventPlace = placesList[position]

        holder.placeDisplayCountTextView.text = places.id.toString()
        //holder.placeDisplayTextCountTextView.text = places.place
        //holder.placeTextView.text = places.placeName
        //holder.address.text = places.fullAddress
        holder.noOfVotesPlaceTextview.text = places.votes.toString()

        holder.placeMapView.apply {

            onCreate(null)
            getMapAsync{

                val LATLNG = LatLng(13.03,77.60)

                with(it){

                    onResume()
                    moveCamera(CameraUpdateFactory.newLatLngZoom(LATLNG, 13f))
                    addMarker(MarkerOptions().position(LATLNG))
                }
            }
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val placeDisplayCountTextView : TextView = itemView.placeDisplayCountTextView
        val placeDisplayTextCountTextView : TextView = itemView.placeDisplayTextCountTextView
        val placeTextView : TextView = itemView.placeTextView
        val address : TextView = itemView.address
        val noOfVotesPlaceTextview : TextView = itemView.noOfVotesPlaceTextview
        val seeOnMapLayout : ConstraintLayout = itemView.seeOnMapLayout
        val hideMapLayout : ConstraintLayout = itemView.hideMapLayout
        val seeOnMapLoader : ImageView = itemView.seeOnMapLoader
        val placeMapView : MapView = itemView.placeMapView
    }


}
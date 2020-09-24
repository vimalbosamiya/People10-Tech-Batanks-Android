package com.batanks.nextplan.eventdetailsadmin.adapter

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
import com.batanks.nextplan.eventdetailsadmin.viewmodel.EventDetailViewModelAdmin
import com.batanks.nextplan.swagger.model.EventPlace
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.layout_place_display.view.*

class VoteForPlaceMultipleListAdapterAdmin (val placesList: ArrayList<EventPlace>, val context: Context,
                                            private val eventDetailViewModelAdmin: EventDetailViewModelAdmin,
                                            private val callBack: AddPlaceRecyclerViewCallBack)
                                            : RecyclerView.Adapter<VoteForPlaceMultipleListAdapterAdmin.ViewHolder>() {

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

    override fun getItemCount() = placesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val place = placesList[position]

        holder.closeButton.setOnClickListener {

            placesList.forEach{

                it.visibility = false

            }

            placesList.removeAt(position)
            callBack.closeButtonAddPlaceItemListener(position)
        }
        holder.placeDisplayCountTextView.text = place.id.toString()
        holder.placeTextView.text = place.place.name
        holder.address.text = place.place.address
        holder.noOfVotesPlaceTextview.text = place.votes.size.toString()

        if (place.visibility){

            holder.closeButton.visibility = View.VISIBLE
        }

        else{

            holder.closeButton.visibility = GONE

        }

        if (place.place.map == false){

            holder.seeOnMapLayout.visibility = GONE
        }

        //val LATLNG = LatLng(place.place.latitude,place.place.longitude)
        holder.placeMapView.apply {

            onCreate(null)
            getMapAsync{

                with(it){

                    onResume()
                    //moveCamera(CameraUpdateFactory.newLatLngZoom(LATLNG, 13f))
                    //addMarker(MarkerOptions().position(LATLNG))
                }
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val closeButton: ImageView = itemView.placeCloseButton
        val placeDisplayCountTextView :TextView = itemView.placeDisplayCountTextView
        val placeTextView : TextView = itemView.placeTextView
        val address : TextView = itemView.address
        val placeMapView : MapView = itemView.placeMapView
        val noOfVotesPlaceTextview : TextView = itemView.noOfVotesPlaceTextview
        val seeOnMapLayout : ConstraintLayout = itemView.seeOnMapLayout
    }

    interface AddPlaceRecyclerViewCallBack {
        fun closeButtonAddPlaceItemListener(pos: Int)
    }
}
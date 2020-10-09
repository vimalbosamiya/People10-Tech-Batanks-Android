package com.batanks.nextplan.home.fragment.place

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.swagger.model.EventPlace
import com.batanks.nextplan.swagger.model.Place
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.layout_add_plan_add_place_card.view.*

class AddPlaceRecyclerView(private val callBack: AddPlaceRecyclerViewCallBack, private val modelList: ArrayList<EventPlace>) :
        RecyclerView.Adapter<AddPlaceRecyclerView.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_add_plan_add_place_card, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = modelList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.closeButton.setOnClickListener {
            modelList.removeAt(position)
            callBack.closeButtonAddPlaceItemListener(position)
        }
        holder.placeName.text = modelList[position].name
        holder.placeAddress.text = modelList[position].address

        if (modelList[position].map == false){

            holder.map.visibility = View.GONE
        } else {

            holder.map.apply {
                onCreate(null)
                getMapAsync {
                    val LATLNG = LatLng(modelList[position].place.latitude, modelList[position].place.longitude)
                    with(it) {
                        onResume()
                        moveCamera(CameraUpdateFactory.newLatLngZoom(LATLNG, 13f))
                        addMarker(MarkerOptions().position(LATLNG))
                    }
                }
            }
        }


    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val placeName: TextView = item.placeNameTextView
        val placeAddress: TextView = item.placeAddressTextView
        val closeButton: ImageView = item.closeButtonIcon
        val map: MapView = item.mapView
    }

    interface AddPlaceRecyclerViewCallBack {
        fun closeButtonAddPlaceItemListener(pos: Int)
    }
}
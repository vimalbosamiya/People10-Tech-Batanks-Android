package com.batanks.nextplan.home.fragment.place

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.swagger.model.EventPlace
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.layout_add_plan_add_place_card.view.*

class AddPlaceRecyclerView(private val callBack: AddPlaceRecyclerViewCallBack
                           )
    : RecyclerView.Adapter<AddPlaceRecyclerView.MyViewHolder>() {

    private val modelList = ArrayList<EventPlace>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_add_plan_add_place_card, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = /*modelList.size*/ 5

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.closeButton.setOnClickListener {
            modelList.removeAt(position)
            callBack.closeButtonAddPlaceItemListener(position)
        }
        /*holder.placeName.text = modelList[position].placeName
        holder.placeAddress.text = modelList[position].placeAddress*/
        holder.map.apply {
            onCreate(null)
            getMapAsync {
                val SYDNEY = LatLng(-33.862, 151.21)
                val ZOOM_LEVEL = 13f
                with(it) {
                    onResume()
                    moveCamera(CameraUpdateFactory.newLatLngZoom(SYDNEY, ZOOM_LEVEL))
                    addMarker(MarkerOptions().position(SYDNEY))
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
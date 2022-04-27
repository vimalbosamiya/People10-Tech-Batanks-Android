package com.batanks.nextplan.home.fragment.place

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.swagger.model.EventPlace
import com.batanks.nextplan.swagger.model.Place
import com.batanks.nextplan.swagger.model.PostPlaces
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.layout_add_plan_add_place_card.view.*

class AddPlaceRecyclerView(private val callBack: AddPlaceRecyclerViewCallBack, private val modelList: ArrayList<PostPlaces>, private val editButtonClicked : Boolean) :
        RecyclerView.Adapter<AddPlaceRecyclerView.MyViewHolder>() {

    lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_add_plan_add_place_card, parent, false)

        context = parent.context
        return MyViewHolder(view)
    }

    override fun getItemCount() = modelList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.closeButton.setOnClickListener {
            //modelList.removeAt(position)
            callBack.closeButtonAddPlaceItemListener(position)
        }

        holder.placeName.text = modelList[position].name


        val name : String = modelList[position].name!!
        var address : String = modelList[position].address!!
        val city : String = modelList[position].city!!
        val country : String = modelList[position].country!!
        val zipcode : String? = modelList[position].zipcode
        var latitude : Double = 0.0
        var longitude : Double = 0.0

        address = address.replace("\\s".toRegex(), " ")


        val stringBuilder = StringBuilder()
                .append(name)
                .append("+")
                .append(address)
                .append("+")
                .append(city)
                .append("+")
                .append(country)
                .append("+")
                .append(zipcode)

        val stringBuilderAddress = StringBuilder()
                /*.append(name)
                .append(" ")*/
                .append(address)
                .append("\n")
                .append(zipcode)
                .append(" ")
                .append(city)
                .append(" ")
                .append(country)
                .append(" ")

        if (!stringBuilderAddress.toString().isNullOrEmpty()){

            holder.placeAddress.text = stringBuilderAddress.toString()
            holder.placeAddress.visibility = View.VISIBLE

        } else {

            holder.placeAddress.visibility = View.GONE
        }

        //val result: List<Address> = Geocoder(context).getFromLocationName(stringBuilder.toString(), 5)

       /* if (result.isEmpty()) {
            //showMessage("We are unable to find the location info, Please enter a different location.")
            Toast.makeText(context,context.getString(R.string.place_not_found), Toast.LENGTH_LONG).show()

        }else {
            latitude = result[0].latitude
            longitude = result[0].longitude

            //Toast.makeText(activity,place.toString(),Toast.LENGTH_LONG).show()
        }*/

        if (modelList[position].map == false){

            holder.map.visibility = View.GONE
        } else {

            holder.map.apply {
                onCreate(null)
                getMapAsync {
                    val LATLNG = LatLng(latitude, longitude)
                    with(it) {
                        onResume()
                        moveCamera(CameraUpdateFactory.newLatLngZoom(LATLNG, 13f))
                        addMarker(MarkerOptions().position(LATLNG))
                    }
                }
            }
        }

        if (editButtonClicked == true){

            holder.editButtonIcon.visibility = View.VISIBLE
            holder.closeButton.visibility = View.GONE

        }else{

            holder.editButtonIcon.visibility = View.GONE
            holder.closeButton.visibility = View.VISIBLE
        }

        holder.editButtonIcon.setOnClickListener {

            callBack.editButtonAddPlaceItemListener(position)
        }
    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val placeName: TextView = item.placeNameTextView
        val placeAddress: TextView = item.placeAddressTextView
        val closeButton: ImageView = item.closeButtonIcon
        val editButtonIcon: ImageView = item.editButtonIcon
        val map: MapView = item.mapView
    }

    interface AddPlaceRecyclerViewCallBack {
        fun closeButtonAddPlaceItemListener(pos: Int)
        fun editButtonAddPlaceItemListener(pos: Int)
    }
}
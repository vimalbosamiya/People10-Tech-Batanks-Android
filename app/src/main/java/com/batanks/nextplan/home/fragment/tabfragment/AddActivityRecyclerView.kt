package com.batanks.nextplan.home.fragment.tabfragment

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.swagger.model.Activity
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_add_activity_item.view.*

class AddActivityRecyclerView (private val callBack: AddActivityRecyclerViewCallBack , private val modelList: ArrayList<Activity>)
: RecyclerView.Adapter<AddActivityRecyclerView.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.fragment_add_activity_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = modelList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        //val itemView = holder.itemView

        holder.txt_activity_name.text = modelList.get(position).title

        val placeName = modelList[position].place.name
        val placeCity = modelList[position].place.city
        val placeCountry = modelList[position].place.country
        val placeZipcode = modelList[position].place.zipcode
        val address = modelList[position].place.address

        /*val stringBuilder = StringBuilder()
                .append(placeName)
                .append(",")
                .append(" ")
                .append(address)
                .append(",")
                .append(" ")
                .append(placeCity)
                .append(",")
                .append(" ")
                .append(placeCountry)
                .append(",")
                .append(" ")
                .append(placeZipcode)*/

        val stringBuilder = StringBuilder()
                .append(placeName)
                .append(" ")
                .append(address)
                .append(" ")
                .append(placeCity)
                .append(" ")
                .append(placeCountry)
                .append(" ")
                .append(placeZipcode)

        holder.txt_activity_location_name.text = stringBuilder.toString()
        holder.activity_txt_date.text = modelList.get(position).date
        holder.activity_txt_time.text = modelList.get(position).duration.toString()

        if (!TextUtils.isEmpty(modelList.get(position).price)){

            holder.activity_textViewCostPerPersonAmount.text = modelList.get(position).price
        }

        //holder.activity_textViewCostPerPersonCurrency.text = modelList.get(position).price_currency

        if (modelList.get(position).per_person == true){

            holder.activity_textViewCostPerPerson.text = "Cost Per Person"

        } else if (modelList.get(position).per_person == false){

           holder.activity_textViewCostPerPerson.text = "Total Cost"
        }

        if (modelList[position].place.map == true){

            holder.activityMapbackgroundHolder.visibility = View.VISIBLE

            holder.activity_map.apply {
                onCreate(null)
                getMapAsync {
                    val LATLNG = LatLng(modelList[position].place.latitude, modelList[position].place.longitude)
                    with(it) {
                        onResume()
                        moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(LATLNG, 13f))
                        addMarker(com.google.android.gms.maps.model.MarkerOptions().position(LATLNG))
                    }
                }
            }

        } else {

            holder.activityMapbackgroundHolder.visibility = View.GONE
            holder.activitylocationIcon.visibility = GONE
            holder.txt_activity_location_name.visibility = GONE
        }

        holder.activity_hideMapIcon

        holder.activity_hideMapIcon.setOnClickListener {

            holder.activityMapbackgroundVisible.visibility = GONE
            holder.activity_MapbackgroundHide.visibility = VISIBLE
        }

        holder.hideMapIconHide.setOnClickListener {

            holder.activity_MapbackgroundHide.visibility = GONE
            holder.activityMapbackgroundVisible.visibility = VISIBLE
        }

       /* holder.activity_map.apply {
            onCreate(null)
            getMapAsync {
                val LATLNG = LatLng(modelList[position].place.latitude, modelList[position].place.longitude)
                with(it) {
                    onResume()
                    moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(LATLNG, 13f))
                    addMarker(com.google.android.gms.maps.model.MarkerOptions().position(LATLNG))
                }
            }
        }*/

       /* holder.activity_hideMapImg.setOnClickListener(View.OnClickListener {
            if(holder.activity_map.visibility == View.VISIBLE){
                holder.activity_map.visibility = View.GONE
            } else {
                holder.activity_map.visibility = View.VISIBLE
            }
        })*/

        holder.closeButton.setOnClickListener {
            modelList.removeAt(position)
            callBack.closeButtonAddActivityItemListener(position)
        }
    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        //val contactName: TextView = item.txt_contact_name
        val closeButton: ImageView = item.activitycloseButtonIcon
        val activitylocationIcon : ImageView = item.activitylocationIcon
        val activity_hideMapImg : ImageView = item.activity_hideMapImg
        val activity_hideMapIcon : ImageView = item.activity_hideMapIcon
        val hideMapIconHide : ImageView = item.hideMapIconHide

        val activity_txt_date :TextView = item.activity_txt_date
        val activity_txt_time : TextView = item.activity_txt_time
        val txt_activity_name : TextView = item.txt_activity_name
        val activity_textViewCostPerPerson :TextView = item.activity_textViewCostPerPerson
        val txt_activity_location_name : TextView = item.txt_activity_location_name
        val activity_textViewCostPerPersonAmount : TextView = item.activity_textViewCostPerPersonAmount
        val activity_textViewCostPerPersonCurrency : TextView = item.activity_textViewCostPerPersonCurrency

        val activity_map : MapView = item.activity_MapView

        val activityMapbackgroundVisible : ConstraintLayout = item.activityMapbackgroundVisible
        val activityMapbackgroundHolder : ConstraintLayout = item.activityMapbackgroundHolder
        val activity_MapbackgroundHide : ConstraintLayout = item.activity_MapbackgroundHide
    }
    interface AddActivityRecyclerViewCallBack {
        fun closeButtonAddActivityItemListener(pos: Int)
    }
}
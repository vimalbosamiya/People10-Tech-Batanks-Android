package com.batanks.nextplan.eventdetails.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.swagger.model.Activity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.layout_activity_display.view.*

class EventActivityListAdapter (val activityList : List<Activity>, val context: Context): RecyclerView.Adapter<EventActivityListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_activity_display, parent, false)

        view.personCostDropDown.setOnClickListener {

            view.eventInfoBackground.visibility = View.GONE

            view.eventInfobackgroundMulti.visibility = View.VISIBLE
        }

        view.personCostDropDownMulti.setOnClickListener {

            view.eventInfobackgroundMulti.visibility = View.GONE

            view.eventInfoBackground.visibility = View.VISIBLE

        }

        view.hideMapIcon.setOnClickListener {

            view.eventMapbackgroundVisible.visibility = View.GONE

            view.eventMapbackgroundHide.visibility = View.VISIBLE
        }

        view.hideMapIconHide.setOnClickListener {

            view.eventMapbackgroundHide.visibility = View.GONE

            view.eventMapbackgroundVisible.visibility = View.VISIBLE
        }

        view.hideEverybodyCome.setOnClickListener {

            view.activityEverybodyComeVisible.visibility = View.GONE

            view.activityEverybodyComeHide.visibility = View.VISIBLE
        }

        view.hideEverybodyComeHide.setOnClickListener {

            view.activityEverybodyComeHide.visibility = View.GONE

            view.activityEverybodyComeVisible.visibility = View.VISIBLE
        }

        return  ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return activityList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val activity : Activity = activityList[position]

        holder.textViewActivityName.text = activity.title
        holder.textViewEventNameOfPerPersonMulti.text = activity.title
        holder.locationName.text = activity.place.name

        val address = activity.place.address
        val placeCity = activity.place.city
        val placeCountry = activity.place.country
        val placeZipcode = activity.place.zipcode

        val stringBuilder = StringBuilder()
                .append(address)

                .append(" ")
                .append(placeCity)

                .append(" ")
                .append(placeCountry)

                .append(" ")
                .append(placeZipcode)

        holder.fullLocation.text = stringBuilder.toString()
        holder.textViewEventStartDate.text = activity.date
        holder.textViewEventTime.text = activity.duration.toString()

        if (activity.per_person == true){

            holder.textViewCostPerPerson.setText(R.string.cost_per_person)
            holder.textViewCostPerPersonMulti.setText(R.string.cost_per_person)

        }else {

            holder.textViewCostPerPerson.setText(R.string.total_cost)
            holder.textViewCostPerPersonMulti.setText(R.string.total_cost)
        }

        holder.textViewCostPerPersonAmount.text = activity.price.toString()
        holder.textViewCostPerPersonAmountMulti.text = activity.price.toString()
        holder.textViewCostPerPersonSymbol.text = activity.price_currency
        holder.textViewCostPerPersonAmountSymbol.text = activity.price_currency


        holder.activityMapView.apply {

            onCreate(null)
            getMapAsync{

                val LATLNG = LatLng(activity.place.latitude,activity.place.longitude)

                with(it){

                    onResume()
                    moveCamera(CameraUpdateFactory.newLatLngZoom(LATLNG, 13f))
                    addMarker(MarkerOptions().position(LATLNG))
                }
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textViewActivityName : TextView = itemView.textViewActivityName
        val textViewEventNameOfPerPersonMulti : TextView = itemView.textViewEventNameOfPerPersonMulti
        val locationName : TextView = itemView.locationName
        val fullLocation : TextView = itemView.fullLocation
        val textViewEventStartDate : TextView = itemView.textViewEventStartDate
        val textViewEventTime : TextView = itemView.textViewEventTime
        val textViewCostPerPerson : TextView = itemView.textViewCostPerPerson
        val textViewCostPerPersonMulti : TextView = itemView.textViewCostPerPersonMulti
        val textViewCostPerPersonAmount : TextView = itemView.textViewCostPerPersonAmount
        val textViewCostPerPersonAmountMulti : TextView = itemView.textViewCostPerPersonAmountMulti
        val textViewCostPerPersonSymbol : TextView = itemView.textViewCostPerPersonSymbol
        val textViewCostPerPersonAmountSymbol : TextView = itemView.textViewCostPerPersonAmountSymbol
        val activityMapView : MapView = itemView.activityMapView

    }
}
package com.batanks.nextplan.eventdetailsadmin.adapter

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
import kotlinx.android.synthetic.main.layout_activity_display_admin.view.*
import org.w3c.dom.Text

class EventActivityListAdapterAdmin (val activityList : List<Activity>, val context: Context): RecyclerView.Adapter<EventActivityListAdapterAdmin.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_activity_display_admin, parent, false)

        view.activityDropDown.setOnClickListener {

            view.eventInfoBackground.visibility = View.GONE

            view.eventInfobackgroundMulti.visibility = View.VISIBLE
        }

        view.activityDropDownMulti.setOnClickListener {

            view.eventInfobackgroundMulti.visibility = View.GONE

            view.eventInfoBackground.visibility = View.VISIBLE

        }

        view.activityMapHide.setOnClickListener {

            view.eventMapbackgroundVisible.visibility = View.GONE

            view.eventMapbackgroundHide.visibility = View.VISIBLE
        }

        view.activityMapShow.setOnClickListener {

            view.eventMapbackgroundHide.visibility = View.GONE

            view.eventMapbackgroundVisible.visibility = View.VISIBLE
        }

        view.activityEverybodyComehider.setOnClickListener {

            view.activityEverybodyComeVisible.visibility = View.GONE

            view.activityEverybodyComeHide.visibility = View.VISIBLE
        }

        view.activityEverybodyComeDropDown.setOnClickListener {

            view.activityEverybodyComeHide.visibility = View.GONE

            view.activityEverybodyComeVisible.visibility = View.VISIBLE
        }

        view.participateToActivityIcon.setOnClickListener {  }

        return  ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return activityList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val activity : Activity = activityList[position]

        holder.activityIdTextView.text = activity.id.toString()
        holder.textViewActivityName.text = activity.title
        //holder.textViewActivityCost.text = activity.price
        holder.activityNameTextView.text = activity.title
        holder.textViewActivityStartDate.text = activity.date
        holder.textViewActivityTime.text = activity.duration.toString()
        holder.textViewActivityParticipants.text = activity.participants.size.toString()
        holder.textViewTotalParticipants.text = activity.max_participants.toString()
        holder.activityCostTextView.text = activity.price

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

        val activityIdTextView : TextView = itemView.activityIdTextView
        val textViewActivityName : TextView = itemView.textViewActivityName
        val textViewActivityCost : TextView = itemView.textViewActivityCost
        val activityNameTextView : TextView = itemView.activityNameTextView
        val locationName : TextView = itemView.locationName
        val activityMapView : MapView = itemView.activityMapView
        val textViewActivityStartDate :TextView = itemView.textViewActivityStartDate
        val textViewActivityTime : TextView = itemView.textViewActivityTime
        val textViewActivityParticipants : TextView = itemView.textViewActivityParticipants
        val textViewTotalParticipants : TextView = itemView.textViewTotalParticipants
        val activityCostTextView : TextView = itemView.activityCostTextView


    }

    interface AddActivityRecyclerViewCallBack {
        fun closeButtonAddActivityItemListener(pos: Int)
    }

}
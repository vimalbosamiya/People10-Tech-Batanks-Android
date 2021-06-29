package com.batanks.nextplan.eventdetailsadmin.adapter

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.eventdetails.adapter.ActivityEverybodyComeListAdapter
import com.batanks.nextplan.swagger.model.Activity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.layout_activity_display_admin.view.*
import org.w3c.dom.Text
import java.text.SimpleDateFormat

class EventActivityListAdapterAdmin (val activityList : ArrayList<Activity>, val context: Context,
                                     private val callBack: AddActivityRecyclerViewCallBack): RecyclerView.Adapter<EventActivityListAdapterAdmin.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_activity_display_admin, parent, false)

        view.activityDropDown.setOnClickListener {

            view.activityInfo.visibility = View.VISIBLE

            view.activityDropDown.visibility = View.GONE

            view.activityDropUp.visibility = View.VISIBLE
        }

        view.activityDropUp.setOnClickListener {

            view.activityInfo.visibility = View.GONE

            view.activityDropDown.visibility = View.VISIBLE

            view.activityDropUp.visibility = View.GONE
        }

        view.activityMapHide.setOnClickListener {

            view.eventMapbackgroundVisible.visibility = View.GONE

            view.eventMapbackgroundHide.visibility = View.VISIBLE
        }

        view.activityMapShow.setOnClickListener {

            view.eventMapbackgroundHide.visibility = View.GONE

            view.eventMapbackgroundVisible.visibility = View.VISIBLE
        }

        view.activityEverybodyComehiderInitial.setOnClickListener {

            view.activityParticipantsListAdmin.visibility = View.VISIBLE

            view.activityEverybodyComehider.visibility = View.VISIBLE

            view.activityEverybodyComehiderInitial.visibility = View.GONE

            view.costPerPersonCalenderBackground.visibility = View.GONE
        }

        view.activityEverybodyComehider.setOnClickListener {

            view.activityParticipantsListAdmin.visibility = View.GONE

            view.activityEverybodyComehider.visibility = View.GONE

            view.activityEverybodyComehiderInitial.visibility = View.VISIBLE

            view.costPerPersonCalenderBackground.visibility = View.VISIBLE
        }

        return  ViewHolder(view)
    }

    override fun getItemCount() = activityList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val activity : Activity = activityList[position]

        holder.textViewActivityName.text = activity.title
        holder.comingVotesInitial.text = activity.participants.size.toString()
        holder.textViewActivityCost.text = activity.price.toString()
        holder.textViewCostPerPersonAmountSymbol.text = activity.price_currency

        if (activity.place != null){

            holder.locationName.text = activity.place.name

            val stringBuilder = StringBuilder()
                    .append(activity.place.address)

                    .append(" ")
                    .append(activity.place.city)

                    .append(" ")
                    .append(activity.place.country)

                    .append(" ")
                    .append(activity.place.zipcode)

            holder.fullLocation.text = stringBuilder.toString()

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

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val outputFormat = SimpleDateFormat("E, MMM dd yyyy h:mm a")
        val startDate = inputFormat.parse(activity.date)
        val formattedStartDate = outputFormat.format(startDate)

        holder.textViewActivityStartDate.text = formattedStartDate

        val hours : Int = activity.duration/60
        val mins : Int = activity.duration % 60

        holder.textViewEventTimeHours.text = hours.toString()
        holder.textViewEventTimeMins.text = mins.toString()

        if (activity.per_person == true){

            holder.textViewCostPerPerson.setText(R.string.cost_per_person)

        }else {

            holder.textViewCostPerPerson.setText(R.string.total_cost)
        }

        holder.activityParticipantsListAdmin.layoutManager = LinearLayoutManager(context)
        holder.activityParticipantsListAdmin.adapter = ActivityEverybodyComeListAdapterAdmin(activity.participants,context)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        /*val activityIdTextView : TextView = itemView.activityIdTextView*/
        val textViewActivityName : TextView = itemView.textViewActivityName
        val comingVotesInitial : TextView = itemView.comingVotesInitial
        //val totalNoOfVotesInitial : TextView = itemView.totalNoOfVotesInitial
        val textViewCostPerPerson : TextView = itemView.textViewCostPerPerson
        val textViewActivityCost : TextView = itemView.textViewActivityCost
        val textViewEventTimeHours : TextView = itemView.textViewEventTimeHours
        val textViewEventTimeMins : TextView = itemView.textViewEventTimeMins
        val textViewCostPerPersonAmountSymbol : TextView = itemView.textViewCostPerPersonAmountSymbol
        //val activityNameTextView : TextView = itemView.activityNameTextView
        val locationName : TextView = itemView.locationName
        val fullLocation : TextView = itemView.fullLocation
        val activityMapView : MapView = itemView.activityMapView
        val textViewActivityStartDate :TextView = itemView.textViewActivityStartDate
        //val activitySettingsIcon : ImageView = itemView.activitySettingsIcon
        val activityParticipantsListAdmin : RecyclerView = itemView.activityParticipantsListAdmin
    }

    interface AddActivityRecyclerViewCallBack {
        fun closeButtonAddActivityItemListener(pos: Int)
        fun settingsButtonAddActivityItemListener(pos: Int)
    }

}
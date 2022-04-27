package com.batanks.nextplan.eventdetails.adapter

import ActivitySubscribe
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleObserver
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.response.Status
import com.batanks.nextplan.eventdetails.viewmodel.EventDetailViewModel
import com.batanks.nextplan.home.fragment.CreatePlanFragment
import com.batanks.nextplan.swagger.model.Activity
import com.batanks.nextplan.swagger.model.Empty
import com.batanks.nextplan.swagger.model.Event
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kotlinx.android.synthetic.main.layout_activity_display.view.*
import java.text.SimpleDateFormat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.batanks.nextplan.eventdetails.EventDetailView

class EventActivityListAdapter (private val activityList : ArrayList<Activity>, val context: Context, private val eventDetailViewModel: EventDetailViewModel, private val eventId : String, val listener : EventActivityListAdapterListener)
                                                 : RecyclerView.Adapter<EventActivityListAdapter.ViewHolder>() {

    private var activityAcceptResponse : ActivitySubscribe? = null

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

    override fun getItemCount() = activityList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val activity : Activity = activityList[position]
        val userId : Int = context.getSharedPreferences("USER_DETAILS", AppCompatActivity.MODE_PRIVATE).getInt("ID",0)

        holder.textViewActivityName.text = activity.title
        holder.textViewEventNameOfPerPersonMulti.text = activity.title
        holder.locationName.text = activity.place.name

        val stringBuilder = StringBuilder()
                .append(activity.place.address)
                .append("\n")
                .append(activity.place.zipcode)
                .append(" ")
                .append(activity.place.city)
                .append(" ")
                .append(activity.place.country)

        holder.fullLocation.text = stringBuilder.toString()

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val outputFormat = SimpleDateFormat("E, MMM dd yyyy h:mm a")
        val startDate = inputFormat.parse(activity.date)
        val formattedStartDate = outputFormat.format(startDate)

        holder.textViewEventStartDate.text = formattedStartDate

        val hours : Int = activity.duration/60
        val mins : Int = activity.duration % 60

        holder.textViewEventTimeHours.text = hours.toString()
        holder.textViewEventTimeMins.text = mins.toString()

        if (activity.per_person){

            holder.textViewCostPerPerson.setText(R.string.cost_per_person)
            holder.textViewCostPerPersonMulti.setText(R.string.cost_per_person)
            holder.activity_img_cost_per_person.setImageResource(R.drawable.ic_cost_perperson_icon)
            holder.activity_img_cost_per_person_expanded.setImageResource(R.drawable.ic_cost_perperson_icon)

        }else {

            holder.textViewCostPerPerson.setText(R.string.total_cost)
            holder.textViewCostPerPersonMulti.setText(R.string.total_cost)
            holder.activity_img_cost_per_person.setImageResource(R.drawable.ic_action_cost_icon)
            holder.activity_img_cost_per_person_expanded.setImageResource(R.drawable.ic_action_cost_icon)
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

        holder.comingVotes.text = activity.participants.size.toString()
        holder.comingVotesHide.text = activity.participants.size.toString()

        /*for (item in activity.participants){

            if (item.id == userId){

                holder.unsubscribeFromActivityIcon.visibility = View.VISIBLE
                holder.participateToActivityIcon.visibility = View.GONE
                break

            } else {

                holder.participateToActivityIcon.visibility = View.VISIBLE
                holder.unsubscribeFromActivityIcon.visibility = View.GONE
            }
        }*/

        if (activity.is_user_subscribe) {

            holder.unsubscribeFromActivityIcon.visibility = View.VISIBLE
            holder.participateToActivityIcon.visibility = View.GONE

        } else if (!activity.is_user_subscribe){

            holder.participateToActivityIcon.visibility = View.VISIBLE
            holder.unsubscribeFromActivityIcon.visibility = View.GONE
        }

        holder.eventContactList.layoutManager = LinearLayoutManager(context)
        holder.eventContactList.adapter = ActivityEverybodyComeListAdapter(activity.participants,context)

        holder.participateToActivityIcon.setOnClickListener {

            eventDetailViewModel.activityAccepted(activity.id.toString(),eventId, Empty(""))
            listener.activityParticipantClickedPos(position)
        }

        holder.unsubscribeFromActivityIcon.setOnClickListener {

            eventDetailViewModel.activityRejected(activity.id.toString(),eventId, Empty(""))
            listener.activityParticipantClickedPos(position)
        }

        holder.costPerPersonCalenderBackground.setOnClickListener {

            eventDetailViewModel.activityAccepted(activity.id.toString(),eventId, Empty(""))
            listener.activityParticipantClickedPos(position)
        }
    }

    public class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textViewActivityName : TextView = itemView.textViewActivityName
        val textViewEventNameOfPerPersonMulti : TextView = itemView.textViewEventNameOfPerPersonMulti
        val locationName : TextView = itemView.locationName
        val fullLocation : TextView = itemView.fullLocation
        val textViewEventStartDate : TextView = itemView.textViewEventStartDate
        val textViewEventTimeHours : TextView = itemView.textViewEventTimeHours
        val textViewHours : TextView = itemView.textViewHours
        val textViewEventTimeMins : TextView = itemView.textViewEventTimeMins
        val textViewMins : TextView = itemView.textViewMins
        val textViewCostPerPerson : TextView = itemView.textViewCostPerPerson
        val textViewCostPerPersonMulti : TextView = itemView.textViewCostPerPersonMulti
        val textViewCostPerPersonAmount : TextView = itemView.textViewCostPerPersonAmount
        val textViewCostPerPersonAmountMulti : TextView = itemView.textViewCostPerPersonAmountMulti
        val textViewCostPerPersonSymbol : TextView = itemView.textViewCostPerPersonSymbol
        val textViewCostPerPersonAmountSymbol : TextView = itemView.textViewCostPerPersonAmountSymbol
        val activityMapView : MapView = itemView.activityMapView
        val eventContactList : RecyclerView = itemView.eventContactList
        val comingVotes : TextView = itemView.comingVotes
        val comingVotesHide : TextView = itemView.comingVotesHide
        val participateToActivityIcon : ExtendedFloatingActionButton = itemView.participateToActivityIcon
        val unsubscribeFromActivityIcon : ExtendedFloatingActionButton = itemView.unsubscribeFromActivityIcon
        val costPerPersonCalenderBackground : ImageView = itemView.costPerPersonCalenderBackground
        val activity_img_cost_per_person : ImageView = itemView.activity_img_cost_per_person
        val activity_img_cost_per_person_expanded : ImageView = itemView.activity_img_cost_per_person_expanded
    }

    interface EventActivityListAdapterListener {

        fun activityParticipantClickedPos(pos : Int)
    }
}




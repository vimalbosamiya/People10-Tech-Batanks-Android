package com.batanks.nextplan.home.fragment.tabfragment

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.eventdetailsadmin.adapter.ActivityEverybodyComeListAdapterAdmin
import com.batanks.nextplan.swagger.model.Activity
import com.batanks.nextplan.swagger.model.Event
import com.batanks.nextplan.swagger.model.PostActivities
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_add_activity_item.view.*
import java.text.SimpleDateFormat

class AddActivityRecyclerView (private val callBack: AddActivityRecyclerViewCallBack , private val modelList: ArrayList<PostActivities>, private val editButtonClicked : Boolean,
                               private val deleteButtonClicked : Boolean, private val event : Event?) : RecyclerView.Adapter<AddActivityRecyclerView.MyViewHolder>() {

    lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.fragment_add_activity_item, parent, false)

        context = parent.context

        return MyViewHolder(view)
    }

    override fun getItemCount() = modelList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        //val itemView = holder.itemView

        println(modelList)

        holder.closeButton.setOnClickListener {
            //modelList.removeAt(position)
            callBack.closeButtonAddActivityItemListener(position)
        }

        holder.activityEditButtonIcon.setOnClickListener {callBack.editButtonAddActivityItemListener(position)}

        holder.txt_activity_name.text = modelList.get(position).title

        val placeName = modelList[position].place?.name
        val placeCity = modelList[position].place?.city
        val placeCountry = modelList[position].place?.country
        val placeZipcode = modelList[position].place?.zipcode
        val address = modelList[position].place?.address
        var latitude : Double = 0.0
        var longitude : Double = 0.0

        val stringBuilder1 = StringBuilder()

        if (address != null){

            stringBuilder1
                .append(address)
                .append(placeCountry)

        }else {

            stringBuilder1
                .append(placeZipcode)
                .append(" ")
                .append(placeCity)
                .append(" ")
                .append(placeCountry)
        }

        val stringBuilder = StringBuilder()
            .append(address)
            .append("\n")
            .append(placeZipcode)
            .append(" ")
            .append(placeCity)
            .append(" ")
            .append(placeCountry)

        val result: List<Address> = Geocoder(context).getFromLocationName(stringBuilder.toString(), 5)

        if (result.isEmpty()) {
            //showMessage("We are unable to find the location info, Please enter a different location.")
            Toast.makeText(context,context.getString(R.string.place_not_found), Toast.LENGTH_LONG).show()

        }else {
            latitude = result[0].latitude
            longitude = result[0].longitude

            //Toast.makeText(activity,place.toString(),Toast.LENGTH_LONG).show()
        }

        holder.txt_activity_place_name.text = placeName
        holder.txt_activity_location_name.text = stringBuilder1.toString()


        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss") /*"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"*/
        val outputFormat = SimpleDateFormat("EEE, MMM d yyyy HH:mm")

        val fromDate = inputFormat.parse(modelList.get(position).date)
        val formattedFromDate = outputFormat.format(fromDate)

        holder.activity_txt_date.text = formattedFromDate

        val hours : Int = modelList.get(position).duration!! /60
        val mins : Int = modelList.get(position).duration!! % 60

        holder.activity_txt_time.text = hours.toString()
        holder.textViewEventTimeMins.text = mins.toString()

        if (modelList.get(position).price!! > 0){

            holder.activity_textViewCostPerPersonAmount.text = String.format("%,d",modelList.get(position).price)
        } else {}

        val currency : String? = context?.getSharedPreferences("SAVED_CURREN", AppCompatActivity.MODE_PRIVATE)?.getString("SAVED_CURRENCY","USD")

        holder.activity_textViewCostPerPersonCurrency.text = currency

        if (modelList.get(position).per_person == true){

            holder.activity_textViewCostPerPerson.setText(R.string.cost_per_person)
            holder.activity_img_cost_per_person.setImageResource(R.drawable.ic_cost_perperson_icon)

        } else if (modelList.get(position).per_person == false){

           holder.activity_textViewCostPerPerson.setText(R.string.total_cost)
            holder.activity_img_cost_per_person.setImageResource(R.drawable.ic_action_cost_icon)
        }

        if (modelList[position].place?.map == true){

            holder.activityMapbackgroundHolder.visibility = View.VISIBLE

            holder.activity_map.apply {
                onCreate(null)
                getMapAsync {
                    val LATLNG = LatLng(latitude, longitude)
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
            holder.txt_activity_place_name.visibility = GONE
            holder.txt_activity_location_name.visibility = GONE
        }

        holder.activity_hideMapIcon.setOnClickListener {

            holder.activityMapbackgroundVisible.visibility = GONE
            holder.activity_MapbackgroundHide.visibility = VISIBLE
        }

        holder.hideMapIconHide.setOnClickListener {

            holder.activity_MapbackgroundHide.visibility = GONE
            holder.activityMapbackgroundVisible.visibility = VISIBLE
        }

        holder.activity_eventContactList.layoutManager = LinearLayoutManager(context)

        if (editButtonClicked == true) {

            if (modelList != null){

                if (modelList.size >0){

                    if (modelList.get(position)?.participants!!.size > 0 /*!= null*/){

                        holder.activity_eventContactList.adapter = modelList[position].participants?.let {
                            ActivityEverybodyComeListAdapterAdmin(
                                it, context)
                        }
                        holder.activity_comingVotes.text = modelList.get(position)?.participants?.size.toString()
                        //holder.activity_totalNoOfVotes.text = event!!.activities[position].participants.size.toString()
                    } else {}

                } else { }

            } else {}

        }

       /* if (event != null) {

            if (event.activities?.size!! > 0){

                if (event.activities?.get(position)?.participants != null){

                    holder.activity_eventContactList.adapter = ActivityEverybodyComeListAdapterAdmin(
                        event!!.activities!![position].participants, context)
                    holder.activity_comingVotes.text = event!!.activities?.get(position)?.participants?.size.toString()
                    //holder.activity_totalNoOfVotes.text = event!!.activities[position].participants.size.toString()

                }else { }

            } else {}

        }*/

        holder.activity_showEverybodyCome.setOnClickListener {

            holder.activity_hideEverybodyCome.visibility = View.VISIBLE
            holder.activity_eventContactList.visibility = View.VISIBLE
            holder.activity_showEverybodyCome.visibility = GONE
        }

        holder.activity_hideEverybodyCome.setOnClickListener {

            holder.activity_hideEverybodyCome.visibility = View.GONE
            holder.activity_eventContactList.visibility = View.GONE
            holder.activity_showEverybodyCome.visibility = VISIBLE
        }

        if (editButtonClicked == true){

            holder.activityEditButtonIcon.visibility = View.VISIBLE
            holder.closeButton.visibility = View.GONE

        }else {

            holder.activityEditButtonIcon.visibility = View.GONE
            holder.closeButton.visibility = View.VISIBLE
        }
    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        //val contactName: TextView = item.txt_contact_name
        val closeButton: ImageView = item.activitycloseButtonIcon
        val activityEditButtonIcon: ImageView = item.activityEditButtonIcon
        val activitylocationIcon : ImageView = item.activitylocationIcon
        val activity_hideMapImg : ImageView = item.activity_hideMapImg
        val activity_hideMapIcon : ImageView = item.activity_hideMapIcon
        val hideMapIconHide : ImageView = item.hideMapIconHide
        val activity_showEverybodyCome : ImageView = item.activity_showEverybodyCome
        val activity_hideEverybodyCome : ImageView = item.activity_hideEverybodyCome
        val activity_img_cost_per_person : ImageView = item.activity_img_cost_per_person

        val activity_txt_date :TextView = item.activity_txt_date
        val activity_txt_time : TextView = item.activity_txt_time
        val textViewEventTimeMins : TextView = item.textViewEventTimeMins
        val txt_activity_name : TextView = item.txt_activity_name
        val activity_textViewCostPerPerson :TextView = item.activity_textViewCostPerPerson
        val txt_activity_location_name : TextView = item.txt_activity_location_name
        val txt_activity_place_name : TextView = item.txt_activity_place_name
        val activity_textViewCostPerPersonAmount : TextView = item.activity_textViewCostPerPersonAmount
        val activity_textViewCostPerPersonCurrency : TextView = item.activity_textViewCostPerPersonCurrency
        val activity_comingVotes : TextView = item.activity_comingVotes
        //val activity_totalNoOfVotes : TextView = item.activity_totalNoOfVotes

        val activity_eventContactList : RecyclerView = item.activity_eventContactList

        val activity_map : MapView = item.activity_MapView

        val activityMapbackgroundVisible : ConstraintLayout = item.activityMapbackgroundVisible
        val activityMapbackgroundHolder : ConstraintLayout = item.activityMapbackgroundHolder
        val activity_MapbackgroundHide : ConstraintLayout = item.activity_MapbackgroundHide
    }

    interface AddActivityRecyclerViewCallBack {
        fun closeButtonAddActivityItemListener(pos: Int)
        fun editButtonAddActivityItemListener(pos: Int)

    }
}
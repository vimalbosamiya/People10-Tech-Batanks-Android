package com.batanks.nextplan.eventdetailsadmin

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseAppCompatActivity
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.eventdetailsadmin.adapter.*
import com.batanks.nextplan.eventdetailsadmin.viewmodel.EventDetailViewModelAdmin
import com.batanks.nextplan.home.fragment.place.AddPlaceFragment
import com.batanks.nextplan.home.fragment.tabfragment.ButtonContract
import com.batanks.nextplan.home.fragment.tabfragment.publicplan.viewmodel.PublicPlanViewModel
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.swagger.api.EventAPI
import com.batanks.nextplan.swagger.model.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_event_detail_view_admin.*
import kotlinx.android.synthetic.main.layout_add_guests.view.*
import kotlinx.android.synthetic.main.layout_add_plan_add_period.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EventDetailViewAdmin : BaseAppCompatActivity(), ButtonContract,
        VoteForDateMultipleListAdapterAdmin.AddPeriodRecyclerViewCallBack,
        VoteForPlaceMultipleListAdapterAdmin.AddPlaceRecyclerViewCallBack,
        AddPlaceFragment.AddPlaceFragmentListener {

    private val eventDetailViewModelAdmin: EventDetailViewModelAdmin by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            RetrofitClient.getRetrofitInstance(this)?.create(EventAPI::class.java)?.let {
                EventDetailViewModelAdmin(it)
            }
        }).get(EventDetailViewModelAdmin::class.java)
    }

    /*private val publicPlanViewModel: PublicPlanViewModel by lazy {
        ViewModelProvider(this)[PublicPlanViewModel::class.java]
    }*/

    var dates = ArrayList<EventDate>()
    var places = ArrayList<EventPlace>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail_view_admin)

        var vote : MutableList<Int> = mutableListOf(1,2,3,4,10)
        //var dates = ArrayList<EventDate>()
        dates.add(EventDate(1,"Thu, Mar 20 2019", "Wed, Mar 25 2019 06:00 pm",vote))
        dates.add(EventDate(2,"Fri, Mar 16 2019", "Wed, Mar 17 2019 06:00 pm",vote))
        dates.add(EventDate(3,"Sat, Mar 17 2019", "Wed, Mar 18 2019 06:00 pm",vote))
        /*dates.add(EventDate(4,"Sun, Mar 19 2019", "Wed, Mar 20 2019 06:00 pm",vote))
        dates.add(EventDate(4,"Sun, Mar 19 2019", "Wed, Mar 20 2019 06:00 pm",vote))
        dates.add(EventDate(4,"Sun, Mar 19 2019", "Wed, Mar 20 2019 06:00 pm",vote))
        dates.add(EventDate(4,"Sun, Mar 19 2019", "Wed, Mar 20 2019 06:00 pm",vote))*/

        /*var dates : List<EventDate> = listOf((EventDate(1,"Thu, Mar 13 2019", "Wed, Mar 14 2019 06:00 pm",vote)),
                (EventDate(2,"Fri, Mar 16 2019", "Wed, Mar 17 2019 06:00 pm",vote)),
                (EventDate(3,"Sat, Mar 17 2019", "Wed, Mar 18 2019 06:00 pm",vote)),
                (EventDate(4,"Sun, Mar 19 2019", "Wed, Mar 20 2019 06:00 pm",vote)),
                (EventDate(4,"Sun, Mar 19 2019", "Wed, Mar 20 2019 06:00 pm",vote)),
                (EventDate(4,"Sun, Mar 19 2019", "Wed, Mar 20 2019 06:00 pm",vote)))*/

        val place : Place = Place("Nellore","Buchi","524305","Nellore","India",true,27.2038,77.5011)
        val place1 : Place = Place("Nellore","Buchi","524305","Nellore","India",true,27.2038,77.5011)
        val place2 : Place = Place("Nellore","Buchi","524305","Nellore","India",true,27.2038,77.5011)

        places.add(EventPlace(1, place,"Khajanagar","Buchi","524305","Nellore","India",true,vote))
        places.add(EventPlace(2, place1,"Khajanagar","Buchi","524305","Nellore","India",true,vote))
        places.add(EventPlace(3, place2,"Khajanagar","Buchi","524305","Nellore","India",true,vote))

        //places.add(place) as EventPlace

        dateInitAdmin(dates)
        placeInitAdmin(places)

        eventInfoMapView.apply {

            onCreate(null)
            getMapAsync{

                val LATLNG = LatLng(13.03,77.60)

                with(it){

                    onResume()
                    moveCamera(CameraUpdateFactory.newLatLngZoom(LATLNG, 13f))
                    addMarker(MarkerOptions().position(LATLNG))
                }
            }
        }

        userIcon.setOnClickListener {

            organizerInitial.visibility = GONE

            organizerFull.visibility = VISIBLE
        }

        userIconInFull.setOnClickListener {

            organizerFull.visibility = GONE

            organizerInitial.visibility = VISIBLE
        }

        dateBackgroundMultiple.setOnClickListener {

            dateBackgroundMultiple.visibility = GONE

            dateDropDownBackgroundMultiple.visibility = VISIBLE
        }

        imgDateMultiLoader.setOnClickListener {

            dateDropDownBackgroundMultiple.visibility = GONE

            dateBackgroundMultiple.visibility = VISIBLE
        }

        placeBackgroundMulti.setOnClickListener {

            placeBackgroundMulti.visibility = GONE

            placeDropDownBackgroundMultiple.visibility = VISIBLE

        }

        imgPlaceMultiLoader.setOnClickListener {

            placeDropDownBackgroundMultiple.visibility = GONE

            placeBackgroundMulti.visibility = VISIBLE

        }

        totalCostBackground.setOnClickListener {

            totalCostBackground.visibility = GONE

            totalCostBackgroundFull.visibility = VISIBLE

        }

        totalCostBackgroundFull.setOnClickListener {

            totalCostBackgroundFull.visibility = GONE

            totalCostBackground.visibility = VISIBLE

        }

        eventInfoBackground.setOnClickListener {

            eventInfoBackground.visibility = GONE

            eventInfobackgroundMulti.visibility = VISIBLE

        }

        imgInfoMultiLoader.setOnClickListener {

            eventInfobackgroundMulti.visibility = GONE

            eventInfoBackground.visibility = VISIBLE

        }

        participantsListBackground.setOnClickListener {

            participantsListBackground.visibility = GONE

            participantsListBackgroundMulti.visibility = VISIBLE

        }

        imgParticipantsMultiLoader.setOnClickListener {

            participantsListBackgroundMulti.visibility = GONE

            participantsListBackground.visibility = VISIBLE

        }

        commentsBackgroundConstraint.setOnClickListener {

            commentsBackgroundConstraint.visibility = GONE

            commentsBackgroundMulti.visibility = VISIBLE

        }

        dummyCommentsBackgroundMulti.setOnClickListener {

            commentsBackgroundMulti.visibility = GONE

            commentsBackgroundConstraint.visibility = VISIBLE

        }

        imgHideMap.setOnClickListener {

            eventMapbackgroundVisible.visibility = GONE

            eventMapbackgroundHide.visibility = VISIBLE
        }

        eventMapbackgroundHide.setOnClickListener {

            eventMapbackgroundHide.visibility = GONE

            eventMapbackgroundVisible.visibility = VISIBLE
        }

        everybodyComeHider.setOnClickListener {

            activityEverybodyComeVisible.visibility = GONE

            activityEverybodyComeHide.visibility = VISIBLE
        }

        activityEverybodyComeHide.setOnClickListener {

            activityEverybodyComeHide.visibility = GONE

            activityEverybodyComeVisible.visibility = VISIBLE
        }

        tripCalenderBackground.setOnClickListener {

            tripCalenderBackground.visibility = GONE

            addGuestBackground.visibility = VISIBLE

            /*takePartVisible.visibility = GONE
            addGuestVisible.visibility = VISIBLE*/
        }

        addPeriodImg.setOnClickListener {

            addPeriodClicked()

        }

        addPlaceImg.setOnClickListener {

            addPlaceClicked()
        }

        addGuestBackground.setOnClickListener {

            addGuestsDialogShow()
        }


        /*takePartVisible.setOnClickListener {

            takePartVisible.visibility = GONE

            addGuestVisible.visibility = VISIBLE

            tripCalenderBackground.visibility = GONE

            addGuestBackground.visibility = VISIBLE

            //eventDetailViewModel.eventAccepted(id.toString(), event_obj)
        }*/


    }

    fun addGuestsDialogShow(){

        val addGuestsView = LayoutInflater.from(this).inflate(R.layout.layout_add_guests,null)

        val addGuestsBuilder = AlertDialog.Builder(this).setView(addGuestsView)

        val addGuestsDialog = addGuestsBuilder.show()

        addGuestsView.textViewCancel.setOnClickListener {

            addGuestsDialog.dismiss()
        }

        addGuestsView.add.setOnClickListener {

            var count : Int = addGuestsView.countTextView.text.toString().toInt()

            count += 1

            addGuestsView.countTextView.text = count.toString()
        }

        addGuestsView.substract.setOnClickListener {

            var count : Int = addGuestsView.countTextView.text.toString().toInt()

            if (count > 0){

                count -= 1

                addGuestsView.countTextView.text = count.toString()
            }
        }

        addGuestsView.textViewOk.setOnClickListener {

            noOfGuests.text = addGuestsView.countTextView.text

            addGuestsDialog.dismiss()

        }

    }

    fun dateInitAdmin(dates : ArrayList<EventDate>){

        val dateRecyclerView = findViewById<RecyclerView>(R.id.VoteForDateMultipleRecyclerViewAdmin) as RecyclerView
        dateRecyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = VoteForDateMultipleListAdapterAdmin(dates,this,eventDetailViewModelAdmin ,this/*,thispublicPlanViewModel.eventDate*/)
        dateRecyclerView.adapter = adapter

    }

    fun placeInitAdmin(places : ArrayList<EventPlace>){

        val placeRecyclerView = findViewById<RecyclerView>(R.id.VoteForPlaceMultipleRecyclerViewAdmin) as RecyclerView
        placeRecyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = VoteForPlaceMultipleListAdapterAdmin(places,this,eventDetailViewModelAdmin,this /*publicPlanViewModel.place*/)
        placeRecyclerView.adapter = adapter

    }

    fun taskInitAdmin(tasks : List<Task>){

        val recyclerView = findViewById<RecyclerView>(R.id.VoteForPlaceMultipleRecyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        /*val adapter = EventActionListAdapterAdmin(tasks,this)
        recyclerView.adapter = adapter*/

    }

    fun activityInitAdmin(activity : List<Activity>){

        val recyclerView = findViewById<RecyclerView>(R.id.VoteForPlaceMultipleRecyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        /*val adapter = EventActivityListAdapterAdmin(activity,this)
        recyclerView.adapter = adapter*/

    }

    /*fun activityEverybodyComeInitAdmin(participantsList : List<Int>){

        val recyclerView = findViewById<RecyclerView>(R.id.activityParticipantsListAdmin) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        *//*val adapter = ActivityEverybodyComeListAdapterAdmin(participantsList,this)
        recyclerView.adapter = adapter*//*

    }

    fun EveryBodyComeListAdapter(guestsList : List<Int>){

        val recyclerView = findViewById<RecyclerView>(R.id.everybodyComeListAdmin) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        *//*val adapter = ActivityEverybodyComeListAdapterAdmin(guestsList,this)
        recyclerView.adapter = adapter*//*

    }

    fun CommentsListAdapter(commentsList : List<Int>){

        val recyclerView = findViewById<RecyclerView>(R.id.commentsListRecyclerViewAdmin) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        *//*val adapter = ActivityEverybodyComeListAdapterAdmin(commentsList,this)
        recyclerView.adapter = adapter*//*

    }*/

    override fun addPeriodClicked() {

        val dateRecyclerView = findViewById<RecyclerView>(R.id.VoteForDateMultipleRecyclerViewAdmin) as RecyclerView
        //var datesUpdated = ArrayList<EventDate>()

        /*fragmentManager?.let { AddPeriodFragment().show(it, AddPeriodFragment::class.java.simpleName) }*/

        val mCal = Calendar.getInstance()
        val mDay = mCal.get(Calendar.DAY_OF_MONTH)
        val mMonth = mCal.get(Calendar.MONTH)
        val mYear = mCal.get(Calendar.YEAR)
        val mHour = mCal.get(Calendar.HOUR_OF_DAY)
        val mMin = mCal.get(Calendar.MINUTE)

        val fromDate = DatePickerDialog(this,R.style.AlertDialogTheme, DatePickerDialog.OnDateSetListener{ fromDatePicker, fromYear, fromMonth, fromDay ->
            val toDate = DatePickerDialog(this, R.style.AlertDialogTheme, DatePickerDialog.OnDateSetListener { toDatePicker, toYear, toMonth, toDay ->
                val fromTime = TimePickerDialog(this, R.style.AlertDialogTheme, TimePickerDialog.OnTimeSetListener { fromTimePicker, fromHourOfDay, fromMinute ->
                    val toTime = TimePickerDialog(this, R.style.AlertDialogTheme, TimePickerDialog.OnTimeSetListener { toTimePicker, toHourOfDay, toMinute ->

                        val cal = Calendar.getInstance()
                        cal.set(fromYear, fromMonth, fromDay, fromHourOfDay, fromMinute)

                        /*FromDate*/
                        val dateFormatter = SimpleDateFormat("E, MMM dd yyyy HH:mm a")
                        val startDate = dateFormatter.format(cal.time)
                        println(startDate)

                        /*ToDate*/
                        cal.set(toYear, toMonth, toDay, toHourOfDay, toMinute)
                        val endDate = dateFormatter.format(cal.time)
                        println(endDate)

                        /*publicPlanViewModel.eventDate.add(EventDate(id = publicPlanViewModel.eventDate.size, start = startDate, end = endDate, votes = mutableListOf()))
                        dateRecyclerView?.adapter?.notifyDataSetChanged()*/
                        //addPeriodButton.text = "ADD AN OTHER PERIOD"

                        dates.add(EventDate((dates.size + 1),startDate,endDate, mutableListOf()))
                        dateRecyclerView?.adapter?.notifyDataSetChanged()

                    }, mHour, mMin, false)
                    toTime.show()
                }, mHour, mMin, false)
                fromTime.show()
            }, mYear, mMonth, mDay)
            toDate.datePicker.minDate = System.currentTimeMillis()
            toDate.setCanceledOnTouchOutside(false)
            toDate.show()
        }, mYear, mMonth, mDay)
        fromDate.datePicker.minDate = System.currentTimeMillis()
        fromDate.setCanceledOnTouchOutside(false)
        fromDate.show()
    }

    override fun addPlaceClicked() {

        this.supportFragmentManager
                .beginTransaction()
                .add(AddPlaceFragment(this), AddPlaceFragment::class.java.canonicalName)
                .commitAllowingStateLoss()

        VoteForPlaceMultipleRecyclerViewAdmin?.adapter?.notifyDataSetChanged()
    }

    override fun addActionClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addActivityClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addPeopleClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun closeButtonAddPeriodItemListener(pos: Int) {

        val dateRecyclerView = findViewById<RecyclerView>(R.id.VoteForDateMultipleRecyclerViewAdmin) as RecyclerView
        dateRecyclerView?.adapter?.notifyDataSetChanged()
    }

    override fun closeButtonAddPlaceItemListener(pos: Int) {

        val placeRecyclerView = findViewById<RecyclerView>(R.id.VoteForPlaceMultipleRecyclerViewAdmin) as RecyclerView
        placeRecyclerView?.adapter?.notifyDataSetChanged()

    }

    override fun addPlaceFragmentAddressFetch(place: Place) {

        (this.supportFragmentManager.findFragmentByTag(AddPlaceFragment::class.java.canonicalName)
                as? AddPlaceFragment)?.dismiss()

        places.add(EventPlace(places.size + 1,place,place.name,place.address,place.zipcode,place.city,place.country,place.map,mutableListOf()))

        //Toast.makeText(this,place.map.toString(),Toast.LENGTH_LONG).show()

        val placeRecyclerView = findViewById<RecyclerView>(R.id.VoteForPlaceMultipleRecyclerViewAdmin) as RecyclerView
        placeRecyclerView?.adapter?.notifyDataSetChanged()
    }

    override fun cancelPlaceFragmentAddressFetch() {

        (this.supportFragmentManager.findFragmentByTag(AddPlaceFragment::class.java.canonicalName)
                as? AddPlaceFragment)?.dismiss()
    }


}

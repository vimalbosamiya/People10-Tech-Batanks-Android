package com.batanks.nextplan.eventdetails

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseAppCompatActivity
import com.batanks.nextplan.arch.response.Status
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.common.getLoadingDialog
import com.batanks.nextplan.eventdetails.adapter.VoteForDateMultipleListAdapter
import com.batanks.nextplan.eventdetails.adapter.VoteForPlaceMultipleListAdapter
import com.batanks.nextplan.eventdetails.dataclass.MultipleDateDisplay
import com.batanks.nextplan.eventdetails.dataclass.MultiplePlaceDisplay
import com.batanks.nextplan.eventdetails.fragment.*
import com.batanks.nextplan.eventdetails.viewmodel.EventDetailViewModel
import com.batanks.nextplan.home.viewmodel.HomePlanPreviewViewModel
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.swagger.api.AuthenticationAPI
import com.batanks.nextplan.swagger.api.EventAPI
import com.batanks.nextplan.swagger.model.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_event_detail_view.*
import kotlinx.android.synthetic.main.layout_date_display.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EventDetailView : BaseAppCompatActivity()/*, OnClickFunImplementation*/ {

/*    val createVoteForDateFragment = CreateVoteForDateFragment()
    val createVoteForDateMultipleFragment = CreateVoteForDateMultipleFragment()
    val createVoteForDateMultipleListFragment = CreateVoteForDateMultipleListFragment()
    val createVoteForPlaceFragment = CreateVoteForPlaceFragment()
    val createVoteForPlaceMultipleFragment = CreateVoteForPlaceMultipleFragment()
    val createVoteForPlaceMultipleListFragment = CreateVoteForPlaceMultipleListFragment()
    val createEventFullDescriptionFragment = CreateEventFullDescriptionFragment()

    val fragmentManager = supportFragmentManager*/
    //lateinit var googleMap : GoogleMap

    private val eventDetailViewModel: EventDetailViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            RetrofitClient.getRetrofitInstance(this)?.create(EventAPI::class.java)?.let {
                EventDetailViewModel(it)
            }
        }).get(EventDetailViewModel::class.java)
    }

    var event_obj : Event? = null

    //lateinit var datesList : List<EventDate>
    var datesList : List<EventDate>? = null
    var placeList : List<EventPlace>? = null
    var taskList : List<Task>? = null
    var activityList : List<Activity>? = null
    var contactList : EventInvitation? = null
    var attendersList : EventInvitation? = null
    var id : Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail_view)

        loadingDialog = this.getLoadingDialog(0, R.string.opening_page_please_wait, theme = R.style.AlertDialogCustom)

        //eventDetailViewModel.getEventData(id)

        eventDetailViewModel.responseLiveData.observe(this, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {

                    event_obj = response.data as Event

                    id = event_obj?.id
                    eventName.text = event_obj?.title
                    eventDescription.text = event_obj?.detail
                    noOfGuests.text = event_obj?.max_guests.toString()
                    costPerPerson.text = event_obj?.periodicity?.amount.toString()
                    datesList = event_obj?.dates
                    placeList = event_obj?.places
                    taskList = event_obj?.tasks
                    activityList = event_obj?.activities
                    contactList = event_obj?.guests
                    contactList?.users?.size
                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                    Toast.makeText(context() , "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        })

        /*val BASE_URL = "http://93.90.204.56/"
        var retrofit: Retrofit? = null

        retrofit = Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()

       Call<Event> call =  retrofit.create(EventAPI::class.java).eventRead("")

        RetrofitClient.getRetrofitInstance(this)?.create(EventAPI::class.java)?.eventRead("")

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = RetrofitClient.getRetrofitInstance(this)?.create(EventAPI::class.java)
        val call = service?.eventRead("")

        call?.enqueue(object : Callback<Event> {
            override fun onResponse(call: Call<Event>, response: Response<Event>) {

            }
            override fun onFailure(call: Call<Event>, t: Throwable) {

            }
        })*/

        datesList?.let { dateInit(it) }
        placeList?.let { placeInit(it) }


        /*mapViewPlace.getMapAsync(OnMapReadyCallback {

            googleMap = it

            val location = LatLng(13.03,77.60)

            googleMap.addMarker(MarkerOptions().position(location).title("My Location"))
        })*/

        mapView.apply {

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

            takePartVisible.visibility = GONE

            addGuestVisible.visibility = VISIBLE

        }

        takePartVisible.setOnClickListener {

            takePartVisible.visibility = GONE

            addGuestVisible.visibility = VISIBLE

            tripCalenderBackground.visibility = GONE

            addGuestBackground.visibility = VISIBLE

            //eventDetailViewModel.eventAccepted(id.toString(), event_obj)
        }


        /*tripCalenderIcon.setOnClickListener {

            val data = Invitation(100, StatusEnum.AC,1000)

            //eventDetailViewModel.eventInvitationAccepted(id.toString(),AC,data)

        }

        tripCalenderIcon.setOnClickListener {

            eventDetailViewModel.eventAccepted(id.toString(), event_obj)
        }*/

      /*  takePartVisible.setOnClickListener {


        }*/

        participateToActivityIcon.setOnClickListener {


        }


        /*descriptionFrameBackground.setOnClickListener {

            totalCostBackground.visibility = GONE

            fragmentManager.beginTransaction().add(R.id.descriptionFrameBackground, createEventFullDescriptionFragment).addToBackStack(null).commit()
        }*/

        //addFragment()
    }

    fun dateInit(dates : List<EventDate>){

        val recyclerView = findViewById<RecyclerView>(R.id.VoteForDateMultipleRecyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        /*dates.add(EventDate(1,"Thu, Mar 13 2019", "Wed, Mar 14 2019 06:00 pm",10))
        dates.add(EventDate(2,"Fri, Mar 16 2019", "Wed, Mar 17 2019 06:00 pm",20))
        dates.add(EventDate(3,"Sat, Mar 17 2019", "Wed, Mar 18 2019 06:00 pm",30))
        dates.add(EventDate(4,"Sun, Mar 19 2019", "Wed, Mar 20 2019 06:00 pm",40))
        dates.add(EventDate(4,"Sun, Mar 19 2019", "Wed, Mar 20 2019 06:00 pm",40))
        dates.add(EventDate(4,"Sun, Mar 19 2019", "Wed, Mar 20 2019 06:00 pm",40))
        dates.add(EventDate(4,"Sun, Mar 19 2019", "Wed, Mar 20 2019 06:00 pm",40))*/

        val adapter = VoteForDateMultipleListAdapter(dates,this, eventDetailViewModel)

        recyclerView.adapter = adapter

    }

    fun placeInit(places : List<EventPlace>){

        val recyclerView = findViewById<RecyclerView>(R.id.VoteForPlaceMultipleRecyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        //val places = ArrayList<EventPlace>()

        /*places.add(MultiplePlaceDisplay(1,"First Place","Bangalore","People10, Bangalore",
                10))
        places.add(MultiplePlaceDisplay(2,"Second Place","Chennai","People10 Chennai",
                20))
        places.add(MultiplePlaceDisplay(3,"Third Place","Hyderabad","People10 Hyderabad",
                30))
        places.add(MultiplePlaceDisplay(4,"Fourth Place","Mumbai","People10 Mumbai",
                40))*/

        val adapter = VoteForPlaceMultipleListAdapter(places,this)

        recyclerView.adapter = adapter

    }

    /*fun addFragment() {

        var i = 1

        val fragtransaction = fragmentManager.beginTransaction()

        if (i == 0) {

            fragtransaction.add(R.id.dateFrameBackground, createVoteForDateFragment)
            fragtransaction.add(R.id.placeFrameBackground, createVoteForPlaceFragment)

        } else if (i == 1) {

            fragtransaction.add(R.id.dateFrameBackground, createVoteForDateMultipleFragment)
            fragtransaction.add(R.id.placeFrameBackground, createVoteForPlaceMultipleFragment)

            val dateView: View = findViewById(R.id.dateFrameBackground)
            addOnClickToDateView(dateView)

            val placeView: View = findViewById(R.id.placeFrameBackground)
            addOnClickToPlaceView(placeView)
        }

        fragtransaction.addToBackStack(null)

        fragtransaction.commit()
    }*/

   /* override fun addOnClickToDateView(view: View) {

        *//*view.setOnClickListener {

            *//**//*val textview : TextView = view.findViewById(R.id.textviewVoteFor)
            addOnClickToDateBackView(textview)*//**//*

            *//**//*val textview : TextView = view.findViewById(R.id.textviewVoteFor)
            //addOnClickToDateBackView(textview)

            textview.setOnClickListener {

                Toast.makeText(this,"TextView is Clicked",Toast.LENGTH_SHORT).show()
                val viewtransaction = fragmentManager.beginTransaction()

                viewtransaction.replace(R.id.dateFrameBackground, createVoteForDateMultipleFragment).addToBackStack(null).commit()
            }*//**//*

            val viewtransaction = fragmentManager.beginTransaction()

            viewtransaction.replace(R.id.dateFrameBackground, createVoteForDateMultipleListFragment).addToBackStack(null).commit()

            val textview : TextView = view.findViewById(R.id.textviewVoteFor)
            addOnClickToDateBackView(textview)
        }*//*
    }

    override fun addOnClickToDateBackView(view: View) {

        *//*view.setOnClickListener {

            val viewtransaction = fragmentManager.beginTransaction()

            viewtransaction.replace(R.id.dateFrameBackground, createVoteForDateMultipleFragment).addToBackStack(null).commit()
        }*//*
    }

    override fun addOnClickToPlaceView(view: View) {

        *//*view.setOnClickListener {

            val viewtransaction = fragmentManager.beginTransaction()

            viewtransaction.replace(R.id.placeFrameBackground, createVoteForPlaceMultipleListFragment).addToBackStack(null).commit()
        }*//*
    }

    override fun addOnClickToPlaceBackView(view: View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addOnClickToDescriptionBackView(view: View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addOnClickToActivityAssociatedBackView(view: View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addOnClickToEverybodyComeBackView(view: View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addOnClickCommentsBackView(view: View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }*/
}

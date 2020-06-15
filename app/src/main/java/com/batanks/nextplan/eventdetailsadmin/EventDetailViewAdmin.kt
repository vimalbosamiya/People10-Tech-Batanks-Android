package com.batanks.nextplan.eventdetailsadmin

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.Window
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
import com.batanks.nextplan.home.fragment.action.AddActionFragment
import com.batanks.nextplan.home.fragment.place.AddPlaceFragment
import com.batanks.nextplan.home.fragment.tabfragment.AddActivityFragment
import com.batanks.nextplan.home.fragment.tabfragment.ButtonContract
import com.batanks.nextplan.home.fragment.tabfragment.publicplan.viewmodel.PublicPlanViewModel
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.swagger.api.EventAPI
import com.batanks.nextplan.swagger.model.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_event_detail_view_admin.*
import kotlinx.android.synthetic.main.activity_event_detail_view_admin.addGuestBackground
import kotlinx.android.synthetic.main.layout_add_guests.*
import kotlinx.android.synthetic.main.layout_add_guests.view.*
import kotlinx.android.synthetic.main.layout_add_plan_add_period.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EventDetailViewAdmin : BaseAppCompatActivity(), ButtonContract, View.OnClickListener,
        VoteForDateMultipleListAdapterAdmin.AddPeriodRecyclerViewCallBack,
        VoteForPlaceMultipleListAdapterAdmin.AddPlaceRecyclerViewCallBack,
        AddPlaceFragment.AddPlaceFragmentListener,
        AddActionFragment.AddActionFragmentListener,
        EventActionListAdapterAdmin.AddActionRecyclerViewCallBack,
        AddActivityFragment.AddActivityFragmentListener,
        EventActivityListAdapterAdmin.AddActivityRecyclerViewCallBack{

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
    var tasks = ArrayList<Task>()
    var activities = ArrayList<Activity>()


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

        tasks.add(Task(1,"1000","Task 1","Un de chaque saveur Description (facultative) Cupcake ipsum dolor sit amet sugar plum soufflé. Jelly beans I love I love cotton candy icing sweet roll pastry brownie.","1000",true,1))
        tasks.add(Task(2,"1000","Task 2","Un de chaque saveur Description (facultative) Cupcake ipsum dolor sit amet sugar plum soufflé. Jelly beans I love I love cotton candy icing sweet roll pastry brownie.","1000",true,1))
        tasks.add(Task(3,"1000","Task 3","Un de chaque saveur Description (facultative) Cupcake ipsum dolor sit amet sugar plum soufflé. Jelly beans I love I love cotton candy icing sweet roll pastry brownie.","1000",true,1))
        tasks.add(Task(4,"1000","Task 4","Un de chaque saveur Description (facultative) Cupcake ipsum dolor sit amet sugar plum soufflé. Jelly beans I love I love cotton candy icing sweet roll pastry brownie.","1000",true,1))
        /*tasks.add(Task(5,"1000","Task 5","Un de chaque saveur Description (facultative) Cupcake ipsum dolor sit amet sugar plum soufflé. Jelly beans I love I love cotton candy icing sweet roll pastry brownie.","",true,1))*/
        //places.add(place) as EventPlace

        activities.add(Activity(1,place1,"1000","Activity 1","","",10,"",true,10,vote))
        activities.add(Activity(2,place1,"1000","Activity 2","","",10,"",true,10,vote))
        activities.add(Activity(3,place1,"1000","Activity 3","","",10,"",true,10,vote))
        activities.add(Activity(4,place1,"1000","Activity 4","","",10,"",true,10,vote))


        dateInitAdmin(dates)
        placeInitAdmin(places)
        taskInitAdmin(tasks)
        activityInitAdmin(activities)


        /*eventInfoMapView.apply {

            onCreate(null)
            getMapAsync{

                val LATLNG = LatLng(13.03,77.60)

                with(it){

                    onResume()
                    moveCamera(CameraUpdateFactory.newLatLngZoom(LATLNG, 13f))
                    addMarker(MarkerOptions().position(LATLNG))
                }
            }
        }*/

        userIcon.setOnClickListener(this)
        userIconInFull.setOnClickListener(this)
        dateDropDown.setOnClickListener(this)
        dateDropDownMulti.setOnClickListener(this)
        placeDropDown.setOnClickListener(this)
        placeDropDownMulti.setOnClickListener(this)
        totalParticipantsDropDown.setOnClickListener(this)
        totalParticipantsDropDownMulti.setOnClickListener(this)
        totalCommentsDropDown.setOnClickListener(this)
        totalCommentsDropDownMulti.setOnClickListener(this)
        tripCalenderBackground.setOnClickListener(this)
        addGuestBackground.setOnClickListener(this)
        addPeriodImg.setOnClickListener(this)
        addPlaceImg.setOnClickListener(this)
        addActionBackgroundImg.setOnClickListener(this)
        addActivityBackgroundImg.setOnClickListener(this)

        //dateBackgroundMultiple.setOnClickListener(this)

       /* totalCostBackground.setOnClickListener {

            totalCostBackground.visibility = GONE

            totalCostBackgroundFull.visibility = VISIBLE

        }

        totalCostBackgroundFull.setOnClickListener {

            totalCostBackgroundFull.visibility = GONE

            totalCostBackground.visibility = VISIBLE

        }*/

        /*eventInfoBackground.setOnClickListener {

            eventInfoBackground.visibility = GONE

            eventInfobackgroundMulti.visibility = VISIBLE

        }

        imgInfoMultiLoader.setOnClickListener {

            eventInfobackgroundMulti.visibility = GONE

            eventInfoBackground.visibility = VISIBLE

        }*/

        /*imgHideMap.setOnClickListener {

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
        }*/

        /*takePartVisible.setOnClickListener {

            takePartVisible.visibility = GONE

            addGuestVisible.visibility = VISIBLE

            tripCalenderBackground.visibility = GONE

            addGuestBackground.visibility = VISIBLE

            //eventDetailViewModel.eventAccepted(id.toString(), event_obj)
        }*/


    }

    fun addGuestsDialogShow(){
        /*val addGuestsView = LayoutInflater.from(this).inflate(R.layout.layout_add_guests,null)
        val addGuestsBuilder = AlertDialog.Builder(this).setView(addGuestsView)
        val addGuestsDialog = addGuestsBuilder.show()*/

        val addGuestsView = Dialog(this/*,android.R.style.Theme_Translucent_NoTitleBar*/)
        addGuestsView.requestWindowFeature(Window.FEATURE_NO_TITLE)
        addGuestsView.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        addGuestsView.setCancelable(false)
        addGuestsView.setContentView(R.layout.layout_add_guests)
        addGuestsView.show()

        addGuestsView.countTextView.text = noOfGuests.text

        addGuestsView.textViewCancel.setOnClickListener {

            addGuestsView.dismiss()
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

            addGuestsView.dismiss()

        }

        /*addGuestsView.textViewCancel.setOnClickListener {

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

        }*/

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

        val actionRecyclerView = findViewById<RecyclerView>(R.id.actionRecyclerViewAdmin) as RecyclerView
        actionRecyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = EventActionListAdapterAdmin(tasks,this)
        actionRecyclerView.adapter = adapter

    }

    fun activityInitAdmin(activity : List<Activity>){

        val activityRecyclerView = findViewById<RecyclerView>(R.id.activityRecyclerViewAdmin) as RecyclerView
        activityRecyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = EventActivityListAdapterAdmin(activity,this)
        activityRecyclerView.adapter = adapter

    }


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

        val placeRecyclerView = findViewById<RecyclerView>(R.id.VoteForPlaceMultipleRecyclerViewAdmin) as RecyclerView

        placeRecyclerView?.adapter?.notifyDataSetChanged()
    }

    override fun addActionClicked() {

        this.supportFragmentManager
                .beginTransaction()
                .add(AddActionFragment(this), AddActionFragment::class.java.canonicalName)
                .commitAllowingStateLoss()

        val actionRecyclerView = findViewById<RecyclerView>(R.id.actionRecyclerViewAdmin) as RecyclerView

        actionRecyclerView?.adapter?.notifyDataSetChanged()
    }

    override fun addActivityClicked() {

        this.supportFragmentManager
                .beginTransaction()
                .add(AddActivityFragment(this), AddActivityFragment::class.java.canonicalName)
                .commitAllowingStateLoss()

        val activityRecyclerView = findViewById<RecyclerView>(R.id.activityRecyclerViewAdmin) as RecyclerView

        activityRecyclerView?.adapter?.notifyDataSetChanged()
    }

    override fun addPeopleClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun addPlaceFragmentAddressFetch(place: Place) {

        (this.supportFragmentManager.findFragmentByTag(AddPlaceFragment::class.java.canonicalName)
                as? AddPlaceFragment)?.dismiss()

        places.add(EventPlace(places.size + 1, place, place.name, place.address, place.zipcode, place.city, place.country, place.map, mutableListOf()))

        //Toast.makeText(this,place.map.toString(),Toast.LENGTH_LONG).show()

        val placeRecyclerView = findViewById<RecyclerView>(R.id.VoteForPlaceMultipleRecyclerViewAdmin) as RecyclerView
        placeRecyclerView?.adapter?.notifyDataSetChanged()
    }

    override fun cancelPlaceFragmentAddressFetch() {

        (this.supportFragmentManager.findFragmentByTag(AddPlaceFragment::class.java.canonicalName)
                as? AddPlaceFragment)?.dismiss()
    }


    override fun AddActionFragmentFetch(task: Task) {

        (this.supportFragmentManager.findFragmentByTag(AddActionFragment::class.java.canonicalName)
                as? AddActionFragment)?.dismiss()

        tasks.add(Task(tasks.size + 1,task.price, task.name, task.description, task.price_currency, task.per_person, task.assignee))

        val actionRecyclerView = findViewById<RecyclerView>(R.id.actionRecyclerViewAdmin) as RecyclerView
        actionRecyclerView?.adapter?.notifyDataSetChanged()
    }

    override fun cancelActionFragmentFetch() {

        (this.supportFragmentManager.findFragmentByTag(AddActionFragment::class.java.canonicalName)
                as? AddActionFragment)?.dismiss()
    }

    override fun AddActivityFragmentFetch(activity: Activity) {

        (this.supportFragmentManager.findFragmentByTag(AddActivityFragment::class.java.canonicalName)
                as? AddActivityFragment)?.dismiss()

        val place1 : Place = Place("Nellore","Buchi","524305","Nellore","India",true,27.2038,77.5011)

       activities.add(Activity(activities.size + 1,place1,activity.price,activity.title,activity.detail,activity.date,activity.max_participants,activity.price_currency,activity.per_person,activity.duration,activity.participants))

        val activityRecyclerView = findViewById<RecyclerView>(R.id.activityRecyclerViewAdmin) as RecyclerView

        activityRecyclerView?.adapter?.notifyDataSetChanged()
    }

    override fun CancelActivityFragmentFetch() {

        (this.supportFragmentManager.findFragmentByTag(AddActivityFragment::class.java.canonicalName)
                as? AddActivityFragment)?.dismiss()
    }


    override fun closeButtonAddPeriodItemListener(pos: Int) {

        val dateRecyclerView = findViewById<RecyclerView>(R.id.VoteForDateMultipleRecyclerViewAdmin) as RecyclerView
        dateRecyclerView?.adapter?.notifyDataSetChanged()
    }

    override fun closeButtonAddPlaceItemListener(pos: Int) {

        val placeRecyclerView = findViewById<RecyclerView>(R.id.VoteForPlaceMultipleRecyclerViewAdmin) as RecyclerView
        placeRecyclerView?.adapter?.notifyDataSetChanged()

    }

    override fun closeButtonAddActionItemListener(pos: Int) {

        val actionRecyclerView = findViewById<RecyclerView>(R.id.actionRecyclerViewAdmin) as RecyclerView
        actionRecyclerView?.adapter?.notifyDataSetChanged()

    }

    override fun closeButtonAddActivityItemListener(pos: Int) {

        val activityRecyclerView = findViewById<RecyclerView>(R.id.activityRecyclerViewAdmin) as RecyclerView
        activityRecyclerView?.adapter?.notifyDataSetChanged()
    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.userIcon -> {

                organizerInitial.visibility = GONE

                organizerFull.visibility = VISIBLE
            }

            R.id.userIconInFull -> {

                organizerFull.visibility = GONE

                organizerInitial.visibility = VISIBLE
            }

            R.id.dateDropDown -> {

                dateBackgroundMultiple.visibility = GONE

                dateDropDownBackgroundMultiple.visibility = VISIBLE
            }

            R.id.dateDropDownMulti -> {

                dateDropDownBackgroundMultiple.visibility = GONE

                dateBackgroundMultiple.visibility = VISIBLE
            }

            R.id.placeDropDown -> {

                placeBackgroundMulti.visibility = GONE

                placeDropDownBackgroundMultiple.visibility = VISIBLE

            }

            R.id.placeDropDownMulti -> {

                placeDropDownBackgroundMultiple.visibility = GONE

                placeBackgroundMulti.visibility = VISIBLE

            }

            R.id.totalParticipantsDropDown -> {

                participantsListBackground.visibility = GONE

                participantsListBackgroundMulti.visibility = VISIBLE

            }

            R.id.totalParticipantsDropDownMulti -> {

                participantsListBackgroundMulti.visibility = GONE

                participantsListBackground.visibility = VISIBLE

            }

            R.id.totalCommentsDropDown -> {

                commentsBackgroundConstraint.visibility = GONE

                commentsBackgroundMulti.visibility = VISIBLE

            }

            R.id.totalCommentsDropDownMulti -> {

                commentsBackgroundMulti.visibility = GONE

                commentsBackgroundConstraint.visibility = VISIBLE

            }

            R.id.tripCalenderBackground -> {

                tripCalenderBackground.visibility = GONE

                addGuestBackground.visibility = VISIBLE

            }

            R.id.addPeriodImg -> {

                addPeriodClicked()

            }

            R.id.addPlaceImg -> {

                addPlaceClicked()
            }

            R.id.addActionBackgroundImg -> {

                addActionClicked()
            }

            R.id.addActivityBackgroundImg -> {

                addActivityClicked()
            }

            R.id.addGuestBackground -> {

                addGuestsDialogShow()
            }
        }


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




}

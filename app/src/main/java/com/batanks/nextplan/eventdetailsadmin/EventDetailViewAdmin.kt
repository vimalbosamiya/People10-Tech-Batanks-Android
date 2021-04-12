package com.batanks.nextplan.eventdetailsadmin

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.MotionEvent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseAppCompatActivity
import com.batanks.nextplan.arch.response.Status
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.common.getLoadingDialog
import com.batanks.nextplan.eventdetails.viewmodel.EventDetailViewModel
import com.batanks.nextplan.eventdetailsadmin.adapter.*
import com.batanks.nextplan.home.HomePlanPreview
import com.batanks.nextplan.home.fragment.CreatePlanFragment
import com.batanks.nextplan.home.fragment.action.AddActionFragment
import com.batanks.nextplan.home.fragment.contacts.AddContactsFragment
import com.batanks.nextplan.home.fragment.place.AddPlaceFragment
import com.batanks.nextplan.home.fragment.tabfragment.AddActivityFragment
import com.batanks.nextplan.home.fragment.tabfragment.ButtonContract
import com.batanks.nextplan.invitationstatus.InvitationStatus
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.swagger.api.EventAPI
import com.batanks.nextplan.swagger.model.*
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_event_detail_view_admin.*
import kotlinx.android.synthetic.main.activity_event_detail_view_admin.addGuestBackground
import kotlinx.android.synthetic.main.comments_card_admin.*
import kotlinx.android.synthetic.main.everybody_come_card_admin.*
import kotlinx.android.synthetic.main.layout_add_guests.*
import kotlinx.android.synthetic.main.layout_comment_display.*
import kotlinx.android.synthetic.main.vote_for_date_card_admin.*
import kotlinx.android.synthetic.main.vote_for_place_card_admin.*

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EventDetailViewAdmin : BaseAppCompatActivity(), ButtonContract, AddCommentImplementation,View.OnClickListener,
        VoteForDateMultipleListAdapterAdmin.AddPeriodRecyclerViewCallBack,
        VoteForPlaceMultipleListAdapterAdmin.AddPlaceRecyclerViewCallBack,
        AddPlaceFragment.AddPlaceFragmentListener,
        AddActionFragment.AddActionFragmentListener,
        EventActionListAdapterAdmin.AddActionRecyclerViewCallBack,
        AddActivityFragment.AddActivityFragmentListener,
        EventActivityListAdapterAdmin.AddActivityRecyclerViewCallBack,
        EveryBodyComeListAdapterAdmin.AddPeopleRecyclerViewCallBack,
        AddCommentsFragment.AddCommentsFragmentListener,
        CommentsListAdapterAdmin.AddCommentsRecyclerViewCallBack,
        AddContactsFragment.AddContactsFragmentListner{

    var event_obj : Event? = null
    var creator : Creator? = null
    private var creatorId : Int = 0
    private var id : Int = 0
    private val position : Int = -1

    private var userName : String? = null

    var getCategory : CategoryList? = null
    var getPeriodicity : Periodicity? = null
    var getCreator :  Creator? = null

    var getDates : ArrayList<EventDate> = arrayListOf()
    var getPlaces : ArrayList<EventPlace> = arrayListOf()
    var getTasks : ArrayList<Task> = arrayListOf()
    var getActivities : ArrayList<Activity> = arrayListOf()
    var getGuests : ArrayList<Guests> = arrayListOf()
    var attendingGuests : ArrayList<Guests> = arrayListOf()
    var getComments : ArrayList<Comment> = arrayListOf()

    /*var dates = ArrayList<EventDate>()
    var places = ArrayList<EventPlace>()
    var tasks = ArrayList<Task>()
    var activities = ArrayList<Activity>()
    var contacts = ArrayList<String>()
    var comments = ArrayList<Comment>()*/

    private val eventDetailViewModel: EventDetailViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            RetrofitClient.getRetrofitInstance(this)?.create(EventAPI::class.java)?.let {
                EventDetailViewModel(it)
            }
        }).get(EventDetailViewModel::class.java)
    }

    var postDates : ArrayList<PostDates> = arrayListOf()
    var postPlaces : ArrayList<PostPlaces> = arrayListOf()
    var postTasks : ArrayList<PostTasks> = arrayListOf()
    var postActivities : ArrayList<PostActivities> = arrayListOf()
    var postGuests : PostGuests? = null
    var postComments : ArrayList<PostComments> = arrayListOf()
    var postContacts : ArrayList<String> = arrayListOf()

    lateinit var addPeopleRecyclerView: RecyclerView
    lateinit var commentsListRecyclerViewAdmin :RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail_view_admin)

        id  = intent.getIntExtra("ID",0)

        userName = getSharedPreferences("USER_DETAILS", MODE_PRIVATE).getString("USERNAME",null)

        addPeopleRecyclerView = findViewById(R.id.addPeopleRecyclerView)
        addPeopleRecyclerView.layoutManager = LinearLayoutManager(this)

        commentsListRecyclerViewAdmin = findViewById(R.id.commentsListRecyclerViewAdmin)
        commentsListRecyclerViewAdmin.layoutManager = LinearLayoutManager(this)

        loadingDialog = this.getLoadingDialog(0, R.string.loading_page_please_wait, theme = R.style.AlertDialogCustom)

        //editPlan()

        guestsHolder.setOnClickListener {

            intent = Intent(this, InvitationStatus :: class.java)
            intent.putExtra("ID", id)
            startActivity(intent)
            //finish()
        }

        eventDetailViewModel.getEventData(id.toString())

        eventDetailViewModel.responseLiveData.observe(this, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    //showLoader()
                }

                Status.SUCCESS -> {

                    //hideLoader()

                    event_obj = response.data as Event

                    creator = event_obj!!.creator

                    creatorId = creator!!.id
                    organizer.text = creator!!.username
                    organizerInFull.text = creator!!.username
                    organizerFirstName.text = creator!!.first_name
                    organizerLastName.text = creator!!.last_name
                    organizerEmail.text = creator!!.email
                    //val mobileNumber : String = "+"+creator!!.phone_number.toString()
                    organizerMobileNumber.text = creator!!.phone_number.toString()
                    //println(creator!!.phone_number.toString())

                    if (!TextUtils.isEmpty(creator!!.picture)){

                        userIcon.background = null
                        Glide.with(this).load(creator!!.picture).circleCrop().into(userIcon)
                        Glide.with(this).load(creator!!.picture).circleCrop().into(userIconInFull)
                    }

                    if(creator!!.is_in_contacts == true){

                        textViewAddContact.visibility = GONE
                        addToContact.visibility = GONE
                    }

                    eventName.text = event_obj!!.title
                    category.text = event_obj!!.category.name
                    Glide.with(this).load(event_obj!!.category.picture).circleCrop().into(tripIcon)

                    if (event_obj!!._private == true){

                        privateIcon.visibility = VISIBLE

                    }else {

                        privateIcon.visibility = GONE
                    }

                    noOfGuests.text = event_obj!!.guests.size.toString()

                    /*for(item in event_obj!!.guests){

                        if (item.is_current_user == true){

                            guestsComing.text = item.people_coming.toString()

                            println(item.people_coming)

                            invitationId = item.invitation_id.toString()

                            if (item.status == "AC"){

                                eventAccepted = true

                                accepted()

                            } else if(item.status == "PD"){

                                pending()

                            } else if(item.status == "DN"){

                                declined()
                            }
                        }
                    }*/

                    val acceptedGuests : ArrayList<Guests> = arrayListOf()
                    val DeclinedGuests : ArrayList<Guests> = arrayListOf()

                    for (item in event_obj!!.guests){

                        if (item.status == "AC"){

                            acceptedGuests.add(item)

                            noOfParticipants.text = acceptedGuests.size.toString()

                        } else  if (item.status == "DN"){

                            DeclinedGuests.add(item)

                            declinedGuests.text = DeclinedGuests.size.toString()
                        }
                    }

                    costPerPerson.text = event_obj!!.price.toString()
                    costPerPersonSymbol.text = event_obj!!.price_currency
                    eventDescription.text = event_obj!!.detail

                    getDates = event_obj!!.dates

                    getDates?.let { dateInitAdmin(it) }

                    getPlaces = event_obj!!.places

                    getPlaces?.let { placeInitAdmin(it) }

                    getTasks = event_obj!!.tasks

                    getTasks?.let { taskInitAdmin(it) }

                    getActivities = event_obj!!.activities

                    getActivities?.let { activityInitAdmin(it) }

                    getGuests = event_obj!!.guests

                    getGuests?.let { peopleInitAdmin(it) }

                    getComments = event_obj!!.comments

                    getComments?.let { commentsInitAdmin(it) }

                    attendingGuests.clear()

                    for (item in getGuests){

                        if (item.status == "AC"){

                            if (!attendingGuests.contains(item)){

                                attendingGuests.add(item)
                            }
                        }
                    }

                    textViewAttendingMulti.text = attendingGuests.size.toString()

                    textViewAttending.text = attendingGuests.size.toString()

                    textViewTotalParticipantsMulti.text = getGuests.size.toString()

                    textViewTotalParticipants.text = getGuests.size.toString()

                    textViewTotalComments.text = event_obj!!.comments.size.toString()

                    textViewTotalCommentsMulti.text = event_obj!!.comments.size.toString()

                }

                Status.ERROR -> {
                   // hideLoader()
                    showMessage(response.error?.message.toString())
                    println(response.error)
                    //Toast.makeText(context() , "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        })

        eventDetailViewModel.responseLiveDataVoteDate.observe(this, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }

                Status.SUCCESS -> {

                    hideLoader()

                    eventDetailViewModel.getEventData(id.toString())
                }

                Status.ERROR -> {
                    hideLoader()

                    showMessage(response.error?.message.toString())
                    println(response.error)
                }
            }
        })

        eventDetailViewModel.responseLiveDataVotePlace.observe(this, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }

                Status.SUCCESS -> {

                    hideLoader()

                    eventDetailViewModel.getEventData(id.toString())
                }

                Status.ERROR -> {
                    hideLoader()

                    showMessage(response.error?.message.toString())
                    println(response.error)
                }
            }
        })

        eventDetailViewModel.responseLiveDataTaskPatch.observe(this, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }

                Status.SUCCESS -> {

                    hideLoader()

                    eventDetailViewModel.getEventData(id.toString())
                }

                Status.ERROR -> {
                    hideLoader()

                    showMessage(response.error?.message.toString())
                    println(response.error)
                }
            }
        })

        eventDetailViewModel.responseLiveDataGuest.observe(this, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }

                Status.SUCCESS -> {
                    hideLoader()

                    eventDetailViewModel.getEventData(id.toString())
                }

                Status.ERROR -> {
                    hideLoader()

                    showMessage(response.error?.message.toString())
                    println(response.error)
                }
            }
        })

        eventDetailViewModel.responseLiveDataComment.observe(this, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }

                Status.SUCCESS -> {

                    hideLoader()

                   /* val responseComment = response.data as Comment

                    getComments.add(responseComment)

                    commentsInit(getComments)

                    textViewTotalComments.text = getComments.size.toString()

                    textViewTotalCommentsMulti.text = getComments.size.toString()*/

                    Toast.makeText(this, getString(R.string.comment_added), Toast.LENGTH_SHORT).show()

                    eventDetailViewModel.getEventData(id.toString())

                    // listener.voteIconClicked()
                }

                Status.ERROR -> {
                    hideLoader()

                    showMessage(response.error?.message.toString())
                    println(response.error)
                }
            }
        })

        eventDetailViewModel.responseLiveDataEventDelete.observe(this, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }

                Status.SUCCESS -> {

                    hideLoader()

                    intent = Intent(this, HomePlanPreview :: class.java)
                    startActivity(intent)
                    finish()

                    Toast.makeText(this, getString(R.string.event_deleted), Toast.LENGTH_SHORT).show()
                }

                Status.ERROR -> {
                    hideLoader()

                    showMessage(response.error?.message.toString())
                    println(response.error)
                }
            }
        })


        dateInitAdmin(getDates)
        placeInitAdmin(getPlaces)
        taskInitAdmin(getTasks)
        activityInitAdmin(getActivities)
        peopleInitAdmin(getGuests)
        commentsInitAdmin(getComments)
        userIcon.setOnClickListener(this)
        userIconInFull.setOnClickListener(this)
        //dateSettingsIcon.setOnClickListener(this)
        hamburger.setOnClickListener(this)
        //placeSettingsIcon.setOnClickListener(this)
        //commentsSettingsIcon.setOnClickListener(this)
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
        //addPeriodImg.setOnClickListener(this)
        //dateCloseVotes.setOnClickListener(this)
        /*addPlaceImg.setOnClickListener(this)*/
      /*  addActionBackgroundImg.setOnClickListener(this)
        addActivityBackgroundImg.setOnClickListener(this)*/
        /*addPeople.setOnClickListener(this)
        addPeopleInitial.setOnClickListener(this)*/
        addCommentsImg.setOnClickListener(this)
        backArrow.setOnClickListener(this)
        removeConstraint.setOnClickListener(this)
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v: View? = getCurrentFocus()
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm?.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    fun addGuestsDialogShow(){

        val addGuestsView = Dialog(this/*,android.R.style.Theme_Translucent_NoTitleBar*/)
        addGuestsView.requestWindowFeature(Window.FEATURE_NO_TITLE)
        addGuestsView.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        addGuestsView.setCancelable(true)
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

        val adapter = VoteForDateMultipleListAdapterAdmin(dates,this,eventDetailViewModel ,this/*,thispublicPlanViewModel.eventDate*/, id.toString())
        dateRecyclerView.adapter = adapter
    }

    fun placeInitAdmin(places : ArrayList<EventPlace>){

        val placeRecyclerView = findViewById<RecyclerView>(R.id.VoteForPlaceMultipleRecyclerViewAdmin) as RecyclerView
        placeRecyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = VoteForPlaceMultipleListAdapterAdmin(places,this,eventDetailViewModel,this /*publicPlanViewModel.place*/, id.toString())
        placeRecyclerView.adapter = adapter

    }

    fun taskInitAdmin(tasks : ArrayList<Task>){

        val actionRecyclerViewAdmin = findViewById<RecyclerView>(R.id.actionRecyclerViewAdmin) as RecyclerView
        actionRecyclerViewAdmin.layoutManager = LinearLayoutManager(this)

        /*if(tasks.size <= 3){

            val params = actionRecyclerViewAdmin.getLayoutParams() as ConstraintLayout.LayoutParams
            params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
            actionRecyclerViewAdmin.setLayoutParams(params)
        }*/

        val adapter = EventActionListAdapterAdmin(tasks,this,this, eventDetailViewModel, id)
        actionRecyclerViewAdmin.adapter = adapter

    }

    fun activityInitAdmin(activity : ArrayList<Activity>){

        val activityRecyclerView = findViewById<RecyclerView>(R.id.activityRecyclerViewAdmin) as RecyclerView
        activityRecyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = EventActivityListAdapterAdmin(activity,this,this)
        activityRecyclerView.adapter = adapter

    }

    fun peopleInitAdmin(guests : ArrayList<Guests>){

        val adapter = EveryBodyComeListAdapterAdmin(guests,this,eventDetailViewModel,this, id)
        addPeopleRecyclerView.adapter = adapter

    }

    fun commentsInitAdmin(comments : ArrayList<Comment>){

        val adapter = CommentsListAdapterAdmin(comments, this, this, userName, eventDetailViewModel, id)
        commentsListRecyclerViewAdmin.adapter = adapter
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

        /*val fromDate = DatePickerDialog(this,R.style.AlertDialogTheme, DatePickerDialog.OnDateSetListener{ fromDatePicker, fromYear, fromMonth, fromDay ->
            val toDate = DatePickerDialog(this, R.style.AlertDialogTheme, DatePickerDialog.OnDateSetListener { toDatePicker, toYear, toMonth, toDay ->
                val fromTime = TimePickerDialog(this, R.style.AlertDialogTheme, TimePickerDialog.OnTimeSetListener { fromTimePicker, fromHourOfDay, fromMinute ->
                    val toTime = TimePickerDialog(this, R.style.AlertDialogTheme, TimePickerDialog.OnTimeSetListener { toTimePicker, toHourOfDay, toMinute ->

                        val cal = Calendar.getInstance()
                        cal.set(fromYear, fromMonth, fromDay, fromHourOfDay, fromMinute)

                        *//*FromDate*//*
                        val dateFormatter = SimpleDateFormat("E, MMM dd yyyy HH:mm a")
                        val startDate = dateFormatter.format(cal.time)
                        println(startDate)

                        *//*ToDate*//*
                        cal.set(toYear, toMonth, toDay, toHourOfDay, toMinute)
                        val endDate = dateFormatter.format(cal.time)
                        println(endDate)

                        *//*publicPlanViewModel.eventDate.add(EventDate(id = publicPlanViewModel.eventDate.size, start = startDate, end = endDate, votes = mutableListOf()))
                        dateRecyclerView?.adapter?.notifyDataSetChanged()*//*
                        //addPeriodButton.text = "ADD AN OTHER PERIOD"

                        var visible : Boolean

                        if (dates.size == 0){
                            visible = false
                        }

                        else{
                            visible = dates[0].visibility
                        }

                        dates.add(EventDate((dates.size + 1),startDate,endDate, mutableListOf(),visible))
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
        fromDate.show()*/

        val fromDate = DatePickerDialog(this, R.style.AlertDialogTheme, DatePickerDialog.OnDateSetListener { fromDatePicker, fromYear, fromMonth, fromDay ->

            val fromTime = TimePickerDialog(this, R.style.AlertDialogTheme, TimePickerDialog.OnTimeSetListener { fromTimePicker, fromHourOfDay, fromMinute ->

                val toDate = DatePickerDialog(this, R.style.AlertDialogTheme, DatePickerDialog.OnDateSetListener { toDatePicker, toYear, toMonth, toDay ->

                    val toTime = TimePickerDialog(this, R.style.AlertDialogTheme, TimePickerDialog.OnTimeSetListener { toTimePicker, toHourOfDay, toMinute ->

                        val cal = Calendar.getInstance()
                        cal.set(fromYear, fromMonth, fromDay, fromHourOfDay, fromMinute)

                        //FromDate
                        val dateFormatter = SimpleDateFormat("E, MMM dd yyyy hh:mm a")
                        val startDate = dateFormatter.format(cal.time)
                        println(startDate)

                        //ToDate
                        cal.set(toYear, toMonth, toDay, toHourOfDay, toMinute)
                        val endDate = dateFormatter.format(cal.time)
                        println(endDate)

                        /*  publicPlanViewModel.eventDate.add(EventDate(id = publicPlanViewModel.eventDate.size, start = startDate, end = endDate, votes = mutableListOf()))
                          addPeriodRecyclerView?.adapter?.notifyDataSetChanged()
                          addPeriodButton.text = "ADD AN OTHER PERIOD"
                          addPeriodButton.strokeColor = ColorStateList.valueOf(Color.WHITE)*/

                        var visible: Boolean

                        if (getDates.size == 0) {
                            visible = false
                        } else {
                            visible = getDates[0].visibility
                        }

                        //dates.add(EventDate((dates.size + 1), mutableListOf() ,startDate, endDate, visible))
                        postDates.add(PostDates(startDate,endDate))
                        dateRecyclerView?.adapter?.notifyDataSetChanged()

                    }, mHour, mMin, false)
                    toTime.show()

                }, mYear, mMonth, mDay)
                toDate.datePicker.minDate = System.currentTimeMillis()
                toDate.setCanceledOnTouchOutside(false)
                toDate.show()

            }, mHour, mMin, false)
            fromTime.show()

        }, mYear, mMonth, mDay)
        fromDate.datePicker.minDate = System.currentTimeMillis()
        fromDate.setCanceledOnTouchOutside(false)
        fromDate.show()
    }

    override fun addPlaceClicked(placePosition : Int,placeList : ArrayList<PostPlaces>) {

        this.supportFragmentManager
                .beginTransaction()
                .add(AddPlaceFragment(this,position, arrayListOf()), AddPlaceFragment::class.java.canonicalName)
                .commitAllowingStateLoss()

        val placeRecyclerView = findViewById<RecyclerView>(R.id.VoteForPlaceMultipleRecyclerViewAdmin) as RecyclerView

        placeRecyclerView?.adapter?.notifyDataSetChanged()
    }

    override fun addActionClicked(taskPosition : Int, taskList : ArrayList<PostTasks>, editButtonClicked : Boolean) {

        this.supportFragmentManager
                .beginTransaction()
                .add(AddActionFragment(this, position, arrayListOf(), null, false), AddActionFragment::class.java.canonicalName)
                .commitAllowingStateLoss()

        val actionRecyclerView = findViewById<RecyclerView>(R.id.actionRecyclerViewAdmin) as RecyclerView

        actionRecyclerView?.adapter?.notifyDataSetChanged()
    }

    override fun addActivityClicked(activityPosition : Int, activityList : ArrayList<PostActivities>, editButtonClicked : Boolean) {

        this.supportFragmentManager
                .beginTransaction()
                .add(AddActivityFragment(this, position, arrayListOf(), null, false), AddActivityFragment::class.java.canonicalName)
                .commitAllowingStateLoss()

        val activityRecyclerView = findViewById<RecyclerView>(R.id.activityRecyclerViewAdmin) as RecyclerView

        activityRecyclerView?.adapter?.notifyDataSetChanged()
    }

    override fun addPeopleClicked() {

        this.supportFragmentManager
                .beginTransaction()
                .add(AddContactsFragment(this), AddContactsFragment::class.java.canonicalName)
                .commitAllowingStateLoss()

        addPeopleRecyclerView.adapter?.notifyDataSetChanged()

    }

    override fun addCommentClicked(){

        this.supportFragmentManager
                .beginTransaction()
                .add(AddCommentsFragment(this), AddCommentsFragment::class.java.canonicalName)
                .commitAllowingStateLoss()

        commentsListRecyclerViewAdmin.adapter?.notifyDataSetChanged()

    }


    override fun addPlaceFragmentAddressFetch(updatedPosition : Int ,place: PostPlaces) {

        (this.supportFragmentManager.findFragmentByTag(AddPlaceFragment::class.java.canonicalName)
                as? AddPlaceFragment)?.dismiss()

        var visible : Boolean

        if (getPlaces.size == 0){

            visible = false
        }

        else{

            visible = getPlaces[0].visibility
        }

        //places.add(EventPlace(places.size + 1, place.place, /*place.name, place.address, place.zipcode, place.city, place.country, place.map,*/ mutableListOf(),visible))
        postPlaces.add(PostPlaces(place.place,place.name,place.address,place.zipcode,place.city,place.country,place.map,visible))

        //Toast.makeText(this,place.map.toString(),Toast.LENGTH_LONG).show()

        val placeRecyclerView = findViewById<RecyclerView>(R.id.VoteForPlaceMultipleRecyclerViewAdmin) as RecyclerView
        placeRecyclerView?.adapter?.notifyDataSetChanged()
    }

    override fun cancelPlaceFragmentAddressFetch() {

        (this.supportFragmentManager.findFragmentByTag(AddPlaceFragment::class.java.canonicalName)
                as? AddPlaceFragment)?.dismiss()
    }


    override fun AddActionFragmentFetch(updatedPosition : Int , task: PostTasks) {

        (this.supportFragmentManager.findFragmentByTag(AddActionFragment::class.java.canonicalName)
                as? AddActionFragment)?.dismiss()

        //tasks.add(Task(tasks.size + 1,task.price, task.name, task.description, task.price_currency, task.per_person, task.assignee/*,task.assigneeName*/))
        postTasks.add(PostTasks(task.price,task.name,task.description,task.per_person,task.assignee))

        val actionRecyclerView = findViewById<RecyclerView>(R.id.actionRecyclerViewAdmin) as RecyclerView
        actionRecyclerView?.adapter?.notifyDataSetChanged()
    }

    override fun cancelActionFragmentFetch() {

        (this.supportFragmentManager.findFragmentByTag(AddActionFragment::class.java.canonicalName)
                as? AddActionFragment)?.dismiss()
    }


    override fun AddActivityFragmentFetch(updatedPosition : Int ,activity: PostActivities) {

        (this.supportFragmentManager.findFragmentByTag(AddActivityFragment::class.java.canonicalName)
                as? AddActivityFragment)?.dismiss()

        //val place1 : Place = Place("Nellore","Buchi","524305","Nellore","India",true,27.2038,77.5011)

        postActivities.add(PostActivities(activity.place,activity.price,activity.participants,activity.title,activity.detail,activity.date,
                                          activity.max_participants,activity.per_person,activity.duration))

       /*activities.add(Activity(activities.size + 1,activity.place,activity.price,acti,activity.title,activity.detail,activity.date,
                                activity.max_participants,activity.price_currency,activity.per_person,activity.duration,activity.participants))*/

        val activityRecyclerView = findViewById<RecyclerView>(R.id.activityRecyclerViewAdmin) as RecyclerView

        activityRecyclerView?.adapter?.notifyDataSetChanged()
    }

    override fun CancelActivityFragmentFetch() {

        (this.supportFragmentManager.findFragmentByTag(AddActivityFragment::class.java.canonicalName)
                as? AddActivityFragment)?.dismiss()
    }


    override fun addCommentFragmentFetch(comment: PostComments) {

        (this.supportFragmentManager.findFragmentByTag(AddCommentsFragment::class.java.canonicalName)
                as? AddCommentsFragment)?.dismiss()

        var visible : Boolean

        if (getComments.size == 0){

            visible = false
        }

        else{

            //visible = getComments[0].visibility
        }


        eventDetailViewModel.postComment(id.toString(), comment)
        //postComments.add(PostComments(comment.message))
        //comments.add(Comment(comment.comment,visible))

        commentsListRecyclerViewAdmin?.adapter?.notifyDataSetChanged()
    }

    override fun cancelCommentFragmentFetch() {

        (this.supportFragmentManager.findFragmentByTag(AddCommentsFragment::class.java.canonicalName)
                as? AddCommentsFragment)?.dismiss()

        commentsListRecyclerViewAdmin?.adapter?.notifyDataSetChanged()
    }


    private fun dateDeleteDialog() {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.layout_delete_popup)

        val deletePopup = dialog.findViewById(R.id.deletePopup) as ConstraintLayout

        deletePopup.setOnClickListener{


            getDates.forEach{

                it.visibility = true

            }

            dateBackgroundMultiple.visibility = GONE

            dateDropDownBackgroundMultiple.visibility = VISIBLE

            //closeButtonIcon.visibility = VISIBLE

            val dateRecyclerView = findViewById<RecyclerView>(R.id.VoteForDateMultipleRecyclerViewAdmin) as RecyclerView
            dateRecyclerView.adapter?.notifyDataSetChanged()

            dialog.dismiss()

        }

        dialog.show()
    }

    private fun placeDeleteDialog() {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.layout_delete_popup)

        val deletePopup = dialog.findViewById(R.id.deletePopup) as ConstraintLayout

        deletePopup.setOnClickListener{


            getPlaces.forEach{

                it.visibility = true

            }

            placeBackgroundMulti.visibility = GONE

            placeDropDownBackgroundMultiple.visibility = VISIBLE

            //placeCloseButton.visibility = VISIBLE

            val placeRecyclerView = findViewById<RecyclerView>(R.id.VoteForPlaceMultipleRecyclerViewAdmin) as RecyclerView
            placeRecyclerView.adapter?.notifyDataSetChanged()

            dialog.dismiss()

        }

        dialog.show()
    }

    private fun commentsDeleteDialog() {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.layout_delete_popup)

        val deletePopup = dialog.findViewById(R.id.deletePopup) as ConstraintLayout

        deletePopup.setOnClickListener{


            getComments.forEach{

                //it.visibility = true

            }

            commentsCloseButoon.visibility = VISIBLE

            commentsListRecyclerViewAdmin.adapter?.notifyDataSetChanged()

            dialog.dismiss()

        }

        dialog.show()
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

    override fun settingsButtonAddActionItemListener(pos: Int) {

        showDialog()
    }

    override fun closeButtonAddActivityItemListener(pos: Int) {

        val activityRecyclerView = findViewById<RecyclerView>(R.id.activityRecyclerViewAdmin) as RecyclerView
        activityRecyclerView?.adapter?.notifyDataSetChanged()
    }

    override fun settingsButtonAddActivityItemListener(pos: Int) {

        showDialog()
    }

    override fun closeButtonAddCommentItemListener(pos: Int) {

        commentsListRecyclerViewAdmin?.adapter?.notifyDataSetChanged()
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

         /*  R.id.dateSettingsIcon -> {



            }*/

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

           /* R.id.dateCloseVotes -> {

                //addPeriodClicked()

                //TODO : Need to make an API call here to close votes for date

            }*/

            /*R.id.addPeriodImg -> {

                addPeriodClicked()

            }*/

          /*  R.id.addPlaceImg -> {

                //addPlaceClicked()
            }*/

          /*  R.id.addActionBackgroundImg -> {

                addActionClicked()
            }*/

           /* R.id.addActivityBackgroundImg -> {

                addActivityClicked()
            }*/

/*            R.id.addPeople -> {

                addPeopleClicked()
            }

            R.id.addPeopleInitial -> {

                addPeopleClicked()
            }*/

            R.id.addCommentsImg -> {

                addCommentClicked()

                //Toast.makeText(this,"addcomment from details clicked",Toast.LENGTH_SHORT).show()

            }

            R.id.addGuestBackground -> {

                addGuestsDialogShow()
            }

            R.id.backArrow -> {

                intent = Intent(this, HomePlanPreview :: class.java)
                startActivity(intent)
                finish()
            }

            /*R.id.dateSettingsIcon -> {

                //frameLayoutAdmin.visibility = View.VISIBLE

                showDialog()
            }*/

            R.id.hamburger -> {

                showDialog()
          }

           /* R.id.placeSettingsIcon -> {

                showDialog()
            }*/

           /* R.id.commentsSettingsIcon -> {

                showDialog()
                //frameLayoutAdmin.visibility = View.VISIBLE

            }*/

            R.id.removeConstraint -> {

                eventDetailViewModel.apiEventDelete(id.toString())
            }
        }
    }

    private fun editPlan(editButtonClicked : Boolean, deleteButtonClicked : Boolean){

        val draft: Boolean = false

        Handler().postDelayed({



        }, DELAY)

        supportFragmentManager.beginTransaction()
                .add(R.id.frameLayoutAdmin, CreatePlanFragment(draft, id, editButtonClicked, deleteButtonClicked), CreatePlanFragment.TAG)
                .addToBackStack(CreatePlanFragment.TAG).commit()

        frameLayoutAdmin.visibility = View.VISIBLE
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.layout_edit_groups)
        val edit = dialog.findViewById(R.id.rl_edit_groups_edit) as RelativeLayout
        val delete = dialog.findViewById(R.id.rl_edit_groups_delete) as RelativeLayout

        edit.setOnClickListener {

            editPlan(true, false)
            dialog.dismiss()
        }
        delete.setOnClickListener {

            editPlan(false, true)

            dialog.dismiss()

        }
        dialog.show()

    }

    companion object {
        const val DELAY : Long = 3 * 1000 //times in milliseconds
    }

    override fun AddSelectedParticipants(participants: ArrayList<ActivityParticipants>) {

        println()
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

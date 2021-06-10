package com.batanks.nextplan.eventdetails

import ActivitySubscribe
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
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
import com.batanks.nextplan.eventdetails.adapter.*
import com.batanks.nextplan.eventdetails.dataclass.MultipleDateDisplay
import com.batanks.nextplan.eventdetails.dataclass.MultiplePlaceDisplay
import com.batanks.nextplan.eventdetails.fragment.*
import com.batanks.nextplan.eventdetails.viewmodel.AddContactViewModel
import com.batanks.nextplan.eventdetails.viewmodel.EventDetailViewModel
import com.batanks.nextplan.eventdetailsadmin.AddCommentImplementation
import com.batanks.nextplan.eventdetailsadmin.AddCommentsFragment
import com.batanks.nextplan.home.HomePlanPreview
import com.batanks.nextplan.home.viewmodel.HomePlanPreviewViewModel
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.swagger.api.AuthenticationAPI
import com.batanks.nextplan.swagger.api.EventAPI
import com.batanks.nextplan.swagger.api.GroupsAPI
import com.batanks.nextplan.swagger.model.*
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_event_detail_view.*
import kotlinx.android.synthetic.main.activity_event_detail_view.addGuestBackground
import kotlinx.android.synthetic.main.comments_card.*
import kotlinx.android.synthetic.main.edit_propriety.*
import kotlinx.android.synthetic.main.everybody_come_card.*
import kotlinx.android.synthetic.main.layout_add_guests.*
import kotlinx.android.synthetic.main.layout_add_guests.add
import kotlinx.android.synthetic.main.layout_add_guests.substract
import kotlinx.android.synthetic.main.layout_add_guests.view.*
import kotlinx.android.synthetic.main.layout_date_display.*
import kotlinx.android.synthetic.main.layout_eventdetails_organizer_details.*
import kotlinx.android.synthetic.main.vote_for_date_card.*
import kotlinx.android.synthetic.main.vote_for_place_card.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList

class EventDetailView (/*val listener: VoteDateClickImplementation, val placelistener: VotePlaceClickImplementation*/) : BaseAppCompatActivity(), View.OnClickListener,
        AddCommentImplementation, AddCommentsFragment.AddCommentsFragmentListener, CommentsListAdapter.AddCommentsRecyclerViewCallBack/*, OnClickFunImplementation*/ {

/*    val createVoteForDateFragment = CreateVoteForDateFragment()
    val createVoteForDateMultipleFragment = CreateVoteForDateMultipleFragment()
    val createVoteForDateMultipleListFragment = CreateVoteForDateMultipleListFragment()
    val createVoteForPlaceFragment = CreateVoteForPlaceFragment()
    val createVoteForPlaceMultipleFragment = CreateVoteForPlaceMultipleFragment()
    val createVoteForPlaceMultipleListFragment = CreateVoteForPlaceMultipleListFragment()
    val createEventFullDescriptionFragment = CreateEventFullDescriptionFragment()

    val fragmentManager = supportFragmentManager*/

    private val eventDetailViewModel: EventDetailViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            RetrofitClient.getRetrofitInstance(this)?.create(EventAPI::class.java)?.let {
                EventDetailViewModel(it)
            }
        }).get(EventDetailViewModel::class.java)
    }

    private val addContactViewModel: AddContactViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            RetrofitClient.getRetrofitInstance(this)?.create(GroupsAPI::class.java)?.let {
                AddContactViewModel(it)
            }
        }).get(AddContactViewModel::class.java)
    }

    var event_obj : Event? = null

    var creator : Creator? = null
    private var creatorId : Int = 0
    private var invitationId : String? = null
    private val amount : Int = 1
    private var acceptResponse : Invitation? = null
    private var guestResponse : Invitation? = null
    private var activityAcceptResponse : ActivitySubscribe? = null

    var getDates : ArrayList<EventDate>? = null
    var getPlaces : ArrayList<EventPlace> = arrayListOf()
    var getTasks : ArrayList<Task> = arrayListOf()
    var getActivities : ArrayList<Activity> = arrayListOf()
    var getGuests : ArrayList<Guests> = arrayListOf()
    var attendingGuests : ArrayList<Guests> = arrayListOf()
    var getComments : ArrayList<Comment> = arrayListOf()

    private var id : Int = 0

    private var userId : Int = 0
    private var userName : String? = null
    private var eventAccepted : Boolean = false

    lateinit var commentsList : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail_view)

        commentsList = findViewById(R.id.commentsList)
        commentsList.layoutManager = LinearLayoutManager(this)

         id  = intent.getIntExtra("ID",0)

        userId = getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).getInt("ID",0)
        userName = getSharedPreferences("USER_DETAILS", MODE_PRIVATE).getString("USERNAME",null)

        //loadingDialog = this.getLoadingDialog(0, R.string.loading_page_please_wait, theme = R.style.AlertDialogCustom)

        eventDetailViewModel.getEventData(id.toString())

        eventDetailViewModel.responseLiveData.observe(this, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }

                Status.SUCCESS -> {

                    hideLoader()

                    event_obj = response.data as Event
                    eventDetailViewModel.response = response.data as Event

                    creator = event_obj!!.creator

                    creatorId = creator!!.id
                    organizer.text = creator!!.username
                    organizerInFull.text = creator!!.username
                    organizerFirstName.text = creator!!.first_name
                    organizerLastName.text = creator!!.last_name
                    organizerEmail.text = creator!!.email
                    //val mobileNumber : String = "+"+creator!!.phone_number.toString()
                    organizerMobileNumber.text = creator!!.phone_number.toString()

                    val perPersonCost = (event_obj!!.price)!! /event_obj!!.guests.size
                    perPersonAmount.text = String.format("%.2f",perPersonCost)

                    if (!TextUtils.isEmpty(creator!!.picture)){

                        userIcon.background = null
                        Glide.with(this).load(creator!!.picture).circleCrop().into(userIcon)
                        Glide.with(this).load(creator!!.picture).circleCrop().into(userIconInFull)
                    }

                    if(creator!!.is_in_contacts == true){

                        textViewAddContact.visibility = GONE
                        addContactIcon.visibility = GONE
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

                    for(item in event_obj!!.guests){

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
                    }

                    val acceptedGuests : ArrayList<Guests> = arrayListOf()

                    for (item in event_obj!!.guests){

                        if (item.status == "AC"){

                            acceptedGuests.add(item)

                            noOfParticipants.text = /*event_obj!!.guests.size.toString()*/acceptedGuests.size.toString()
                        }
                    }

                    costPerPerson.text = event_obj!!.price.toString()
                    costPerPersonSymbol.text = event_obj!!.price_currency
                    eventDescription.text = event_obj!!.detail

                    //if (event_obj!!.dates.size > 1){ inVotingDate.visibility = View.VISIBLE } else { inVotingDate.visibility = View.GONE }

                    //if (event_obj!!.places.size > 1){ inVotingPlace.visibility = View.VISIBLE } else { inVotingPlace.visibility = View.GONE }

                    getDates = event_obj!!.dates

                    getDates?.let { dateInit(it) }

                    getPlaces = event_obj!!.places

                    getPlaces?.let { placeInit(it) }

                    getTasks = event_obj!!.tasks

                    getTasks?.let { taskInit(it) }

                    getActivities = event_obj!!.activities

                    getActivities?.let { activityInit(it) }

                    getGuests = event_obj!!.guests

                    getGuests?.let { everyBodyComeInit(it) }

                    getComments = event_obj!!.comments

                    getComments?.let { commentsInit(it) }

                    //everyBodyComeListAdapter(getGuests)

                    //if(getGuests.size != null)

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

                    //if (event_obj!!.vote_date_closed == true){ inVotingDate.visibility = GONE }else if (event_obj!!.vote_date_closed == false){ inVotingDate.visibility = VISIBLE }

                    //if (event_obj!!.vote_place_closed == true){ inVotingPlace.visibility = GONE }else if (event_obj!!.vote_place_closed == false){ inVotingPlace.visibility = VISIBLE }
                }

                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                    println(response.error)
                    //Toast.makeText(context() , "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        })

        eventDetailViewModel.responseLiveDataAccept.observe(this, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }

                Status.SUCCESS -> {

                    hideLoader()

                    acceptResponse = response.data as Invitation

                    eventDetailViewModel.getEventData(id.toString())

                    //guestsComing.text = acceptResponse!!.amount.toString()

                    if (eventAccepted == false && acceptResponse!!.amount >= 1){

                        eventAccepted = true

                        Toast.makeText(context() , getString(R.string.invite_accepted), Toast.LENGTH_SHORT).show()

                    }else if (eventAccepted == true && acceptResponse!!.amount >= 1){

                        Toast.makeText(context() , getString(R.string.guests_added), Toast.LENGTH_SHORT).show()

                    }else if (acceptResponse!!.amount == 0){

                        eventAccepted = false

                        Toast.makeText(context() , getString(R.string.invite_declined), Toast.LENGTH_SHORT).show()
                    }

                    //noOfGuests.text = acceptResponse!!.amount.toString()

                    if (acceptResponse!!.status == ACCEPT){

                        accepted()

                    } else if (acceptResponse!!.status == DECLINE) {

                        declined()

                    } else if (acceptResponse!!.status == PENDING) {

                        pending()
                    }
                }

                Status.ERROR -> {
                    hideLoader()

                    showMessage(response.error?.message.toString())
                    println(response.error)
                }
            }
        })

        eventDetailViewModel.responseLiveDataActivityAccept.observe(this, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }

                Status.SUCCESS -> {

                    hideLoader()

                    activityAcceptResponse = response.data as ActivitySubscribe

                    eventDetailViewModel.getEventData(id.toString())
                }

                Status.ERROR -> {
                    hideLoader()

                    showMessage(response.error?.message.toString())
                    println(response.error)
                }
            }
        })

        eventDetailViewModel.responseLiveDataActivityReject.observe(this, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }

                Status.SUCCESS -> {

                    hideLoader()

                    activityAcceptResponse = response.data as ActivitySubscribe

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

                    Toast.makeText(context() , getString(R.string.guests_added), Toast.LENGTH_SHORT).show()

                    guestResponse = response.data as Invitation

                   // noOfGuests.text = guestResponse!!.amount.toString()
                }

                Status.ERROR -> {
                    hideLoader()

                    showMessage(response.error?.message.toString())
                    println(response.error)
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

        eventDetailViewModel.responseLiveDataComment.observe(this, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }

                Status.SUCCESS -> {

                    hideLoader()

                    Toast.makeText(this, getString(R.string.comment_added),Toast.LENGTH_SHORT).show()

                    eventDetailViewModel.getEventData(id.toString())

                    /*val responseComment = response.data as Comment

                    getComments.add(responseComment)

                    commentsInit(getComments)

                    textViewTotalComments.text = getComments.size.toString()

                    textViewTotalCommentsMulti.text = getComments.size.toString()*/

                    // listener.voteIconClicked()
                }

                Status.ERROR -> {
                    hideLoader()

                    showMessage(response.error?.message.toString())
                    println(response.error)
                }
            }
        })

        eventDetailViewModel.responseLiveDataEditComment.observe(this, Observer { response ->

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

        eventDetailViewModel.responseLiveDataDeleteComment.observe(this, Observer { response ->

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

        addContactViewModel.responseLiveData.observe(this, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }

                Status.SUCCESS -> {

                    hideLoader()

                    textViewAddContact.visibility = GONE
                    addContactIcon.visibility = GONE

                    Toast.makeText(context() , getString(R.string.contact_added), Toast.LENGTH_SHORT).show()
                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                    println(response.error)
                    //Toast.makeText(context() , "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        })

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

        addguestIcon.setOnClickListener(this)
        addGuestImage.setOnClickListener(this)
        userIcon.setOnClickListener(this)
        userIconInFull.setOnClickListener (this)
        dateDropDown.setOnClickListener(this)
        dateDropDownMulti.setOnClickListener(this)
        placeDropDown.setOnClickListener(this)
        placeDropDownMulti.setOnClickListener(this)
        totalParticipantsDropDown.setOnClickListener(this)
        totalParticipantsDropDownMulti.setOnClickListener(this)
        totalCommentsDropDown.setOnClickListener(this)
        totalCommentsDropDownMulti.setOnClickListener(this)
        tripCalenderBackground.setOnClickListener(this)
        takePartImage.setOnClickListener(this)
        backArrow.setOnClickListener(this)
        addComment.setOnClickListener {

            addCommentClicked()
        }
        textViewAddContact.setOnClickListener(this)
        declineInvitationTextView.setOnClickListener(this)
    }

    fun dateInit(dates : ArrayList<EventDate>){

        val recyclerView = findViewById<RecyclerView>(R.id.VoteForDateMultipleRecyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = VoteForDateMultipleListAdapter(dates,this,eventDetailViewModel, id.toString())
        recyclerView.adapter = adapter
    }

    fun placeInit(places : List<EventPlace>){

        val recyclerView = findViewById<RecyclerView>(R.id.VoteForPlaceMultipleRecyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = VoteForPlaceMultipleListAdapter(places,this,eventDetailViewModel, id.toString())
        recyclerView.adapter = adapter
    }

    fun taskInit(tasks : List<Task>){

        val recyclerView = findViewById<RecyclerView>(R.id.actionRecyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        if (tasks.size <= 3){

            val params = recyclerView.getLayoutParams() as ConstraintLayout.LayoutParams
            params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
            recyclerView.setLayoutParams(params)
        }

        val adapter = EventActionListAdapter(tasks,this)
        recyclerView.adapter = adapter

    }

    fun activityInit(activity : ArrayList<Activity>){

        val recyclerView = findViewById<RecyclerView>(R.id.activityRecyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = EventActivityListAdapter(activity,this,eventDetailViewModel, id.toString())
        recyclerView.adapter = adapter

    }

    fun commentsInit(comments : ArrayList<Comment>){

        val recyclerView = findViewById<RecyclerView>(R.id.commentsList) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = CommentsListAdapter(comments, this, this, userName,eventDetailViewModel, id)
        recyclerView.adapter = adapter
    }

    fun everyBodyComeInit(guestsList :ArrayList<Guests>){

        val recyclerView = findViewById<RecyclerView>(R.id.everybodyComeList) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = EveryBodyComeListAdapter(guestsList,this)
        recyclerView.adapter = adapter

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

        addGuestsView.countTextView.text = guestsComing.text

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

            if (count > 1){

                count -= 1

                addGuestsView.countTextView.text = count.toString()
            }
        }
        addGuestsView.textViewOk.setOnClickListener {

            accept(addGuestsView.countTextView.text.toString().toInt())

            //eventDetailViewModel.eventInvitationAccepted(id.toString(), invitationId, GuestAmount(ACCEPT, addGuestsView.countTextView.text.toString().toInt()))

            addGuestsView.dismiss()

        }
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

            R.id.addguestIcon -> {

                addGuestsDialogShow()
            }

            R.id.addGuestImage -> {

                addGuestsDialogShow()
            }

            R.id.dateDropDownMulti ->{

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

                accept(amount)
            }

            R.id.takePartImage -> {

                accept(amount)
            }

            R.id.declineInvitationTextView -> {

                decline()
            }

            R.id.backArrow -> {

                intent = Intent(this, HomePlanPreview :: class.java)
                startActivity(intent)
                finish()
            }

            R.id.textViewAddContact -> {

                addContactViewModel.addContact(AddContact(creatorId))
            }

        }
    }

    private fun accept(amount: Int){

        eventDetailViewModel.acceptEvent(id.toString(), EventAccept(ACCEPT, amount))
    }

    private fun decline(){

        eventDetailViewModel.acceptEvent(id.toString(), EventAccept(DECLINE,0))
    }

    private fun accepted(){

        tripCalenderBackground.visibility = GONE
        addGuestBackground.visibility = VISIBLE
        takePartImage.visibility = GONE
        addGuestVisible.visibility = VISIBLE
        declineBackground.visibility = VISIBLE
    }

    private fun declined(){

        tripCalenderBackground.visibility = VISIBLE
        addGuestBackground.visibility = GONE
        takePartImage.visibility = VISIBLE
        addGuestVisible.visibility = GONE
        declineBackground.visibility = GONE
    }

    private fun pending(){

        tripCalenderBackground.visibility = VISIBLE
        addGuestBackground.visibility = GONE
        takePartImage.visibility = VISIBLE
        addGuestVisible.visibility = GONE
        declineBackground.visibility = VISIBLE
    }

    companion object{

        const val ACCEPT : String = "AC"
        const val PENDING : String = "PD"
        const val DECLINE : String = "DN"
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

       // commentsList?.adapter?.notifyDataSetChanged()

    }

    override fun cancelCommentFragmentFetch() {

        (this.supportFragmentManager.findFragmentByTag(AddCommentsFragment::class.java.canonicalName)
                as? AddCommentsFragment)?.dismiss()

        commentsList?.adapter?.notifyDataSetChanged()
    }

    override fun closeButtonAddCommentItemListener(pos: Int) {

        commentsList?.adapter?.notifyDataSetChanged()
    }

    override fun addCommentClicked() {

        this.supportFragmentManager
                .beginTransaction()
                .add(AddCommentsFragment(this), AddCommentsFragment::class.java.canonicalName)
                .commitAllowingStateLoss()

        commentsList.adapter?.notifyDataSetChanged()

    }

    interface VoteDateClickImplementation {

        fun voteIconClicked()
    }

    interface VotePlaceClickImplementation {

        fun voteIconClicked()
    }
}



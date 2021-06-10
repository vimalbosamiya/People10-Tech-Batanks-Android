package com.batanks.nextplan.home.fragment.tabfragment.privateplan

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseFragment
import com.batanks.nextplan.arch.response.Status
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.customfrequency.CustomFrequency
import com.batanks.nextplan.eventdetails.viewmodel.AddContactViewModel
import com.batanks.nextplan.eventdetails.viewmodel.EventDetailViewModel
import com.batanks.nextplan.home.fragment.CreatePlanFragment
import com.batanks.nextplan.home.fragment.action.AddActionFragment
import com.batanks.nextplan.home.fragment.action.AddActionRecyclerView
import com.batanks.nextplan.home.fragment.contacts.AddContactsFragment
import com.batanks.nextplan.home.fragment.contacts.ParticipantsAdapter
import com.batanks.nextplan.home.fragment.period.AddPeriodRecyclerViewPublic

import com.batanks.nextplan.home.fragment.place.AddPlaceFragment
import com.batanks.nextplan.home.fragment.place.AddPlaceRecyclerView
import com.batanks.nextplan.home.fragment.spinner.CustomArrayAdapter
import com.batanks.nextplan.home.fragment.tabfragment.AddActivityFragment
import com.batanks.nextplan.home.fragment.tabfragment.AddActivityRecyclerView
import com.batanks.nextplan.home.fragment.tabfragment.ButtonContract
import com.batanks.nextplan.home.fragment.tabfragment.publicplan.viewmodel.CategoryViewModel
import com.batanks.nextplan.home.fragment.tabfragment.publicplan.viewmodel.PublicPlanViewModel
import com.batanks.nextplan.home.markInRed
import com.batanks.nextplan.home.markRequiredInRed
import com.batanks.nextplan.home.markRequiredRed
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.swagger.api.CategoryAPI
import com.batanks.nextplan.swagger.api.EventAPI
import com.batanks.nextplan.swagger.api.GroupsAPI
import com.batanks.nextplan.swagger.model.*
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_private_new_plan.*
import kotlinx.android.synthetic.main.fragment_private_new_plan.actv_category
import kotlinx.android.synthetic.main.fragment_private_new_plan.categoryTextField
import kotlinx.android.synthetic.main.fragment_private_new_plan.detailDescriptionEditText
import kotlinx.android.synthetic.main.fragment_private_new_plan.detailDescriptionTextField
import kotlinx.android.synthetic.main.fragment_private_new_plan.howOftenEditText
import kotlinx.android.synthetic.main.fragment_private_new_plan.maxParticipantsTextField
import kotlinx.android.synthetic.main.fragment_private_new_plan.planNameEditText
import kotlinx.android.synthetic.main.fragment_private_new_plan.planNameTextField
import kotlinx.android.synthetic.main.fragment_public_new_plan.*

import kotlinx.android.synthetic.main.layout_add_plan_add_action.*
import kotlinx.android.synthetic.main.layout_add_plan_add_activity.*
import kotlinx.android.synthetic.main.layout_add_plan_add_people_public.*

import kotlinx.android.synthetic.main.layout_add_plan_add_period.*

import kotlinx.android.synthetic.main.layout_add_plan_add_place.*
import kotlinx.android.synthetic.main.layout_add_plan_footer.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PrivatePlanFragment (private var draft : Boolean, private val eventId : Int?, private val listener: PrivatePlanFragmentListener?, private val editButtonClicked : Boolean,
                           private val deleteButtonClicked : Boolean, private val isPrivtaeEvent : Boolean): BaseFragment(), ButtonContract, View.OnClickListener,
        AddPeriodRecyclerViewPublic.AddPeriodRecyclerViewCallBack,
        AddPlaceRecyclerView.AddPlaceRecyclerViewCallBack,
        AddActionFragment.AddActionFragmentListener,

        AddActionRecyclerView.AddActionRecyclerViewCallBack,
        AddActivityRecyclerView.AddActivityRecyclerViewCallBack,
        AddActivityFragment.AddActivityFragmentListener,
        AddPlaceFragment.AddPlaceFragmentListener,
        CustomFrequency.CustomFrequencyListener,
        AddContactsFragment.AddContactsFragmentListner{

    private val position : Int = -1
    var catregoryId : Int = 0


    private var fromDate : String? = null
    private var toDate : String? = null

    private var commentsChecked : Boolean = false

    private var planCreatedResponse : GetEventListHome? = null
    private var periodicity : Periodicity? = null
    private var planEditedResponse : Event? = null
    private var planEdited : Event? = null
    var event : Event? = null

    private var addedparticipants: ArrayList<ContactsList> = arrayListOf()
    var getDates : ArrayList<EventDate> = arrayListOf()

    var participantsIds : ArrayList<Int> = arrayListOf()

    private var addPeriodRecyclerViewPublic: RecyclerView? = null
    private var addPlaceRecyclerView: RecyclerView? = null
    private var actionRecyclerView : RecyclerView? = null
    private var activityRecyclerView : RecyclerView? = null
    private var addpeopleRecyclerView : RecyclerView? = null

    private val publicPlanViewModel: PublicPlanViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            getContext()?.let {
                RetrofitClient.getRetrofitInstance(it)?.create(EventAPI::class.java)?.let {
                    PublicPlanViewModel(it)
                }
            }
        }).get(PublicPlanViewModel::class.java)
    }

    private val eventDetailViewModel: EventDetailViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            getContext()?.let {
                RetrofitClient.getRetrofitInstance(it)?.create(EventAPI::class.java)?.let {
                    EventDetailViewModel(it)
                }
            }
        }).get(EventDetailViewModel::class.java)
    }

    private val categoryViewModel: CategoryViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            getContext()?.let {
                RetrofitClient.getRetrofitInstance(it)?.create(CategoryAPI::class.java)?.let {
                    CategoryViewModel(it)
                }
            }
        }).get(CategoryViewModel::class.java)
    }

    private val addContactViewModel: AddContactViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            getContext()?.let {
                RetrofitClient.getRetrofitInstance(it)?.create(GroupsAPI::class.java)?.let {
                    AddContactViewModel(it)
                }
            }
        }).get(AddContactViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_private_new_plan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (editButtonClicked == true){

            mapData(deleteButtonClicked)

            createPlanHolder.visibility = View.GONE
            updatePlanButton.visibility = View.VISIBLE

        } else if (draft== true){

            mapData(deleteButtonClicked)

            createPlanHolder.visibility = View.VISIBLE
            updatePlanButton.visibility = View.GONE

        } else if (deleteButtonClicked == true) {

            mapData(deleteButtonClicked)

            createPlanHolder.visibility = View.GONE
            updatePlanButton.visibility = View.VISIBLE

        } else {

            createPlanHolder.visibility = View.VISIBLE
            updatePlanButton.visibility = View.GONE
        }

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // in here you can do logic when backPress is clicked

                requireActivity().supportFragmentManager.findFragmentByTag(CreatePlanFragment.TAG)?.let { requireActivity().supportFragmentManager.beginTransaction().remove(it).commit()}

                if (editButtonClicked == false && deleteButtonClicked == false){

                    activity?.appBarLayout?.visibility = View.VISIBLE

                    activity?.extFab!!.visibility = View.VISIBLE
                }
            }
        })

        //loadingDialog = context?.getLoadingDialog(0, R.string.loading_list_please_wait, theme = R.style.AlertDialogCustom)

        categoryViewModel.getCategoryList()

        categoryViewModel.responseLiveData.observe(viewLifecycleOwner, Observer{ response ->

            when(response.status){
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()

                    categoryViewModel.response = response.data as InlineResponse200

                    categoryViewModel.categoryList = categoryViewModel.response!!.results

                    populateCategory(categoryViewModel.categoryList!!)

                    println(categoryViewModel.categoryList)
                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })

        view.setOnTouchListener(object : View.OnTouchListener {

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                if (event?.action == MotionEvent.ACTION_DOWN) {
                    if(v is TextInputEditText) {
                        val outRect = Rect()
                        v.getGlobalVisibleRect(outRect)
                        if (!outRect.contains(event.rawX as Int, event.rawY as Int)) {
                            v.clearFocus()
                            val imm = v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                        }
                    }
                }
                return false
            }
        })

        planNameTextField.markRequiredInRed()
        actv_category.markRequiredRed()
        addPeriodButton.markInRed()
        addPlaceButton.markInRed()

        publicPlanViewModel.responseLiveData.observe(viewLifecycleOwner, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }

                Status.SUCCESS -> {
                    hideLoader()

                    planCreatedResponse = response.data as GetEventListHome

                    listener?.refreshHomeFragmentData(true)

                    requireActivity().supportFragmentManager.findFragmentByTag(CreatePlanFragment.TAG)?.let { requireActivity().supportFragmentManager.beginTransaction().remove(it).commit()}

                    activity?.appBarLayout?.visibility = View.VISIBLE
                    activity?.extFab!!.visibility = View.VISIBLE
                    activity?.search!!.visibility = View.VISIBLE
                    activity?.notification!!.visibility = View.VISIBLE
                    activity?.img_settings!!.visibility = View.VISIBLE

                    if (planCreatedResponse!!.draft == true){

                        Toast.makeText(context,getString(R.string.draft_created), Toast.LENGTH_SHORT).show()

                    } else{

                        Toast.makeText(context,getString(R.string.plan_created), Toast.LENGTH_SHORT).show()
                    }


                    //listener.refreshHomeFragmentData(true)

                    //homePlanPreviewViewModel.eventList()

                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                    //homePlanPreviewViewModel.eventList()
/*
                    requireActivity().supportFragmentManager.findFragmentByTag(CreatePlanFragment.TAG)?.let { requireActivity().supportFragmentManager.beginTransaction().remove(it).commit()}

                    activity?.appBarLayout?.visibility = View.VISIBLE
                    activity?.extFab!!.visibility = View.VISIBLE
                    activity?.search!!.visibility = View.VISIBLE
                    activity?.notification!!.visibility = View.VISIBLE
                    activity?.img_settings!!.visibility = View.VISIBLE*/

                    println("Error coming from here" + response.error )

                }
            }
        })

        publicPlanViewModel.responseLiveData.observe(viewLifecycleOwner, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }

                Status.SUCCESS -> {
                    hideLoader()

                    planCreatedResponse = response.data as GetEventListHome

                    requireActivity().supportFragmentManager.findFragmentByTag(CreatePlanFragment.TAG)?.let { requireActivity().supportFragmentManager.beginTransaction().remove(it).commit()}

                    activity?.appBarLayout?.visibility = View.VISIBLE
                    activity?.extFab!!.visibility = View.VISIBLE
                    activity?.search!!.visibility = View.VISIBLE
                    activity?.notification!!.visibility = View.VISIBLE
                    activity?.img_settings!!.visibility = View.VISIBLE

                    if (planCreatedResponse!!.draft == true){

                        Toast.makeText(context,getString(R.string.draft_created), Toast.LENGTH_SHORT).show()

                    } else{

                        Toast.makeText(context,getString(R.string.plan_created), Toast.LENGTH_SHORT).show()
                    }


                    //listener.refreshHomeFragmentData(true)

                    //homePlanPreviewViewModel.eventList()

                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                    //homePlanPreviewViewModel.eventList()
/*
                    requireActivity().supportFragmentManager.findFragmentByTag(CreatePlanFragment.TAG)?.let { requireActivity().supportFragmentManager.beginTransaction().remove(it).commit()}

                    activity?.appBarLayout?.visibility = View.VISIBLE
                    activity?.extFab!!.visibility = View.VISIBLE
                    activity?.search!!.visibility = View.VISIBLE
                    activity?.notification!!.visibility = View.VISIBLE
                    activity?.img_settings!!.visibility = View.VISIBLE*/

                    println("Error coming from here" + response.error )

                }
            }
        })

        publicPlanViewModel.responseLiveDataUpdate.observe(viewLifecycleOwner, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }

                Status.SUCCESS -> {
                    hideLoader()

                    planEdited = response.data as Event

                    requireActivity().supportFragmentManager.findFragmentByTag(CreatePlanFragment.TAG)?.let { requireActivity().supportFragmentManager.beginTransaction().remove(it).commit()}
                    //requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()

                    /*activity?.appBarLayout?.visibility = View.VISIBLE
                    activity?.extFab!!.visibility = View.VISIBLE
                    activity?.search!!.visibility = View.VISIBLE
                    activity?.notification!!.visibility = View.VISIBLE
                    activity?.img_settings!!.visibility = View.VISIBLE*/

                    Toast.makeText(context,getString(R.string.plan_updated), Toast.LENGTH_SHORT).show()


                    //listener.refreshHomeFragmentData(true)

                    //homePlanPreviewViewModel.eventList()

                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                    println("Error coming from here" + response.error )
                }
            }
        })

        publicPlanViewModel.responseLiveDataPartialUpdate.observe(viewLifecycleOwner, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }

                Status.SUCCESS -> {
                    hideLoader()

                    planEditedResponse = response.data as Event

                    requireActivity().supportFragmentManager.findFragmentByTag(CreatePlanFragment.TAG)?.let { requireActivity().supportFragmentManager.beginTransaction().remove(it).commit()}
                    //requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()

                    activity?.appBarLayout?.visibility = View.VISIBLE
                    activity?.extFab!!.visibility = View.VISIBLE
                    activity?.search!!.visibility = View.VISIBLE
                    activity?.notification!!.visibility = View.VISIBLE
                    activity?.img_settings!!.visibility = View.VISIBLE

                    if (planEditedResponse!!.draft == true){

                        Toast.makeText(context,getString(R.string.draft_created), Toast.LENGTH_SHORT).show()

                    } else{

                        Toast.makeText(context,getString(R.string.plan_created), Toast.LENGTH_SHORT).show()
                    }


                    //listener.refreshHomeFragmentData(true)

                    //homePlanPreviewViewModel.eventList()

                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                    //homePlanPreviewViewModel.eventList()
/*
                    requireActivity().supportFragmentManager.findFragmentByTag(CreatePlanFragment.TAG)?.let { requireActivity().supportFragmentManager.beginTransaction().remove(it).commit()}

                    activity?.appBarLayout?.visibility = View.VISIBLE
                    activity?.extFab!!.visibility = View.VISIBLE
                    activity?.search!!.visibility = View.VISIBLE
                    activity?.notification!!.visibility = View.VISIBLE
                    activity?.img_settings!!.visibility = View.VISIBLE*/

                    println("Error coming from here" + response.error )

                }
            }
        })

        eventDetailViewModel.responseLiveDataTaskPatchFull.observe(viewLifecycleOwner, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }

                Status.SUCCESS -> {
                    hideLoader()

                    eventDetailViewModel.getEventData(eventId.toString())

                    println("patch action is working fine")

                }
                Status.ERROR -> {
                    hideLoader()
                }
            }
        })

        eventDetailViewModel.responseLiveDataTaskPatchWithoutAssignee.observe(viewLifecycleOwner, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }

                Status.SUCCESS -> {
                    hideLoader()

                    eventDetailViewModel.getEventData(eventId.toString())

                    println("patch action is working fine without assignee")

                }
                Status.ERROR -> {
                    hideLoader()
                }
            }
        })

        eventDetailViewModel.responseLiveDataDateDelete.observe(viewLifecycleOwner, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }

                Status.SUCCESS -> {
                    hideLoader()

                    eventDetailViewModel.getEventData(eventId.toString())

                    println("patch action is working fine")

                }
                Status.ERROR -> {
                    hideLoader()
                }
            }
        })

        eventDetailViewModel.responseLiveDataPlaceDelete.observe(viewLifecycleOwner, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }

                Status.SUCCESS -> {
                    hideLoader()

                    eventDetailViewModel.getEventData(eventId.toString())

                    println("patch action is working fine")

                }
                Status.ERROR -> {
                    hideLoader()
                }
            }
        })

        eventDetailViewModel.responseLiveDataActionDelete.observe(viewLifecycleOwner, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }

                Status.SUCCESS -> {
                    hideLoader()

                    eventDetailViewModel.getEventData(eventId.toString())

                    println("patch action is working fine")

                }
                Status.ERROR -> {
                    hideLoader()
                }
            }
        })

        eventDetailViewModel.responseLiveDataActivityDelete.observe(viewLifecycleOwner, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }

                Status.SUCCESS -> {
                    hideLoader()

                    eventDetailViewModel.getEventData(eventId.toString())

                    println("patch action is working fine")

                }
                Status.ERROR -> {
                    hideLoader()
                }
            }
        })

        addPlaceButton.setOnClickListener(this)
        addActionButton.setOnClickListener(this)
        addActivityButton.setOnClickListener(this)
        addPeriodButton.setOnClickListener(this)
        addPeopleButton.setOnClickListener(this)
        addPeopleButtonInitial.setOnClickListener(this)
        howOftenEditText.setOnClickListener(this)
        howOftenEditText.setText(R.string.once)

        actv_category.setOnClickListener {

            categoryViewModel.categoryList?.let { it1 -> populateCategory(it1) }

            println(catregoryId)
        }

//        populateCategory()

        createPlanButton.setOnClickListener(this)
        saveDraftButton.setOnClickListener(this)
        updatePlanButton.setOnClickListener(this)
        totalParticipantsUp.setOnClickListener(this)
        totalParticipantsDropDown.setOnClickListener(this)

        populateAddPeriodRecyclerViewIfAny()
        populateAddPlaceRecyclerViewIfAny()
        populateAddActionRecyclerViewIfAny()
        populateAddActivityRecyclerViewIfAny()
        populateAddPeopleRecyclerViewIfAny()

    }

    fun mapData(deleteButtonClicked: Boolean){

        eventDetailViewModel.getEventData(eventId.toString())

        eventDetailViewModel.responseLiveData.observe(viewLifecycleOwner, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }

                Status.SUCCESS -> {
                    hideLoader()

                    publicPlanViewModel.publicEventDate.clear()
                    publicPlanViewModel.publicPlace.clear()
                    publicPlanViewModel.publicAction.clear()
                    publicPlanViewModel.publicActivity.clear()
                    addedparticipants.clear()


                    event = response.data as Event

                    if (event!!._private == false){

                        actionRecyclerView?.adapter = AddActionRecyclerView(this, publicPlanViewModel.publicAction, editButtonClicked, deleteButtonClicked, event)
                        activityRecyclerView?.adapter = AddActivityRecyclerView(this, publicPlanViewModel.publicActivity, editButtonClicked, deleteButtonClicked, event)

                        planNameEditText.setText(event!!.title)

                        detailDescriptionEditText.setText(event!!.detail)

                        actv_category.setText(event!!.category.name)

                        catregoryId = event!!.category.pk!!

                        howOftenEditText.setText(event!!.periodicity?.unit)

                        maxParticipantsTextField.editText?.setText(event!!.max_guests.toString())

                        textViewAttending.setText(event!!.guests.size.toString())

                        textViewTotalParticipants.setText(event!!.guests.size.toString())

                        if (event!!.comments_closed == true){

                            privateCheckbox.isChecked = true

                        }else {

                            privateCheckbox.isChecked = false
                        }

                        getDates = event!!.dates

                        //val finalConvertedDateList = getDates.toPostDates

                        for (item in event!!.dates){

                            val finalConvertedDateItem = item.toPostDates()

                            publicPlanViewModel.publicEventDate.add(finalConvertedDateItem)

                            if (publicPlanViewModel.publicEventDate.size > 0){

                                addPeriodButton.setText(getString(R.string.add_another_period))
                            }

                            //item.start?.let { item.end?.let { it1 -> PostDates(it, it1) } }?.let { publicPlanViewModel.eventDate.add(it) }
                        }

                        addPeriodRecyclerViewPublic?.adapter = AddPeriodRecyclerViewPublic(this, publicPlanViewModel.publicEventDate /*getDates*/, editButtonClicked, deleteButtonClicked)
                        //addPeriodRecyclerView?.adapter?.notifyDataSetChanged()

                        for(item in event!!.places){

                            val finalConvertedPlaceItem = item.toPostPlaces()

                            publicPlanViewModel.publicPlace.add(finalConvertedPlaceItem)

                            if (publicPlanViewModel.publicPlace.size > 0){

                                addPlaceButton.setText(getString(R.string.add_another_place))
                            }

                            /*  publicPlanViewModel.place.add(PostPlaces(PostPlaceInfo(item.place.name!!, item.place.address!!, (item.place.zipcode)!!,
                                      item.place.city!!, item.place.country!!,item.place.map),item.place.name,item.place.address,(item.place.zipcode),
                                      item.place.city, item.place.country, item.place.map))*/
                        }

                        addPlaceRecyclerView?.adapter = AddPlaceRecyclerView(this, publicPlanViewModel.publicPlace, editButtonClicked)

                        for (item in event!!.guests){

                            val finalConvertedGuests = item.toContactsList()

                            addedparticipants?.add(finalConvertedGuests)
                        }

                        if (addedparticipants.size > 0){

                            addPeopleButtonInitial.visibility = View.GONE

                            participantsCardView.visibility = View.VISIBLE

                            addpeopleRecyclerView?.adapter = addedparticipants?.let { ParticipantsAdapter(it, addContactViewModel) }

                        } else if (addedparticipants.size == 0){

                            participantsCardView.visibility = View.GONE

                            addPeopleButtonInitial.visibility = View.VISIBLE
                        }

                        //addPlaceRecyclerView?.adapter?.notifyDataSetChanged()

                        for (item in event!!.tasks){

                            publicPlanViewModel.publicAction.add(PostTasks(item.price.toInt(),item.name,item.description,item.per_person, item.assignee?.id))
                        }

                        //actionRecyclerView?.adapter = AddActionRecyclerView(this, publicPlanViewModel.action, editButtonClicked, event)

                        actionRecyclerView?.adapter?.notifyDataSetChanged()

                        for (item in event!!.activities){

                            publicPlanViewModel.publicActivity.add(PostActivities(PostPlaceInfo(item.place?.name!!, item.place?.address!!,(item.place?.zipcode)!!,
                                    item.place?.city!!, item.place?.country!!,item.place?.map),item.price.toInt(), arrayListOf(),item.title,item.detail,
                                    item.date,item.max_participants,item.per_person,item.duration))
                        }

                        activityRecyclerView?.adapter?.notifyDataSetChanged()
                    }
                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                    println(response.error)

                }
            }
        })
    }

    private fun populateCategory(list: List<CategoryList>) {
        val customSpinner = categoryViewModel.categoryList?.let {
            CustomArrayAdapter(requireContext(), it)
        }
        actv_category?.threshold = 1
        actv_category?.setAdapter(customSpinner)
        actv_category?.setOnItemClickListener { parent, _, position, _ ->

            val obj = parent.adapter.getItem(position) as CategoryList?
            actv_category.setText(obj?.name)
            catregoryId = obj?.pk!!.toInt()
        }
    }

    @SuppressLint("NewApi")
    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.addPlaceButton -> {
                addPlaceClicked(position, arrayListOf())
            }

            R.id.addActionButton -> {
                addActionClicked(position, arrayListOf(), editButtonClicked)
            }

            R.id.addActivityButton -> {
                addActivityClicked(position, arrayListOf(),editButtonClicked)
            }

            R.id.addPeriodButton -> {
                addPeriodClicked()
            }

            R.id.addPeopleButton -> {
                addPeopleClicked()
            }

            R.id.addPeopleButtonInitial -> {
                addPeopleClicked()
            }

            R.id.createPlanButton -> {

                commentsClosed()

                if(planNameTextField.editText?.length()!! >= 3){

                    /*if (catregoryId <= 0){

                        if (draft == true){

                            if (catregoryId == 0){

                                catregoryId = event?.category?.pk!!
                            }
                        }
                    }*/

                    if (catregoryId > 0){

                        if (publicPlanViewModel.publicEventDate.size > 0){

                            if (publicPlanViewModel.publicPlace.size > 0){

                                if (draft == true){

                                    publicPlanViewModel.updateEvent(eventId.toString(), PostEvent(title = planNameTextField.editText?.text.toString(), detail = detailDescriptionTextField.editText?.text.toString(),
                                            private = isPrivtaeEvent, category = catregoryId, max_guests = maxGuests(), draft = false, periodicity = /*period!!*/ periodicity,
                                            creator = creator(), dates = publicPlanViewModel.publicEventDate, places = publicPlanViewModel.publicPlace, tasks = publicPlanViewModel.publicAction,
                                            activities = publicPlanViewModel.publicActivity, guests = publicPlanViewModel.publicParticipants, comments = arrayListOf(),
                                            vote_place_closed = false, vote_date_closed = false, comments_closed = commentsChecked))

                                } else {

                                    publicPlanViewModel.createEvent(PostEvent(title = planNameTextField.editText?.text.toString(), detail = detailDescriptionTextField.editText?.text.toString(),
                                            private = isPrivtaeEvent, category = catregoryId, max_guests = maxGuests(), draft = false, periodicity = /*period!!*/ periodicity,
                                            creator = creator(), dates = publicPlanViewModel.publicEventDate, places = publicPlanViewModel.publicPlace, tasks = publicPlanViewModel.publicAction,
                                            activities = publicPlanViewModel.publicActivity, guests = publicPlanViewModel.publicParticipants, comments = arrayListOf(),
                                            vote_place_closed = false, vote_date_closed = false, comments_closed = commentsChecked))
                                }



                            } else{

                                Toast.makeText(context,getString(R.string.select_place), Toast.LENGTH_LONG).show()
                                addPlaceButton.isFocusable = true
                                addPlaceButton.isFocusableInTouchMode = true
                                addPlaceButton.requestFocus()
                            }

                        } else {

                            Toast.makeText(context,getString(R.string.select_period), Toast.LENGTH_LONG).show()
                            addPeriodButton.isFocusable = true
                            addPeriodButton.isFocusableInTouchMode = true
                            addPeriodButton.requestFocus()
                        }

                    } else {

                        Toast.makeText(context,getString(R.string.select_category), Toast.LENGTH_LONG).show()
                        categoryTextField.requestFocus()
                        /*categoryTextField.editText?.error = "Category is Required"
                        categoryTextField.requestFocus()*/
                    }

                } else {

                    planNameTextField.editText?.setError(getString(R.string.plan_name_error))
                    planNameTextField.requestFocus()
                }
            }

            R.id.updatePlanButton -> {

                commentsClosed()

                if(planNameTextField.editText?.length()!! >= 3){

                    if (catregoryId > 0){

                        if (publicPlanViewModel.publicEventDate.size > 0){

                            if (publicPlanViewModel.publicPlace.size > 0){

                                /* val guest : ArrayList<Int> = arrayListOf()
                                 val contacts : ArrayList<Contacts> = arrayListOf()
                                 val emails : ArrayList<Emails> = arrayListOf()
                                 val guests = PostGuests(users = guest, contacts = contacts)
                                 val comments : ArrayList<PostComments> = arrayListOf()*/

                                publicPlanViewModel.updateEvent(eventId.toString(), PostEvent(title = planNameTextField.editText?.text.toString(), detail = detailDescriptionTextField.editText?.text.toString(),
                                        private = isPrivtaeEvent, category = catregoryId, max_guests = maxGuests(), draft = false, periodicity = /*period!!*/ periodicity,
                                        creator = creator(), dates = publicPlanViewModel.publicEventDate, places = publicPlanViewModel.publicPlace, tasks = publicPlanViewModel.publicAction,
                                        activities = publicPlanViewModel.publicActivity, guests = publicPlanViewModel.publicParticipants, comments = arrayListOf(),
                                        vote_place_closed = false, vote_date_closed = false,comments_closed = commentsChecked))


                                /*publicPlanViewModel.createEvent(PostEvent(title = planNameTextField.editText?.text.toString(), detail = detailDescriptionTextField.editText?.text.toString(),
                                        private = privateEvent, category = catregoryId, max_guests = maxGuests(), draft = false, periodicity = *//*period!!*//* periodicity,
                                        creator = creator(), dates = publicPlanViewModel.eventDate, places = publicPlanViewModel.place, tasks = publicPlanViewModel.action,
                                        activities = publicPlanViewModel.activity, guests = PostGuests(participantsIds, arrayListOf()), comments = arrayListOf(),
                                        vote_place_closed = false, vote_date_closed = false, comments_closed = false))*/
                            } else{

                                Toast.makeText(context,getString(R.string.select_place), Toast.LENGTH_LONG).show()
                                addPlaceButton.isFocusable = true
                                addPlaceButton.isFocusableInTouchMode = true
                                addPlaceButton.requestFocus()
                            }

                        } else {

                            Toast.makeText(context,getString(R.string.select_period), Toast.LENGTH_LONG).show()
                            addPeriodButton.isFocusable = true
                            addPeriodButton.isFocusableInTouchMode = true
                            addPeriodButton.requestFocus()
                        }

                    } else {

                        Toast.makeText(context,getString(R.string.select_category), Toast.LENGTH_LONG).show()
                        categoryTextField.requestFocus()
                        /*categoryTextField.editText?.error = "Category is Required"
                        categoryTextField.requestFocus()*/
                    }

                } else {

                    planNameTextField.editText?.setError(getString(R.string.plan_name_error))
                    planNameTextField.requestFocus()
                }
            }

            R.id.saveDraftButton -> {

                commentsClosed()

                if(planNameTextField.editText?.length()!! >= 3){

                    val maxGuests : Int = maxGuests()
                    var category : Int? = null

                    if(catregoryId == 0){

                        category = null

                    } else {

                        category = catregoryId
                    }

                    if (draft == true){

                        publicPlanViewModel.apiEventPartialUpdate(eventId.toString(), PostEvent(title = planNameTextField.editText?.text.toString(), detail = detailDescriptionTextField.editText?.text.toString(),
                                private = isPrivtaeEvent, category = category, max_guests = maxGuests, draft = true, periodicity = /*period!!*/ periodicity,
                                creator = creator(), dates = publicPlanViewModel.publicEventDate, places = publicPlanViewModel.publicPlace, tasks = publicPlanViewModel.publicAction,
                                activities = publicPlanViewModel.publicActivity, guests = publicPlanViewModel.publicParticipants, comments = arrayListOf(),
                                vote_place_closed = false, vote_date_closed = false, comments_closed = commentsChecked))

                    } else {

                        publicPlanViewModel.createEvent(PostEvent(title = planNameTextField.editText?.text.toString(), detail = detailDescriptionTextField.editText?.text.toString(),
                                private = isPrivtaeEvent, category = category, max_guests = maxGuests, draft = true, periodicity = /*period!!*/ periodicity,
                                creator = creator(), dates = publicPlanViewModel.publicEventDate, places = publicPlanViewModel.publicPlace, tasks = publicPlanViewModel.publicAction,
                                activities = publicPlanViewModel.publicActivity, guests = publicPlanViewModel.publicParticipants, comments = arrayListOf(),
                                vote_place_closed = false, vote_date_closed = false, comments_closed = commentsChecked))
                    }
                } else {

                    planNameTextField.editText?.setError(getString(R.string.plan_name_error))
                    planNameTextField.requestFocus()
                }

                //refreshHomePlanPreviewList()
            }

            /*R.id.howOftenEditText -> {

                if (isPrivtaeEvent == true){

                    howOftenDialog(requireContext())
                }
                //println("How often working.")
            }*/

            R.id.totalParticipantsUp -> {

                addpeopleRecyclerView?.visibility = View.GONE
                totalParticipantsUp.visibility = View.GONE
                totalParticipantsDropDown.visibility = View.VISIBLE
            }

            R.id.totalParticipantsDropDown -> {

                addpeopleRecyclerView?.visibility = View.VISIBLE
                totalParticipantsUp.visibility = View.VISIBLE
                totalParticipantsDropDown.visibility = View.GONE
            }
        }
    }

    private fun commentsClosed(){

        if (privateCheckbox.isChecked == true){

            commentsChecked = false

        } else {

            commentsChecked = true
        }
    }

    override fun addPeriodClicked() {

        if (publicPlanViewModel.publicEventDate.size > 0){

            Toast.makeText(context,getString(R.string.public_start_date_error), Toast.LENGTH_LONG).show()

        }else {

            context?.let { showDialog(it,position) }
        }
    }

    override fun addPlaceClicked(placePosition: Int, placeList: ArrayList<PostPlaces>) {

        if (publicPlanViewModel.publicPlace.size > 0){

            Toast.makeText(context,getString(R.string.public_place_error), Toast.LENGTH_LONG).show()

        } else {

            requireActivity().supportFragmentManager
                    .beginTransaction()
                    .add(AddPlaceFragment(this, placePosition, placeList), AddPlaceFragment::class.java.canonicalName)
                    .commitAllowingStateLoss()
        }
    }

    override fun addActionClicked(taskPosition: Int, taskList: ArrayList<PostTasks>, editButtonClicked: Boolean) {

        if (isPrivtaeEvent == false){

            Toast.makeText(context,getString(R.string.cannot_add_task), Toast.LENGTH_SHORT).show()

        } else {}
    }

    override fun addActivityClicked(activityPosition: Int, activityList: ArrayList<PostActivities>, editButtonClicked: Boolean) {

        if (isPrivtaeEvent == false){

            Toast.makeText(context,getString(R.string.cannot_add_activity), Toast.LENGTH_SHORT).show()

        } else {}
    }

    override fun addPeopleClicked() {

        /*requireActivity().supportFragmentManager
                .beginTransaction()
                .add(AddContactsFragment(this), AddContactsFragment::class.java.canonicalName)
                .commitAllowingStateLoss()*/
    }



    override fun closeButtonAddPeriodItemListener(pos: Int) {

        if (deleteButtonClicked == true){

            eventDetailViewModel.apiEventDateDelete(event!!.dates[pos].id.toString(), eventId.toString())
        }

        //eventDetailViewModel.apiEventDateDelete(event!!.dates[pos].id.toString(), eventId.toString())

        if (publicPlanViewModel.publicEventDate.size == 0){

            addPeriodButton.setText(getString(R.string.add_a_period))
            addPeriodButton.markInRed()
            addPeriodButton.strokeColor = ColorStateList.valueOf(resources.getColor(R.color.colorLightBlue))
        } else {

            addPeriodButton.setText(getString(R.string.add_another_period))
            //addPeriodButton.strokeColor = ColorStateList.valueOf(Color.WHITE)
        }

        addPeriodRecyclerViewPublic?.adapter?.notifyDataSetChanged()
    }

    override fun editButtonAddPeriodItemListener(pos: Int) {

        context?.let { showDialog(it, pos) }
    }


    override fun closeButtonAddPlaceItemListener(pos: Int) {

        if (deleteButtonClicked == true){

            eventDetailViewModel.apiEventPlaceDelete(eventId.toString(), event!!.places[pos].id.toString())

        }else {

            publicPlanViewModel.publicPlace.removeAt(pos)
        }

        if(publicPlanViewModel.publicPlace.size == 0){

            addPlaceButton.setText(getString(R.string.add_place))
            addPlaceButton.markInRed()
            addPlaceButton.strokeColor = ColorStateList.valueOf(resources.getColor(R.color.colorLightBlue))
        } else {

            addPlaceButton.setText(getString(R.string.add_another_place))
            //addPlaceButton.strokeColor = ColorStateList.valueOf(Color.WHITE)
        }

        addPlaceRecyclerView?.adapter?.notifyDataSetChanged()
    }

    override fun editButtonAddPlaceItemListener(pos: Int) {

        addPlaceClicked(pos, publicPlanViewModel.publicPlace)
    }


    override fun closeButtonAddActionItemListener(pos: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun editButtonAddActionItemListener(pos: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun closeButtonAddActivityItemListener(pos: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun editButtonAddActivityItemListener(pos: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



    private fun populateAddPeriodRecyclerViewIfAny() {
        addPeriodRecyclerViewPublic = requireActivity().findViewById(R.id.periodRecyclerViewPublic)
        addPeriodRecyclerViewPublic?.setHasFixedSize(true)
        addPeriodRecyclerViewPublic?.layoutManager = LinearLayoutManager(requireActivity())
        addPeriodRecyclerViewPublic?.adapter = AddPeriodRecyclerViewPublic(this, publicPlanViewModel.publicEventDate, editButtonClicked, deleteButtonClicked)
    }

    private fun populateAddPlaceRecyclerViewIfAny() {

        addPlaceRecyclerView = requireActivity().findViewById(R.id.placeRecyclerViewPublic)
        addPlaceRecyclerView?.setHasFixedSize(true)
        addPlaceRecyclerView?.layoutManager = LinearLayoutManager(requireActivity())
        addPlaceRecyclerView?.adapter = AddPlaceRecyclerView(this, publicPlanViewModel.publicPlace, editButtonClicked)
    }

    private fun populateAddActionRecyclerViewIfAny() {

        actionRecyclerView = requireActivity().findViewById(R.id.actionRecyclerViewPublic)
        actionRecyclerView?.setHasFixedSize(true)
        actionRecyclerView?.layoutManager = LinearLayoutManager(requireActivity())
        //actionRecyclerView?.adapter = AddActionRecyclerView(this, publicPlanViewModel.action, editButtonClicked, event)
    }

    private fun populateAddActivityRecyclerViewIfAny() {

        activityRecyclerView = requireActivity().findViewById(R.id.activityRecyclerView)
        activityRecyclerView?.setHasFixedSize(true)
        activityRecyclerView?.layoutManager = LinearLayoutManager(requireActivity())
        //activityRecyclerView?.adapter = AddActivityRecyclerView(this, publicPlanViewModel.activity, editButtonClicked)
    }

    private fun populateAddPeopleRecyclerViewIfAny() {

        addpeopleRecyclerView = requireActivity().findViewById(R.id.addpeopleRecyclerViewPublic)
        addpeopleRecyclerView?.setHasFixedSize(true)
        addpeopleRecyclerView?.layoutManager = LinearLayoutManager(requireActivity())
        addpeopleRecyclerView?.adapter = ParticipantsAdapter(AddContactsFragment.participants, addContactViewModel)
    }




    override fun addPlaceFragmentAddressFetch(updatedPosition: Int, place: PostPlaces) {

        (requireActivity().supportFragmentManager.findFragmentByTag(AddPlaceFragment::class.java.canonicalName)
                as? AddPlaceFragment)?.dismiss()

        //addPlaceButton.text = "ADD AN OTHER PLACE"
        addPlaceButton.setText(getString(R.string.add_another_place))
        //addPlaceButton.strokeColor = ColorStateList.valueOf(Color.WHITE)

        if (updatedPosition >= 0){

            publicPlanViewModel.publicPlace.set(updatedPosition,place)
            addPlaceRecyclerView?.adapter = AddPlaceRecyclerView(this, publicPlanViewModel.publicPlace, editButtonClicked)
            //addPlaceRecyclerView?.adapter?.notifyDataSetChanged()

        }else {

            publicPlanViewModel.publicPlace.add(place)
            addPlaceRecyclerView?.adapter = AddPlaceRecyclerView(this, publicPlanViewModel.publicPlace, editButtonClicked)
            addPlaceRecyclerView?.adapter?.notifyDataSetChanged()
        }
    }

    override fun cancelPlaceFragmentAddressFetch() {

        (requireActivity().supportFragmentManager.findFragmentByTag(AddPlaceFragment::class.java.canonicalName)
                as? AddPlaceFragment)?.dismiss()
    }


    override fun AddActionFragmentFetch(updatedPosition: Int, task: PostTasks) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun cancelActionFragmentFetch() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun AddActivityFragmentFetch(updatedPosition: Int, activity: PostActivities) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun CancelActivityFragmentFetch() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun AddSelectedParticipants(participants: ArrayList<ContactsList>) {

        //addpeopleRecyclerView = requireActivity().findViewById(R.id.addpeopleRecyclerViewPublic)

        for(item in participants){

            item.id?.let { participantsIds.add(it) }
        }

        //publicPlanViewModel.participants.add(PostGuests(participantsIds, arrayListOf()))
        publicPlanViewModel.publicParticipants = PostGuests(participantsIds, arrayListOf())

        addedparticipants = participants

        if (participants.size > 0){

            addPeopleButtonInitial.visibility = View.GONE

            participantsCardView.visibility = View.VISIBLE

            //addpeopleRecyclerViewPublic.visibility = View.VISIBLE

        } else if (participants.size == 0){

            participantsCardView.visibility = View.GONE

            addPeopleButtonInitial.visibility = View.VISIBLE
        }

        addpeopleRecyclerView?.adapter = ParticipantsAdapter(addedparticipants!!, addContactViewModel)
    }



    override fun customFrequencyHowOftenFetch(periodicity: Periodicity) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



    fun maxGuests() : Int {

        var maxGuests : Int = 0

        if (!TextUtils.isEmpty(maxParticipantsTextField.editText?.text.toString()) && maxParticipantsTextField.editText?.text.toString().toInt() > 0){

            maxGuests = maxParticipantsTextField.editText?.text.toString().toInt()

        }

        return maxGuests
    }

    fun creator() : PostCreator{

        val userName: String = context?.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE)?.getString("USERNAME", "")!!
        val firstName: String = context?.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE)?.getString("FIRSTNAME","")!!
        val lastName:String = context?.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE)?.getString("LASTNAME","")!!
        val email: String = context?.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE)?.getString("EMAIL","")!!
        val phoneNumber: String =context?.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE)?.getString("PHONENUMBER","")!!

        val creator: PostCreator = PostCreator(email = email, first_name = firstName, last_name = lastName, phone_number = phoneNumber)

        return creator
    }

    private fun showDialog(context: Context, pos : Int ) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.layout_add_date)

        val mCal = Calendar.getInstance()
        val mDay = mCal.get(Calendar.DAY_OF_MONTH)
        val mMonth = mCal.get(Calendar.MONTH)
        val mYear = mCal.get(Calendar.YEAR)
        val mHour = mCal.get(Calendar.HOUR_OF_DAY)
        val mMin = mCal.get(Calendar.MINUTE)

        var sDate : Date? = null
        var eDate : Date? = null

        val inputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        //val outputFormatter = SimpleDateFormat("E, MMM dd yyyy hh:mm a")
        val outputFormatter = SimpleDateFormat("E, MMM dd yyyy HH:mm")

        val fromDateTextField = dialog.findViewById(R.id.fromDateTextField) as TextInputLayout
        val fromDateEditText = dialog.findViewById(R.id.fromDateEditText) as TextInputEditText
        val toDateEditText = dialog.findViewById(R.id.toDateEditText) as TextInputEditText
        val toDateTextField = dialog.findViewById(R.id.toDateTextField) as TextInputLayout
        val btn_create_group_ok = dialog.findViewById(R.id.btn_create_group_ok) as MaterialButton
        val btn_create_group_cancel = dialog.findViewById(R.id.btn_create_group_cancel) as MaterialButton

        fromDateTextField.markRequiredInRed()

        if (pos >= 0 ){

            val startDateServer = inputFormatter.parse(publicPlanViewModel.publicEventDate[pos].start)
            val displayStartDate = outputFormatter.format(startDateServer)
            fromDateTextField.editText?.setText(displayStartDate)

            if (!publicPlanViewModel.publicEventDate[pos].end.isNullOrEmpty()){

                val endDateServer = inputFormatter.parse(publicPlanViewModel.publicEventDate[pos].end)
                val displayEndDate = outputFormatter.format(endDateServer)
                toDateTextField.editText?.setText(displayEndDate)
            }
        }

        fromDateEditText.setOnClickListener {

            val fromDate = DatePickerDialog(requireContext(), R.style.AlertDialogTheme, DatePickerDialog.OnDateSetListener { fromDatePicker, fromYear, fromMonth, fromDay ->

                val fromTime = TimePickerDialog(requireContext(), R.style.AlertDialogTheme, TimePickerDialog.OnTimeSetListener { fromTimePicker, fromHourOfDay, fromMinute ->

                    val cal = Calendar.getInstance()
                    cal.set(fromYear, fromMonth, fromDay, fromHourOfDay, fromMinute)

                    //FromDate
                    //val dateFormatter = SimpleDateFormat("E, MMM dd yyyy hh:mm a")
                    val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")  //2020-08-27T15:36:28.811Z
                    val startDate = inputFormatter.format(cal.time)
                    val displayDateFormatted = outputFormatter.format(cal.time)
//                        sDate = inputFormatter.parse(startDate)

                    fromDate = startDate

                    // val displayStartDate = outputFormatter.parse(startDate)

                    fromDateTextField.editText?.setText(displayDateFormatted)
                    //fromDateEditText.setText(fromDate)

                    /* publicPlanViewModel.eventDate.add(PostDates(start = startDate, end = endDate*//*id = publicPlanViewModel.eventDate.size, start = startDate, end = endDate, votes = mutableListOf()*//*))
                            addPeriodRecyclerView?.adapter?.notifyDataSetChanged()
                            addPeriodButton.text = "ADD AN OTHER PERIOD"*/
                    //addPeriodButton.strokeColor = ColorStateList.valueOf(Color.WHITE)

                }, mHour, mMin, false)
                fromTime.show()

            }, mYear, mMonth, mDay)

            fromDate.datePicker.minDate = System.currentTimeMillis()
            fromDate.setCanceledOnTouchOutside(true)
            //fromDate.setTitle("From")
            fromDate.setCustomTitle(layoutInflater.inflate(R.layout.date_from,null))
            fromDate.show()
        }

        toDateEditText.setOnClickListener {

            val toDate = DatePickerDialog(requireContext(), R.style.AlertDialogTheme, DatePickerDialog.OnDateSetListener { toDatePicker, toYear, toMonth, toDay ->

                val toTime = TimePickerDialog(requireContext(), R.style.AlertDialogTheme, TimePickerDialog.OnTimeSetListener { toTimePicker, toHourOfDay, toMinute ->

                    val cal = Calendar.getInstance()

                    //FromDate
                    //val dateFormatter = SimpleDateFormat("E, MMM dd yyyy hh:mm a")
                    val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")  //2020-08-27T15:36:28.811Z

                    //ToDate
                    cal.set(toYear, toMonth, toDay, toHourOfDay, toMinute)
                    val endDate = inputFormatter.format(cal.time)
                    val displayEndFormatted = outputFormatter.format(cal.time)
//                    eDate = inputFormatter.parse(endDate)

                    toDate = endDate

                    //val displayEndDate = outputFormatter.format(endDate)

                    toDateTextField.editText?.setText(displayEndFormatted)

                    /* publicPlanViewModel.eventDate.add(PostDates(start = startDate, end = endDate*//*id = publicPlanViewModel.eventDate.size, start = startDate, end = endDate, votes = mutableListOf()*//*))
                                addPeriodRecyclerView?.adapter?.notifyDataSetChanged()
                                addPeriodButton.text = "ADD AN OTHER PERIOD"*/
                    //addPeriodButton.strokeColor = ColorStateList.valueOf(Color.WHITE)

                }, mHour, mMin, false)
                toTime.show()

            }, mYear, mMonth, mDay)
            toDate.datePicker.minDate = System.currentTimeMillis()
            toDate.setCanceledOnTouchOutside(true)
            toDate.setCustomTitle(layoutInflater.inflate(R.layout.date_to,null))
            toDate.show()

        }


        btn_create_group_ok.setOnClickListener {

            if (fromDateEditText.text.isNullOrEmpty()){

                Toast.makeText(context,getString(R.string.start_date_required), Toast.LENGTH_LONG).show()

                /*  fromDateEditText.setError(context.getString(R.string.add_period_error))
                  fromDateTextField.requestFocus()*/

            } else if (!fromDateEditText.text.isNullOrEmpty() && !toDateEditText.text.isNullOrEmpty()){

                sDate = outputFormatter.parse(fromDateEditText.text.toString())
                eDate = outputFormatter.parse(toDateEditText.text.toString())

                if(sDate?.before(eDate)!!){

                    if (pos >= 0){

                        eventDetailViewModel.apiEventDatePartialUpdate(event!!.dates[pos].id.toString(),event!!.id.toString(), PostDates(fromDate, toDate))

                        eventDetailViewModel.responseLiveDataDatePatch.observe(viewLifecycleOwner, Observer { response ->

                            when (response.status) {
                                Status.LOADING -> {
                                    showLoader()
                                }

                                Status.SUCCESS -> {
                                    hideLoader()

                                    eventDetailViewModel.getEventData(eventId.toString())
                                }
                                Status.ERROR -> {
                                    hideLoader()
                                    showMessage(response.error?.message.toString())
                                    println(response.error?.message.toString())
                                }
                            }
                        })

                        //publicPlanViewModel.eventDate.set(pos, PostDates(start = fromDate, end = toDate))

                        //addPeriodRecyclerView?.adapter?.notifyDataSetChanged()

                    } else {

                        publicPlanViewModel.publicEventDate.add(PostDates(start = fromDate, end = toDate))
                        addPeriodRecyclerViewPublic?.adapter = AddPeriodRecyclerViewPublic(this, publicPlanViewModel.publicEventDate, editButtonClicked, deleteButtonClicked)
                        //addPeriodRecyclerView?.adapter?.notifyDataSetChanged()
                        addPeriodButton.setText(getString(R.string.add_another_period))
                    }

                    dialog.dismiss()

                } else {

                    /* fromDateEditText.setError(context.getString(R.string.add_period_error))
                     toDateTextField.requestFocus()*/

                    Toast.makeText(context,getString(R.string.start_date_error), Toast.LENGTH_LONG).show()
                }

            } else if (!fromDateEditText.text.isNullOrEmpty() && toDateEditText.text.isNullOrEmpty()){

                if (pos >= 0){

                    publicPlanViewModel.publicEventDate.set(pos, PostDates(start = fromDate, end = null))
                    addPeriodRecyclerViewPublic?.adapter = AddPeriodRecyclerViewPublic(this, publicPlanViewModel.publicEventDate, editButtonClicked,deleteButtonClicked)
                    //addPeriodRecyclerView?.adapter?.notifyDataSetChanged()

                }else {

                    publicPlanViewModel.publicEventDate.add(PostDates(start = fromDate, end = null))
                    addPeriodRecyclerViewPublic?.adapter = AddPeriodRecyclerViewPublic(this, publicPlanViewModel.publicEventDate, editButtonClicked, deleteButtonClicked)
                    addPeriodRecyclerViewPublic?.adapter?.notifyDataSetChanged()
                    addPeriodButton.setText(getString(R.string.add_another_period))
                }

                dialog.dismiss()
            }

            //dialog.dismiss()
        }

        btn_create_group_cancel.setOnClickListener { dialog.dismiss() }

        dialog.show()
    }

    interface PrivatePlanFragmentListener{

        fun refreshHomeFragmentData(success : Boolean)
    }


    fun EventDate.toPostDates() = PostDates(

            start = start,
            end = end
    )

    fun EventPlace.toPostPlaces() = PostPlaces(

            place = place.toPostPlaceInfo(),
            name = place.name,
            address = place.address,
            zipcode = place.zipcode,
            city = place.city,
            country = place.country,
            map = place.map,
            visibility = true

    )

    fun Place.toPostPlaceInfo() = PostPlaceInfo(

            name = name,
            address = address,
            zipcode = zipcode,
            city  = city,
            country = country,
            map = map
    )

   /* fun Guests.toActivityParticipants() = ActivityParticipants(

            participantName = name,
            id = user_id
    )*/

    fun Guests.toContactsList() = ContactsList(

            id = user_id,
            first_name = null,
            last_name = null,
            username = name,
            email = email,
            phone_number = phone_number,
            picture = null,
            selection = false
    )
}
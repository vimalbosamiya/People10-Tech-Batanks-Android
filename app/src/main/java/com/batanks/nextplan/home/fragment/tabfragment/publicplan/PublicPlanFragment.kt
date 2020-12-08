package com.batanks.nextplan.home.fragment.tabfragment.publicplan

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseFragment
import com.batanks.nextplan.arch.response.Status
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.common.getLoadingDialog
import com.batanks.nextplan.customfrequency.CustomFrequency
import com.batanks.nextplan.home.HomePlanPreview
import com.batanks.nextplan.home.fragment.CreatePlanFragment
import com.batanks.nextplan.home.fragment.action.AddActionFragment
import com.batanks.nextplan.home.fragment.action.AddActionRecyclerView
import com.batanks.nextplan.home.fragment.contacts.AddContactsFragment
import com.batanks.nextplan.home.fragment.period.AddPeriodRecyclerView
import com.batanks.nextplan.home.fragment.place.AddPlaceFragment
import com.batanks.nextplan.home.fragment.place.AddPlaceRecyclerView
import com.batanks.nextplan.home.fragment.spinner.CustomArrayAdapter
import com.batanks.nextplan.home.fragment.tabfragment.AddActivityFragment
import com.batanks.nextplan.home.fragment.tabfragment.AddActivityRecyclerView
import com.batanks.nextplan.home.fragment.tabfragment.ButtonContract
import com.batanks.nextplan.home.fragment.tabfragment.publicplan.viewmodel.CategoryViewModel
import com.batanks.nextplan.home.fragment.tabfragment.publicplan.viewmodel.PublicPlanViewModel
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.swagger.api.CategoryAPI
import com.batanks.nextplan.swagger.api.EventAPI
import com.batanks.nextplan.swagger.model.*
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_public_new_plan.*
import kotlinx.android.synthetic.main.how_often_pop.*
import kotlinx.android.synthetic.main.layout_add_plan_add_action.*
import kotlinx.android.synthetic.main.layout_add_plan_add_activity.*
import kotlinx.android.synthetic.main.layout_add_plan_add_people.*
import kotlinx.android.synthetic.main.layout_add_plan_add_period.*
import kotlinx.android.synthetic.main.layout_add_plan_add_place.*
import kotlinx.android.synthetic.main.layout_add_plan_footer.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class PublicPlanFragment : BaseFragment(), ButtonContract, View.OnClickListener,
        AddPeriodRecyclerView.AddPeriodRecyclerViewCallBack,
        AddPlaceRecyclerView.AddPlaceRecyclerViewCallBack,
        AddActionFragment.AddActionFragmentListener,

        AddActionRecyclerView.AddActionRecyclerViewCallBack,
        AddActivityRecyclerView.AddActivityRecyclerViewCallBack,
        AddActivityFragment.AddActivityFragmentListener,
        AddPlaceFragment.AddPlaceFragmentListener {

    private var addPeriodRecyclerView: RecyclerView? = null
    private var addPlaceRecyclerView: RecyclerView? = null
    private var actionRecyclerView : RecyclerView? = null
    private var activityRecyclerView : RecyclerView? = null

    //lateinit var categoryList : List<CategoryList>

    val privateEvent : Boolean = true
    var pk : Int = 0
    var system: Resources = Resources.getSystem()

    /*private val publicPlanViewModel: PublicPlanViewModel by lazy {
        ViewModelProvider(this)[PublicPlanViewModel::class.java]
    }*/

    private val publicPlanViewModel: PublicPlanViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            getContext()?.let {
                RetrofitClient.getRetrofitInstance(it)?.create(EventAPI::class.java)?.let {
                    PublicPlanViewModel(it)
                }
            }
        }).get(PublicPlanViewModel::class.java)
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

   /* private val homePlanPreviewViewModel: HomePlanPreviewViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            RetrofitClient.getRetrofitInstance(this)?.create(EventAPI::class.java)?.let {
                HomePlanPreviewViewModel(it)
            }
        }).get(HomePlanPreviewViewModel::class.java)
    }*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_public_new_plan, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //println(context?.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE)?.getString("USERNAME", ""))

        loadingDialog = context?.getLoadingDialog(0, R.string.loading_list_please_wait, theme = R.style.AlertDialogCustom)

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

        /*planNameEditText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                planNameEditText.hint = "Vinoth"
                planNameEditText.requestFocus()
            } else {
                planNameEditText.hint = ""
            }
        }*/

        /*override fun dispatchTouchEvent(event: MotionEvent): Boolean {
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
  }*/
        
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

        //var planName : String = "<font color='#ffffff'>Name </font>" + "<font color='#FF0000'>*</font>"

        //planNameEditText.hint = Html.fromHtml(planName) as Editable?

        planNameTextField.markRequiredInRed()

        //planNameTextField.hint = Html.fromHtml(planName) as Editable?

        //populateCategory()

        //planNameTextField.hint = ("Plan name" +" "+getString(R.string.asteriskred))

        addPlaceButton.setOnClickListener(this)
        addActionButton.setOnClickListener(this)
        addActivityButton.setOnClickListener(this)
        addPeriodButton.setOnClickListener(this)
        addPeopleButton.setOnClickListener(this)
        howOftenEditText.setOnClickListener(this)


        actv_category.setOnClickListener {

            categoryViewModel.categoryList?.let { it1 -> populateCategory(it1) }

            println(pk)

            //Toast.makeText(context,pk, Toast.LENGTH_SHORT).show()
        }

//        populateCategory()

        createPlanButton.setOnClickListener(this)
        saveDraftButton.setOnClickListener(this)

        populateAddPeriodRecyclerViewIfAny()
        populateAddPlaceRecyclerViewIfAny()
        populateAddActionRecyclerViewIfAny()
        populateAddActivityRecyclerViewIfAny()
    }


    /*override fun dispatchTouchEvent(event: MotionEvent): Boolean {
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
    }*/

    fun TextInputLayout.markRequiredInRed() {

        hint = buildSpannedString {
            append(hint)
            color(Color.RED) { append(" *") }
        }
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
            pk = obj?.pk!!.toInt()
        }
    }

    @SuppressLint("NewApi")
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.addPlaceButton -> {
                addPlaceClicked()
            }
            R.id.addActionButton -> {
                addActionClicked()
            }
            R.id.addActivityButton -> {
                addActivityClicked()
            }
            R.id.addPeriodButton -> {
                addPeriodClicked()
            }
            R.id.addPeopleButton -> {
                addPeopleClicked()
            }

            R.id.createPlanButton -> {

                /* if(TextUtils.isEmpty(planNameTextField?.editText?.text.toString())){

                    planNameTextField.editText?.error = "Plan name is Required"
                    planNameTextField.requestFocus()
                }*/

                if(planNameTextField.editText?.length()!! >= 3){

                    if (pk > 0){

                        if (publicPlanViewModel.eventDate.size > 0){

                            if (publicPlanViewModel.place.size > 0){

                                val guest : ArrayList<Int> = arrayListOf()
                                val phones : ArrayList<Phones> = arrayListOf()
                                val emails : ArrayList<Emails> = arrayListOf()

                val userName: String = context?.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE)?.getString("USERNAME", "")!!
                val firstName: String = context?.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE)?.getString("FIRSTNAME","")!!
                val lastName:String = context?.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE)?.getString("LASTNAME","")!!
                val email: String = context?.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE)?.getString("EMAIL","")!!
                val phoneNumber: String =context?.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE)?.getString("PHONENUMBER","")!!

                val creator = PostCreator(username = userName, email = email, first_name = firstName, last_name = lastName, phone_number = phoneNumber.toInt())
                val guests = PostGuests(users = guest, phones = phones, emails = emails)
                val comments : ArrayList<PostComments> = arrayListOf()
                //val category = CategoryList(1,)

                var maxGuests : Int = 0

                if (!TextUtils.isEmpty(maxParticipantsTextField.editText?.text.toString()) && maxParticipantsTextField.editText?.text.toString().toInt() > 0){

                    maxGuests = maxParticipantsTextField.editText?.text.toString().toInt()

                }

                publicPlanViewModel.createEvent(PostEvent(title = planNameTextField.editText?.text.toString(), detail = detailDescriptionTextField.editText?.text.toString(),
                                                         private = privateEvent, category = pk, max_guests = maxParticipantsTextField.editText?.text.toString().toInt(),
                                                         draft = false, periodicity = Periodicity("d",0), creator = creator, dates = publicPlanViewModel.eventDate,
                                                         places = publicPlanViewModel.place, tasks = publicPlanViewModel.action, activities = publicPlanViewModel.activity,
                                                         guests = guests, comments = comments,  vote_place_closed = false, vote_date_closed = false, comments_closed = false))


                                /*Toast.makeText(context,"Create plan clicked", Toast.LENGTH_SHORT).show()
                                requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
                                requireActivity().intent = Intent(context, HomePlanPreview :: class.java)
                                startActivity(requireActivity().intent)*/

                                Toast.makeText(context,"Create plan clicked", Toast.LENGTH_SHORT).show()

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

                /*val guest : MutableList<Int> = mutableListOf(18)
                val phone : MutableList<EventInvitationPhone> = mutableListOf()
                val emailInvite : MutableList<EventInvitationEmail> = mutableListOf()

                val userName: String = context?.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE)?.getString("USERNAME", "")!!
                val firstName: String = context?.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE)?.getString("FIRSTNAME","")!!
                val lastName:String = context?.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE)?.getString("LASTNAME","")!!
                val email: String = context?.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE)?.getString("EMAIL","")!!
                val phoneNumber: String =context?.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE)?.getString("PHONENUMBER","")!!

                val creator = Creator(first_name = firstName, last_name = lastName, username = userName, email = email, phone_number = phoneNumber, picture = "")
                val guests = EventInvitation(users = guest, phones = phone, emails = emailInvite)
                //val category = CategoryList(1,)

                var maxGuests : Int = 0

                if (!TextUtils.isEmpty(maxParticipantsTextField.editText?.text.toString()) && maxParticipantsTextField.editText?.text.toString().toInt() > 0){

                    maxGuests = maxParticipantsTextField.editText?.text.toString().toInt()

                }

                publicPlanViewModel.createEvent(Event(1, title = planNameTextField.editText?.text.toString(), detail = detailDescriptionTextField.editText?.text.toString(),
                                                        _private = privateEvent, category = 1, max_guests = maxGuests, draft = false,
                                                         periodicity = Periodicity("d",0), creator = creator, dates = publicPlanViewModel.eventDate,
                                                         places = publicPlanViewModel.place, tasks = publicPlanViewModel.action, activities = publicPlanViewModel.activity,
                                                         guests = guests, created = "", modified = "", vote_place_closed = false, vote_date_closed = false))

                Toast.makeText(context,"Create plan clicked", Toast.LENGTH_SHORT).show()*/
            }

            R.id.saveDraftButton -> {

                refreshHomePlanPreviewList()
            }

            R.id.howOftenEditText -> {

                howOftenDialog(requireContext())
                //println("How often working.")
            }
        }
    }

    private fun refreshHomePlanPreviewList(){

        //(activity as HomePlanPreview).notifyDataSetChange()

        val handler = Handler()
        handler.postDelayed({
            // do something after 1000ms
            requireActivity().supportFragmentManager.findFragmentByTag(CreatePlanFragment.TAG)?.let { requireActivity().supportFragmentManager.beginTransaction().remove(it).commit() }
            ///(activity as HomePlanPreview).extFab.visibility = View.VISIBLE               //uncomment
        }, 1000)
    }

    override fun addPeriodClicked() {

        /*fragmentManager?.let { AddPeriodFragment().show(it, AddPeriodFragment::class.java.simpleName) }*/

        val mCal = Calendar.getInstance()
        val mDay = mCal.get(Calendar.DAY_OF_MONTH)
        val mMonth = mCal.get(Calendar.MONTH)
        val mYear = mCal.get(Calendar.YEAR)
        val mHour = mCal.get(Calendar.HOUR_OF_DAY)
        val mMin = mCal.get(Calendar.MINUTE)

       /* val fromDate = DatePickerDialog(requireContext(), R.style.AlertDialogTheme, OnDateSetListener { fromDatePicker, fromYear, fromMonth, fromDay ->
            val toDate = DatePickerDialog(requireContext(), R.style.AlertDialogTheme, OnDateSetListener { toDatePicker, toYear, toMonth, toDay ->
                val fromTime = TimePickerDialog(requireContext(), R.style.AlertDialogTheme, TimePickerDialog.OnTimeSetListener { fromTimePicker, fromHourOfDay, fromMinute ->
                    val toTime = TimePickerDialog(requireContext(), R.style.AlertDialogTheme, TimePickerDialog.OnTimeSetListener { toTimePicker, toHourOfDay, toMinute ->

                        val cal = Calendar.getInstance()
                        cal.set(fromYear, fromMonth, fromDay, fromHourOfDay, fromMinute)

                        //FromDate
                        val dateFormatter = SimpleDateFormat("E, MMM dd yyyy HH:mm a")
                        val startDate = dateFormatter.format(cal.time)
                        println(startDate)

                        //ToDate
                        cal.set(toYear, toMonth, toDay, toHourOfDay, toMinute)
                        val endDate = dateFormatter.format(cal.time)
                        println(endDate)

                        publicPlanViewModel.eventDate.add(EventDate(id = publicPlanViewModel.eventDate.size, start = startDate, end = endDate, votes = mutableListOf()))
                        addPeriodRecyclerView?.adapter?.notifyDataSetChanged()
                        addPeriodButton.text = "ADD AN OTHER PERIOD"

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



        val fromDate = DatePickerDialog(requireContext(), R.style.AlertDialogTheme, OnDateSetListener { fromDatePicker, fromYear, fromMonth, fromDay ->

            val fromTime = TimePickerDialog(requireContext(), R.style.AlertDialogTheme, TimePickerDialog.OnTimeSetListener { fromTimePicker, fromHourOfDay, fromMinute ->

                val toDate = DatePickerDialog(requireContext(), R.style.AlertDialogTheme, OnDateSetListener { toDatePicker, toYear, toMonth, toDay ->

                    val toTime = TimePickerDialog(requireContext(), R.style.AlertDialogTheme, TimePickerDialog.OnTimeSetListener { toTimePicker, toHourOfDay, toMinute ->

                        val cal = Calendar.getInstance()
                        cal.set(fromYear, fromMonth, fromDay, fromHourOfDay, fromMinute)

                        //FromDate
                        //val dateFormatter = SimpleDateFormat("E, MMM dd yyyy hh:mm a")
                        val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")  //2020-08-27T15:36:28.811Z
                        val startDate = dateFormatter.format(cal.time)
                        println(startDate)

                        //ToDate
                        cal.set(toYear, toMonth, toDay, toHourOfDay, toMinute)
                        val endDate = dateFormatter.format(cal.time)
                        println(endDate)

                        publicPlanViewModel.eventDate.add(PostDates(start = startDate, end = endDate/*id = publicPlanViewModel.eventDate.size, start = startDate, end = endDate, votes = mutableListOf()*/))
                        addPeriodRecyclerView?.adapter?.notifyDataSetChanged()
                        addPeriodButton.text = "ADD AN OTHER PERIOD"
                        //addPeriodButton.strokeColor = ColorStateList.valueOf(Color.WHITE)

                    }, mHour, mMin, false)
                    toTime.show()

                }, mYear, mMonth, mDay)
                toDate.datePicker.minDate = System.currentTimeMillis()
                toDate.setCanceledOnTouchOutside(true)
                toDate.setCustomTitle(layoutInflater.inflate(R.layout.date_to,null))
                toDate.show()

            }, mHour, mMin, false)
            fromTime.show()

        }, mYear, mMonth, mDay)

        fromDate.datePicker.minDate = System.currentTimeMillis()
        fromDate.setCanceledOnTouchOutside(true)
        //fromDate.setTitle("From")
        fromDate.setCustomTitle(layoutInflater.inflate(R.layout.date_from,null))
        fromDate.show()
    }

    /*fun set_timepicker_text_colour(){

        var ampm_numberpicker_id : Int = system.getIdentifier("amPm", "id", "android")

        val ampm_numberpicker = time_picker.findViewById(ampm_numberpicker_id) as NumberPicker

    }*/


    override fun addPlaceClicked() {
        requireActivity().supportFragmentManager
                .beginTransaction()
                .add(AddPlaceFragment(this), AddPlaceFragment::class.java.canonicalName)
                .commitAllowingStateLoss()

        //addPlaceRecyclerView?.adapter?.notifyDataSetChanged()
    }

    override fun addActionClicked() {
        requireActivity().supportFragmentManager
                .beginTransaction()
                .add(AddActionFragment(this), AddActionFragment::class.java.canonicalName)
                .commitAllowingStateLoss()
    }

    override fun addActivityClicked() {
        requireActivity().supportFragmentManager
                .beginTransaction()
                .add(AddActivityFragment(this), AddActivityFragment::class.java.canonicalName)
                .commitAllowingStateLoss()
    }

    override fun addPeopleClicked() {
        requireActivity().supportFragmentManager
                .beginTransaction()
                .add(AddContactsFragment(), AddContactsFragment::class.java.canonicalName)
                .commitAllowingStateLoss()
    }

    override fun closeButtonAddPeriodItemListener(pos: Int) {

        if (publicPlanViewModel.eventDate.size == 0){

            addPeriodButton.text = "ADD A PERIOD"
            addPeriodButton.strokeColor = ColorStateList.valueOf(resources.getColor(R.color.colorLightBlue))
        } else {

            addPeriodButton.text = "ADD AN OTHER PERIOD"
            //addPeriodButton.strokeColor = ColorStateList.valueOf(Color.WHITE)
        }

        addPeriodRecyclerView?.adapter?.notifyDataSetChanged()
    }

    override fun closeButtonAddPlaceItemListener(pos: Int) {

        if(publicPlanViewModel.place.size == 0){

            addPlaceButton.text = "ADD A PLACE"
            addPlaceButton.strokeColor = ColorStateList.valueOf(resources.getColor(R.color.colorLightBlue))
        } else {

            addPlaceButton.text = "ADD AN OTHER PLACE"
            //addPlaceButton.strokeColor = ColorStateList.valueOf(Color.WHITE)
        }

        addPlaceRecyclerView?.adapter?.notifyDataSetChanged()
    }

    override fun closeButtonAddActionItemListener(pos: Int) {

        if (publicPlanViewModel.action.size == 0){

            addActionButton.text = "ADD AN ACTION"
            addActionButton.strokeColor = ColorStateList.valueOf(resources.getColor(R.color.colorLightBlue))
        } else {

            addActionButton.text = "ADD AN OTHER ACTION"
            //addActionButton.strokeColor = ColorStateList.valueOf(Color.WHITE)

        }


        actionRecyclerView?.adapter?.notifyDataSetChanged()
    }

    override fun closeButtonAddActivityItemListener(pos: Int) {

        if (publicPlanViewModel.activity.size == 0){

            addActivityButton.text = "ADD AN ACTIVITY"
            addActivityButton.strokeColor = ColorStateList.valueOf(resources.getColor(R.color.colorLightBlue))
        } else {

            addActivityButton.text = "ADD AN OTHER ACTIVITY"
            //addActivityButton.strokeColor = ColorStateList.valueOf(Color.WHITE)
        }

        activityRecyclerView?.adapter?.notifyDataSetChanged()
    }

    private fun populateAddPeriodRecyclerViewIfAny() {
        addPeriodRecyclerView = requireActivity().findViewById(R.id.periodRecyclerView)
        addPeriodRecyclerView?.setHasFixedSize(true)
        addPeriodRecyclerView?.layoutManager = LinearLayoutManager(requireActivity())
        addPeriodRecyclerView?.adapter = AddPeriodRecyclerView(this, publicPlanViewModel.eventDate)
    }

    private fun populateAddPlaceRecyclerViewIfAny() {

        addPlaceRecyclerView = requireActivity().findViewById(R.id.placeRecyclerView)
        addPlaceRecyclerView?.setHasFixedSize(true)
        addPlaceRecyclerView?.layoutManager = LinearLayoutManager(requireActivity())
        addPlaceRecyclerView?.adapter = AddPlaceRecyclerView(this, publicPlanViewModel.place)
    }

    private fun populateAddActionRecyclerViewIfAny() {

        actionRecyclerView = requireActivity().findViewById(R.id.actionRecyclerView)
        actionRecyclerView?.setHasFixedSize(true)
        actionRecyclerView?.layoutManager = LinearLayoutManager(requireActivity())
        actionRecyclerView?.adapter = AddActionRecyclerView(this, publicPlanViewModel.action)
    }

    private fun populateAddActivityRecyclerViewIfAny() {

        activityRecyclerView = requireActivity().findViewById(R.id.activityRecyclerView)
        activityRecyclerView?.setHasFixedSize(true)
        activityRecyclerView?.layoutManager = LinearLayoutManager(requireActivity())
        activityRecyclerView?.adapter = AddActivityRecyclerView(this, publicPlanViewModel.activity)
    }


    override fun addPlaceFragmentAddressFetch(place: PostPlaces) {
        (requireActivity().supportFragmentManager.findFragmentByTag(AddPlaceFragment::class.java.canonicalName)
                as? AddPlaceFragment)?.dismiss()

        addPlaceButton.text = "ADD AN OTHER PLACE"
        //addPlaceButton.strokeColor = ColorStateList.valueOf(Color.WHITE)

        publicPlanViewModel.place.add(place)
        addPlaceRecyclerView?.adapter?.notifyDataSetChanged()
    }

    override fun cancelPlaceFragmentAddressFetch() {

        (requireActivity().supportFragmentManager.findFragmentByTag(AddPlaceFragment::class.java.canonicalName)
                as? AddPlaceFragment)?.dismiss()
    }


    override fun AddActionFragmentFetch(task: PostTasks) {
        (requireActivity().supportFragmentManager.findFragmentByTag(AddActionFragment::class.java.canonicalName)
                as? AddActionFragment)?.dismiss()
        addActionButton.text = "ADD AN OTHER ACTION"
        //addActionButton.strokeColor = ColorStateList.valueOf(Color.WHITE)
        publicPlanViewModel.action.add(task)
        actionRecyclerView?.adapter?.notifyDataSetChanged()
    }

    override fun cancelActionFragmentFetch() {

        (requireActivity().supportFragmentManager.findFragmentByTag(AddActionFragment::class.java.canonicalName)
                as? AddActionFragment)?.dismiss()
    }


    override fun AddActivityFragmentFetch(activity: PostActivities) {
        (requireActivity().supportFragmentManager.findFragmentByTag(AddActivityFragment::class.java.canonicalName)
                as? AddActivityFragment)?.dismiss()
        addActivityButton.text = "ADD AN OTHER ACTIVITY"
        //addActivityButton.strokeColor = ColorStateList.valueOf(Color.WHITE)
        publicPlanViewModel.activity.add(activity)
        activityRecyclerView?.adapter?.notifyDataSetChanged()
    }

    override fun CancelActivityFragmentFetch() {

        (requireActivity().supportFragmentManager.findFragmentByTag(AddActivityFragment::class.java.canonicalName)
                as? AddActivityFragment)?.dismiss()
    }

    private fun howOftenDialog(context :Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.how_often_pop)

        val customIcon = dialog.findViewById(R.id.customIcon) as ImageView
        val onceCheckbox = dialog.findViewById(R.id.onceCheckbox) as MaterialCheckBox

        if (howOftenEditText.text.toString() == getText(R.string.once)){

            onceCheckbox.isChecked = true

        }

        onceCheckbox.setOnClickListener {

            if (onceCheckbox.isChecked){

                howOftenEditText.setText(getString(R.string.once))

            }else{

                howOftenEditText.setText("")
            }

            dialog.dismiss()
        }

        customIcon.setOnClickListener {

            val intent : Intent = Intent(context, CustomFrequency:: class.java)
            getActivity()?.startActivity(intent)

            dialog.dismiss()
        }

        dialog.show()
    }
}
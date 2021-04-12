package com.batanks.nextplan.home.fragment.tabfragment

import android.app.DatePickerDialog
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseDialogFragment
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.common.getLoadingDialog
import com.batanks.nextplan.eventdetails.viewmodel.EventDetailViewModel
import com.batanks.nextplan.home.fragment.action.AssignPeopleFragment
import com.batanks.nextplan.home.fragment.spinner.CustomArrayAdapterForAll
import com.batanks.nextplan.home.fragment.spinner.SpinnerModel
import com.batanks.nextplan.home.fragment.spinner.SpinnerModelForAll
import com.batanks.nextplan.home.fragment.tabfragment.publicplan.AddPeopleRecyclerViewAdapter
import com.batanks.nextplan.home.markRequiredInRed
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.swagger.api.EventAPI
import com.batanks.nextplan.swagger.model.*
import com.hbb20.CountryCodePicker
import com.hbb20.CountryCodePicker.OnCountryChangeListener
import kotlinx.android.synthetic.main.fragment_add_activity.*
import kotlinx.android.synthetic.main.fragment_add_activity_how_much.*
import kotlinx.android.synthetic.main.fragment_add_activity_when.*
import kotlinx.android.synthetic.main.fragment_add_activity_where.*
import kotlinx.android.synthetic.main.fragment_add_activity_who_with.*
import java.text.SimpleDateFormat
import java.util.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.arch.response.Status
import kotlinx.android.synthetic.main.activity_registration.view.*


class AddActivityFragment(val listner : AddActivityFragmentListener, private val position : Int, private val activityList : ArrayList<PostActivities>, private val event : Event?,
                          private val editButtonClicked : Boolean) : BaseDialogFragment() , View.OnClickListener, AssignPeopleFragment.AssignPeopleFragmentListner {

    private val newActivity : Int = -1
    private var fromDate : String? = null
    private val postParticipantsList : ArrayList<String> = arrayListOf()
    private val finalActivityParticipant: ArrayList<ActivityParticipant> = arrayListOf()
    private var defaultSelectedGuests : ArrayList<ActivityParticipant>? = arrayListOf()
    private lateinit var ccp: CountryCodePicker
    var locale: Locale? = null

    private val eventDetailViewModel: EventDetailViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            getContext()?.let {
                RetrofitClient.getRetrofitInstance(it)?.create(EventAPI::class.java)?.let {
                    EventDetailViewModel(it)
                }
            }
        }).get(EventDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.App_DialogFragment_Theme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        /*originalMode = activity?.window?.getSoftInputMode()
        activity?.window?.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
        )*/

       // getActivity()?.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        //addPeopleRecyclerViewEdit.layoutManager = LinearLayoutManager(requireActivity())

        dialog?.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        return inflater.inflate(R.layout.fragment_add_activity, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addPeopleRecyclerViewEdit.layoutManager = LinearLayoutManager(requireActivity())

        /*addPeopleRecyclerViewEdit.layoutManager = LinearLayoutManager(requireActivity())
        addPeopleRecyclerViewEdit.adapter = AddPeopleRecyclerViewAdapter(event!!.activities[position].participants)*/

        activityNameTextField.markRequiredInRed()
        fromTextField.markRequiredInRed()
        hoursWhileTextField.markRequiredInRed()
        minutesWhileTextField.markRequiredInRed()

        if (position >= 0){

            val hours = activityList[position].duration?.div(60)
            val mins = activityList[position].duration?.rem(60)
            var natureOfCost : String? = null

            if (activityList[position].per_person == true){ natureOfCost = "Cost Per Person" } else { natureOfCost = "Total Cost" }

            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss") /*"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"*/
            val outputFormat = SimpleDateFormat("EEE, MMM d yyyy")

            val fromDate = inputFormat.parse(activityList[position].date)
            val formattedFromDate = outputFormat.format(fromDate)

            activityNameEditText.setText(activityList[position].title)
            et_from_date_activity.setText(formattedFromDate)
            et_activity_hours.setText(hours.toString())
            et_activity_minutes.setText(mins.toString())
            ActivityplaceNameEditText.setText(activityList[position].place?.name)
            activity_addressEditText.setText(activityList[position].place?.address)
            activity_zipCodeEditText.setText(activityList[position].place?.zipcode)
            activity_townEditText.setText(activityList[position].place?.city)

            val countrycode : String? = activityList[position].place?.country?.let { getCountryCode(it) }
            ccp_activity_country.setCountryForNameCode(countrycode)

            costOfTheActivityEditText.setText(activityList[position].price.toString())
            actv_activity_nature_of_the_cost.setText(natureOfCost)
            max_participantsEditText.setText(activityList[position].max_participants.toString())

            defaultSelectedGuests = event!!.activities[position].participants

            if(event!!.activities[position].participants.size > 0){

                addPeopleRecyclerViewEdit.visibility = View.VISIBLE
                addPeopleRecyclerViewEdit.adapter = AddPeopleRecyclerViewAdapter(event!!.activities[position].participants)
            }
        }

        loadingDialog = requireContext().getLoadingDialog(0, R.string.gathering_information, theme = R.style.AlertDialogCustom)



        et_from_date_activity.setOnClickListener(this)
        //activity_copy_plan_address.setOnClickListener(this)
        btn_activity_ok.setOnClickListener(this)
        activity_cancel.setOnClickListener(this)
        activity_add_people.setOnClickListener(this)

       /* actv_activity_nature_of_the_cost.setOnClickListener {

            populateCategory()
        }*/

        ccp_activity_country.setOnClickListener {

            dismissKeyboard()
        }

        rl_activity_country_layout.setOnClickListener {

            println("Relative layout clicked")
        }


        ccp_activity_country.setOnCountryChangeListener(OnCountryChangeListener {

            //Toast.makeText(context, "Updated " + ccp_activity_country.getSelectedCountryName(), Toast.LENGTH_SHORT).show()
        })

        val nature_of_the_cost = arrayOf("Cost Per Person" , "Total Cost")

        val adapter = activity?.let { ArrayAdapter<String>(it, android.R.layout.simple_list_item_1, nature_of_the_cost) }
        actv_activity_nature_of_the_cost.setAdapter(adapter)
        actv_activity_nature_of_the_cost.threshold = 1

        /*val adapter = ArrayAdapter(requireContext(), R.layout.list_item, nature_of_the_cost)
        actv_activity_nature_of_the_cost.setAdapter(adapter)
        actv_activity_nature_of_the_cost.threshold = 1*/
        //actv_activity_nature_of_the_cost.setDropDownBackgroundResource(R.color.colorLightBlue)

        // Set an item click listener for auto complete text view
        actv_activity_nature_of_the_cost.onItemClickListener = AdapterView.OnItemClickListener{
            parent,view,position,id->
            val selectedItem = parent.getItemAtPosition(position).toString()
            // Display the clicked item using toast
            //Toast.makeText(activity,"Selected : $selectedItem",Toast.LENGTH_SHORT).show()
        }


        // Set a dismiss listener for auto complete text view
        actv_activity_nature_of_the_cost.setOnDismissListener {
            //Toast.makeText(activity,"Suggestion closed.",Toast.LENGTH_SHORT).show()

            //totalCostTextField.hint = null
        }
        // Set a focus change listener for auto complete text view
        actv_activity_nature_of_the_cost.onFocusChangeListener = View.OnFocusChangeListener{
            view, b ->
            if(b){
                // Display the suggestion dropdown on focus
                actv_activity_nature_of_the_cost.showDropDown()
                dismissKeyboard()
            }
        }

        view.setOnTouchListener(object : View.OnTouchListener {

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                if (event?.action == MotionEvent.ACTION_DOWN) {

                    val imm = v?.getContext()?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                    v.clearFocus()
                }

                return false
            }
        })
    }

    fun getCountryCode(countryName: String) =
            Locale.getISOCountries().find { Locale("", it).displayCountry == countryName }

    fun Window.getSoftInputMode() : Int {
        return attributes.softInputMode
    }

    private fun populateCategory() {
        val customSpinner = CustomArrayAdapterForAll(requireContext(), listOf(
                SpinnerModelForAll("Trip"),
                SpinnerModelForAll("Professional"),
                SpinnerModelForAll("Leisure"),
                SpinnerModelForAll("Institutional"),
                SpinnerModelForAll("Other")))
        (totalCostTextField.editText as? AutoCompleteTextView)?.setAdapter(customSpinner)
        (totalCostTextField.editText as? AutoCompleteTextView)?.setOnItemClickListener { parent, _, position, id ->
            val obj = parent.adapter.getItem(position) as SpinnerModel?
            actv_activity_nature_of_the_cost.setText(obj?.title)
        }
    }

    fun addActivityPeriodClicked() {

        /*fragmentManager?.let { AddPeriodFragment().show(it, AddPeriodFragment::class.java.simpleName) }*/

        val mCal = Calendar.getInstance()
        val mDay = mCal.get(Calendar.DAY_OF_MONTH)
        val mMonth = mCal.get(Calendar.MONTH)
        val mYear = mCal.get(Calendar.YEAR)
        val mHour = mCal.get(Calendar.HOUR_OF_DAY)
        val mMin = mCal.get(Calendar.MINUTE)

        val fromDate = DatePickerDialog(requireContext(), R.style.AlertDialogTheme, DatePickerDialog.OnDateSetListener
        { fromDatePicker, fromYear, fromMonth, fromDay ->
                    val cal = Calendar.getInstance()
                    cal.set(fromYear, fromMonth, fromDay)
                    /*FromDate*/
                    val dateFormatter = SimpleDateFormat("E, MMM dd yyyy")
                    val startDate = dateFormatter.format(cal.time)

                    fromTextField.editText?.setText(startDate)

                    val dateFormatter1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")  //2020-08-27T15:36:28.811Z
                    fromDate = dateFormatter1.format(cal.time)

            /*publicPlanViewModel.activityDate.add(EventDate(id = publicPlanViewModel.activityDate.size, start = startDate,
                            end = "", votes = mutableListOf()))*/

        }, mYear, mMonth, mDay)
        fromDate.datePicker.minDate = System.currentTimeMillis()
        fromDate.setCanceledOnTouchOutside(false)
        fromDate.show()
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.et_from_date_activity ->{
                addActivityPeriodClicked()
                dismissKeyboard()
            }

            R.id.btn_activity_ok ->{

                var zipcode : String? = null
                var price : Int = 0

                if (activityNameTextField?.editText?.length()!! >= 2){

                    if (!TextUtils.isEmpty(fromTextField?.editText?.text.toString())){

                        if (!TextUtils.isEmpty(hoursWhileTextField.editText?.text.toString())){

                            if (!TextUtils.isEmpty(minutesWhileTextField.editText?.text.toString())){

                                showLoader()
                                dismissKeyboard()

                                if (!TextUtils.isEmpty(activity_zipCodeTextField?.editText?.text.toString())){

                                    zipcode = activity_zipCodeTextField?.editText?.text.toString()

                                    //println(zipcode)
                                }

                                val place = PostPlaceInfo(name = ActivityplaceNameTextField?.editText?.text.toString(),
                                        address = activity_addressTextField?.editText?.text.toString(),
                                        zipcode = zipcode,
                                        city = activity_townTextField?.editText?.text.toString(),
                                        country = ccp_activity_country.getSelectedCountryName(),
                                        map = false)

                                val stringBuilder = StringBuilder()
                                        .append(place.name)
                                        .append("+")
                                        .append(place.address)
                                        .append("+")
                                        .append(place.city)
                                        .append("+")
                                        .append(place.country)
                                        .append("+")
                                        .append(place.zipcode)

                                if (!TextUtils.isEmpty(ActivityplaceNameTextField?.editText?.text.toString())){

                                    val result: List<Address> = Geocoder(view?.context).getFromLocationName(stringBuilder.toString(), 5)

                                    if (result.isEmpty()) {
                                        //showMessage("We are unable to find the location info, Please enter a different location.")
                                        Toast.makeText(activity,"We are unable to find the location info, Please enter a different location.",Toast.LENGTH_SHORT).show()

                                    } else {

                                        //place.latitude = result[0].latitude
                                        //place.longitude = result[0].longitude
                                        place.map = true
                                        //listener.addPlaceFragmentAddressFetch(place)
                                    }
                                }

                                /* val result: List<Address> = Geocoder(view?.context).getFromLocationName(stringBuilder.toString(), 5)
                                if (result.isEmpty()) {
                                    //showMessage("We are unable to find the location info, Please enter a different location.")
                                    Toast.makeText(activity,"We are unable to find the location info, Please enter a different location.",Toast.LENGTH_SHORT).show()
                                } else {
                                    place.latitude = result[0].latitude
                                    place.longitude = result[0].longitude
                                    //listener.addPlaceFragmentAddressFetch(place)
                                }*/

                                val list = arrayListOf<String>()

                                var perPerson : Boolean = false

                                if (totalCostTextField.editText?.text.toString() == "Cost Per Person"){

                                    perPerson = true

                                } else if (totalCostTextField.editText?.text.toString() == "Total Cost"){

                                    perPerson = false
                                }

                                var maxParticipants : Int = 0

                                if (!TextUtils.isEmpty(max_participantsTextField.editText?.text.toString())){

                                    maxParticipants = max_participantsTextField.editText?.text.toString().toInt()
                                }

                               /* var duration : Int = 0

                                if (!TextUtils.isEmpty(hoursWhileTextField.editText?.text.toString()) && !TextUtils.isEmpty(minutesWhileTextField.editText?.text.toString())){



                                } else if(!TextUtils.isEmpty(hoursWhileTextField.editText?.text.toString()) && TextUtils.isEmpty(minutesWhileTextField.editText?.text.toString())){

                                    duration =
                                }*/

                                val hour : Int = hoursWhileTextField?.editText?.text.toString().toInt() * 60
                                val mins : Int = minutesWhileTextField?.editText?.text.toString().toInt()

                                val duration : Int = hour + mins


                                if (!TextUtils.isEmpty(costOfTheActivityTextField?.editText?.text.toString())){

                                    price = costOfTheActivityTextField?.editText?.text.toString().toInt()

                                    println(price)
                                }

                                val activity = PostActivities(place = place, price = price, participants = list,
                                        title = activityNameTextField?.editText?.text.toString(), detail = "", date = fromDate ,
                                        max_participants = maxParticipants , per_person = perPerson , duration = duration )


                                if (editButtonClicked == true){

                                    if (position >= 0){

                                        val inputFormatter = SimpleDateFormat("E, MMM dd yyyy")
                                        val outputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                                        val inputDate = inputFormatter.parse(et_from_date_activity.text.toString())
                                        val date =  outputFormatter.format(inputDate)

                                        eventDetailViewModel.apiEventActivityUpdate(event!!.activities[position].id.toString(),event!!.id.toString(),
                                                PostActivities(place, price, postParticipantsList, activityNameTextField?.editText?.text.toString(), "",
                                                        date, maxParticipants, perPerson, duration))

                                        eventDetailViewModel.responseLiveDataActivityFullUpdate.observe(viewLifecycleOwner, Observer { response ->

                                            when (response.status) {
                                                Status.LOADING -> {
                                                    showLoader()
                                                }

                                                Status.SUCCESS -> {
                                                    hideLoader()

                                                    listner.AddActivityFragmentFetch(position, activity)
                                                }
                                                Status.ERROR -> {
                                                    hideLoader()

                                                    showMessage(response.error?.message.toString())
                                                    println(response.error?.message.toString())
                                                }
                                            }
                                        })

                                    } else {

                                            eventDetailViewModel.apiEventActivityCreate(event!!.id.toString(), PostActivities(place, price, postParticipantsList,
                                                    activityNameTextField?.editText?.text.toString(), "", fromDate, maxParticipants, perPerson, duration))

                                        eventDetailViewModel.responseLiveDataActivityCreate.observe(viewLifecycleOwner, Observer { response ->

                                            when (response.status) {
                                                Status.LOADING -> {
                                                    showLoader()
                                                }

                                                Status.SUCCESS -> {
                                                    hideLoader()

                                                    listner.AddActivityFragmentFetch(position, activity)
                                                }
                                                Status.ERROR -> {
                                                    hideLoader()

                                                    showMessage(response.error?.message.toString())
                                                    println(response.error?.message.toString())
                                                }
                                            }
                                        })

                                    }

                                }else {

                                    listner.AddActivityFragmentFetch(newActivity, activity)
                                    hideLoader()

                                }
                            } else {

                                Toast.makeText(context,getString(R.string.activity_mins_error), Toast.LENGTH_LONG).show()
                                minutesWhileTextField.isFocusable = true
                                minutesWhileTextField.isFocusableInTouchMode = true
                                minutesWhileTextField.requestFocus()
                            }
                        } else {

                            Toast.makeText(context,getString(R.string.activity_hours_error), Toast.LENGTH_LONG).show()
                            hoursWhileTextField.isFocusable = true
                            hoursWhileTextField.isFocusableInTouchMode = true
                            hoursWhileTextField.requestFocus()
                        }
                    }else {

                        Toast.makeText(context,getString(R.string.activity_date_error), Toast.LENGTH_LONG).show()
                        fromTextField.isFocusable = true
                        fromTextField.isFocusableInTouchMode = true
                        fromTextField.requestFocus()
                    }
                } else {

                        //actionNameTextField.error = "Action name is Required"
                        activityNameTextField.editText?.setError(getString(R.string.activity_name_error))
                        activityNameTextField.requestFocus()
                        //Toast.makeText(activity,"Action name cannot be empty",Toast.LENGTH_SHORT).show()
                }
            }

            R.id.activity_cancel -> {

                listner.CancelActivityFragmentFetch()

            }

            /*R.id.activity_copy_plan_address ->{
                if(activityViewModel.place?.size >0){
                    val place = activityViewModel.place.get(0)
                }
            }*/

            R.id.activity_add_people ->{

                if (editButtonClicked == true){

               /* requireActivity().supportFragmentManager
                        .beginTransaction()
                        .add(AddContactsFragment(), AddContactsFragment::class.java.canonicalName)
                        .commitAllowingStateLoss()*/

                    requireActivity().supportFragmentManager
                            .beginTransaction()
                            .add(AssignPeopleFragment(this, event, false, defaultSelectedGuests), AssignPeopleFragment::class.java.canonicalName)
                            .commitAllowingStateLoss()
                } else {

                    Toast.makeText(activity,getString(R.string.add_people_toast),Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    interface AddActivityFragmentListener {
        fun AddActivityFragmentFetch(updatedPosition : Int, activity : PostActivities)
        fun CancelActivityFragmentFetch()
    }

    override fun AddSelectedAssignee(contact: Guests?, id: Int?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun AddSelectedActivityParticipants(activityParticipants: ArrayList<Guests>?) {

        finalActivityParticipant.clear()

        if (activityParticipants != null) {

            for (item in activityParticipants){

                val participant = item.toActivityParticipant()

                if (!finalActivityParticipant.contains(participant)){

                    finalActivityParticipant.add(participant)
                }
            }
        }

        for (participant in finalActivityParticipant){

            val name = participant.username

            if (name != null) {
                postParticipantsList.add(name)
            }
        }

        defaultSelectedGuests = finalActivityParticipant


        addPeopleRecyclerViewEdit.visibility = View.VISIBLE
        addPeopleRecyclerViewEdit.adapter = AddPeopleRecyclerViewAdapter(finalActivityParticipant)

    }

    fun Guests.toActivityParticipant() = ActivityParticipant(

            id = user_id,
            username = name,
            email = email,
            first_name = null,
            last_name = null,
            phone_number = phone_number,
            picture = null
    )
}
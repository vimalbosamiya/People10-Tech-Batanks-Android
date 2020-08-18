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
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseDialogFragment
import com.batanks.nextplan.common.getLoadingDialog
import com.batanks.nextplan.home.fragment.contacts.AddContactsFragment
import com.batanks.nextplan.home.fragment.spinner.CustomArrayAdapterForAll
import com.batanks.nextplan.home.fragment.spinner.SpinnerModel
import com.batanks.nextplan.home.fragment.spinner.SpinnerModelForAll
import com.batanks.nextplan.home.fragment.tabfragment.publicplan.viewmodel.PublicPlanViewModel
import com.batanks.nextplan.swagger.model.Activity
import com.batanks.nextplan.swagger.model.EventDate
import com.batanks.nextplan.swagger.model.Place
import com.hbb20.CountryCodePicker.OnCountryChangeListener
import kotlinx.android.synthetic.main.fragment_add_activity.*
import kotlinx.android.synthetic.main.fragment_add_activity_how_much.*
import kotlinx.android.synthetic.main.fragment_add_activity_when.*
import kotlinx.android.synthetic.main.fragment_add_activity_where.*
import java.text.SimpleDateFormat
import java.util.*


class AddActivityFragment(val listner : AddActivityFragmentListener) : BaseDialogFragment() , View.OnClickListener {

    private var originalMode : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.App_DialogFragment_Theme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        /*originalMode = activity?.window?.getSoftInputMode()
        activity?.window?.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
        )*/

        getActivity()?.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        return inflater.inflate(R.layout.fragment_add_activity, container, false)
    }

    private val activityViewModel: PublicPlanViewModel by lazy {
        ViewModelProvider(this)[PublicPlanViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = requireContext().getLoadingDialog(0, R.string.gathering_information, theme = R.style.AlertDialogCustom)
        et_from_date_activity.setOnClickListener(this)
        //activity_copy_plan_address.setOnClickListener(this)
        btn_activity_ok.setOnClickListener(this)
        activity_cancel.setOnClickListener(this)
        activity_add_people.setOnClickListener(this)

       /* actv_activity_nature_of_the_cost.setOnClickListener {

            populateCategory()
        }*/

        ccp_activity_country.setOnCountryChangeListener(OnCountryChangeListener { Toast.makeText(context, "Updated " + ccp_activity_country.getSelectedCountryName(), Toast.LENGTH_SHORT).show() })

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
                    println(startDate)
                    fromTextField.editText?.setText(startDate)
                    activityViewModel.activityDate.add(EventDate(id = activityViewModel.activityDate.size, start = startDate,
                            end = "", votes = mutableListOf()))
        }, mYear, mMonth, mDay)
        fromDate.datePicker.minDate = System.currentTimeMillis()
        fromDate.setCanceledOnTouchOutside(false)
        fromDate.show()
    }
    private val publicPlanViewModel: PublicPlanViewModel by lazy {
        ViewModelProvider(this)[PublicPlanViewModel::class.java]
    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.et_from_date_activity ->{
                addActivityPeriodClicked()
            }

            R.id.btn_activity_ok ->{

                if (!TextUtils.isEmpty(activityNameTextField?.editText?.text.toString())){

                    showLoader()
                    dismissKeyboard()

                    val place = Place(name = ActivityplaceNameTextField?.editText?.text.toString(),
                            address = activity_addressTextField?.editText?.text.toString(),
                            zipcode = activity_zipCodeTextField?.editText?.text.toString(),
                            city = activity_townTextField?.editText?.text.toString(),
                            country = ccp_activity_country.getSelectedCountryName(),
                            map = false,
                            latitude = 0.0,
                            longitude = 0.0)

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

                    val result: List<Address> = Geocoder(view?.context).getFromLocationName(stringBuilder.toString(), 5)
                    if (result.isEmpty()) {
                        //showMessage("We are unable to find the location info, Please enter a different location.")
                        Toast.makeText(activity,"We are unable to find the location info, Please enter a different location.",Toast.LENGTH_SHORT).show()
                    } else {
                        place.latitude = result[0].latitude
                        place.longitude = result[0].longitude
                        //listener.addPlaceFragmentAddressFetch(place)
                    }
                    val list = listOf<Int>(1,2,3)
                    val activity = Activity(0 , place , costOfTheActivityTextField?.editText?.text.toString() ,
                            activityNameTextField?.editText?.text.toString() , "" , "date" , 0 ,
                            "" , true , 1 , list);
                    listner.AddActivityFragmentFetch(activity)
                    hideLoader()
                } else {

                    if(TextUtils.isEmpty(activityNameTextField?.editText?.text.toString())){

                        //actionNameTextField.error = "Action name is Required"
                        activityNameTextField.editText?.error = "Activity name is Required"
                        activityNameTextField.requestFocus()
                        //Toast.makeText(activity,"Action name cannot be empty",Toast.LENGTH_SHORT).show()
                    }
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
                requireActivity().supportFragmentManager
                        .beginTransaction()
                        .add(AddContactsFragment(), AddContactsFragment::class.java.canonicalName)
                        .commitAllowingStateLoss()
            }
        }
    }
    interface AddActivityFragmentListener {
        fun AddActivityFragmentFetch(activity :Activity)
        fun CancelActivityFragmentFetch()
    }
}
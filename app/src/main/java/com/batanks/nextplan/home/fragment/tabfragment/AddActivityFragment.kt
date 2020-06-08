package com.batanks.nextplan.home.fragment.tabfragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.red
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseDialogFragment
import com.batanks.nextplan.common.getLoadingDialog
import com.batanks.nextplan.home.fragment.place.AddPlaceFragment
import com.batanks.nextplan.home.fragment.tabfragment.publicplan.viewmodel.PublicPlanViewModel
import com.batanks.nextplan.swagger.model.Activity
import com.batanks.nextplan.swagger.model.EventDate
import com.batanks.nextplan.swagger.model.Place
import kotlinx.android.synthetic.main.fragment_add_activity.*
import kotlinx.android.synthetic.main.fragment_add_activity_how_much.*
import kotlinx.android.synthetic.main.fragment_add_activity_when.*
import kotlinx.android.synthetic.main.fragment_add_activity_where.*
import java.text.SimpleDateFormat
import java.util.*

class AddActivityFragment(val listner : AddActivityFragmentListener) : BaseDialogFragment() , View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.App_DialogFragment_Theme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_activity, container, false)
    }
    private val activityViewModel: PublicPlanViewModel by lazy {
        ViewModelProvider(this)[PublicPlanViewModel::class.java]
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = requireContext().getLoadingDialog(0, R.string.gathering_information, theme = R.style.AlertDialogCustom)
        et_from_date_activity.setOnClickListener(this)
        activity_copy_plan_address.setOnClickListener(this)
        btn_activity_ok.setOnClickListener(this)
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
                showLoader()
                dismissKeyboard()

                val place = Place(name = ActivityplaceNameTextField?.editText?.text.toString(),
                        address = activity_addressTextField?.editText?.text.toString(),
                        zipcode = activity_zipCodeTextField?.editText?.text.toString(),
                        city = activity_townTextField?.editText?.text.toString(),
                        country = activity_countryTextField?.editText?.text.toString(),
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
                    showMessage("We are unable to find the location info, Please enter a different location.")
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
            }
            R.id.activity_copy_plan_address ->{
                if(activityViewModel.place?.size >0){
                    val place = activityViewModel.place.get(0)
                }
            }
        }
    }
    interface AddActivityFragmentListener {
        fun AddActivityFragmentFetch(activity :Activity)
    }
}
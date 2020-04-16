package com.batanks.newplan.home.fragment.tabfragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.newplan.R
import com.batanks.newplan.home.fragment.action.AddActionFragment
import com.batanks.newplan.home.fragment.period.AddPeriodFragment
import com.batanks.newplan.home.fragment.period.AddPeriodRecyclerView
import com.batanks.newplan.home.fragment.period.CalenderModel
import com.batanks.newplan.home.fragment.place.AddPlaceFragment
import com.batanks.newplan.home.fragment.spinner.CustomArrayAdapter
import com.batanks.newplan.home.fragment.spinner.SpinnerModel
import kotlinx.android.synthetic.main.fragment_public_new_plan.*
import kotlinx.android.synthetic.main.layout_add_plan_add_action.*
import kotlinx.android.synthetic.main.layout_add_plan_add_activity.*
import kotlinx.android.synthetic.main.layout_add_plan_add_period.*
import kotlinx.android.synthetic.main.layout_add_plan_add_place.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PublicPlanFragment : Fragment(), ButtonContract, View.OnClickListener, AddPeriodRecyclerView.AddPeriodRecyclerViewCallBack {

    var addPeriodRecyclerView: RecyclerView? = null
    private var choosenDateTime = ArrayList<CalenderModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_public_new_plan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        populateCategory()

        addPlaceButton.setOnClickListener(this)
        addActionButton.setOnClickListener(this)
        addActivityButton.setOnClickListener(this)
        addPeriodButton.setOnClickListener(this)

        addPeriodRecyclerView = requireActivity().findViewById(R.id.periodRecyclerView)
        addPeriodRecyclerView?.setHasFixedSize(true)
        addPeriodRecyclerView?.layoutManager = LinearLayoutManager(requireActivity())
        addPeriodRecyclerView?.adapter = AddPeriodRecyclerView(this, choosenDateTime)
    }

    private fun populateCategory() {
        val customSpinner = CustomArrayAdapter(requireContext(), listOf(
                SpinnerModel("Trip", R.drawable.ic_category_trip),
                SpinnerModel("Professional", R.drawable.ic_category_professional),
                SpinnerModel("Leisure", R.drawable.ic_category_leisure),
                SpinnerModel("Institutional", R.drawable.ic_category_institutional),
                SpinnerModel("Other", R.drawable.ic_category_others)))
        (categoryTextField.editText as? AutoCompleteTextView)?.setAdapter(customSpinner)
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

                val mCal = Calendar.getInstance()
                val mDay = mCal.get(Calendar.DAY_OF_MONTH)
                val mMonth = mCal.get(Calendar.MONTH)
                val mYear = mCal.get(Calendar.YEAR)
                val mHour = mCal.get(Calendar.HOUR_OF_DAY)
                val mMin = mCal.get(Calendar.MINUTE)

                val fromDate = DatePickerDialog(requireContext(), R.style.AlertDialogTheme, OnDateSetListener { fromDatePicker, fromYear, fromMonth, fromDay ->
                    val toDate = DatePickerDialog(requireContext(), R.style.AlertDialogTheme, OnDateSetListener { toDatePicker, toYear, toMonth, toDay ->
                        val fromTime = TimePickerDialog(requireContext(), R.style.AlertDialogTheme, TimePickerDialog.OnTimeSetListener { fromTimePicker, fromHourOfDay, fromMinute ->
                            val toTime = TimePickerDialog(requireContext(), R.style.AlertDialogTheme, TimePickerDialog.OnTimeSetListener { toTimePicker, toHourOfDay, toMinute ->

                                val cal = Calendar.getInstance()
                                cal.set(fromYear, fromMonth, fromDay, fromHourOfDay, fromMinute)

                                /*FromDate*/
                                val dateFormatter = SimpleDateFormat("E, MMM dd yyyy HH:mm a")
                                val chosenFromDateString = dateFormatter.format(cal.time)
                                println(chosenFromDateString)

                                /*ToDate*/
                                cal.set(toYear, toMonth, toDay, toHourOfDay, toMinute)
                                val chosenToDateString = dateFormatter.format(cal.time)
                                println(chosenToDateString)

                                choosenDateTime.add(CalenderModel(fromDate = chosenFromDateString, toDate = chosenToDateString))
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
                fromDate.show()
            }
        }
    }

    override fun addPeriodClicked() {
        fragmentManager?.let { AddPeriodFragment().show(it, AddPeriodFragment::class.java.simpleName) }
    }

    override fun addPlaceClicked() {
        requireActivity().supportFragmentManager
                .beginTransaction()
                .add(AddPlaceFragment(), AddPlaceFragment::class.java.canonicalName)
                .commitAllowingStateLoss()
    }

    override fun addActionClicked() {
        requireActivity().supportFragmentManager
                .beginTransaction()
                .add(AddActionFragment(), AddActionFragment::class.java.canonicalName)
                .commitAllowingStateLoss()
    }

    override fun addActivityClicked() {
        requireActivity().supportFragmentManager
                .beginTransaction()
                .add(AddActivityFragment(), AddActivityFragment::class.java.canonicalName)
                .commitAllowingStateLoss()
    }

    override fun addPeopleClicked() {}

    override fun addPeriodItemListener(pos: Int) {
        addPeriodButton.text = "ADD A PERIOD"
        addPeriodRecyclerView?.adapter?.notifyDataSetChanged()
        /*val address = Geocoder(requireContext()).getFromLocationName("Kongu school,638182", 5)*/
    }
}
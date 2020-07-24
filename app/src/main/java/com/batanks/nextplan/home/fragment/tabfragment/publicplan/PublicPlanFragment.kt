package com.batanks.nextplan.home.fragment.tabfragment.publicplan

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.NumberPicker
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseFragment
import com.batanks.nextplan.home.fragment.action.AddActionFragment
import com.batanks.nextplan.home.fragment.action.AddActionRecyclerView
import com.batanks.nextplan.home.fragment.contacts.AddContactsFragment
import com.batanks.nextplan.home.fragment.period.AddPeriodRecyclerView
import com.batanks.nextplan.home.fragment.place.AddPlaceFragment
import com.batanks.nextplan.home.fragment.place.AddPlaceRecyclerView
import com.batanks.nextplan.home.fragment.spinner.CustomArrayAdapter
import com.batanks.nextplan.home.fragment.spinner.SpinnerModel
import com.batanks.nextplan.home.fragment.tabfragment.AddActivityFragment
import com.batanks.nextplan.home.fragment.tabfragment.AddActivityRecyclerView
import com.batanks.nextplan.home.fragment.tabfragment.ButtonContract
import com.batanks.nextplan.home.fragment.tabfragment.publicplan.viewmodel.PublicPlanViewModel
import com.batanks.nextplan.swagger.model.Activity
import com.batanks.nextplan.swagger.model.EventDate
import com.batanks.nextplan.swagger.model.Place
import com.batanks.nextplan.swagger.model.Task
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_public_new_plan.*
import kotlinx.android.synthetic.main.layout_add_plan_add_action.*
import kotlinx.android.synthetic.main.layout_add_plan_add_activity.*
import kotlinx.android.synthetic.main.layout_add_plan_add_people.*
import kotlinx.android.synthetic.main.layout_add_plan_add_period.*
import kotlinx.android.synthetic.main.layout_add_plan_add_place.*
import java.text.SimpleDateFormat
import java.util.*


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

    var system: Resources = Resources.getSystem()

    private val publicPlanViewModel: PublicPlanViewModel by lazy {
        ViewModelProvider(this)[PublicPlanViewModel::class.java]
    }

   /* private val publicPlanViewModel: PublicPlanViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            RetrofitClient.getRetrofitInstance(this)?.create(EventAPI::class.java)?.let {
                PublicPlanViewModel(it)
            }
        }).get(PublicPlanViewModel::class.java)
    }*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.fragment_public_new_plan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        planNameEditText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                planNameEditText.hint = "Vinoth"
                planNameEditText.requestFocus()
            } else {
                planNameEditText.hint = ""
            }
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
        
        view.setOnTouchListener(object : View.OnTouchListener {

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                if (event?.action == MotionEvent.ACTION_DOWN) {
                    if(v is EditText) {
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

        var planName : String = "<font color='#ffffff'>Name </font>" + "<font color='#FF0000'>*</font>"

        //planNameEditText.hint = Html.fromHtml(planName) as Editable?

        //planNameTextField.markRequiredInRed()

        //planNameTextField.hint = Html.fromHtml(planName) as Editable?

        //populateCategory()

        //planNameTextField.hint = ("Plan name" +" "+getString(R.string.asteriskred))

        addPlaceButton.setOnClickListener(this)
        addActionButton.setOnClickListener(this)
        addActivityButton.setOnClickListener(this)
        addPeriodButton.setOnClickListener(this)
        addPeopleButton.setOnClickListener(this)
        actv_category.setOnClickListener {

            populateCategory()
        }

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

    /*fun TextInputLayout.markRequiredInRed() {

        hint = buildSpannedString {
            append(hint)
            color(Color.RED) { append(" *") }
        }
    }*/

    private fun populateCategory() {
        val customSpinner = CustomArrayAdapter(requireContext(), listOf(
                SpinnerModel("Trip", R.drawable.ic_category_trip),
                SpinnerModel("Professional", R.drawable.ic_category_professional),
                SpinnerModel("Leisure", R.drawable.ic_category_leisure),
                SpinnerModel("Institutional", R.drawable.ic_category_institutional),
                SpinnerModel("Other", R.drawable.ic_category_others)))
        (categoryTextField.editText as? AutoCompleteTextView)?.setAdapter(customSpinner)
        (categoryTextField.editText as? AutoCompleteTextView)?.setOnItemClickListener { parent, _, position, id ->
            val obj = parent.adapter.getItem(position) as SpinnerModel?
            actv_category.setText(obj?.title)
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
        }
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
                        addPeriodButton.strokeColor = ColorStateList.valueOf(Color.WHITE)

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

   /* fun set_timepicker_text_colour(){

        var ampm_numberpicker_id : Int = system.getIdentifier("amPm", "id", "android")

        val ampm_numberpicker = time_picker.findViewById(ampm_numberpicker_id) as NumberPicker

    }*/


    override fun addPlaceClicked() {
        requireActivity().supportFragmentManager
                .beginTransaction()
                .add(AddPlaceFragment(this), AddPlaceFragment::class.java.canonicalName)
                .commitAllowingStateLoss()

        addPlaceRecyclerView?.adapter?.notifyDataSetChanged()
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
            addPeriodButton.strokeColor = ColorStateList.valueOf(Color.WHITE)
        }

        addPeriodRecyclerView?.adapter?.notifyDataSetChanged()
    }

    override fun closeButtonAddPlaceItemListener(pos: Int) {

        if(publicPlanViewModel.place.size == 0){

            addPlaceButton.text = "ADD A PLACE"
            addPlaceButton.strokeColor = ColorStateList.valueOf(resources.getColor(R.color.colorLightBlue))
        } else {

            addPlaceButton.text = "ADD ANOTHER PLACE"
            addPlaceButton.strokeColor = ColorStateList.valueOf(Color.WHITE)
        }

        addPlaceRecyclerView?.adapter?.notifyDataSetChanged()
    }

    override fun closeButtonAddActionItemListener(pos: Int) {

        if (publicPlanViewModel.action.size == 0){

            addActionButton.text = "ADD ACTION"
            addActionButton.strokeColor = ColorStateList.valueOf(resources.getColor(R.color.colorLightBlue))
        } else {

            addActionButton.text = "ADD ANOTHER ACTION"
            addActionButton.strokeColor = ColorStateList.valueOf(Color.WHITE)

        }


        actionRecyclerView?.adapter?.notifyDataSetChanged()
    }

    override fun closeButtonAddActivityItemListener(pos: Int) {

        if (publicPlanViewModel.activity.size == 0){

            addActivityButton.text = "ADD ACTIVITY"
            addActivityButton.strokeColor = ColorStateList.valueOf(resources.getColor(R.color.colorLightBlue))
        } else {

            addActivityButton.text = "ADD ANOTHER ACTIVITY"
            addActivityButton.strokeColor = ColorStateList.valueOf(Color.WHITE)
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


    override fun addPlaceFragmentAddressFetch(place: Place) {
        (requireActivity().supportFragmentManager.findFragmentByTag(AddPlaceFragment::class.java.canonicalName)
                as? AddPlaceFragment)?.dismiss()

        addPlaceButton.text = "ADD ANOTHER PLACE"
        //addPlaceButton.strokeColor = ColorStateList.valueOf(Color.WHITE)

        publicPlanViewModel.place.add(place)
        addPlaceRecyclerView?.adapter?.notifyDataSetChanged()
    }

    override fun cancelPlaceFragmentAddressFetch() {

        (requireActivity().supportFragmentManager.findFragmentByTag(AddPlaceFragment::class.java.canonicalName)
                as? AddPlaceFragment)?.dismiss()
    }


    override fun AddActionFragmentFetch(task: Task) {
        (requireActivity().supportFragmentManager.findFragmentByTag(AddActionFragment::class.java.canonicalName)
                as? AddActionFragment)?.dismiss()
        addActionButton.text = "ADD ANOTHER ACTION"
        addActionButton.strokeColor = ColorStateList.valueOf(Color.WHITE)
        publicPlanViewModel.action.add(task)
        actionRecyclerView?.adapter?.notifyDataSetChanged()
    }

    override fun cancelActionFragmentFetch() {

        (requireActivity().supportFragmentManager.findFragmentByTag(AddActionFragment::class.java.canonicalName)
                as? AddActionFragment)?.dismiss()
    }


    override fun AddActivityFragmentFetch(activity: Activity) {
        (requireActivity().supportFragmentManager.findFragmentByTag(AddActivityFragment::class.java.canonicalName)
                as? AddActivityFragment)?.dismiss()
        addActivityButton.text = "ADD ANOTHER ACTIVITY"
        addActivityButton.strokeColor = ColorStateList.valueOf(Color.WHITE)
        publicPlanViewModel.activity.add(activity)
        activityRecyclerView?.adapter?.notifyDataSetChanged()
    }

    override fun CancelActivityFragmentFetch() {

        (requireActivity().supportFragmentManager.findFragmentByTag(AddActivityFragment::class.java.canonicalName)
                as? AddActivityFragment)?.dismiss()
    }

}
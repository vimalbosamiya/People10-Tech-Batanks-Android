package com.batanks.nextplan.home.fragment.tabfragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.red
import androidx.lifecycle.ViewModelProvider
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseDialogFragment
import com.batanks.nextplan.home.fragment.tabfragment.publicplan.viewmodel.PublicPlanViewModel
import com.batanks.nextplan.swagger.model.EventDate
import kotlinx.android.synthetic.main.fragment_add_activity_when.*
import java.text.SimpleDateFormat
import java.util.*

class AddActivityFragment : BaseDialogFragment() , View.OnClickListener {

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
        et_from_date_activity.setOnClickListener(this)
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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.et_from_date_activity ->{
                addActivityPeriodClicked()
            }
        }
    }
}
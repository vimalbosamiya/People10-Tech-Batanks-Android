package com.batanks.nextplan.customfrequency

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.Window
import android.widget.TextView
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseAppCompatActivity
import com.batanks.nextplan.home.fragment.tabfragment.publicplan.PublicPlanFragment
import com.batanks.nextplan.swagger.model.Periodicity
import kotlinx.android.synthetic.main.activity_custom_frequency.*
import java.text.SimpleDateFormat
import java.util.*


class CustomFrequency : BaseAppCompatActivity(), View.OnClickListener {

    private var periodicity : Periodicity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_frequency)

        val unit : String? = intent.getStringExtra("UNIT")
        val occurrences : String? = intent.getStringExtra("Occurrences")
        println(occurrences)
        val end_date : String? = intent.getStringExtra("End_Date")

        if (unit != null){

            if (unit == "d"){

                repeatEditText.setText(R.string.day)

            }else if (unit == "w"){

                repeatEditText.setText(R.string.week)

            }else if (unit == "m"){

                repeatEditText.setText(R.string.month)

            }else if (unit == "y"){

                repeatEditText.setText(R.string.year)
            }
        }

        if (occurrences != null && occurrences != "0"){

            howManyTimesEditText.setText(occurrences)
            howManyTimesTextField.visibility = VISIBLE
            howManyTimesCheckBox.isChecked = true

        } else if(occurrences == "0" || occurrences == null){

            howManyTimesTextField.visibility = GONE
            howManyTimesCheckBox.isChecked = false
        }

        if (end_date != null){

            var endDate : Date?
            var formattedEndDate : String? = null

            val inputFormat = SimpleDateFormat("yyyy-MM-dd")
            val outputFormat = SimpleDateFormat("EEE, MMM d yyyy")

            endDate = inputFormat.parse(end_date)
            formattedEndDate = outputFormat.format(endDate)

            untilWhenEditText.setText(formattedEndDate)
            untilWhenTextField.visibility = VISIBLE
            UntilWhenCheckBox.isChecked = true

        }else{}

        ok.setOnClickListener(this)
        closeButton.setOnClickListener(this)
        cancel.setOnClickListener(this)
        repeatEditText.setOnClickListener(this)
        howManyTimesCheckBox.setOnClickListener(this)
        UntilWhenCheckBox.setOnClickListener(this)
        //untilWhenTextField.setOnClickListener(this)
        untilWhenEditText.setOnClickListener(this)
    }

    private fun howOftenDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.repeat_pop_up)

        val dateTV = dialog.findViewById(R.id.dateTV) as TextView
        val weekTV = dialog.findViewById(R.id.weekTV) as TextView
        val monthTV = dialog.findViewById(R.id.monthTV) as TextView
        val yearTV = dialog.findViewById(R.id.yearTV) as TextView

        dateTV.setOnClickListener {

            repeatEditText.setText(R.string.day)
            repeatTextField.hintTextColor = getColorStateList(R.color.colorLightBlue)
            dialog.dismiss()
        }

        weekTV.setOnClickListener {

            repeatEditText.setText(R.string.week)
            dialog.dismiss()
        }

        monthTV.setOnClickListener {

            repeatEditText.setText(R.string.month)
            dialog.dismiss()
        }

        yearTV.setOnClickListener {

            repeatEditText.setText(R.string.year)
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.ok -> {

                var unit : String? = null

                if (repeatEditText.text.toString() == getString(R.string.day)){

                    unit = "d"

                } else if (repeatEditText.text.toString() == getString(R.string.week)){

                    unit = "w"

                }else if (repeatEditText.text.toString() == getString(R.string.month)){

                    unit = "m"

                }else if (repeatEditText.text.toString() == getString(R.string.year)){

                    unit = "y"
                }

                var occurence : Int? = 0

                if (howManyTimesCheckBox.isChecked && !TextUtils.isEmpty(howManyTimesEditText.text)){

                    occurence = howManyTimesEditText.text.toString().toInt()

                } else {

                    occurence = 0
                }

                var end_date : String? = null

                if (UntilWhenCheckBox.isChecked && !TextUtils.isEmpty(untilWhenEditText.text)){

                    end_date = untilWhenEditText.text.toString()

                } else {

                    end_date  = null
                }

                val intent = Intent()
                intent.putExtra(PublicPlanFragment.UNIT,unit)
                intent.putExtra(PublicPlanFragment.OCCURENCE,occurence.toString())
                intent.putExtra(PublicPlanFragment.END_DATE, end_date)
                setResult(PublicPlanFragment.RESULT_CODE,intent)
                finish()
            }

            R.id.closeButton -> { finish() }
            R.id.cancel -> { finish() }
            R.id.repeatEditText -> { howOftenDialog() }

            R.id.howManyTimesCheckBox -> {

                if (howManyTimesCheckBox.isChecked){

                    howManyTimesTextField.visibility = VISIBLE

                    untilWhenTextField.visibility = GONE

                    UntilWhenCheckBox.isChecked = false

                    untilWhenEditText.setText(null)

                } else{

                    howManyTimesTextField.visibility = GONE
                }
            }

            R.id.UntilWhenCheckBox -> { untilWhen() }

            //R.id.untilWhenTextField -> { untilWhen() }
            R.id.untilWhenEditText -> { untilWhen() }
        }
    }

    private fun untilWhen(){

        if (UntilWhenCheckBox.isChecked){

            howManyTimesCheckBox.isChecked = false
            howManyTimesTextField.visibility = GONE

            val mCal = Calendar.getInstance()
            val mDay = mCal.get(Calendar.DAY_OF_MONTH)
            val mMonth = mCal.get(Calendar.MONTH)
            val mYear = mCal.get(Calendar.YEAR)
            val mHour = mCal.get(Calendar.HOUR_OF_DAY)
            val mMin = mCal.get(Calendar.MINUTE)

            val fromDate = DatePickerDialog(this, R.style.AlertDialogTheme, DatePickerDialog.OnDateSetListener
            { fromDatePicker, fromYear, fromMonth, fromDay ->
                val cal = Calendar.getInstance()
                cal.set(fromYear, fromMonth, fromDay)
                /*FromDate*/
                val dateFormatter = SimpleDateFormat("E, MMM dd yyyy")
                val startDate = dateFormatter.format(cal.time)
                println(startDate)
                untilWhenTextField.editText?.setText(startDate)

                /*publicPlanViewModel.activityDate.add(EventDate(id = publicPlanViewModel.activityDate.size, start = startDate,
                                end = "", votes = mutableListOf()))*/

            }, mYear, mMonth, mDay)
            fromDate.datePicker.minDate = System.currentTimeMillis()
            fromDate.setCanceledOnTouchOutside(false)
            fromDate.show()

            untilWhenTextField.visibility = VISIBLE

        } else {

            untilWhenTextField.visibility = GONE
        }
    }

    interface CustomFrequencyListener{

        fun customFrequencyHowOftenFetch(periodicity: Periodicity)
    }
}

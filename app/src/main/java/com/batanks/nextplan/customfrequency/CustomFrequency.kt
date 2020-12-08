package com.batanks.nextplan.customfrequency

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.Window
import android.widget.TextView
import com.batanks.nextplan.R
import kotlinx.android.synthetic.main.activity_custom_frequency.*
import java.text.SimpleDateFormat
import java.util.*

class CustomFrequency : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_frequency)

        closeButton.setOnClickListener {

            finish()
        }

        cancel.setOnClickListener {

            finish()
        }

        repeatTextField.setOnClickListener {

            howOftenDialog()
        }

        howManyTimesCheckBox.setOnClickListener {

            if (howManyTimesCheckBox.isChecked){

                howManyTimesTextField.visibility = VISIBLE

                untilWhenTextField.visibility = GONE

                UntilWhenCheckBox.isChecked = false

            } else{

                howManyTimesTextField.visibility = GONE
            }
        }

        UntilWhenCheckBox.setOnClickListener {

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
}

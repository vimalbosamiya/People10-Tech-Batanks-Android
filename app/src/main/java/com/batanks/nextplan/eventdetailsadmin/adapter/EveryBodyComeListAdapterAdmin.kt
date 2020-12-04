package com.batanks.nextplan.eventdetailsadmin.adapter

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.eventdetailsadmin.viewmodel.EventDetailViewModelAdmin
import com.batanks.nextplan.swagger.model.ContactsList
import com.batanks.nextplan.swagger.model.EventDate
import com.batanks.nextplan.swagger.model.Guests
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import kotlinx.android.synthetic.main.layout_contact.view.*

class EveryBodyComeListAdapterAdmin (val contactsList: ArrayList<Guests>, val context: Context,
                                     private val eventDetailViewModelAdmin: EventDetailViewModelAdmin,
                                     private val callBack: AddPeopleRecyclerViewCallBack): RecyclerView.Adapter<EveryBodyComeListAdapterAdmin.ViewHolder>() {

    private var dialogContext: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        dialogContext = parent.context

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_contact, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = contactsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val contact: Guests = contactsList[position]

        holder.contactName.text = contact.name

        holder.contactSettings.setOnClickListener {

            if(position ==0){

                dialogContext?.let { it1 -> showDialog(it1) }

            } else if (position == 1){

                dialogContext?.let { it1 -> showDialogNo(it1) }

            } else {


                dialogContext?.let { it1 -> showDialogYes(it1) }
            }



            println("working fine ")
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val contactName: TextView = itemView.contactName
        val contactSettings: ImageView = itemView.contactSettings

    }

    /*private fun showDialog(context: Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.edit_propriety)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val okButton = dialog.findViewById(R.id.okButton) as MaterialButton
        val cancelButton = dialog.findViewById(R.id.cancelButton) as MaterialButton

        okButton.setOnClickListener {

            dialog.dismiss()
        }

        cancelButton.setOnClickListener {

            dialog.dismiss()
        }

        dialog.show()
    }*/

    private fun showDialog(context :Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.edit_propriety)

        val substract = dialog.findViewById(R.id.substract) as ImageView
        val add = dialog.findViewById(R.id.add) as ImageView
        val countTextview = dialog.findViewById(R.id.countTextview) as TextView
        val yesCheckbox = dialog.findViewById(R.id.yesCheckbox) as MaterialCheckBox
        val noCheckbox = dialog.findViewById(R.id.noCheckbox) as MaterialCheckBox
        val okButton = dialog.findViewById(R.id.okButton) as MaterialButton
        val cancelButton = dialog.findViewById(R.id.cancelButton) as MaterialButton

        substract.setOnClickListener {

            var count : Int = countTextview.text.toString().toInt()

            if (count > 0){

                count -= 1

                countTextview.text = count.toString()
            }
        }

        add.setOnClickListener {

            var count : Int = countTextview.text.toString().toInt()

            count += 1

            countTextview.text = count.toString()
        }

        yesCheckbox.setOnClickListener {

            if (noCheckbox.isChecked == true){

                noCheckbox.isChecked = false
            }
        }

        noCheckbox.setOnClickListener {

            if(yesCheckbox.isChecked == true){

                yesCheckbox.isChecked = false
            }
        }

        okButton.setOnClickListener {

            dialog.dismiss()
        }

        cancelButton.setOnClickListener {

            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showDialogNo(context :Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.edit_propriety_no)

        val substract = dialog.findViewById(R.id.substract) as ImageView
        val add = dialog.findViewById(R.id.add) as ImageView
        val countTextview = dialog.findViewById(R.id.countTextview) as TextView
        //val yesCheckbox = dialog.findViewById(R.id.yesCheckbox) as MaterialCheckBox
        val noCheckbox = dialog.findViewById(R.id.noCheckbox) as MaterialCheckBox
        val okButton = dialog.findViewById(R.id.okButton) as MaterialButton
        val cancelButton = dialog.findViewById(R.id.cancelButton) as MaterialButton

        substract.setOnClickListener {

            var count : Int = countTextview.text.toString().toInt()

            if (count > 0){

                count -= 1

                countTextview.text = count.toString()
            }
        }

        add.setOnClickListener {

            var count : Int = countTextview.text.toString().toInt()

            count += 1

            countTextview.text = count.toString()
        }

      /*  yesCheckbox.setOnClickListener {

            if (noCheckbox.isChecked == true){

                noCheckbox.isChecked = false
            }
        }

        noCheckbox.setOnClickListener {

            if(yesCheckbox.isChecked == true){

                yesCheckbox.isChecked = false
            }
        }*/

        okButton.setOnClickListener {

            dialog.dismiss()
        }

        cancelButton.setOnClickListener {

            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showDialogYes(context :Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.edit_propriety_yes)

        val substract = dialog.findViewById(R.id.substract) as ImageView
        val add = dialog.findViewById(R.id.add) as ImageView
        val countTextview = dialog.findViewById(R.id.countTextview) as TextView
        val yesCheckbox = dialog.findViewById(R.id.yesCheckbox) as MaterialCheckBox
        //val noCheckbox = dialog.findViewById(R.id.noCheckbox) as MaterialCheckBox
        val okButton = dialog.findViewById(R.id.okButton) as MaterialButton
        val cancelButton = dialog.findViewById(R.id.cancelButton) as MaterialButton

        substract.setOnClickListener {

            var count : Int = countTextview.text.toString().toInt()

            if (count > 0){

                count -= 1

                countTextview.text = count.toString()
            }
        }

        add.setOnClickListener {

            var count : Int = countTextview.text.toString().toInt()

            count += 1

            countTextview.text = count.toString()
        }

       /* yesCheckbox.setOnClickListener {

            if (noCheckbox.isChecked == true){

                noCheckbox.isChecked = false
            }
        }

        noCheckbox.setOnClickListener {

            if(yesCheckbox.isChecked == true){

                yesCheckbox.isChecked = false
            }
        }*/

        okButton.setOnClickListener {

            dialog.dismiss()
        }

        cancelButton.setOnClickListener {

            dialog.dismiss()
        }

        dialog.show()
    }

    interface AddPeopleRecyclerViewCallBack {
        fun closeButtonAddPeriodItemListener(pos: Int)
    }


}
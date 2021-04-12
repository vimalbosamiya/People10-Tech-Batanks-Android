package com.batanks.nextplan.eventdetailsadmin.adapter

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.eventdetails.viewmodel.EventDetailViewModel
import com.batanks.nextplan.swagger.model.Task
import com.batanks.nextplan.swagger.model.TaskPatch
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import kotlinx.android.synthetic.main.layout_action_display_admin.view.*

class EventActionListAdapterAdmin (val actionList : ArrayList<Task>, val context: Context, private val callBack: AddActionRecyclerViewCallBack,
                                   private val eventDetailViewModel: EventDetailViewModel, private val eventId: Int) : RecyclerView.Adapter<EventActionListAdapterAdmin.ViewHolder>() {

    private var dialogContext: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        dialogContext = parent.context

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_action_display_admin, parent, false)

        view.actionDropDown.setOnClickListener {

            view.totalCostBackground.visibility = View.GONE

            view.totalCostBackgroundFull.visibility = View.VISIBLE
        }

        view.actionDropDownMulti.setOnClickListener {

            view.totalCostBackgroundFull.visibility = View.GONE

            view.totalCostBackground.visibility = View.VISIBLE
        }

        return  ViewHolder(view)

    }

    override fun getItemCount() = actionList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val action : Task = actionList[position]

        holder.textViewEventName.text = action.name
        holder.textViewEventNameMulti.text = action.name
        holder.textViewEventDescription.text = action.description

        /*holder.actionIdTextView.text = (position + 1).toString()*/

        if (action.assignee != null){

            holder.contactBackground.visibility = View.VISIBLE
            holder.contactBackgroundFull.visibility = View.VISIBLE
            holder.contactName.text = action.assignee.username
            holder.contactNameFull.text = action.assignee.username

            if (!action.assignee?.picture.isNullOrEmpty()){

                Glide.with(context).load(action.assignee?.picture).circleCrop().into(holder.contactStatus)
                Glide.with(context).load(action.assignee?.picture).circleCrop().into(holder.contactStatusFull)
                holder.contactImage.visibility = View.GONE
                holder.contactImageFull.visibility = View.GONE
                holder.userImage.visibility = View.GONE
                holder.userImageFull.visibility = View.GONE
            }
        } else if (action.assignee == null){

            /*holder.contactBackground.visibility = View.VISIBLE
            holder.contactBackgroundFull.visibility = View.VISIBLE*/
        }

        holder.contactSettings.setOnClickListener {

            dialogContext?.let { it1 -> showDialog(it1, action.id.toString()) }
        }

        holder.contactSettingsFull.setOnClickListener {

            dialogContext?.let { it1 -> showDialog(it1,action.id.toString()) }
        }

        if (action.per_person == true){

            holder.textViewTotalCost.setText(R.string.cost_per_person)
            holder.textViewTotalCostMulti.setText(R.string.cost_per_person)

        }else {

            holder.textViewTotalCost.setText(R.string.total_cost)
            holder.textViewTotalCostMulti.setText(R.string.total_cost)
        }

        holder.textViewTotalAmount.text = action.price.toString()
        holder.textViewTotalAmountMulti.text = action.price.toString()
        holder.textViewCurrencySymbol.text = action.price_currency
        holder.textViewTotalAmountSymbol.text = action.price_currency

        if (!action.assignee?.picture.isNullOrEmpty()){

            Glide.with(context).load(action.assignee?.picture).circleCrop().into(holder.contactStatus)
            holder.contactBackground.visibility = View.VISIBLE
        }

        /*holder.actionSettingsIcon.setOnClickListener {

            //actionDeleteDialog(position)
            callBack.settingsButtonAddActionItemListener(position)
        }*/
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textViewEventName : TextView = itemView.textViewEventName
        val textViewEventNameMulti : TextView = itemView.textViewEventNameMulti
        /*val actionIdTextView : TextView = itemView.actionIdTextView*/
        val textViewTotalAmount : TextView = itemView.textViewTotalAmount
        val textViewTotalAmountMulti : TextView = itemView.textViewTotalAmountMulti
        val textViewEventDescription : TextView = itemView.textViewEventDescription
        //val actionSettingsIcon : ImageView = itemView.actionSettingsIcon
        val contactSettingsFull : ImageView = itemView.contactSettingsFull
        val contactSettings : ImageView = itemView.contactSettings
        val textViewTotalCost : TextView = itemView.textViewTotalCost
        val textViewTotalCostMulti : TextView = itemView.textViewTotalCostMulti
        val textViewTotalAmountSymbol : TextView = itemView.textViewTotalAmountSymbol
        val textViewCurrencySymbol : TextView = itemView.textViewCurrencySymbol
        val contactNameFull : TextView = itemView.contactNameFull
        val contactName : TextView = itemView.contactName
        val contactBackground : ConstraintLayout = itemView.contactBackground
        val contactBackgroundFull : ConstraintLayout = itemView.contactBackgroundFull
        val contactStatus : ImageView = itemView.contactStatus
        val contactImage : ImageView = itemView.contactImage
        val contactImageFull : ImageView = itemView.contactImage
        val userImage : ImageView = itemView.userImage
        val userImageFull : ImageView = itemView.userImage
        val contactStatusFull : ImageView = itemView.contactStatusFull


    }

    private fun actionDeleteDialog(position: Int) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.layout_delete_popup)

        val deletePopup = dialog.findViewById(R.id.deletePopup) as ConstraintLayout

        deletePopup.setOnClickListener{

            //Toast.makeText(context,"worked", Toast.LENGTH_SHORT).show()

            actionList.removeAt(position)
            callBack.closeButtonAddActionItemListener(position)

            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showDialog(context :Context, taskId : String) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.edit_propriety_action)

        val checkbox = dialog.findViewById(R.id.Checkbox) as MaterialCheckBox
        val okButton = dialog.findViewById(R.id.okButton) as MaterialButton
        val cancelButton = dialog.findViewById(R.id.cancelButton) as MaterialButton

        okButton.setOnClickListener{

            if (checkbox.isChecked == true){

                eventDetailViewModel.apiEventTaskPatch(eventId.toString(), taskId, TaskPatch(" "))
            }

            dialog.dismiss()
        }

        cancelButton.setOnClickListener {

            dialog.dismiss()
        }

        dialog.show()
    }

    interface AddActionRecyclerViewCallBack {
        fun closeButtonAddActionItemListener(pos: Int)
        fun settingsButtonAddActionItemListener(pos: Int)
    }
}
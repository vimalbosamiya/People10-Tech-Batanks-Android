package com.batanks.nextplan.eventdetailsadmin.adapter

import android.app.Dialog
import android.content.Context
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
import com.batanks.nextplan.swagger.model.Task
import kotlinx.android.synthetic.main.layout_action_display_admin.view.*

class EventActionListAdapterAdmin (val actionList : ArrayList<Task>, val context: Context,
                                   private val callBack: AddActionRecyclerViewCallBack): RecyclerView.Adapter<EventActionListAdapterAdmin.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

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

        holder.actionSettingsIcon.setOnClickListener {

            actionDeleteDialog(position)

        }

        holder.textViewEventName.text = action.name
        holder.textViewEventNameMulti.text = action.name
        holder.actionIdTextView.text = action.id.toString()
        holder.textViewTotalAmount.text = action.price_currency
        holder.textViewTotalAmountMulti.text = action.price_currency
        holder.textViewEventDescription.text = action.description
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textViewEventName : TextView = itemView.textViewEventName
        val textViewEventNameMulti : TextView = itemView.textViewEventNameMulti
        val actionIdTextView : TextView = itemView.actionIdTextView
        val textViewTotalAmount : TextView = itemView.textViewTotalAmount
        val textViewTotalAmountMulti : TextView = itemView.textViewTotalAmountMulti
        val textViewEventDescription : TextView = itemView.textViewEventDescription
        val actionSettingsIcon : ImageView = itemView.actionSettingsIcon
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

    interface AddActionRecyclerViewCallBack {
        fun closeButtonAddActionItemListener(pos: Int)
    }
}
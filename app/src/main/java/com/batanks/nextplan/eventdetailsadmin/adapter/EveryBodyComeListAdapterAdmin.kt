package com.batanks.nextplan.eventdetailsadmin.adapter

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.eventdetails.viewmodel.EventDetailViewModel
import com.batanks.nextplan.search.AddToGroupActivity
import com.batanks.nextplan.swagger.model.*
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import kotlinx.android.synthetic.main.layout_contact.view.*

class EveryBodyComeListAdapterAdmin (val contactsList: ArrayList<Guests>, val context: Context,
                                     private val eventDetailViewModel: EventDetailViewModel,
                                     private val callBack: AddPeopleRecyclerViewCallBack, private val eventId : Int): RecyclerView.Adapter<EveryBodyComeListAdapterAdmin.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_contact, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = contactsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val contact: Guests = contactsList[position]

        holder.contactName.text = contact.user.username

        holder.close.setImageResource(R.drawable.ic_event_details_settings)

        if (contact.status == "AC"){

            holder.contactStatus.setImageResource(R.drawable.ic_user_accepted)

        } else if(contact.status == "DN"){

            holder.contactStatus.setImageResource(R.drawable.ic_user_declined)
        }

        holder.close.setOnClickListener {

            if(contact.status == "PD"){

                context.let { it1 -> showDialogPending(it1, contact.user.id, contact.invitation_id.toString()) }

            } else if (contact.status == "AC"){

                context.let { it1 -> showDialogNo(it1,
                    contact.user.id, contact.invitation_id.toString(), contact.people_coming!!) }

            } else if (contact.status == "DN") {

                context.let { it1 -> showDialogYes(it1, contact.user.id, contact.invitation_id.toString()) }
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val contactStatus : ImageView = itemView.contactStatus
        val contactName: TextView = itemView.contactName
        val close: ImageView = itemView.close
    }

    private fun showDialogPending(context :Context, guestId : Int, invitationId: String) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.edit_propriety)

        val addToGroupIcon = dialog.findViewById(R.id.addToGroupIcon) as ImageView
        val substract = dialog.findViewById(R.id.substract) as ImageView
        val add = dialog.findViewById(R.id.add) as ImageView
        val countTextview = dialog.findViewById(R.id.countTextview) as TextView
        val yesCheckbox = dialog.findViewById(R.id.yesCheckbox) as MaterialCheckBox
        val noCheckbox = dialog.findViewById(R.id.noCheckbox) as MaterialCheckBox
        val okButton = dialog.findViewById(R.id.okButton) as MaterialButton
        val cancelButton = dialog.findViewById(R.id.cancelButton) as MaterialButton

        substract.setOnClickListener {

            var count : Int = countTextview.text.toString().toInt()

            if (count > 1){

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

            if (yesCheckbox.isChecked == true){

               accept(countTextview.text.toString().toInt(), invitationId, true)

            }else if (noCheckbox.isChecked == true){

                accept(countTextview.text.toString().toInt(), invitationId, false)
            }

            dialog.dismiss()
        }

        cancelButton.setOnClickListener {

            dialog.dismiss()
        }

        addToGroupIcon.setOnClickListener {

            addToGroup(guestId)

            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showDialogNo(context :Context, guestId : Int, invitationId: String, peopleComing : Int) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.edit_propriety_no)

        val addToGroupIcon = dialog.findViewById(R.id.addToGroupIcon) as ImageView
        val substract = dialog.findViewById(R.id.substract) as ImageView
        val add = dialog.findViewById(R.id.add) as ImageView
        val countTextview = dialog.findViewById(R.id.countTextview) as TextView
        //val yesCheckbox = dialog.findViewById(R.id.yesCheckbox) as MaterialCheckBox
        val noCheckbox = dialog.findViewById(R.id.noCheckbox) as MaterialCheckBox
        val okButton = dialog.findViewById(R.id.okButton) as MaterialButton
        val cancelButton = dialog.findViewById(R.id.cancelButton) as MaterialButton

        if (peopleComing == 0 ){ countTextview.text = "1"
        } else { countTextview.text = peopleComing.toString()}

        substract.setOnClickListener {

            var count : Int = countTextview.text.toString().toInt()

            if (count > 1){

                count -= 1

                countTextview.text = count.toString()
            }
        }

        add.setOnClickListener {

            var count : Int = countTextview.text.toString().toInt()

            count += 1

            countTextview.text = count.toString()
        }

        okButton.setOnClickListener {

            if (noCheckbox.isChecked == true){

                accept(countTextview.text.toString().toInt(), invitationId, false)

            } else if (noCheckbox.isChecked == false){

                accept(countTextview.text.toString().toInt(), invitationId, true)
            }
            dialog.dismiss()
        }

        cancelButton.setOnClickListener {

            dialog.dismiss()
        }

        addToGroupIcon.setOnClickListener {

            addToGroup(guestId)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showDialogYes(context :Context, guestId : Int, invitationId: String) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.edit_propriety_yes)

        val addToGroupIcon = dialog.findViewById(R.id.addToGroupIcon) as ImageView
        val substract = dialog.findViewById(R.id.substract) as ImageView
        val add = dialog.findViewById(R.id.add) as ImageView
        val countTextview = dialog.findViewById(R.id.countTextview) as TextView
        val yesCheckbox = dialog.findViewById(R.id.yesCheckbox) as MaterialCheckBox
        //val noCheckbox = dialog.findViewById(R.id.noCheckbox) as MaterialCheckBox
        val okButton = dialog.findViewById(R.id.okButton) as MaterialButton
        val cancelButton = dialog.findViewById(R.id.cancelButton) as MaterialButton

        substract.setOnClickListener {

            var count : Int = countTextview.text.toString().toInt()

            if (count > 1){

                count -= 1

                countTextview.text = count.toString()
            }
        }

        add.setOnClickListener {

            var count : Int = countTextview.text.toString().toInt()

            count += 1

            countTextview.text = count.toString()
        }

        okButton.setOnClickListener {

            if (yesCheckbox.isChecked == true){

                accept(countTextview.text.toString().toInt(), invitationId, true)

            }

            dialog.dismiss()
        }

        cancelButton.setOnClickListener {

            dialog.dismiss()
        }

        addToGroupIcon.setOnClickListener {

            addToGroup(guestId)

            dialog.dismiss()
        }

        dialog.show()
    }

    private fun accept(amount: Int, invitationId : String, accept : Boolean){

        if (accept == true){

            eventDetailViewModel.eventInvitationAccepted(
                context,
                eventId.toString(),
                invitationId,
                GuestAmount(ACCEPT,amount)
            )

        } else if (accept == false){

            eventDetailViewModel.eventInvitationAccepted(
                context,
                eventId.toString(),
                invitationId,
                GuestAmount(DECLINE,0)
            )
        }
    }

    private fun addToGroup(guestId : Int){

        val intent = Intent(context, AddToGroupActivity::class.java)
        intent.putExtra("Id", guestId)
        ContextCompat.startActivity(context, intent, null)
        //(context as EventDetailViewAdmin).finish()
    }

    companion object{

        const val ACCEPT : String = "AC"
        const val PENDING : String = "PD"
        const val DECLINE : String = "DN"
    }

    interface AddPeopleRecyclerViewCallBack {
        fun closeButtonAddPeriodItemListener(pos: Int)
    }
}
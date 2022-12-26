package com.batanks.nextplan.invitationstatus.adapters

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
import com.batanks.nextplan.swagger.model.GuestAmount
import com.batanks.nextplan.swagger.model.Guests
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import kotlinx.android.synthetic.main.layout_invitation_status_item.view.*

class InvitationStatusListAdapter (private val invitatioStatusList : ArrayList<Guests>, private val eventDetailViewModel: EventDetailViewModel, private val eventId : Int)
                                : RecyclerView.Adapter<InvitationStatusListAdapter.ViewHolder>() {

    lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        context = parent.context

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_invitation_status_item, parent, false)

        return ViewHolder(view)

    }

    override fun getItemCount() = invitatioStatusList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val guest  = invitatioStatusList[position]

        holder.userName.text = guest.user.username
        holder.guestsCount.text = guest.people_coming.toString()

        if (guest.status == "AC"){

            holder.userStatus.setImageResource(R.drawable.ic_user_accepted)

        } else if(guest.status == "DN"){

            holder.userStatus.setImageResource(R.drawable.ic_user_declined)

        } else if(guest.status == "PD"){

            holder.userStatus.setImageResource(R.drawable.ic_user_pending)
        }

        /*if (!action.assignee?.picture.isNullOrEmpty()){

            Glide.with(context).load(action.assignee?.picture).circleCrop().into(holder.contactStatus)
            Glide.with(context).load(action.assignee?.picture).circleCrop().into(holder.contactStatusFull)
            holder.contactImage.visibility = View.GONE
            holder.contactImageFull.visibility = View.GONE
            holder.userImage.visibility = View.GONE
            holder.userImageFull.visibility = View.GONE
        }*/

        holder.costPerPerson
        holder.totalCost

        holder.cost.text = guest.price
        holder.costInTotal.text = guest.price


        holder.settings.setOnClickListener {

            if (guest.user.id != null){

                if(guest.status == "PD"){

                    context?.let { it1 -> showDialog(it1, guest.user.id!!, guest.invitation_id.toString(), "PD", guest.people_coming.toString()) }

                } else if (guest.status == "AC"){

                    context?.let { it1 -> showDialogNo(it1, guest.user.id!!, guest.invitation_id.toString(), "AC", guest.people_coming.toString()) }

                } else if (guest.status == "DN") {

                    context?.let { it1 -> showDialogYes(it1, guest.user.id!!, guest.invitation_id.toString(), "DN", guest.people_coming.toString()) }
                }
            }
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val userName : TextView = itemView.userName
        val guestsCount : TextView = itemView.guestsCount
        val costPerPerson : TextView = itemView.costPerPerson
        val cost : TextView = itemView.cost
        val currencySymbol : TextView = itemView.currencySymbol
        val totalCost : TextView = itemView.totalCost
        val costInTotal : TextView = itemView.costInTotal
        val currencySymbolForTotal : TextView = itemView.currencySymbolForTotal
        val userStatus : ImageView = itemView.userStatus
        val settings : ImageView = itemView.settings
    }

    private fun showDialog(context : Context, guestId : Int, invitationId : String, guestStatus : String, totalGuests : String) {
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

        countTextview.setText(totalGuests)

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

            }else {

                eventDetailViewModel.eventInvitationAccepted(context,eventId.toString(), invitationId, GuestAmount(PENDING,countTextview.text.toString().toInt()))
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

    private fun showDialogNo(context :Context, guestId : Int, invitationId: String , guestStatus : String, totalGuests : String) {
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

        countTextview.setText(totalGuests)

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

            if (noCheckbox.isChecked == true){

                accept(countTextview.text.toString().toInt(), invitationId, false)

            }else {

                eventDetailViewModel.eventInvitationAccepted(
                    context,
                    eventId.toString(),
                    invitationId,
                    GuestAmount(ACCEPT,countTextview.text.toString().toInt())
                )
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

    private fun showDialogYes(context :Context, guestId : Int, invitationId: String, guestStatus : String, totalGuests : String) {
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

        if (totalGuests.isNullOrEmpty() || totalGuests == "0"){ countTextview.setText("1") } else { countTextview.setText(totalGuests) }

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

            if (yesCheckbox.isChecked == true){

                accept(countTextview.text.toString().toInt(), invitationId, true)

            }else {

                eventDetailViewModel.eventInvitationAccepted(
                    context,
                    eventId.toString(),
                    invitationId,
                    GuestAmount(DECLINE,countTextview.text.toString().toInt())
                )
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
}
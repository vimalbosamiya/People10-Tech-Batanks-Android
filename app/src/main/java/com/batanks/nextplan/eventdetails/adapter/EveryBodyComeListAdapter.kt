package com.batanks.nextplan.eventdetails.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.Settings.UsersInfo
import com.batanks.nextplan.swagger.model.Event
import com.batanks.nextplan.swagger.model.EventInvitation
import com.batanks.nextplan.swagger.model.Guests
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_contact.view.*
import kotlinx.android.synthetic.main.layout_eventdetails_organizer_details.*

class EveryBodyComeListAdapter(val guestsList: ArrayList<Guests>, val context: Context, val acceptedStatus: Event?): RecyclerView.Adapter<EveryBodyComeListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_contact, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = guestsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val guest : Guests = guestsList[position]

        if (guest.user != null){

            holder.contactName.text = guest.user.username
        }

        //holder.contactName.text = guest.user.username
        holder.close.visibility = View.GONE

        if (guest.status == "AC"){

            holder.contactStatus.setImageResource(R.drawable.ic_user_accepted)


        } else if(guest.status == "DN"){

            holder.contactStatus.setImageResource(R.drawable.ic_user_declined)

        }


        holder.contactBackground.setOnClickListener {

            if (acceptedStatus?.status.equals("AC")){

                val intent = Intent(context, UsersInfo::class.java)
                if (guestsList.get(position).user != null){
                    intent.putExtra("ID",guestsList.get(position).user.id)
                    intent.putExtra("NAME",guestsList.get(position).user.username)
                    intent.putExtra("FIRST_NAME",guestsList.get(position).user.first_name)
                    intent.putExtra("LAST_NAME",guestsList.get(position).user.last_name)
                    intent.putExtra("EMAIL",guestsList.get(position).user.email)
                    intent.putExtra("PHNO",guestsList.get(position).user.phone_number)
                    intent.putExtra("PIC",guestsList.get(position).user.picture)
                    //intent.putExtra("CONTACT",true)
                    intent.putExtra("FROM_SETTINS_PAGE", false)
                }
                (context as Activity).startActivityForResult(intent,1)

            }else { Toast.makeText(context , "Accept the plan first", Toast.LENGTH_SHORT).show()}
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val contactStatus : ImageView = itemView.contactStatus
        val contactName : TextView = itemView.contactName
        val close : ImageView = itemView.close
        val contactBackground : ConstraintLayout = itemView.contactBackground
        //val contactImage : ImageView = itemView.contactImage
    }
}
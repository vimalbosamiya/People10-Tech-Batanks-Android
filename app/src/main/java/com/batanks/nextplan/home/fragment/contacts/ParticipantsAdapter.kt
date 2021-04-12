package com.batanks.nextplan.home.fragment.contacts

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
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.eventdetails.viewmodel.AddContactViewModel
import com.batanks.nextplan.search.AddToGroupActivity
import com.batanks.nextplan.swagger.model.ActivityParticipants
import com.batanks.nextplan.swagger.model.AddContact
import kotlinx.android.synthetic.main.layout_contact.view.*

class ParticipantsAdapter ( private val myList: ArrayList<ActivityParticipants>, private val addContactViewModel : AddContactViewModel) : RecyclerView.Adapter<ParticipantsAdapter.MyViewHolder>() {

    private var context: Context? = null
    var id : Int? = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        context = parent.context

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_contact, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = myList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        id = myList[position].id

        holder.contactName.text = myList[position].participantName

        holder.contactSettings.setOnClickListener {

            context?.let { it1 -> showDialog(it1) }

        }

    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        val contactStatus : ImageView = itemView.contactStatus
        //val contactImage : ImageView = itemView.contactImage
        //val contactImage : ImageView = itemView.contactImage
        val contactName : TextView = itemView.contactName
        val contactSettings : ImageView = itemView.contactSettings
    }

    private fun showDialog(context : Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.layout_add_user)

        val rl_edit_contact_add = dialog.findViewById(R.id.rl_edit_contact_add) as RelativeLayout
        //val addToGroup = dialog.findViewById(R.id.img_contact_add_to_group_right_icon) as ImageView
        val addToGroup = dialog.findViewById(R.id.rl_edit_contact_add_to_groups) as RelativeLayout


        rl_edit_contact_add.setOnClickListener {

            addContactViewModel.addContact(id?.let { it1 -> AddContact(it1) })

            dialog.dismiss()
        }

        addToGroup.setOnClickListener {

            val intent : Intent = Intent(context, AddToGroupActivity:: class.java)
            intent.putExtra("Id", id)
            ContextCompat.startActivity(context, intent, null)

            dialog.dismiss()
        }

        dialog.show()
    }
}
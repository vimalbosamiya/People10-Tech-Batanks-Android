package com.batanks.nextplan.Settings.Adapters

import android.app.Activity
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
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.Settings.Contact
import com.batanks.nextplan.Settings.UsersInfo
import com.batanks.nextplan.arch.response.Status
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.eventdetails.viewmodel.AddContactViewModel
import com.batanks.nextplan.home.fragment.contacts.ContactsModel
import com.batanks.nextplan.home.viewmodel.ContactsViewModel
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.search.AddToGroupActivity
import com.batanks.nextplan.swagger.api.ContactsAPI
import com.batanks.nextplan.swagger.model.ContactsList
import kotlinx.android.synthetic.main.layout_settings_contacts_item.view.*
import java.security.AccessController.getContext

class ContactsAdapter_Settings (private val myList: ArrayList<ContactsList>, val contactsViewModel: ContactsViewModel
                                /*, private val listener : userInfoListener*/) : RecyclerView.Adapter<ContactsAdapter_Settings.MyViewHolder>() {

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_settings_contacts_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = myList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        //val itemView = holder.itemView

        holder.contactName.text = myList.get(position).username

        holder.img_contact_list_item_dots.setOnClickListener(View.OnClickListener {

            //id = myList.get(position).id

            context?.let { it1 -> showDialog(it1, myList.get(position).id!!) }
        })

        holder.contactName.setOnClickListener {

            //listener.itemClicked(myList[position])

            val intent = Intent(context, UsersInfo::class.java)
            intent.putExtra("ID",myList.get(position).id)
            intent.putExtra("NAME",myList.get(position).username)
            intent.putExtra("FIRST_NAME",myList.get(position).first_name)
            intent.putExtra("LAST_NAME",myList.get(position).last_name)
            intent.putExtra("EMAIL",myList.get(position).email)
            intent.putExtra("PHNO",myList.get(position).phone_number)
            intent.putExtra("PIC",myList.get(position).picture)
            intent.putExtra("CONTACT",true)
            context?.startActivity(intent)
            //(context as Activity).finish()
        }
    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val contactName: TextView = item.txt_contact_list_item
        val img_contact_list_item_dots : ImageView = item.img_contact_list_item_dots
    }

    private fun showDialog(context :Context, id : Int) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.layout_edit_contacts)

        val delete = dialog.findViewById(R.id.rl_edit_contact_delete) as RelativeLayout
        val addToGroup = dialog.findViewById(R.id.rl_edit_contact_add_to_groups) as RelativeLayout

        delete.setOnClickListener {

            contactsViewModel.deleteContact(id.toString())
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

    interface userInfoListener{

        fun itemClicked(friendInfo : ContactsList)
    }
}
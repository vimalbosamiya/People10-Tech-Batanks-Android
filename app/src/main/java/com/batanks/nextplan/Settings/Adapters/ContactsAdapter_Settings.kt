package com.batanks.nextplan.Settings.Adapters

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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.Settings.Contact
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.home.fragment.contacts.ContactsModel
import com.batanks.nextplan.home.viewmodel.ContactsViewModel
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.search.AddToGroupActivity
import com.batanks.nextplan.swagger.api.ContactsAPI
import com.batanks.nextplan.swagger.model.ContactsList
import kotlinx.android.synthetic.main.layout_settings_contacts_item.view.*
import java.security.AccessController.getContext

class ContactsAdapter_Settings (private val myList: List<ContactsList>, val contactsViewModel: ContactsViewModel ) : RecyclerView.Adapter<ContactsAdapter_Settings.MyViewHolder>() {

    private var context: Context? = null
    var Id : Int? = 0

   /* private val contactsViewModel: ContactsViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            getContext()?.let {
                context?.let { it1 ->
                    RetrofitClient.getRetrofitInstance(it1)?.create(ContactsAPI::class.java)?.let {
                        ContactsViewModel(it)
                    }
                }
            }
        }).get(ContactsViewModel::class.java)
    }*/



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_settings_contacts_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = myList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        //val itemView = holder.itemView

        holder.contactName.text = myList.get(position).first_name

        holder.img_contact_list_item_dots.setOnClickListener(View.OnClickListener {

            Id = myList.get(position).id

            println("Id from onBind : " +" " +Id)

            context?.let { it1 -> showDialog(it1) }
        })
    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val contactName: TextView = item.txt_contact_list_item
        val img_contact_list_item_dots : ImageView = item.img_contact_list_item_dots
    }

    private fun showDialog(context :Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.layout_edit_contacts)

        val edit = dialog.findViewById(R.id.rl_edit_contact_edit) as RelativeLayout
        val delete = dialog.findViewById(R.id.rl_edit_contact_delete) as RelativeLayout
        val addToGroup = dialog.findViewById(R.id.img_contact_add_to_group_right_icon) as ImageView
        //val add_to_contacts = dialog.findViewById(R.id.rl_edit_contact_add_to_groups) as RelativeLayout

        edit.setOnClickListener {

            dialog.dismiss()

        }

        delete.setOnClickListener {

            contactsViewModel.deleteContact(Id.toString())

            //println("Id from delete Click : " +" " +Id)

            dialog.dismiss()
        }

        //add_to_contacts.setOnClickListener { dialog.dismiss() }

        addToGroup.setOnClickListener {

            val intent : Intent = Intent(context, AddToGroupActivity:: class.java)
            intent.putExtra("Id", Id)
            //println(Id!!)

            ContextCompat.startActivity(context, intent, null)

            dialog.dismiss()
        }

        dialog.show()

    }
}
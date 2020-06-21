package com.batanks.nextplan.search

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.home.fragment.contacts.ContactsModel
import kotlinx.android.synthetic.main.layout_settings_contacts_item.view.*

class SearchUsersAdapter (private val usersList: List<ContactsModel>) : RecyclerView.Adapter<SearchUsersAdapter.ViewHolder>() {

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        context = parent.context

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_settings_contacts_item, parent, false)

        return ViewHolder(view)

    }

    override fun getItemCount() = usersList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val user = usersList[position]

        holder.txt_contact_list_item.text = user.contactname

        holder.img_contact_list_item_dots.setOnClickListener {

            //context?.let { it1 -> showDialog(it1) }

            //Toast.makeText(context,"Working",Toast.LENGTH_SHORT).show()
            context?.let { it1 -> showDialog(it1) }
        }
    }

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        val txt_contact_list_item : TextView = itemView.txt_contact_list_item
        val img_contact_list_item_dots :ImageView = item.img_contact_list_item_dots
    }

    private fun showDialog(context :Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_edit_contacts)
        val edit = dialog.findViewById(R.id.rl_edit_contact_edit) as RelativeLayout
        val delete = dialog.findViewById(R.id.rl_edit_contact_delete) as RelativeLayout
        val add_to_contacts = dialog.findViewById(R.id.rl_edit_contact_add_to_groups) as RelativeLayout

        edit.setOnClickListener {
            dialog.dismiss()
        }
        delete.setOnClickListener { dialog.dismiss() }
        add_to_contacts.setOnClickListener { dialog.dismiss() }
        dialog.show()

    }
}
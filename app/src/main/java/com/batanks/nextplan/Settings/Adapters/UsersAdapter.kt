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
import android.widget.Filterable
import android.widget.Filter;
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.Settings.UsersInfo
import com.batanks.nextplan.eventdetails.viewmodel.AddContactViewModel
import com.batanks.nextplan.home.viewmodel.ContactsViewModel
import com.batanks.nextplan.search.AddToGroupActivity
import com.batanks.nextplan.swagger.model.AddContact
import com.batanks.nextplan.swagger.model.ContactsList
import com.batanks.nextplan.swagger.model.UserSearchResults
import kotlinx.android.synthetic.main.layout_settings_contacts_item.view.*

class UsersAdapter (private val usersList: ArrayList<ContactsList>, private val addContactViewModel : AddContactViewModel) : RecyclerView.Adapter<UsersAdapter.MyViewHolder>(), Filterable {

    private var searchedUsersList: ArrayList<ContactsList> = usersList
    private var context: Context? = null
    //var id : Int? = 0
    private var pos : ArrayList<Int> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        context = parent.context
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_settings_contacts_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = usersList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        pos.add(position)

        holder.contactName.text = usersList.get(position).username

        println(usersList.get(position).username)
        println(usersList.get(position).id)

        holder.img_contact_list_item_dots.setOnClickListener(View.OnClickListener {

            context?.let { it1 -> showDialog(it1, usersList[position].id!!) }
        })

        holder.contactName.setOnClickListener {





            val intent = Intent(context, UsersInfo::class.java)
            intent.putExtra("ID",usersList.get(position).id)
            intent.putExtra("NAME",usersList.get(position).username)
            intent.putExtra("FIRST_NAME",usersList.get(position).first_name)
            intent.putExtra("LAST_NAME",usersList.get(position).last_name)
            intent.putExtra("EMAIL",usersList.get(position).email)
            intent.putExtra("PHNO",usersList.get(position).phone_number)
            intent.putExtra("PIC",usersList.get(position).picture)
            intent.putExtra("CONTACT",false)
            intent.putExtra("CONTACT",false)
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

    override fun getFilter(): Filter {
        return object : Filter() {
            private val filterResults = FilterResults()
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                searchedUsersList.clear()
                if (constraint.isNullOrBlank()) {
                    searchedUsersList.addAll(usersList)
                } else {
                    val searchResults = usersList.filter { it.username!!.contains(constraint) }
                    searchedUsersList.addAll(searchResults)
                }
                return filterResults.also {
                    it.values = searchedUsersList
                }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                // no need to use "results" filtered list provided by this method.
               /* if (searchedUsersList.isNullOrEmpty())
                    onNothingFound?.invoke()*/

                searchedUsersList = results?.values as ArrayList<ContactsList>
                notifyDataSetChanged()

            }
        }
    }


}
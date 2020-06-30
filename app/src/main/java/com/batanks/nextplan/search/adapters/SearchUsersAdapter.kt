package com.batanks.nextplan.search.adapters

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.home.fragment.contacts.ContactsModel
import com.batanks.nextplan.search.AddToGroupActivity
import kotlinx.android.synthetic.main.layout_settings_contacts_item.view.*

class SearchUsersAdapter (private val usersList: List<ContactsModel>) : RecyclerView.Adapter<SearchUsersAdapter.ViewHolder>() {

    protected lateinit var rootView: View

    private var context: Context? = null

    lateinit var groupsRecyclerView : RecyclerView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        context = parent.context

        rootView = LayoutInflater.from(parent.context).inflate(R.layout.layout_settings_contacts_item, parent, false)

        return ViewHolder(rootView)

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
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.layout_people_settings)

        val addToGroupIcon = dialog.findViewById(R.id.addToGroupIcon) as ImageView

        addToGroupIcon.setOnClickListener {

            //groupPopUp(context)

            val intent : Intent = Intent(context, AddToGroupActivity:: class.java)
            startActivity(context,intent,null)

            //rootView.addToGroupFrameLayout.visibility = VISIBLE

            /*rootView.context.supportFragmentManager.beginTransaction()
                    .add(R.id.frameLayout, SearchFragment())
                    .addToBackStack(SearchFragment.TAG).commit()*/

            dialog.dismiss()

        }

        dialog.show()
    }

    private fun groupPopUp(context :Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.fragment_add_to_groups)

       /* val addToGroupIcon = dialog.findViewById(R.id.addToGroupIcon) as ImageView

        addToGroupIcon.setOnClickListener {

        }*/

        groupsRecyclerView =  dialog.findViewById(R.id.groupsRecyclerView)
        groupsRecyclerView.layoutManager = LinearLayoutManager(context)
        groupsRecyclerView.adapter = AddToGroupsAdapter(listOf<String>())


        dialog.show()
    }

    private fun setUpDummyData(){


    }
}
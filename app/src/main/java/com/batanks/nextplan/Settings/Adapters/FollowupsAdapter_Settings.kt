package com.batanks.nextplan.Settings.Adapters

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.Settings.Followups
import com.batanks.nextplan.home.fragment.contacts.ContactsModel
import com.batanks.nextplan.search.Search
import com.google.gson.Gson
import kotlinx.android.synthetic.main.layout_settings_followups.view.*

class FollowupsAdapter_Settings (private val callBack : FollowupsAdapter_SettingsCallBack, private val myList: ArrayList<String>) : RecyclerView.Adapter<FollowupsAdapter_Settings.MyViewHolder>() {

    lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_settings_followups, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = myList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        //val itemView = holder.itemView

        holder.contactName.text = myList.get(position)
        holder.img_groups_list_item_dots.setOnClickListener(View.OnClickListener {
            context?.let { it1 -> showDialog(it1,position) }
        })

        holder.txt_followups_list_item.setOnClickListener {

            val intent = Intent(context, Search::class.java)
            intent.putExtra("FILTER", myList.get(position))
            startActivity(context, intent, null)
            (context as Followups).finish()
        }
    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val contactName: TextView = item.txt_followups_list_item
        val img_groups_list_item_dots : ImageView = item.img_followups_list_item_dots
        val txt_followups_list_item : TextView = item.txt_followups_list_item
    }

    private fun showDialog(context : Context, position: Int) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.layout_edit_groups)
        val edit = dialog.findViewById(R.id.rl_edit_groups_edit) as RelativeLayout
        val delete = dialog.findViewById(R.id.rl_edit_groups_delete) as RelativeLayout

        edit.setOnClickListener {

            callBack.editButtonFollowUpItemListener(position)
            dialog.dismiss()
        }

        delete.setOnClickListener {

           myList.removeAt(position)
           callBack.closeButtonFollowUpItemListener(position)
           removeData()
           dialog.dismiss()
        }

        dialog.show()

    }

    private fun removeData(){

        val sharedPreference =  context?.getSharedPreferences("FOLLOW _UP_PREFERENCE",Context.MODE_PRIVATE)
        var editor = sharedPreference?.edit()
        val gson = Gson()

        val json = gson.toJson(myList)
        editor?.putString("follow up list", json)
        editor?.apply()
    }

    interface FollowupsAdapter_SettingsCallBack{

        fun editButtonFollowUpItemListener(pos: Int)
        fun closeButtonFollowUpItemListener(pos: Int)
    }
}
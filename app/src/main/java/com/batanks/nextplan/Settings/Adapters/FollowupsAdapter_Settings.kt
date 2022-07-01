package com.batanks.nextplan.Settings.Adapters

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
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.Settings.Followups
import com.batanks.nextplan.Settings.viewmodel.FiltersViewModel
import com.batanks.nextplan.search.Search
import com.batanks.nextplan.swagger.model.FilterResultsList
import com.google.gson.Gson
import kotlinx.android.synthetic.main.layout_settings_followups.view.*

class FollowupsAdapter_Settings(
    private val callBack: FollowupsAdapter_SettingsCallBack,
    private val myList: ArrayList<FilterResultsList>,
    private val filtersViewModel: FiltersViewModel
) : RecyclerView.Adapter<FollowupsAdapter_Settings.MyViewHolder>() {

    lateinit var context : Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_settings_followups, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = myList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        //val itemView = holder.itemView

        holder.contactName.text = myList.get(position).name
        holder.img_groups_list_item_dots.setOnClickListener(View.OnClickListener {
            context?.let { it1 -> showDialog(it1,position,myList.get(position).id) }
        })

        holder.txt_followups_list_item.setOnClickListener {

            val intent = Intent(context, Search::class.java)
            intent.putExtra("FILTER", myList[position].keyword)
            startActivity(context, intent, null)
            (context as Followups).finish()
        }
    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val contactName: TextView = item.txt_followups_list_item
        val img_groups_list_item_dots : ImageView = item.img_followups_list_item_dots
        val txt_followups_list_item : TextView = item.txt_followups_list_item
    }

    private fun showDialog(context: Context, position: Int, id: Int?) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.layout_edit_groups)
        val edit = dialog.findViewById(R.id.rl_edit_groups_edit) as RelativeLayout
        val delete = dialog.findViewById(R.id.rl_edit_groups_delete) as RelativeLayout

        edit.setOnClickListener {

            callBack.editButtonFollowUpItemListener(myList.get(position).id!!,myList.get(position).name,myList.get(position).keyword)
            dialog.dismiss()
        }

        delete.setOnClickListener {
            filtersViewModel.deleteFilter(id)
            //myList.removeAt(position)
           Toast.makeText(context,context.getString(R.string.filter_deleted), Toast.LENGTH_SHORT).show()
           callBack.closeButtonFollowUpItemListener(position)
            filtersViewModel.getFiltersList()
           //removeData()
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

        fun editButtonFollowUpItemListener(pos: Int, name: String?, keyword: String?)
        fun closeButtonFollowUpItemListener(pos: Int)
    }
}
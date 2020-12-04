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
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.swagger.model.Group
import kotlinx.android.synthetic.main.layout_settings_groups_item.view.*


class GroupsAdapter_Settings (private val myList: List<Group>) : RecyclerView.Adapter<GroupsAdapter_Settings.MyViewHolder>() {
    private var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_settings_groups_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = myList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        //val itemView = holder.itemView

        holder.contactName.text = myList.get(position).name
        val id : Int = myList.get(position).id
        holder.contactName.setOnClickListener {

            val intent = Intent(context, com.batanks.nextplan.Settings.Group::class.java)
            intent.putExtra("ID", id)
            intent.putExtra("Group_Name", myList.get(position).name)
            context?.let { it1 -> startActivity(it1,intent,null) }

            (context as Activity).finish()

            /*val intent = Intent(context, Group :: class.java)
            startActivity(intent)*/
        }
        holder.img_groups_list_item_dots.setOnClickListener(View.OnClickListener {
            context?.let { it1 -> showDialog(it1) }
        })
    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val contactName: TextView = item.txt_groups_list_item
        val img_groups_list_item_dots : ImageView = item.img_groups_list_item_dots
    }

    private fun showDialog(context : Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.layout_edit_groups)
        val edit = dialog.findViewById(R.id.rl_edit_groups_edit) as RelativeLayout
        val delete = dialog.findViewById(R.id.rl_edit_groups_delete) as RelativeLayout

        edit.setOnClickListener {
            dialog.dismiss()
        }
        delete.setOnClickListener { dialog.dismiss() }
        dialog.show()

    }
}
package com.batanks.nextplan.Settings.Adapters

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.home.fragment.contacts.ContactsModel
import com.batanks.nextplan.swagger.model.CategoryList
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_plan_sorting_item.view.*

class Plan_Sorting_Adapter (private val categoryList: List<CategoryList>) : RecyclerView.Adapter<Plan_Sorting_Adapter.MyViewHolder>() {

//    private var context: Context? = null
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_plan_sorting_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = categoryList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val category = categoryList[position]

        holder.txt_plan_sorting_list_item.text = category.name
        Glide.with(context).load(category.picture).into(holder.img_plan_sorting_list_item)
    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val txt_plan_sorting_list_item: TextView = item.txt_plan_sorting_list_item
        val cb_plan_sorting_list_item : CheckBox = item.cb_plan_sorting_list_item
        val img_plan_sorting_list_item : ImageView = item.img_plan_sorting_list_item
    }

    private fun showDialog(context : Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
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
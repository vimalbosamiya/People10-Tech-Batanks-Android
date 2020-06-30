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
import kotlinx.android.synthetic.main.layout_plan_sorting_item.view.*

class Plan_Sorting_Adapter (private val myList: List<ContactsModel>) : RecyclerView.Adapter<Plan_Sorting_Adapter.MyViewHolder>() {
    private var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_plan_sorting_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = myList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        //val itemView = holder.itemView
        if(position <=3){
            holder.img_plan_sorting_list_item.setImageResource(R.drawable.ic_sort_plan)
        } else if(position == 4){
            holder.img_plan_sorting_list_item.setImageResource(R.drawable.ic_category_trip)
        }else if(position == 5){
            holder.img_plan_sorting_list_item.setImageResource(R.drawable.ic_category_professional)
        } else if(position == 6){
            holder.img_plan_sorting_list_item.setImageResource(R.drawable.ic_category_leisure)
        } else if(position == 7){
            holder.img_plan_sorting_list_item.setImageResource(R.drawable.ic_category_institutional)
        } else if(position == 8){
            holder.img_plan_sorting_list_item.setImageResource(R.drawable.ic_category_others)
        }

        holder.txt_plan_sorting_list_item.text = myList.get(position).contactname
        holder.cb_plan_sorting_list_item.setOnClickListener(View.OnClickListener {
            context?.let { it1 -> showDialog(it1) }
        })
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
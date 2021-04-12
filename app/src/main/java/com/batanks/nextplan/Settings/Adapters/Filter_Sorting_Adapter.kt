package com.batanks.nextplan.Settings.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.swagger.model.Filter
import com.batanks.nextplan.swagger.model.Guests
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_plan_sorting_item.view.*


class Filter_Sorting_Adapter (private val filterList: ArrayList<Filter>, private val listner :PlanFilterCallBack, private val sFilter: String?, private val sFilterType : String?) : RecyclerView.Adapter<Filter_Sorting_Adapter.MyViewHolder>(){

    lateinit var context: Context
    private var lastChecked: CheckBox? = null
    private var lastCheckedPos = -1
     //val isFilter: Boolean = isFilter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        context = parent.context
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_plan_sorting_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = filterList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        /*holder.txt_plan_sorting_list_item.text = filterList[position]
        if (isFilter == true){

            holder.img_plan_sorting_list_item.setImageResource(R.drawable.ic_sort_plan)

        }else if (isFilter == false){

            holder.img_plan_sorting_list_item.setImageResource(R.drawable.ic_settings_followup)
        }*/

        holder.txt_plan_sorting_list_item.text = filterList[position].filter

        if (filterList[position].filterType.toString() == "EVENTTYPE") {

            holder.img_plan_sorting_list_item.setImageResource(R.drawable.ic_sort_plan)

        } else if (filterList[position].filterType.toString() == "FOLLOWUP") {

            holder.img_plan_sorting_list_item.setImageResource(R.drawable.ic_settings_followup)

        } else if (filterList[position].filterType.toString() == "CATEGORY") {

            Glide.with(context).load(filterList[position].imgUrl).into(holder.img_plan_sorting_list_item)
        }

        if (sFilter == filterList[position].filter && sFilterType == filterList[position].filterType.toString()){

            holder.checkBox.isChecked = true
            val selectedFilter = filterList.get(position)
            listner.SelectedFilter(selectedFilter)
        }


        if (holder.checkBox.isChecked){

            lastChecked = holder.checkBox
            lastCheckedPos = position
        }

        holder.checkBox.setOnClickListener { v ->
            val cb = v as CheckBox
            val clickedPos = /*(cb.tag as Int).toInt()*/ position

            if (cb.isChecked) {
                if (lastChecked != null) {
                    lastChecked!!.isChecked = false
                    filterList.get(lastCheckedPos)?.selection = false
                }
                lastChecked = cb
                lastCheckedPos = clickedPos

                filterList.get(clickedPos).selection = (cb.isChecked)
                //Toast.makeText(v.context , "" + myList.get(clickedPos).contactname , Toast.LENGTH_SHORT).show()
                val selectedFilter = filterList.get(position)
                listner.SelectedFilter(selectedFilter)
            }

            else{

                lastChecked = null
                listner.SelectedFilter(null)
            }
        }


    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val txt_plan_sorting_list_item: TextView = item.txt_plan_sorting_list_item
        val checkBox : CheckBox = item.cb_plan_sorting_list_item
        val img_plan_sorting_list_item : ImageView = item.img_plan_sorting_list_item
    }

    interface PlanFilterCallBack {
        fun SelectedFilter(filter : Filter?)
    }

}
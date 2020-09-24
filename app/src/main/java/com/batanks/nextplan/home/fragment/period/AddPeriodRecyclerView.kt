package com.batanks.nextplan.home.fragment.period

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.swagger.model.EventDate
import kotlinx.android.synthetic.main.layout_add_plan_add_period.*
import kotlinx.android.synthetic.main.layout_add_plan_add_period_card.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddPeriodRecyclerView(private val callBack: AddPeriodRecyclerViewCallBack,
                            private val modelList: ArrayList<EventDate>)
    : RecyclerView.Adapter<AddPeriodRecyclerView.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_add_plan_add_period_card, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = modelList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val itemView = holder.itemView

        holder.close.setOnClickListener {
            modelList.removeAt(position)
            callBack.closeButtonAddPeriodItemListener(position)
        }

        val cal = Calendar.getInstance()
        val inputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val dateFormatter = SimpleDateFormat("E, MMM dd yyyy hh:mm a")

        val tempStartDate : String = modelList[position].start
        val tempEndDate : String = modelList[position].end

        val startDate : Date = inputFormatter.parse(tempStartDate)
        val endDate : Date = inputFormatter.parse(tempEndDate)

        val finalStartDate = dateFormatter.format(startDate)
        val finalEndDate = dateFormatter.format(endDate)

        println(finalStartDate)
        println(finalEndDate)

        /*val startDate = dateFormatter.format(modelList[position].start)
        val endDate = dateFormatter.format(modelList[position].end)*/

        holder.from.text = finalStartDate
        holder.to.text = finalEndDate
    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val from: TextView = item.fromResponseTextView
        val to: TextView = item.toResponseTextView
        val close: ImageView = item.closeButtonIcon
    }

    interface AddPeriodRecyclerViewCallBack {
        fun closeButtonAddPeriodItemListener(pos: Int)
    }
}
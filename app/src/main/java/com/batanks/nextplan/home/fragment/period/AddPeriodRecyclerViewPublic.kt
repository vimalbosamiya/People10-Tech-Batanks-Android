package com.batanks.nextplan.home.fragment.period

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.swagger.model.PostDates
import kotlinx.android.synthetic.main.layout_add_plan_add_period_card.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddPeriodRecyclerViewPublic(private val callBack: AddPeriodRecyclerViewCallBack,
                            private val modelList: ArrayList<PostDates>, private val editButtonClicked : Boolean, private val deleteButtonClicked : Boolean)
    : RecyclerView.Adapter<AddPeriodRecyclerViewPublic.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_add_plan_add_period_card, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = modelList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val itemView = holder.itemView

        holder.close.setOnClickListener {

            if (deleteButtonClicked == false){

                modelList.removeAt(position)
            }

            callBack.closeButtonAddPeriodItemListener(position)
        }

        val cal = Calendar.getInstance()
        val inputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val dateFormatter = SimpleDateFormat("E, MMM dd yyyy hh:mm a")
        val Formatter = SimpleDateFormat("E, MMM dd yyyy HH:mm")

        var finalEndDate : String? = null

       /* val tempStartDate : String = modelList[position].start!!
        val startDate : Date = inputFormatter.parse(tempStartDate)
        val finalStartDate = Formatter.format(startDate)
        holder.from.text = finalStartDate*/

        if (editButtonClicked == true || deleteButtonClicked == true){

            val tempStartDate : String = modelList[position].start!!
            val startDate : Date = inputFormatter.parse(tempStartDate)
            val finalStartDate = Formatter.format(startDate)
            holder.from.text = finalStartDate

            if (modelList[position].end != null){

                val tempEndDate : String = modelList[position]?.end!!
                val endDate : Date = inputFormatter.parse(tempEndDate)
                val finalEndDate = Formatter.format(endDate)
                holder.to.text = finalEndDate
            }
        } else {
            val tempStartDate : String = modelList[position].start!!
            val startDate : Date = inputFormatter.parse(tempStartDate)
            val finalStartDate = Formatter.format(startDate)
            holder.from.text = finalStartDate

            if (modelList[position].end.isNullOrEmpty()){

                holder.to.text = ""

            } else {

                val tempEndDate : String = modelList[position]?.end!!
                val endDate : Date = inputFormatter.parse(tempEndDate)
                val finalEndDate = Formatter.format(endDate)
                holder.to.text = finalEndDate
            }

        }

        /*if (modelList[position].end != null){

            val tempEndDate : String = modelList[position]?.end!!
            val endDate : Date = inputFormatter.parse(tempEndDate)
            finalEndDate = dateFormatter.format(endDate)
        }*/

        //val tempEndDate : String = modelList[position]?.end!!

//        val startDate : Date = inputFormatter.parse(tempStartDate)
        //val endDate : Date = inputFormatter.parse(tempEndDate)

//        val finalStartDate = dateFormatter.format(startDate)
        //val finalEndDate = dateFormatter.format(endDate)

        //println(finalStartDate)
        //println(finalEndDate)

        /*val startDate = dateFormatter.format(modelList[position].start)
        val endDate = dateFormatter.format(modelList[position].end)*/

//        holder.from.text = finalStartDate
//        holder.to.text = finalEndDate

        if (editButtonClicked == true){

            holder.editButtonIcon.visibility = View.VISIBLE
            holder.close.visibility = View.GONE

        } else {

            holder.editButtonIcon.visibility = View.GONE
            holder.close.visibility = View.VISIBLE
        }

        holder.editButtonIcon.setOnClickListener {

            callBack.editButtonAddPeriodItemListener(position)
        }
    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val from: TextView = item.fromResponseTextView
        val to: TextView = item.toResponseTextView
        val close: ImageView = item.closeButtonIcon
        val editButtonIcon: ImageView = item.editButtonIcon
    }

    interface AddPeriodRecyclerViewCallBack {
        fun closeButtonAddPeriodItemListener(pos: Int)
        fun editButtonAddPeriodItemListener(pos: Int)
    }
}
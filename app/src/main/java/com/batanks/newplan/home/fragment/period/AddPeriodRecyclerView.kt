package com.batanks.newplan.home.fragment.period

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batanks.newplan.R
import kotlinx.android.synthetic.main.layout_add_plan_add_period_card.view.*

class AddPeriodRecyclerView(private val modelList: ArrayList<CalenderModel>) : RecyclerView.Adapter<AddPeriodRecyclerView.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_add_plan_add_period_card, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = modelList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val itemView = holder.itemView

        holder.from.text = modelList[position].fromDate
        holder.to.text = modelList[position].toDate
    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val from: TextView = item.fromResponseTextView
        val to: TextView = item.toResponseTextView
    }
}
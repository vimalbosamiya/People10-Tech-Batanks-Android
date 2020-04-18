package com.batanks.newplan.home.fragment.period

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batanks.newplan.R
import com.batanks.newplan.swagger.model.EventDate
import kotlinx.android.synthetic.main.layout_add_plan_add_period_card.view.*

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
        holder.from.text = modelList[position].start
        holder.to.text = modelList[position].end
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
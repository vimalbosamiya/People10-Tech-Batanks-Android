package com.batanks.newplan.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batanks.newplan.R
import kotlinx.android.synthetic.main.item_event_name.view.*

class HomePlanPreviewAdapter(private val myList: List<String>) : RecyclerView.Adapter<HomePlanPreviewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_event_name, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = /*myList.size*/ 5

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val itemView = holder.itemView

        holder.from.text = "From from Adapter"
        holder.to.text = "From from Adapter"
        holder.place.text = "From from Adapter"
    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val from: TextView = item.fromResponseTextView
        val to: TextView = item.toResponseTextView
        val place: TextView = item.placeResponseTextView
    }
}
package com.batanks.nextplan.home.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.eventdetails.EventDetailView
import com.batanks.nextplan.swagger.model.EventList
import kotlinx.android.synthetic.main.item_event_name.view.*

class HomePlanPreviewAdapter(private val myList: List<String>) : RecyclerView.Adapter<HomePlanPreviewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_event_name, parent, false)

        view.setOnClickListener {

            //Log.i("PrivateEvent","Event was Clicked")

            //Toast.makeText(context,"Event Clicked",Toast.LENGTH_LONG).show()
            val intent = Intent(parent.context, EventDetailView::class.java)
            startActivity(parent.context,intent,null)
        }
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
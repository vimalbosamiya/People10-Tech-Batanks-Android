package com.batanks.nextplan.home.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.eventdetails.EventDetailView
import com.batanks.nextplan.eventdetailsadmin.EventDetailViewAdmin
import kotlinx.android.synthetic.main.item_event_name.view.*

class HomePlanPreviewAdapter(private val myList: List<String>) : RecyclerView.Adapter<HomePlanPreviewAdapter.MyViewHolder>() {
    lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
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
        holder.cardview_homeplan_item.setOnClickListener(View.OnClickListener {
            if(position == 0){
                val intent = Intent(context, EventDetailView::class.java)
                startActivity(context,intent,null)
            } else {
                val intent = Intent(context, EventDetailViewAdmin::class.java)
                startActivity(context,intent,null)
            }
        })
    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val from: TextView = item.fromResponseTextView
        val to: TextView = item.toResponseTextView
        val place: TextView = item.placeResponseTextView
        val cardview_homeplan_item : CardView = item.cardview_homeplan_item
    }
}
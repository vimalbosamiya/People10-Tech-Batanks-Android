package com.batanks.nextplan.eventdetails.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.swagger.model.Task

class EventActionListAdapter (val actionList : List<Task>, val context: Context): RecyclerView.Adapter<EventActionListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_date_display, parent, false)
        return  ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return actionList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val action : Task = actionList[position]

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}
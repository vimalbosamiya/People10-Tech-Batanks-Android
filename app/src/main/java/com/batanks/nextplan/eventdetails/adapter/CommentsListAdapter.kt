package com.batanks.nextplan.eventdetails.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R

class CommentsListAdapter (val commentsList : List<Int>, val context : Context): RecyclerView.Adapter<CommentsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_date_display, parent, false)
        return  ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  commentsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

       val comment : Int = commentsList[position]
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {



    }
}
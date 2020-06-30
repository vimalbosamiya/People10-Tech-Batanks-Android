package com.batanks.nextplan.search.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import kotlinx.android.synthetic.main.layout_group_item.view.*

class AddToGroupsAdapter (private val groupsList: List<String>) : RecyclerView.Adapter<AddToGroupsAdapter.MyViewHolder>() {

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        context = parent.context
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_group_item, parent, false)
        return MyViewHolder(view)

    }

    override fun getItemCount() = 20

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.groupName.text = "Group Name"

    }

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val groupName : TextView = itemView.groupName
}
}
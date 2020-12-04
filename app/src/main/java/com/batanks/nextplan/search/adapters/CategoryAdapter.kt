package com.batanks.nextplan.search.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.swagger.model.CategoryList
import kotlinx.android.synthetic.main.custom_spinner_adater_item.view.*

class CategoryAdapter (private val categoryList: List<CategoryList>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.custom_spinner_adater_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = categoryList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val category = categoryList[position]

        holder.categoryTitle.text = categoryList[position].name
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        //val contactName: TextView = item.txt_contact_name
        //val txt_contact_list_item : TextView = itemView.txt_contact_list_item
        val categoryTitle : TextView = itemView.categoryTitle
    }
}
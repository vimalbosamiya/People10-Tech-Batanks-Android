package com.batanks.nextplan.search.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.swagger.model.CategoryList
import com.bumptech.glide.Glide
import com.google.android.material.checkbox.MaterialCheckBox
import kotlinx.android.synthetic.main.layout_category_pop_up.view.*


class CategoryAdapter (private val categoryList: ArrayList<CategoryList>, var context: Context, private val listner : CategoryRecyclerViewCallBack, private val categoryId : Int?)
                                                : RecyclerView.Adapter<CategoryAdapter.ViewHolder>(){

    private var lastCheckedPos = -1
    private var lastChecked: MaterialCheckBox? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_category_pop_up, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = categoryList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val category = categoryList[position]

        holder.categoryName.text = category.name
        Glide.with(context).load(category.picture).circleCrop().into(holder.categoryIcon)

        if (categoryId == category.pk){

            holder.checkbox.isChecked = true
        }

        if (holder.checkbox.isChecked){

            lastChecked = holder.checkbox
            lastCheckedPos = position
        }

        holder.checkbox.setOnClickListener { v ->
            val cb = v as MaterialCheckBox
            val clickedPos = position

            if (cb.isChecked) {
                if (lastChecked != null) {
                    lastChecked!!.isChecked = false
                }
                lastChecked = cb
                lastCheckedPos = clickedPos

                listner.selectedCategory(category)

            } else{

                lastChecked = null
                listner.selectedCategory(null)
            }
        }

    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val categoryName : TextView = itemView.categoryName
        val categoryIcon : ImageView = itemView.categoryIcon
        val checkbox : MaterialCheckBox = itemView.checkbox

    }

    interface CategoryRecyclerViewCallBack {
        fun selectedCategory(selectedCategory : CategoryList?)
    }
}
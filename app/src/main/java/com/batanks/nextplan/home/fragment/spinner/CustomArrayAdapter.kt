package com.batanks.nextplan.home.fragment.spinner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.batanks.nextplan.R
import kotlinx.android.synthetic.main.custom_spinner_adater_item.view.*

class CustomArrayAdapter(private val mContext: Context, private val modelList: List<SpinnerModel>)
    : ArrayAdapter<SpinnerModel>(mContext, 0, modelList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    private fun createItemView(position: Int, convertView: View?, parent: ViewGroup): View {

        val model = getItem(position)
        val view = convertView
                ?: LayoutInflater.from(parent.context).inflate(R.layout.custom_spinner_adater_item, parent, false)

        model?.id?.let { view.categoryIcon.setImageResource(it) }
        view.categoryTitle.text = model?.title

        return view
    }
}
package com.batanks.nextplan.home.fragment.action

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.swagger.model.Task
import kotlinx.android.synthetic.main.fragment_add_action_card.view.*

class AddActionRecyclerView (private val callBack: AddActionRecyclerViewCallBack, private val modelList: ArrayList<Task>) :
        RecyclerView.Adapter<AddActionRecyclerView.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.fragment_add_action_card, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = modelList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.closeButton.setOnClickListener {
            modelList.removeAt(position)
            callBack.closeButtonAddActionItemListener(position)
        }
        holder.txt_actionname.text = modelList[position].name
        //holder.placeAddress.text = modelList[position].address
    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val txt_actionname: TextView = item.txt_actionname
        //val placeAddress: TextView = item.placeAddressTextView
        val closeButton: ImageView = item.actioncloseButtonIcon
    }

    interface AddActionRecyclerViewCallBack {
        fun closeButtonAddActionItemListener(pos: Int)
    }
}
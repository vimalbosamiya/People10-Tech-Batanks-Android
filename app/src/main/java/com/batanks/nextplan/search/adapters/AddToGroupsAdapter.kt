package com.batanks.nextplan.search.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.home.viewmodel.GroupsViewModel
import com.batanks.nextplan.swagger.model.AddContactToGroup
import com.batanks.nextplan.swagger.model.Group
import com.google.android.material.checkbox.MaterialCheckBox
import kotlinx.android.synthetic.main.layout_group_item.view.*

class AddToGroupsAdapter (private val groupsList: List<Group>, val groupsViewModel: GroupsViewModel, val contactId : Int) : RecyclerView.Adapter<AddToGroupsAdapter.MyViewHolder>() {

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        context = parent.context
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_group_item, parent, false)
        return MyViewHolder(view)

    }

    override fun getItemCount() = groupsList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val groupId : String = groupsList[position].id.toString()

        /*for (item in groupsList[position].users){

            if (item.id == contactId){

                holder.groupCheckBox.isChecked = true
            }
        }*/

        holder.groupName.text = groupsList.get(position).name

        holder.groupCheckBox.setOnClickListener {

            if (holder.groupCheckBox.isChecked){

                groupsViewModel.addContactToGroup(groupId, AddContactToGroup(contactId))
            }
        }

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val groupName : TextView = itemView.groupName
    val groupCheckBox : MaterialCheckBox = itemView.groupCheckBox
}
}
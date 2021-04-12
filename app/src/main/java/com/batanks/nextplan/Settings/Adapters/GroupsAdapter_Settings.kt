package com.batanks.nextplan.Settings.Adapters

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.Settings.viewmodel.GroupListViewModel
import com.batanks.nextplan.arch.response.Status
import com.batanks.nextplan.home.markRequiredInRed
import com.batanks.nextplan.swagger.model.Group
import com.batanks.nextplan.swagger.model.GroupEdit
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.layout_settings_groups_item.view.*


class GroupsAdapter_Settings (private val myList: ArrayList<Group>, val groupListViewModel : GroupListViewModel) : RecyclerView.Adapter<GroupsAdapter_Settings.MyViewHolder>() {

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        context = parent.context
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_settings_groups_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = myList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        //val itemView = holder.itemView

        holder.contactName.text = myList.get(position).name
        //val id : Int = myList.get(position).id
        holder.contactName.setOnClickListener {

            val intent = Intent(context, com.batanks.nextplan.Settings.Group::class.java)
            intent.putExtra("ID", myList.get(position).id)
            intent.putExtra("Group_Name", myList.get(position).name)
            context?.let { it1 -> startActivity(it1,intent,null) }

            //(context as Activity).finish()

            /*val intent = Intent(context, Group :: class.java)
            startActivity(intent)*/
        }

        holder.img_groups_list_item_dots.setOnClickListener(View.OnClickListener {
            context?.let { it1 -> showDialog(it1, myList.get(position).id, myList.get(position).name) }
        })
    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val contactName: TextView = item.txt_groups_list_item
        val img_groups_list_item_dots : ImageView = item.img_groups_list_item_dots
    }

    private fun showDialog(context : Context, id : Int, currentGroupName : String) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.layout_edit_groups)
        val edit = dialog.findViewById(R.id.rl_edit_groups_edit) as RelativeLayout
        val delete = dialog.findViewById(R.id.rl_edit_groups_delete) as RelativeLayout

        edit.setOnClickListener {

            showDialogRename(context,id, currentGroupName)
            dialog.dismiss()
        }
        delete.setOnClickListener {

            groupListViewModel.deleteGroup(id.toString())

            dialog.dismiss()

        }
        dialog.show()

    }

    private fun showDialogRename(context : Context, id: Int, currentGroupName : String) {

        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.layout_rename_group)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        /*val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)*/

        val btn_create_group_cancel = dialog.findViewById(R.id.btn_create_group_cancel) as Button
        val btn_create_group_ok = dialog.findViewById(R.id.btn_create_group_ok) as Button
        val tip_create_group_gname = dialog.findViewById(R.id.tip_create_group_gname) as TextInputLayout
        val input_create_group_gname = dialog.findViewById(R.id.input_create_group_gname) as TextInputEditText

        tip_create_group_gname.markRequiredInRed()

        input_create_group_gname.setText(currentGroupName)

        btn_create_group_cancel.setOnClickListener {

            dialog.dismiss()
        }

        btn_create_group_ok.setOnClickListener {

            if (tip_create_group_gname.editText?.length()!! >= 1){

                dialog.dismiss()

                groupListViewModel.renameGroup(id.toString(), GroupEdit(input_create_group_gname.text.toString()))

            } else {

                tip_create_group_gname.editText?.setError(context.getString(R.string.group_name_error))
                input_create_group_gname.requestFocus()
            }
        }

        //input_create_group_gname.requestFocus()
        dialog.show()

        dialog.window?.decorView?.setOnTouchListener { v, event ->

            if (event?.action == MotionEvent.ACTION_DOWN) {

                val imm = v?.getContext()?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                v.clearFocus()
            }
            false
        }

    }
}
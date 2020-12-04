package com.batanks.nextplan.Settings

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.Settings.Adapters.GroupListAdapter
import com.batanks.nextplan.Settings.viewmodel.GroupListViewModel
import com.batanks.nextplan.arch.BaseAppCompatActivity
import com.batanks.nextplan.arch.response.Status
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.common.getLoadingDialog
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.swagger.api.GroupsAPI
import com.batanks.nextplan.swagger.model.Contact
import com.batanks.nextplan.swagger.model.Group
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_group.*
import kotlinx.android.synthetic.main.layout_rename_group.*

class Group : BaseAppCompatActivity() {

    lateinit var groupRecyclerView : RecyclerView
    lateinit var adapter : GroupListAdapter
    var groupList: ArrayList<Contact> = arrayListOf()
    var group : Group? = null
    var renamedGroup : Group? = null
    var id : Int = 0

    private val groupListViewModel: GroupListViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            RetrofitClient.getRetrofitInstance(this)?.create(GroupsAPI::class.java)?.let {
                GroupListViewModel(it)
            }
        }).get(GroupListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)

         id  = intent.getIntExtra("ID", 0)
        val groupName : String? = intent.getStringExtra("Group_Name")

        textViewGroup.text = groupName

        loadingDialog = this.getLoadingDialog(0, R.string.loading_list_please_wait, theme = R.style.AlertDialogCustom)

        groupListViewModel.getGroupList(id.toString())

        backButton.setOnClickListener {

            finish()
            val intent = Intent(this, com.batanks.nextplan.Settings.Contact :: class.java)
            startActivity(intent)
        }

        groupRecyclerView = findViewById(R.id.groupRecyclerView)
        groupRecyclerView.layoutManager = LinearLayoutManager(this)

        groupListViewModel.responseLiveData1.observe(this, Observer{ response ->

            when(response.status){
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()

                    group = response.data as Group

                    id = response.data.id

                    groupList = group!!.users

                    groupListViewModel.response = group!!.users

                    adapter = GroupListAdapter(groupList)

                    groupRecyclerView.adapter = adapter

                    println(groupList)

                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })

        settingsButton.setOnClickListener {

            showDialog(this)

        }

    }

    private fun showDialog(context : Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.layout_group_settings)
        val rename = dialog.findViewById(R.id.rl_edit_groups_edit) as RelativeLayout
        val removeUser = dialog.findViewById(R.id.rl_edit_groups_delete) as RelativeLayout
        val deleteGroup = dialog.findViewById(R.id.rl_groups_delete) as RelativeLayout


        rename.setOnClickListener {

            showDialog()
            dialog.dismiss()
        }
        removeUser.setOnClickListener {


        }

        deleteGroup.setOnClickListener {

            groupListViewModel.deleteGroup(id.toString())

            groupListViewModel.responseLiveData3.observe(this, Observer{ response ->

                when(response.status){
                    Status.LOADING -> {
                        showLoader()
                    }
                    Status.SUCCESS -> {
                        hideLoader()

                        finish()
                        val intent = Intent(this, com.batanks.nextplan.Settings.Contact :: class.java)
                        startActivity(intent)

                        Toast.makeText(this, "Coming into Success",Toast.LENGTH_LONG).show()

                        println("Coming into Success")

                    }
                    Status.ERROR -> {
                        hideLoader()
                        showMessage(response.error?.message.toString())

                        Toast.makeText(this, "Coming into error",Toast.LENGTH_LONG).show()

                        println("Coming into error")
                        println(response.error?.message.toString())
                    }
                }
            })

            dialog.dismiss()
        }

        dialog.show()

    }

    private fun showDialog() {
        val dialog = Dialog(this)
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

        btn_create_group_cancel.setOnClickListener {

            dialog.dismiss()
        }

        btn_create_group_ok.setOnClickListener {

            if (tip_create_group_gname.editText?.length()!! >= 1){

                dialog.dismiss()

                val editedGroup : Group = Group(id,groupList,input_create_group_gname.text.toString())

                groupListViewModel.renameGroup(id.toString(),editedGroup)

                groupListViewModel.responseLiveData2.observe(this, Observer{ response ->

                    when(response.status){

                        Status.LOADING -> {
                            showLoader()
                        }

                        Status.SUCCESS -> {
                            hideLoader()

                            renamedGroup = response.data as Group

                            textViewGroup.text = renamedGroup!!.name

                            groupList = group!!.users

                            groupListViewModel.response = group!!.users

                            adapter = GroupListAdapter(groupList)

                            groupRecyclerView.adapter = adapter

                            //textViewGroup.text = input_create_group_gname.text.toString()

                        }
                        Status.ERROR -> {
                            hideLoader()
                            showMessage(response.error?.message.toString())
                        }
                    }
                })

                //textViewGroup.text = tip_create_group_gname.editText!!.text

            } else {

                tip_create_group_gname.editText?.error = "Group name should contain atleast one character"
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

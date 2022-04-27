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
import com.batanks.nextplan.home.markRequiredInRed
import com.batanks.nextplan.home.viewmodel.ContactsViewModel
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.swagger.api.ContactsAPI
import com.batanks.nextplan.swagger.api.GroupsAPI
import com.batanks.nextplan.swagger.model.Contact
import com.batanks.nextplan.swagger.model.ContactsList
import com.batanks.nextplan.swagger.model.Group
import com.batanks.nextplan.swagger.model.GroupEdit
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_group.*
import kotlinx.android.synthetic.main.layout_rename_group.*
import java.util.*
import kotlin.collections.ArrayList

class Group : BaseAppCompatActivity() {

    lateinit var groupRecyclerView : RecyclerView
    lateinit var adapter : GroupListAdapter
    var groupList: ArrayList<ContactsList> = arrayListOf()
    var group : Group? = null
    var renamedGroup : Group? = null
    var id : Int = 0
    var closeButtonVisible = false

    private val groupListViewModel: GroupListViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            RetrofitClient.getRetrofitInstance(this)?.create(GroupsAPI::class.java)?.let {
                GroupListViewModel(it)
            }
        }).get(GroupListViewModel::class.java)
    }

    private val contactsViewModel: ContactsViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            RetrofitClient.getRetrofitInstance(this)?.create(ContactsAPI::class.java)?.let {
                ContactsViewModel(it)
            }
        }).get(ContactsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)

         id  = intent.getIntExtra("ID", 0)
        val groupName : String? = intent.getStringExtra("Group_Name")

        //textViewGroup.text = groupName
        //loadingDialog = this.getLoadingDialog(0, R.string.loading_list_please_wait, theme = R.style.AlertDialogCustom)

        groupListViewModel.getGroupList(id.toString())

        backButton.setOnClickListener {

            finish()
            /*val intent = Intent(this, com.batanks.nextplan.Settings.Contact :: class.java)
            startActivity(intent)*/
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

                    textViewGroup.text = group!!.name

                    groupList = group!!.users

                    groupList.sortBy { it.username?.toLowerCase(Locale.ROOT) }

                    groupListViewModel.response = group!!.users

                    adapter = GroupListAdapter(groupList,closeButtonVisible,contactsViewModel)

                    groupRecyclerView.adapter = adapter

                    println(groupList)

                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })

        contactsViewModel.responseLiveDataDel.observe(this, Observer{ response ->

            when(response.status){
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()

                    groupListViewModel.getGroupList(id.toString())

                    /* contactsViewModel.response = response.data as InlineResponse2001

                     contactList = contactsViewModel.response!!.results

                     adapter = ContactsAdapter_Settings(contactList,contactsViewModel,)

                     if(contactList.size <=5) {
                         val params = rv_settings_contacts.getLayoutParams() as ConstraintLayout.LayoutParams
                         params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                         rv_settings_contacts.setLayoutParams(params)
                     }
                     rv_settings_contacts.adapter = adapter*/

                    //println(contactList)
                    println("from contacts delete API")

                }
                Status.ERROR -> {
                    hideLoader()

                    //contactsViewModel.getContactsList()
                    //showMessage(response.error?.message.toString())
                    println("from contacts delete API Error")
                }
            }
        })

        settingsButton.setOnClickListener {

            showDialogSettings(this)

        }

    }

    private fun showDialogSettings(context : Context) {
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

            closeButtonVisible = true

            adapter = GroupListAdapter(groupList,closeButtonVisible,contactsViewModel)
            groupRecyclerView.adapter = adapter
            adapter.notifyDataSetChanged()

            dialog.dismiss()
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
                        /*val intent = Intent(this, com.batanks.nextplan.Settings.Contact :: class.java)
                        startActivity(intent)*/

                        Toast.makeText(this, getString(R.string.group_deleted),Toast.LENGTH_LONG).show()
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

        tip_create_group_gname.markRequiredInRed()

        btn_create_group_cancel.setOnClickListener {

            dialog.dismiss()
        }

        btn_create_group_ok.setOnClickListener {

            if (tip_create_group_gname.editText?.length()!! >= 1){

                dialog.dismiss()

                groupListViewModel.renameGroup(id.toString(), GroupEdit(input_create_group_gname.text.toString()))

                groupListViewModel.responseLiveData2.observe(this, Observer{ response ->

                    when(response.status){

                        Status.LOADING -> {
                            showLoader()
                        }

                        Status.SUCCESS -> {
                            hideLoader()

                            groupListViewModel.getGroupList(id.toString())
                        }
                        Status.ERROR -> {
                            hideLoader()
                            showMessage(response.error?.message.toString())
                        }
                    }
                })

            } else {

                tip_create_group_gname.editText?.setError(getString(R.string.group_name_error))
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

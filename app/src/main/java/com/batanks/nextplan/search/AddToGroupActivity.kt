package com.batanks.nextplan.search

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.MotionEvent
import android.view.View
import android.view.View.VISIBLE
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.Settings.Contact
import com.batanks.nextplan.arch.BaseAppCompatActivity
import com.batanks.nextplan.arch.response.Status
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.common.dismissKeyboard
import com.batanks.nextplan.common.getLoadingDialog
import com.batanks.nextplan.home.HomePlanPreview
import com.batanks.nextplan.home.fragment.contacts.GroupsAdapter
import com.batanks.nextplan.home.markRequiredInRed
import com.batanks.nextplan.home.viewmodel.GroupsViewModel
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.search.adapters.AddToGroupsAdapter
import com.batanks.nextplan.swagger.api.GroupsAPI
import com.batanks.nextplan.swagger.model.AddGroupContact
import com.batanks.nextplan.swagger.model.Group
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_add_to_groups.*
import kotlinx.android.synthetic.main.layout_create_group.*
import java.util.*
import kotlin.collections.ArrayList

class AddToGroupActivity : BaseAppCompatActivity(), View.OnClickListener  {

    lateinit var groupsRecyclerView: RecyclerView
    //lateinit var groupsAdapter: GroupsAdapter
    //lateinit var groupsAdapter: AddToGroupsAdapter

    var Id : Int? = 0

    lateinit var groupList : ArrayList<Group>
    //lateinit var groupAdapter : AddToGroupsAdapter

    /*var friendsExpanded : Boolean = false
    var groupsExpanded : Boolean = false
    var usersExpanded : Boolean = false*/

    private val groupsViewModel: GroupsViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            RetrofitClient.getRetrofitInstance(this)?.create(GroupsAPI::class.java)?.let {
                GroupsViewModel(it)
            }
        }).get(GroupsViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_add_to_groups)

        dismissKeyboard()

        val intent = intent

        Id = intent.getIntExtra("Id", 0)
        /*friendsExpanded = intent.getBooleanExtra("IS_FRIENDS_EXPANDED",true)
        groupsExpanded = intent.getBooleanExtra("IS_GROUPS_EXPANDED",false)
        usersExpanded = intent.getBooleanExtra("IS_USERS_EXPANDED",false)*/
        //println(Id!!)

        groupsRecyclerView = findViewById(R.id.groupsRecyclerView)
        groupsRecyclerView.layoutManager = LinearLayoutManager(this)
        //groupAdapter.notifyDataSetChanged()

        //loadingDialog = this.getLoadingDialog(0, R.string.loading_list_please_wait, theme = R.style.AlertDialogCustom)

        groupsViewModel.getGroupsList()
        //groupAdapter.notifyDataSetChanged()

        groupsViewModel.responseLiveData.observe(this, Observer{ response ->

            when(response.status){
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()

                    groupsViewModel.response = response.data as ArrayList<Group>

                    groupList = response.data

                    groupList.sortBy { it.name?.toLowerCase(Locale.ROOT) }

                    //groupAdapter = AddToGroupsAdapter(groupList)

                    if(groupList.size <=5) {
                        val params = groupsRecyclerView.getLayoutParams() as ConstraintLayout.LayoutParams
                        params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                        groupsRecyclerView.setLayoutParams(params)
                    }
                    groupsRecyclerView.adapter = AddToGroupsAdapter(groupList,groupsViewModel, Id!!)
                    //groupAdapter.notifyDataSetChanged()

                    println(groupList)

                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                    println("error from get" +response.error?.message.toString())
                }
            }
        })

        groupsViewModel.responseLiveDataPost.observe(this, Observer{ response ->

            when(response.status){
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()

                    groupsViewModel.getGroupsList()

                    Toast.makeText(context(),R.string.group_created,Toast.LENGTH_SHORT).show()

                    /*groupsViewModel.response = response.data as List<Group>

                    groupList = response.data

                    //groupAdapter = AddToGroupsAdapter(groupList)

                    if(groupList.size <=5) {
                        val params = groupsRecyclerView.getLayoutParams() as ConstraintLayout.LayoutParams
                        params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                        groupsRecyclerView.setLayoutParams(params)
                    }
                    groupsRecyclerView.adapter = AddToGroupsAdapter(groupList)
                    //groupAdapter.notifyDataSetChanged()

                    println(groupList)*/

                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                    println("error from post" + response.error?.message.toString())
                }
            }
        })

        groupsViewModel.responseLiveDataAddContact.observe(this, Observer{ response ->

            when(response.status){
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()

                    /*groupsViewModel.response = response.data as List<Group>

                    groupList = response.data

                    //groupAdapter = AddToGroupsAdapter(groupList)

                    if(groupList.size <=5) {
                        val params = groupsRecyclerView.getLayoutParams() as ConstraintLayout.LayoutParams
                        params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                        groupsRecyclerView.setLayoutParams(params)
                    }
                    groupsRecyclerView.adapter = AddToGroupsAdapter(groupList)
                    //groupAdapter.notifyDataSetChanged()

                    println(groupList)*/

                    Toast.makeText(context(),R.string.contact_added_to_group,Toast.LENGTH_SHORT).show()

                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })

        //groupsRecyclerView.adapter = AddToGroupsAdapter(listOf<String>())

        backArrow.setOnClickListener {

            finish()

            /*val contactIntent = Intent(this, Contact::class.java)
            contactIntent.putExtra("IS_FRIENDS_EXPANDED", true)
            contactIntent.putExtra("IS_GROUPS_EXPANDED", groupsExpanded)
            contactIntent.putExtra("IS_USERS_EXPANDED", usersExpanded)
            startActivity(contactIntent)
            finish()*/

           /* supportFragmentManager.beginTransaction()
                    .add(R.id.frameLayout, SearchFragment())
                    .addToBackStack(SearchFragment.TAG).commit()

            frameLayout.visibility = VISIBLE*/


        }

        rl_settings_create_new_groups.setOnClickListener {

            showDialog()
            //groupAdapter.notifyDataSetChanged()

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    /*private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_create_followups)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btn_create_followups_cancel = dialog.findViewById(R.id.btn_create_followups_cancel) as Button
        val btn_create_followups_ok = dialog.findViewById(R.id.btn_create_followups_ok) as Button

        btn_create_followups_cancel.setOnClickListener {
            dialog.dismiss()
        }
        btn_create_followups_ok.setOnClickListener { dialog.dismiss() }
        dialog.show()

    }*/

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.layout_create_group)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        /*val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)*/

        val btn_create_group_cancel = dialog.findViewById(R.id.btn_create_group_cancel) as Button
        val btn_create_group_ok = dialog.findViewById(R.id.btn_create_group_ok) as Button
        val tip_create_group_gname = dialog.findViewById(R.id.tip_create_group_gname) as TextInputLayout
        val input_create_group_gname = dialog.findViewById(R.id.input_create_group_gname) as TextInputEditText

        tip_create_group_gname.markRequiredInRed()


        btn_create_group_ok.setOnClickListener {

            /*if (input_create_group_gname != null){

                Toast.makeText(this,"Empty",Toast.LENGTH_SHORT).show()
            } else {

                Toast.makeText(this,"Not empty",Toast.LENGTH_SHORT).show()
            }*/

            if (tip_create_group_gname.editText?.length()!! >= 1){

                //showLoader()
                dismissKeyboard()

                groupsViewModel.createGroup(Id?.let { it1 -> AddGroupContact(it1,tip_create_group_gname.editText?.text.toString())})
                dialog.dismiss()

                //onResume()

                //notifyDataSetChange()

                //groupAdapter.notifyDataSetChanged()

            } else {

                tip_create_group_gname.editText?.setError(getString(R.string.group_name_error))
                input_create_group_gname.requestFocus()
            }

            /*if(!TextUtils.isEmpty(tip_create_group_gname?.editText?.text.toString())){

                // dialog.dismiss()
                Toast.makeText(this,"Condition Running",Toast.LENGTH_SHORT).show()

            }else if(TextUtils.isEmpty(tip_create_group_gname?.editText?.text.toString())){

                Toast.makeText(this,"Came to error",Toast.LENGTH_SHORT).show()

                tip_create_group_gname.editText?.error = "UserName is Required"
                tip_create_group_gname.editText?.requestFocus()
            }*/

        }

        btn_create_group_cancel.setOnClickListener {
            dialog.dismiss()
            dismissKeyboard()
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

    fun notifyDataSetChange(){

        groupsRecyclerView = findViewById(R.id.groupsRecyclerView)
        groupsRecyclerView.layoutManager = LinearLayoutManager(this)


        groupsViewModel.getGroupsList()
        //groupAdapter.notifyDataSetChanged()

        //groupsRecyclerView.adapter?.notifyDataSetChanged()

        groupsViewModel.responseLiveData.observe(this, Observer{ response ->

            when(response.status){
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()

                    groupsViewModel.response = response.data as List<Group>

                    groupList = arrayListOf()
                    groupList += response.data

                    //groupAdapter = AddToGroupsAdapter(groupList)

                    if(groupList.size <=5) {
                        val params = groupsRecyclerView.getLayoutParams() as ConstraintLayout.LayoutParams
                        params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                        groupsRecyclerView.setLayoutParams(params)
                    }
                    groupsRecyclerView.adapter = Id?.let { AddToGroupsAdapter(groupList,groupsViewModel, it) }
                    //groupAdapter.notifyDataSetChanged()

                    groupsRecyclerView.adapter?.notifyDataSetChanged()

                    println(groupList)

                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })

        //groupsRecyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onClick(v: View?) {

    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v: View? = getCurrentFocus()
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm?.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
}
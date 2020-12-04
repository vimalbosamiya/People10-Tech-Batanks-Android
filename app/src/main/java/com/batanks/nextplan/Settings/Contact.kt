package com.batanks.nextplan.Settings

import android.Manifest
import android.app.Dialog
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.view.MotionEvent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.Settings.Adapters.ContactsAdapter_Settings
import com.batanks.nextplan.Settings.Adapters.GroupsAdapter_Settings
import com.batanks.nextplan.arch.BaseAppCompatActivity
import com.batanks.nextplan.arch.response.Status
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.common.getLoadingDialog
import com.batanks.nextplan.home.fragment.contacts.ContactsAdapter
import com.batanks.nextplan.home.fragment.contacts.ContactsModel
import com.batanks.nextplan.home.viewmodel.ContactsViewModel
import com.batanks.nextplan.home.viewmodel.GroupsViewModel
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.search.adapters.AddToGroupsAdapter
import com.batanks.nextplan.swagger.api.ContactsAPI
import com.batanks.nextplan.swagger.api.GroupsAPI
import com.batanks.nextplan.swagger.model.ContactsList
import com.batanks.nextplan.swagger.model.Group
import com.batanks.nextplan.swagger.model.InlineResponse2001
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_contact.*

class Contact : BaseAppCompatActivity() {

    lateinit var rv_settings_contacts: RecyclerView
    lateinit var contactList : ArrayList<ContactsList>
    lateinit var adapter : ContactsAdapter_Settings

    lateinit var rv_settings_groups : RecyclerView
    lateinit var groupList : List<Group>
    lateinit var groups_adapter : GroupsAdapter_Settings

    lateinit var rv_users: RecyclerView

    lateinit var rv_phone_contacts: RecyclerView
    var phoneContactsList : ArrayList<ContactsList>?  = null

    lateinit var rl_settings_create_new_groups : RelativeLayout

    companion object {
        val PERMISSIONS_REQUEST_READ_CONTACTS = 100
    }

    private val groupsViewModel: GroupsViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            RetrofitClient.getRetrofitInstance(this)?.create(GroupsAPI::class.java)?.let {
                GroupsViewModel(it)
            }
        }).get(GroupsViewModel::class.java)
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
        setContentView(R.layout.activity_contact)

        rv_settings_contacts = findViewById(R.id.rv_settings_contacts)
        rv_settings_contacts.layoutManager = LinearLayoutManager(this)

        rv_settings_groups = findViewById(R.id.rv_settings_groups)
        rv_settings_groups.layoutManager = LinearLayoutManager(this)

        rv_users = findViewById(R.id.rv_users)
        rv_users.layoutManager = LinearLayoutManager(this)

        rv_phone_contacts  = findViewById(R.id.rv_phone_contacts)
        rv_phone_contacts.layoutManager = LinearLayoutManager(this)

        rl_settings_create_new_groups = findViewById(R.id.rl_settings_create_new_groups)

        loadingDialog = this.getLoadingDialog(0, R.string.loading_list_please_wait, theme = R.style.AlertDialogCustom)

        groupsViewModel.getGroupsList()
        contactsViewModel.getContactsList()

        contactsViewModel.responseLiveData.observe(this, Observer{ response ->

            when(response.status){
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()

                    contactsViewModel.response = response.data as InlineResponse2001

                    contactList = contactsViewModel.response!!.results

                    adapter = ContactsAdapter_Settings(contactList,contactsViewModel)

                    if(contactList.size <=5) {
                        val params = rv_settings_contacts.getLayoutParams() as ConstraintLayout.LayoutParams
                        params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                        rv_settings_contacts.setLayoutParams(params)
                    }
                    rv_settings_contacts.adapter = adapter

                    println(contactList)

                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })

        groupsViewModel.responseLiveData.observe(this, Observer{ response ->

            when(response.status){
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()

                    groupsViewModel.response = response.data as List<Group>

                    groupList = response.data

                    groups_adapter = GroupsAdapter_Settings(groupList)

                    if(groupList.size <=5) {
                        val params = rv_settings_groups.getLayoutParams() as ConstraintLayout.LayoutParams
                        params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                        rv_settings_groups.setLayoutParams(params)
                    }
                    rv_settings_groups.adapter = groups_adapter

                    println(groupList)

                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })


        img_settings_contacts_contacts_downarrow.setOnClickListener {

            //settings_contacts_list_section2.visibility = VISIBLE
            txt_settings_contacts_contacts_alphabetical.visibility = VISIBLE
            img_settings_contacts_contacts_filter.visibility = VISIBLE
            view_seperator_settings_contactlist.visibility = VISIBLE
            rv_settings_contacts.visibility = VISIBLE
            img_settings_contacts_contacts_downarrow.visibility = GONE
            img_settings_contacts_contacts_uparrow.visibility = VISIBLE
        }

        img_settings_contacts_contacts_uparrow.setOnClickListener {

            //settings_contacts_list_section2.visibility = GONE
            txt_settings_contacts_contacts_alphabetical.visibility = GONE
            img_settings_contacts_contacts_filter.visibility = GONE
            view_seperator_settings_contactlist.visibility = GONE
            rv_settings_contacts.visibility = GONE
            img_settings_contacts_contacts_downarrow.visibility = VISIBLE
            img_settings_contacts_contacts_uparrow.visibility = GONE
        }

        img_settings_contacts_groups_downarrow.setOnClickListener{

            rv_settings_groups.visibility = VISIBLE
            rl_settings_create_new_groups.visibility = VISIBLE
            img_settings_contacts_groups_downarrow.visibility = GONE
            img_settings_contacts_groups_uparrow.visibility = VISIBLE
        }

        img_settings_contacts_groups_uparrow.setOnClickListener{

            rv_settings_groups.visibility = GONE
            rl_settings_create_new_groups.visibility = GONE
            img_settings_contacts_groups_downarrow.visibility = VISIBLE
            img_settings_contacts_groups_uparrow.visibility = GONE
        }

        img_users_downarrow.setOnClickListener {

            rv_users.visibility = VISIBLE
            img_users_downarrow.visibility = GONE
            img_users_uparrow.visibility = VISIBLE

        }

        img_users_uparrow.setOnClickListener {

            rv_users.visibility = GONE
            img_users_downarrow.visibility = VISIBLE
            img_users_uparrow.visibility = GONE

        }

        img_phone_contacts_downarrow.setOnClickListener {

            rv_phone_contacts.visibility = VISIBLE
            img_phone_contacts_downarrow.visibility = GONE
            img_phone_contacts_uparrow.visibility = VISIBLE
            loadContacts()
        }

        img_phone_contacts_uparrow.setOnClickListener {

            rv_phone_contacts.visibility = GONE
            img_phone_contacts_downarrow.visibility = VISIBLE
            img_phone_contacts_uparrow.visibility = GONE
        }

      /*  img_settings_contacts_contacts_downarrow.setOnClickListener(View.OnClickListener {
            if(settings_contacts_list_section2.visibility == View.VISIBLE){
                settings_contacts_list_section2.visibility = View.GONE
            } else {
                settings_contacts_list_section2.visibility = View.VISIBLE
                rv_settings_groups.visibility = View.GONE
                rl_settings_create_new_groups.visibility = View.GONE
            }
        })
        img_settings_contacts_groups_downarrow.setOnClickListener(View.OnClickListener {
            if(rv_settings_groups.visibility == View.VISIBLE){
                rv_settings_groups.visibility = View.GONE
                rl_settings_create_new_groups.visibility = View.GONE
            } else {
                rv_settings_groups.visibility = View.VISIBLE
                rl_settings_create_new_groups.visibility = View.VISIBLE
                settings_contacts_list_section2.visibility = View.GONE
            }
        })*/


        rl_settings_create_new_groups.setOnClickListener(View.OnClickListener {
            showDialog()
        })

        img_contacts_close.setOnClickListener {

            finish()
        }

        //setUpDummyData()
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

        btn_create_group_cancel.setOnClickListener {

            dialog.dismiss()
        }

        btn_create_group_ok.setOnClickListener {

            if (tip_create_group_gname.editText?.length()!! >= 1){

                dialog.dismiss()

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

    private fun loadContacts() {

        var builder = StringBuilder()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && this?.let {
                    ContextCompat.checkSelfPermission(it,
                            Manifest.permission.READ_CONTACTS)
                } != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS),
                    PERMISSIONS_REQUEST_READ_CONTACTS)
            //callback onRequestPermissionsResult
        } else {

            phoneContactsList = getContacts()

            if(phoneContactsList!!.size <=5) {
                val params = rv_phone_contacts.getLayoutParams() as ConstraintLayout.LayoutParams
                params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                rv_phone_contacts.setLayoutParams(params)
            }

            rv_phone_contacts.adapter = ContactsAdapter(phoneContactsList!!)

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                loadContacts()

                // println(phContactsList)

            } else {
                //toast("Permission must be granted in order to display contacts information")
                Toast.makeText(this,"Permission must be granted in order to display contacts information", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getContacts(): ArrayList<ContactsList>{

        var phcontacts : ArrayList<ContactsList>? = arrayListOf()
        var test : ArrayList<String>? = null
        // var phonecontacts : ArrayList<EventInvitationPhone>? = null

        //val builder = StringBuilder()
        val resolver: ContentResolver? = this?.contentResolver
        val cursor = resolver?.query(ContactsContract.Contacts.CONTENT_URI, null, null, null,
                null)

        if (cursor != null) {

            if (cursor.count > 0) {

                while (cursor.moveToNext()) {

                    val id = cursor?.getColumnIndex(ContactsContract.Contacts._ID)?.let { cursor.getString(it) }

                    val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                    val phoneNumber = (cursor.getString(
                            cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))).toLong()

                    if (phoneNumber > 0) {
                        val cursorPhone = this?.contentResolver?.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", arrayOf(id), null)

                        if (cursorPhone != null) {
                            if(cursorPhone.count > 0) {
                                while (cursorPhone.moveToNext()) {
                                    val phoneNumValue = cursorPhone.getString(
                                            cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                                    /*builder.append("Contact: ").append(name).append(", Phone Number: ").append(
                                            phoneNumValue).append("\n\n")*/

                                    //var contactList : ContactsList = ContactsList(name,"",0,"",false)

                                    phcontacts?.add(ContactsList(0,"","",name,"",phoneNumber,"",false))

                                    println(phcontacts)

                                    //phcontacts?.add(contactList)

                                }
                            }
                        }
                        if (cursorPhone != null) {
                            cursorPhone.close()
                        }
                    }
                }
            } else {
                //toast("No contacts available!")

                Toast.makeText(this,"No contacts available", Toast.LENGTH_LONG).show()
            }
        }
        if (cursor != null) {
            cursor.close()
        }
        return phcontacts!!
    }

    /*private fun setUpDummyData(){
        var list: ArrayList<ContactsModel> = ArrayList<ContactsModel>()
        list.add(ContactsModel("User 1", "12345" , false))
        list.add(ContactsModel("User 2", "12345", false))
        list.add(ContactsModel("User 3", "12345", false))
        list.add(ContactsModel("User 4", "12345", false))
        list.add(ContactsModel("User 5", "12345", false))
        list.add(ContactsModel("User 6", "12345", false))
        list.add(ContactsModel("User 7", "12345", false))
        list.add(ContactsModel("User 8", "12345", false))
        list.add(ContactsModel("User 9", "12345", false))
        adapter = ContactsAdapter_Settings(list)
        rv_settings_contacts.adapter = adapter
        rv_settings_groups.adapter = adapter

        var groups_list: ArrayList<ContactsModel> = ArrayList<ContactsModel>()
        groups_list.add(ContactsModel("Group 1", "12345" , false))
        groups_list.add(ContactsModel("Group 2", "12345", false))
        groups_list.add(ContactsModel("Group 3", "12345", false))
        groups_list.add(ContactsModel("Group 4", "12345", false))
        groups_list.add(ContactsModel("Group 5", "12345", false))
        groups_list.add(ContactsModel("Group 6", "12345", false))
        groups_list.add(ContactsModel("Group 7", "12345", false))
        groups_list.add(ContactsModel("Group 8", "12345", false))
        groups_list.add(ContactsModel("Group 9", "12345", false))
        groups_adapter = GroupsAdapter_Settings(groups_list)
        rv_settings_groups.adapter = groups_adapter
        //loadContacts();
    }*/
}

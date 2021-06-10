package com.batanks.nextplan.Settings

import android.Manifest
import android.app.Dialog
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
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
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.Settings.Adapters.ContactsAdapter_Settings
import com.batanks.nextplan.Settings.Adapters.GroupsAdapter_Settings
import com.batanks.nextplan.Settings.Adapters.UsersAdapter
import com.batanks.nextplan.Settings.viewmodel.GroupListViewModel
import com.batanks.nextplan.arch.BaseAppCompatActivity
import com.batanks.nextplan.arch.response.Status
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.common.dismissKeyboard
import com.batanks.nextplan.common.getLoadingDialog
import com.batanks.nextplan.eventdetails.viewmodel.AddContactViewModel
import com.batanks.nextplan.home.HomePlanPreview
import com.batanks.nextplan.home.fragment.contacts.ContactsAdapter
import com.batanks.nextplan.home.fragment.contacts.ContactsModel
import com.batanks.nextplan.home.markRequiredInRed
import com.batanks.nextplan.home.viewmodel.ContactsViewModel
import com.batanks.nextplan.home.viewmodel.GroupsViewModel
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.search.adapters.AddToGroupsAdapter
import com.batanks.nextplan.search.viewmodel.SearchViewModel
import com.batanks.nextplan.swagger.api.ContactsAPI
import com.batanks.nextplan.swagger.api.GroupsAPI
import com.batanks.nextplan.swagger.api.SearchAPI
import com.batanks.nextplan.swagger.model.*
import com.batanks.nextplan.swagger.model.Group
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_contact.*
import kotlinx.android.synthetic.main.edit_propriety.*
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.CoroutineContext

class Contact : BaseAppCompatActivity(), CoroutineScope {

    lateinit var rv_settings_contacts: RecyclerView
    lateinit var contactList : ArrayList<ContactsList>
    var contactListSearched : ArrayList<ContactsList> = arrayListOf()
    lateinit var adapter : ContactsAdapter_Settings

    lateinit var rv_settings_groups : RecyclerView
    var groupList : ArrayList<Group> = arrayListOf()
    var groupListSearched : ArrayList<Group> = arrayListOf()
    lateinit var groups_adapter : GroupsAdapter_Settings

    lateinit var rv_users: RecyclerView
    lateinit var usersList : ArrayList<ContactsList>
    var usersSearchResponse : UserSearch? = null
    var usersListSearched : ArrayList<ContactsList> = arrayListOf()
    lateinit var users_adapter : UsersAdapter

    //lateinit var rv_phone_contacts: RecyclerView
    var phoneContactsList : ArrayList<PhoneContacts>?  = null

    //var renamedGroup : Group? = null

    lateinit var rl_settings_create_new_groups : RelativeLayout

    private var sort : Int = 0
    private var sortFilter : String? = null

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

    private val searchViewModel: SearchViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            RetrofitClient.getRetrofitInstance(this)?.create(SearchAPI::class.java)?.let {
                SearchViewModel(it)
            }
        }).get(SearchViewModel::class.java)
    }

    private val addContactViewModel: AddContactViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            RetrofitClient.getRetrofitInstance(this)?.create(GroupsAPI::class.java)?.let {
                AddContactViewModel(it)
            }
        }).get(AddContactViewModel::class.java)
    }

    private var textChangedJob: Job? = null
    private lateinit var textListener: TextWatcher
    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        rv_settings_contacts = findViewById(R.id.rv_settings_contacts)
        rv_settings_contacts.layoutManager = LinearLayoutManager(this)

        rv_settings_groups = findViewById(R.id.rv_settings_groups)
        rv_settings_groups.layoutManager = LinearLayoutManager(this)

        rv_users = findViewById(R.id.rv_users)
        rv_users.layoutManager = LinearLayoutManager(this)

        /*rv_phone_contacts  = findViewById(R.id.rv_phone_contacts)
        rv_phone_contacts.layoutManager = LinearLayoutManager(this)*/

        rl_settings_create_new_groups = findViewById(R.id.rl_settings_create_new_groups)

        //loadingDialog = this.getLoadingDialog(0, R.string.loading_list_please_wait, theme = R.style.AlertDialogCustom)

        job = Job()

        textListener = object : TextWatcher {
            private var searchFor = "A" // Or view.editText.text.toString()

            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString().trim()
                if (searchText != searchFor) {
                    searchFor = searchText

                    //users_adapter.filter.filter(searchText)

                    textChangedJob?.cancel()
                    textChangedJob = launch(Dispatchers.Main) {
                        //delay(2000L)
                        if (searchText == searchFor) {

                            contactListSearched.clear()
                            groupListSearched.clear()
                            usersListSearched.clear()

                            sortFilter = searchText

                            if (!searchText.isNullOrEmpty()){

                                for(item in contactList){

                                    if (item.username!!.contains(searchText, ignoreCase = true)){

                                        contactListSearched.add(item)
                                    }
                                }

                                if (contactListSearched.size > 0){

                                    if (sort == 0){

                                        contactListSearched.sortBy { it.username?.toLowerCase(Locale.ROOT) }

                                        sort = 1
                                    }
                                }

                                /* if(contactListSearched.size <=5) {
                                    val params = rv_settings_contacts.getLayoutParams() as ConstraintLayout.LayoutParams
                                    params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                                    rv_settings_contacts.setLayoutParams(params)
                                }*/

                                adapter = ContactsAdapter_Settings(contactListSearched,contactsViewModel)
                                rv_settings_contacts.adapter = adapter

                                if(contactListSearched.size > 0){

                                    showContactsRecyclerView()

                                } else {

                                    hideContactsRecyclerView()
                                }

                                for(item in groupList){

                                    if (item.name.contains(searchText, ignoreCase = true)){

                                        groupListSearched.add(item)
                                    }
                                }

                               /* if(groupListSearched.size <=5) {
                                    val params = rv_settings_groups.getLayoutParams() as ConstraintLayout.LayoutParams
                                    params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                                    rv_settings_groups.setLayoutParams(params)
                                }*/

                                groups_adapter = GroupsAdapter_Settings(groupListSearched,groupListViewModel)
                                rv_settings_groups.adapter = groups_adapter

                                if (groupListSearched.size > 0){

                                    showGroupsRecyclerView()

                                }else {

                                    hideGroupsRecyclerView()
                                }


                                for(item in usersList){

                                    if (item.username!!.contains(searchText, ignoreCase = true)){

                                        usersListSearched.add(item)

                                    } else {

                                        usersListSearched.remove(item)
                                    }
                                }

                             /*   if(usersListSearched.size <=5) {
                                    val params = rv_settings_groups.getLayoutParams() as ConstraintLayout.LayoutParams
                                    params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                                    rv_users.setLayoutParams(params)
                                }*/

                                users_adapter = UsersAdapter(usersListSearched,addContactViewModel)
                                rv_users.adapter = users_adapter
                                //users_adapter.notifyDataSetChanged()


                                if (usersListSearched.size > 0){

                                    showUsersRecyclerView()

                                }else {

                                    hideUsersRecyclerView()
                                }

                            }else if(searchText.isNullOrEmpty()){

                                contactListSearched.clear()
                                adapter = ContactsAdapter_Settings(contactList,contactsViewModel)
                                rv_settings_contacts.adapter = adapter
                                //hideContactsRecyclerView()

                                groupListSearched.clear()
                                groups_adapter = GroupsAdapter_Settings(groupList,groupListViewModel)
                                rv_settings_groups.adapter = groups_adapter
                                //hideGroupsRecyclerView()

                                usersListSearched.clear()
                                hideUsersRecyclerView()
                            }

                            //dismissKeyboard()
                        }
                    }
                }
            }
        }

        //settings_contactSearchEditText.

        groupsViewModel.getGroupsList()
        contactsViewModel.getContactsList()
        searchViewModel.getSearchUsers(" ")

        contactsViewModel.responseLiveData.observe(this, Observer{ response ->

            when(response.status){
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()

                    contactsViewModel.response = response.data as InlineResponse2001

                    contactList = contactsViewModel.response!!.results

                    println(contactList)

                    if(contactList.size <=5) {
                        val params = rv_settings_contacts.getLayoutParams() as ConstraintLayout.LayoutParams
                        params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                        rv_settings_contacts.setLayoutParams(params)
                    }

                    if (sort == 0){

                        /*contactList.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER, { it.first_name }))

                        for(item in contactList){

                            println(item.first_name)
                        }*/

                        contactList.sortBy { it.username?.toLowerCase(Locale.ROOT) }

                        /*for(item in contactList){

                            println(item.first_name)
                        }*/

                        sort = 1
                    }

                    adapter = ContactsAdapter_Settings(contactList,contactsViewModel)

                    /*if(contactList.size <=5) {
                        val params = rv_settings_contacts.getLayoutParams() as ConstraintLayout.LayoutParams
                        params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                        rv_settings_contacts.setLayoutParams(params)
                    }*/

                    rv_settings_contacts.adapter = adapter

                    //println(contactList)
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

                    contactsViewModel.getContactsList()
                }
                Status.ERROR -> {
                    hideLoader()

                    //contactsViewModel.getContactsList()
                    showMessage(response.error?.message.toString())
                    //println("from contacts delete API Error")
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

                    groupsViewModel.response = response.data as ArrayList<Group>

                    groupList = response.data

                    groups_adapter = GroupsAdapter_Settings(groupList,groupListViewModel)

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

        groupsViewModel.responseLiveDataCreateGroup.observe(this, Observer{ response ->

            when(response.status){
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()

                    Toast.makeText(this,getString(R.string.group_created),Toast.LENGTH_SHORT).show()

                    groupsViewModel.getGroupsList()
                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })

        groupListViewModel.responseLiveData2.observe(this, Observer{ response ->

                    when(response.status){

                        Status.LOADING -> {
                            showLoader()
                        }

                        Status.SUCCESS -> {
                            hideLoader()

                            groupsViewModel.getGroupsList()
                        }
                        Status.ERROR -> {
                            hideLoader()
                            showMessage(response.error?.message.toString())
                        }
                    }
                })

        groupListViewModel.responseLiveData3.observe(this, Observer{ response ->

            when(response.status){
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()

                    groupsViewModel.getGroupsList()
                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())

                    //Toast.makeText(this, "Coming into error",Toast.LENGTH_LONG).show()

                    //println("Coming into error")
                    println(response.error?.message.toString())
                }
            }
        })

        searchViewModel.responseLiveDataUsers.observe(this, Observer{ response ->

            when(response.status){
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()

                    //searchViewModel.usersSearchResponse = response.data as UserSearch
                    //usersList = searchViewModel.usersSearchResponse!!.results
                    usersSearchResponse = response.data as UserSearch
                    usersList = usersSearchResponse!!.results

                   /* if (usersList.size > 0){

                        showUsersRecyclerView()

                    }else {

                        hideUsersRecyclerView()
                    }

                    users_adapter = UsersAdapter(usersList,addContactViewModel)
                    rv_users.adapter = users_adapter*/

                   /* users_adapter = UsersAdapter(usersList)
                    rv_users.adapter = users_adapter*/

                    println("Initial User List without search is : " + usersList)
                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())

                    //Toast.makeText(this, "Coming into error",Toast.LENGTH_LONG).show()

                    //println("Coming into error")
                    println(response.error?.message.toString())
                }
            }
        })

        addContactViewModel.responseLiveData.observe(this, Observer{ response ->

            when(response.status){
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()

                   /* if (sortFilter.isNullOrEmpty()){

                        contactsViewModel.getContactsList()
                    }*/

                    contactsViewModel.getContactsList()

                    //settings_contactSearchEditText.addTextChangedListener(textListener)

                   //contactsViewModel.getContactsList()

                    Toast.makeText(this,getString(R.string.contact_added),Toast.LENGTH_SHORT).show()

                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })

        /*settings_contactSearchTextField.editText?.doAfterTextChanged {

            searchViewModel.getSearchUsers(settings_contactSearchTextField.editText!!.text.toString())

            searchViewModel.responseLiveDataUsers.observe(this, Observer{ response ->

                when(response.status){
                    Status.LOADING -> {
                        showLoader()
                    }
                    Status.SUCCESS -> {
                        hideLoader()

                        searchViewModel.usersSearchResponse = response.data as UserSearch

                        usersList = searchViewModel.usersSearchResponse!!.results


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
        }*/

        img_settings_contacts_contacts_filter.setOnClickListener {

            if (sortFilter.isNullOrEmpty()){

                if (sort == 0){

                    contactList.sortBy { it.username?.toLowerCase(Locale.ROOT) }

                    sort = 1

                }else if (sort == 1){

                    contactList.sortByDescending { it.username?.toLowerCase(Locale.ROOT) }

                    sort = 0
                }

                adapter = ContactsAdapter_Settings(contactList,contactsViewModel)

                rv_settings_contacts.adapter = adapter

            } else if (sortFilter != null && contactListSearched.size > 0){

                if (sort == 0){

                    contactListSearched.sortBy { it.username?.toLowerCase(Locale.ROOT) }

                    sort = 1

                }else if (sort == 1){

                    contactListSearched.sortByDescending { it.username?.toLowerCase(Locale.ROOT) }

                    sort = 0
                }

                adapter = ContactsAdapter_Settings(contactListSearched,contactsViewModel)

                rv_settings_contacts.adapter = adapter
            }
        }

        img_settings_contacts_contacts_downarrow.setOnClickListener {

            if(contactList.size > 0){

                showContactsRecyclerView()

            } else {

                hideContactsRecyclerView()
            }
        }

        img_settings_contacts_contacts_uparrow.setOnClickListener {

            hideContactsRecyclerView()
        }

        img_settings_contacts_groups_downarrow.setOnClickListener{

            if (groupList.size > 0){

                showGroupsRecyclerView()

            }else {

                hideGroupsRecyclerView()
            }
        }

        img_settings_contacts_groups_uparrow.setOnClickListener{

            hideGroupsRecyclerView()
        }

        img_users_downarrow.setOnClickListener {

            if (usersListSearched.size > 0){

                if (usersListSearched.size > 0){

                    showUsersRecyclerView()

                }else {

                    hideUsersRecyclerView()
                }
            }
        }

        img_users_uparrow.setOnClickListener {

            hideUsersRecyclerView()
        }

        rl_settings_contacts_news_bottom.setOnClickListener {

            intent = Intent(this, HomePlanPreview :: class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.bottom_up, R.anim.nothing)
            finish()
        }

      /*  img_phone_contacts_downarrow.setOnClickListener {

            rv_phone_contacts.visibility = VISIBLE
            img_phone_contacts_downarrow.visibility = GONE
            img_phone_contacts_uparrow.visibility = VISIBLE
            loadContacts()
        }

        img_phone_contacts_uparrow.setOnClickListener {

            rv_phone_contacts.visibility = GONE
            img_phone_contacts_downarrow.visibility = VISIBLE
            img_phone_contacts_uparrow.visibility = GONE
        }*/

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

    /*fun TextInputLayout.markRequiredInRed() {

        hint = buildSpannedString {
            append(hint)
            color(Color.RED) { append(" *") }
        }
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

        btn_create_group_cancel.setOnClickListener {

            dialog.dismiss()
        }

        btn_create_group_ok.setOnClickListener {

            if (tip_create_group_gname.editText?.length()!! >= 1){

                groupsViewModel.createGroupWithoutId(GroupWithoutId(tip_create_group_gname.editText?.text.toString()))

                dialog.dismiss()

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


    private fun showContactsRecyclerView(){

            txt_settings_contacts_contacts_alphabetical.visibility = VISIBLE
            img_settings_contacts_contacts_filter.visibility = VISIBLE
            view_seperator_settings_contactlist.visibility = VISIBLE
            rv_settings_contacts.visibility = VISIBLE
            img_settings_contacts_contacts_downarrow.visibility = GONE
            img_settings_contacts_contacts_uparrow.visibility = VISIBLE
    }

    private fun hideContactsRecyclerView(){

        //settings_contacts_list_section2.visibility = GONE
        txt_settings_contacts_contacts_alphabetical.visibility = GONE
        img_settings_contacts_contacts_filter.visibility = GONE
        view_seperator_settings_contactlist.visibility = GONE
        rv_settings_contacts.visibility = GONE
        img_settings_contacts_contacts_downarrow.visibility = VISIBLE
        img_settings_contacts_contacts_uparrow.visibility = GONE
    }

    private fun showGroupsRecyclerView(){

        rv_settings_groups.visibility = VISIBLE
        rl_settings_create_new_groups.visibility = VISIBLE
        img_settings_contacts_groups_downarrow.visibility = GONE
        img_settings_contacts_groups_uparrow.visibility = VISIBLE
    }

    private fun hideGroupsRecyclerView(){

        rv_settings_groups.visibility = GONE
        rl_settings_create_new_groups.visibility = GONE
        img_settings_contacts_groups_downarrow.visibility = VISIBLE
        img_settings_contacts_groups_uparrow.visibility = GONE
    }

    private fun showUsersRecyclerView(){

        rv_users.visibility = VISIBLE
        img_users_downarrow.visibility = GONE
        img_users_uparrow.visibility = VISIBLE
    }

    private fun hideUsersRecyclerView(){

        rv_users.visibility = GONE
        img_users_downarrow.visibility = VISIBLE
        img_users_uparrow.visibility = GONE
    }

    override fun onResume() {
        super.onResume()
        settings_contactSearchEditText.addTextChangedListener(textListener)
    }

    override fun onPause() {
        settings_contactSearchEditText.removeTextChangedListener(textListener)
        super.onPause()
    }

    override fun onDestroy() {
        textChangedJob?.cancel()
        super.onDestroy()
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

/*    private fun loadContacts() {

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

    private fun getContacts(): ArrayList<PhoneContacts>{

        var phcontacts : ArrayList<PhoneContacts>? = arrayListOf()

        val resolver: ContentResolver? = this?.contentResolver

        val cursor = resolver?.query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null)

        if (cursor != null) {

            if (cursor.count > 0) {

                while (cursor.moveToNext()) {

                    val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))

                    val cursorPhone = this?.contentResolver?.query(
                            ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                            arrayOf(id), null)

                    if (cursorPhone != null) {

                        if (cursor.count > 0) {

                            while (cursorPhone.moveToNext()){

                                val name = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))

                                val email = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA))

                                if (email.isNullOrEmpty() == false){

                                    phcontacts?.add(PhoneContacts(name,email))

                                    println(name)
                                    println(email)

                                }
                            }
                        } else  {

                            println("No contacts")

                            Toast.makeText(this,"No contacts available with email",Toast.LENGTH_LONG).show()
                        }
                    }

                    if (cursorPhone != null) {
                        cursorPhone.close()
                    }

                }
            }
        }

        if (cursor != null) {
            cursor.close()
        }

        return phcontacts!!

        *//*val resolver: ContentResolver? = this?.contentResolver
        val cursor = resolver?.query(ContactsContract.Contacts.CONTENT_URI, null, null, null,
                null)

        if (cursor != null) {

            if (cursor.count > 0) {

                while (cursor.moveToNext()) {

                    val id = cursor?.getColumnIndex(ContactsContract.Contacts._ID)?.let { cursor.getString(it)}

                    val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                    val phoneNumber = (cursor.getString(
                            cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))).toLong()



                    while (cursor1?.moveToNext()!!){

                        val  username = cursor1.getString(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))

                        val useremail = cursor1.getString(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));

                        println(username)
                        println(useremail)

                    }



                    if (phoneNumber > 0) {
                        val cursorPhone = this?.contentResolver?.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", arrayOf(id), null)

                        if (cursorPhone != null) {
                            if(cursorPhone.count > 0) {
                                while (cursorPhone.moveToNext()) {
                                    val phoneNumValue = cursorPhone.getString(
                                            cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                                    *//**//*builder.append("Contact: ").append(name).append(", Phone Number: ").append(
                                            phoneNumValue).append("\n\n")*//**//*

                                    //var contactList : ContactsList = ContactsList(name,"",0,"",false)

                                    phcontacts?.add(PhoneContacts()

                                    //println(phcontacts)

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
        }*//*


    }*/


}

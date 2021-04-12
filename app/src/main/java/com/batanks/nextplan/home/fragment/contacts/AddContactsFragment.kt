package com.batanks.nextplan.home.fragment.contacts

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseDialogFragment
import com.batanks.nextplan.arch.response.Status
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.common.getLoadingDialog
import com.batanks.nextplan.home.viewmodel.ContactsViewModel
import com.batanks.nextplan.home.viewmodel.GroupsViewModel
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.search.viewmodel.SearchViewModel
import com.batanks.nextplan.swagger.api.ContactsAPI
import com.batanks.nextplan.swagger.api.GroupsAPI
import com.batanks.nextplan.swagger.api.SearchAPI
import com.batanks.nextplan.swagger.model.*
import kotlinx.android.synthetic.main.add_contact_fragment.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class AddContactsFragment(private val listner : AddContactsFragmentListner/*, private val participants : ArrayList<Guests>*/) : BaseDialogFragment(), FriendsAdapter.friendsAdapterRecyclerViewCallBack
                                                                                                  , GroupsAdapter.groupsAdapterRecyclerViewCallBack
                                                                                                  , UsersAdapter.usersAdapterRecyclerViewCallBack, CoroutineScope{

    lateinit var phone_contacts_RecyclerView :RecyclerView
    var phoneContactsList : ArrayList<PhoneContacts>?  = null
    lateinit var phoneAdapter : ContactsAdapter

    protected lateinit var rootView: View
   // lateinit var recyclerView: RecyclerView
    lateinit var add_contacts_contacts_RecyclerView : RecyclerView
    lateinit var add_contacts_groups_RecyclerView : RecyclerView
    lateinit var add_contacts_users_RecyclerView :RecyclerView

    var participantsList : ArrayList<ActivityParticipants> = arrayListOf()
    var finalParticipantsList : ArrayList<ActivityParticipants> = arrayListOf()
    var participantsListContacts : ArrayList<ActivityParticipants> = arrayListOf()
    var participantsListGroups : ArrayList<ActivityParticipants> = arrayListOf()
    var participantsListUsers : ArrayList<ActivityParticipants> = arrayListOf()

    lateinit var select_contacts_phone_checkbox : CheckBox
    /*lateinit var select_contacts_contacts_checkbox : CheckBox
    lateinit var select_contacts_group_checkbox : CheckBox
    lateinit var select_contacts_users_checkbox : CheckBox*/

    lateinit var add_contacts_ok : Button
    lateinit var add_contacts_cancel : Button

    lateinit var adapter : FriendsAdapter
    lateinit var groupAdapter : GroupsAdapter

    companion object {
        val PERMISSIONS_REQUEST_READ_CONTACTS = 100

        var participants : ArrayList<ActivityParticipants> = arrayListOf()
    }

    lateinit var contactList : ArrayList<ContactsList>
    var contactListSearched : ArrayList<ContactsList> = arrayListOf()
    lateinit var groupList : List<Group>
    var groupListSearched : ArrayList<Group> = arrayListOf()
    var usersList : ArrayList<UserSearchResults> = arrayListOf()

    var phContactsList : ArrayList<EventInvitationPhone>?  = null

    var phoneNames : ArrayList<String>? = null

    private var usersSearchResponse : UserSearch? = null

    private val contactsViewModel: ContactsViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            getContext()?.let {
                RetrofitClient.getRetrofitInstance(it)?.create(ContactsAPI::class.java)?.let {
                    ContactsViewModel(it)
                }
            }
        }).get(ContactsViewModel::class.java)
    }

    private val groupsViewModel: GroupsViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            getContext()?.let {
                RetrofitClient.getRetrofitInstance(it)?.create(GroupsAPI::class.java)?.let {
                    GroupsViewModel(it)
                }
            }
        }).get(GroupsViewModel::class.java)
    }

    private val searchViewModel: SearchViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            getContext()?.let {
                RetrofitClient.getRetrofitInstance(it)?.create(SearchAPI::class.java)?.let {
                    SearchViewModel(it)
                }
            }
        }).get(SearchViewModel::class.java)
    }

    private var textChangedJob: Job? = null
    private lateinit var textListener: TextWatcher
    private lateinit var job: Job


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.App_DialogFragment_Theme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        rootView =  inflater.inflate(R.layout.add_contact_fragment, container, false)

        //initView()

        participants.clear()

        add_contacts_contacts_RecyclerView = rootView.findViewById(R.id.add_contacts_contacts_RecyclerView)
        add_contacts_contacts_RecyclerView.layoutManager = LinearLayoutManager(activity)

        add_contacts_groups_RecyclerView = rootView.findViewById(R.id.add_contacts_groups_RecyclerView);
        add_contacts_groups_RecyclerView.layoutManager = LinearLayoutManager(activity)

        add_contacts_users_RecyclerView = rootView.findViewById(R.id.add_contacts_users_RecyclerView);
        add_contacts_users_RecyclerView.layoutManager = LinearLayoutManager(activity)

        phone_contacts_RecyclerView = rootView.findViewById(R.id.phone_contacts_RecyclerView)
        phone_contacts_RecyclerView.layoutManager = LinearLayoutManager(activity)


        select_contacts_phone_checkbox = rootView.findViewById(R.id.select_contacts_phone_checkbox)

        add_contacts_ok = rootView.findViewById(R.id.add_contacts_ok)
        add_contacts_cancel = rootView.findViewById(R.id.add_contacts_cancel)


        /*select_contacts_phone_checkbox.setOnCheckedChangeListener { compoundButton, b ->
            if(!b){
                recyclerView.visibility = View.GONE
            } else {
                recyclerView.visibility = View.VISIBLE
                select_contacts_contacts_checkbox.isChecked = false
                select_contacts_group_checkbox.isChecked = false
                select_contacts_users_checkbox.isChecked = false
            }
        }
        select_contacts_contacts_checkbox.setOnCheckedChangeListener { compoundButton, b ->
            if(!b){
                add_contacts_contacts_RecyclerView.visibility = View.GONE
            } else {
                add_contacts_contacts_RecyclerView.visibility = View.VISIBLE
                select_contacts_phone_checkbox.isChecked = false
                select_contacts_group_checkbox.isChecked = false
                select_contacts_users_checkbox.isChecked = false
            }
        }
        select_contacts_group_checkbox.setOnCheckedChangeListener { compoundButton, b ->
            if(!b){
                add_contacts_groups_RecyclerView.visibility = View.GONE
            } else {
                add_contacts_groups_RecyclerView.visibility = View.VISIBLE
                select_contacts_phone_checkbox.isChecked = false
                select_contacts_contacts_checkbox.isChecked = false
                select_contacts_users_checkbox.isChecked = false
            }
        }
        select_contacts_users_checkbox.setOnCheckedChangeListener { compoundButton, b ->
            if(!b){
                add_contacts_users_RecyclerView.visibility = View.GONE
            } else {
                add_contacts_users_RecyclerView.visibility = View.VISIBLE
                select_contacts_phone_checkbox.isChecked = false
                select_contacts_contacts_checkbox.isChecked = false
                select_contacts_group_checkbox.isChecked = false
            }
        }*/

        add_contacts_cancel.setOnClickListener(View.OnClickListener {
            dismiss()
        })

        add_contacts_ok.setOnClickListener(View.OnClickListener {

            for (item in participantsListContacts){

                if (!finalParticipantsList.contains(item)){

                    finalParticipantsList.add(item)
                }
            }

            for (item in participantsListGroups){

                if (!finalParticipantsList.contains(item)){

                    finalParticipantsList.add(item)
                }
            }

            for (item in participantsListUsers){

                if (!finalParticipantsList.contains(item)){

                    finalParticipantsList.add(item)
                }
            }

            listner.AddSelectedParticipants(finalParticipantsList)
            dismiss()
        })

        //loadContacts()

        job = Job()

        textListener = object : TextWatcher {
            private var searchFor = "A" // Or view.editText.text.toString()

            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString().trim()
                if (searchText != searchFor) {
                    searchFor = searchText

                    textChangedJob?.cancel()
                    textChangedJob = launch(Dispatchers.Main) {
                        //delay(2000L)
                        if (searchText == searchFor) {

                            contactListSearched.clear()
                            groupListSearched.clear()

                            searchViewModel.getSearchUsers(searchText)

                            if (!searchText.isNullOrEmpty()){

                                for(item in contactList){

                                    if (item.username.contains(searchText, ignoreCase = true)){

                                        contactListSearched.add(item)
                                    }
                                }

                                showFriendsRecyclerView(contactListSearched)

                                for(item in groupList){

                                    if (item.name.contains(searchText, ignoreCase = true)){

                                        groupListSearched.add(item)
                                    }
                                }

                                showGroupsRecyclerView(groupListSearched)

                            }else if(searchText.isNullOrEmpty()){

                                contactListSearched.clear()
                                contactsViewModel.getContactsList()
                                hideFriendsRecyclerView()

                                groupListSearched.clear()
                                groupsViewModel.getGroupsList()
                                hideGroupsRecyclerView()

                                usersList.clear()
                                hideUsersRecyclerView()
                            }

                            //view?.hideKeyboard()

                            println(searchText )
                            //loadList(searchText)
                        }
                    }
                }
            }
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        loadContacts()

        //loadingDialog = context?.getLoadingDialog(0, R.string.loading_list_please_wait, theme = R.style.AlertDialogCustom)

        contactsViewModel.getContactsList()
        groupsViewModel.getGroupsList()

        contactsViewModel.responseLiveData.observe(viewLifecycleOwner, Observer{ response ->

            when(response.status){
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()

                    contactsViewModel.response = response.data as InlineResponse2001

                    contactList = contactsViewModel.response!!.results

                    adapter = FriendsAdapter(this,contactList)

                    if(contactList.size <=5) {
                        val params = add_contacts_contacts_RecyclerView.getLayoutParams() as ConstraintLayout.LayoutParams
                        params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                        add_contacts_contacts_RecyclerView.setLayoutParams(params)
                    }
                    add_contacts_contacts_RecyclerView.adapter = adapter

                    //println(contactList)

                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })

        groupsViewModel.responseLiveData.observe(viewLifecycleOwner, Observer{ response ->

            when(response.status){
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()

                    groupsViewModel.response = response.data as List<Group>

                    groupList = response.data

                    groupAdapter = GroupsAdapter(this,groupList)

                    if(groupList.size <=5) {
                        val params = add_contacts_groups_RecyclerView.getLayoutParams() as ConstraintLayout.LayoutParams
                        params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                        add_contacts_groups_RecyclerView.setLayoutParams(params)
                    }
                    add_contacts_groups_RecyclerView.adapter = groupAdapter

                    println(groupList)

                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })

        searchViewModel.responseLiveDataUsers.observe(viewLifecycleOwner, Observer{ response ->

            when(response.status){
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()

                    usersSearchResponse = response.data as UserSearch

                    usersList = usersSearchResponse!!.results

                    if (usersList.size > 0){

                        showUsersRecyclerView()

                    } else {

                        hideUsersRecyclerView()
                    }

                    add_contacts_users_RecyclerView.adapter = UsersAdapter(this, usersList)
                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })

        img_contacts_down_arrow_icon.setOnClickListener {

            add_contacts_contacts_RecyclerView.visibility = VISIBLE

            img_contacts_down_arrow_icon.visibility = GONE

            img_contacts_up_arrow_icon.visibility = VISIBLE
        }

        img_contacts_up_arrow_icon.setOnClickListener{

            add_contacts_contacts_RecyclerView.visibility = GONE

            img_contacts_up_arrow_icon.visibility = GONE

            img_contacts_down_arrow_icon.visibility = VISIBLE
        }

        img_group_down_arrow_icon.setOnClickListener {

            add_contacts_groups_RecyclerView.visibility = VISIBLE

            img_group_down_arrow_icon.visibility = GONE

            img_group_up_arrow_icon.visibility = VISIBLE
        }

        img_group_up_arrow_icon.setOnClickListener {

            add_contacts_groups_RecyclerView.visibility = GONE

            img_group_down_arrow_icon.visibility = VISIBLE

            img_group_up_arrow_icon.visibility = GONE
        }

        img_user_down_arrow_icon.setOnClickListener {

            if (usersList.size > 0){

                add_contacts_users_RecyclerView.visibility = VISIBLE

                img_user_down_arrow_icon.visibility = GONE

                img_user_up_arrow_icon.visibility = VISIBLE
            }
        }

        img_user_up_arrow_icon.setOnClickListener {

            add_contacts_users_RecyclerView.visibility = GONE

            img_user_up_arrow_icon.visibility = GONE

            img_user_down_arrow_icon.visibility = VISIBLE
        }

        img_ph_contacts_down_arrow_icon.setOnClickListener {

            phone_contacts_RecyclerView.visibility = VISIBLE

            img_ph_contacts_down_arrow_icon.visibility = GONE

            img_ph_contacts_up_arrow_icon.visibility = VISIBLE

            loadContacts()

            //loadContacts()

            //phoneNames = getContacts()

           // println(phoneNames)
            //phContactsList = getContacts()

            //println(phContactsList)

        }

        img_ph_contacts_up_arrow_icon.setOnClickListener {

            phone_contacts_RecyclerView.visibility = GONE

            img_ph_contacts_up_arrow_icon.visibility = GONE

            img_ph_contacts_down_arrow_icon.visibility = VISIBLE
        }

        view.setOnTouchListener(object : View.OnTouchListener {

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                if (event?.action == MotionEvent.ACTION_DOWN) {

                    val imm = v?.getContext()?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                    v.clearFocus()
                }

                return false
            }
        })
    }

    fun showFriendsRecyclerView( friendsList : ArrayList<ContactsList>){

        if (friendsList.size > 0){

            add_contacts_contacts_RecyclerView.visibility = VISIBLE

            img_contacts_down_arrow_icon.visibility = GONE

            img_contacts_up_arrow_icon.visibility = VISIBLE
        }

        adapter = FriendsAdapter(this,friendsList)
        add_contacts_contacts_RecyclerView.adapter = adapter
    }

    fun hideFriendsRecyclerView(){

        add_contacts_contacts_RecyclerView.visibility = GONE

        img_contacts_up_arrow_icon.visibility = GONE

        img_contacts_down_arrow_icon.visibility = VISIBLE
    }

    fun showGroupsRecyclerView(groupsList : ArrayList<Group>){

        if (groupsList.size > 0){

            add_contacts_groups_RecyclerView.visibility = VISIBLE

            img_group_down_arrow_icon.visibility = GONE

            img_group_up_arrow_icon.visibility = VISIBLE
        }

        groupAdapter = GroupsAdapter(this,groupsList)
        add_contacts_groups_RecyclerView.adapter = groupAdapter
    }

    fun hideGroupsRecyclerView(){

        add_contacts_groups_RecyclerView.visibility = GONE

        img_group_down_arrow_icon.visibility = VISIBLE

        img_group_up_arrow_icon.visibility = GONE
    }

    fun showUsersRecyclerView(){

        add_contacts_users_RecyclerView.visibility = VISIBLE

        img_user_down_arrow_icon.visibility = GONE

        img_user_up_arrow_icon.visibility = VISIBLE
    }

    fun hideUsersRecyclerView(){

        add_contacts_users_RecyclerView.visibility = GONE

        img_user_up_arrow_icon.visibility = GONE

        img_user_down_arrow_icon.visibility = VISIBLE
    }


    private fun loadContacts() {

        var builder = StringBuilder()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity?.let {
                    ContextCompat.checkSelfPermission(it,
                            Manifest.permission.READ_CONTACTS)
                } != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS),
                    PERMISSIONS_REQUEST_READ_CONTACTS)
            //callback onRequestPermissionsResult
        } else {

            phoneContactsList = getContacts()

            if(phoneContactsList!!.size <=5) {
                val params = phone_contacts_RecyclerView.getLayoutParams() as ConstraintLayout.LayoutParams
                params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                phone_contacts_RecyclerView.setLayoutParams(params)
            }

            phone_contacts_RecyclerView.adapter = ContactsAdapter(phoneContactsList!!)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                loadContacts()

               // println(phContactsList)

            } else {
                  //toast("Permission must be granted in order to display contacts information")
                Toast.makeText(context,"Permission must be granted in order to display contacts information",Toast.LENGTH_LONG).show()
            }
        }
    }

   /* private fun getContacts(): ArrayList<PhoneContacts>{

         var phcontacts : ArrayList<PhoneContacts>? = arrayListOf()
        var test : ArrayList<String>? = null
       // var phonecontacts : ArrayList<EventInvitationPhone>? = null

        //val builder = StringBuilder()
        val resolver: ContentResolver? = activity?.contentResolver
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
                        val cursorPhone = activity?.contentResolver?.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", arrayOf(id), null)

                        if (cursorPhone != null) {
                            if(cursorPhone.count > 0) {
                                while (cursorPhone.moveToNext()) {
                                    val phoneNumValue = cursorPhone.getString(
                                            cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                                    *//*builder.append("Contact: ").append(name).append(", Phone Number: ").append(
                                            phoneNumValue).append("\n\n")*//*

                                    //var contactList : ContactsList = ContactsList(name,"",0,"",false)

                                    //phcontacts?.add(ContactsList(0,"","",name,"",phoneNumber,"",false))

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

                Toast.makeText(context,"No contacts available",Toast.LENGTH_LONG).show()
            }
        }
        if (cursor != null) {
            cursor.close()
        }
        return phcontacts!!
    }*/

    private fun getContacts(): ArrayList<PhoneContacts>{

        var phcontacts : ArrayList<PhoneContacts>? = arrayListOf()

        val resolver: ContentResolver? = activity?.contentResolver

        val cursor = resolver?.query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null)

        if (cursor != null) {

            if (cursor.count > 0) {

                while (cursor.moveToNext()) {

                    val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))

                    val cursorPhone = activity?.contentResolver?.query(
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

                            Toast.makeText(activity,"No contacts available with email",Toast.LENGTH_LONG).show()
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

        /*val resolver: ContentResolver? = this?.contentResolver
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

                                    *//*builder.append("Contact: ").append(name).append(", Phone Number: ").append(
                                            phoneNumValue).append("\n\n")*//*

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
        }*/


    }


    /*private fun getContacts(): StringBuilder {

        val builder = StringBuilder()
        val resolver: ContentResolver? = activity?.contentResolver
        val cursor = resolver?.query(ContactsContract.Contacts.CONTENT_URI, null, null, null,
                null)

        if (cursor != null) {

            if (cursor.count > 0) {

                while (cursor.moveToNext()) {

                    val id = cursor?.getColumnIndex(ContactsContract.Contacts._ID)?.let { cursor.getString(it) }

                    val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                    val phoneNumber = (cursor.getString(
                            cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))).toInt()

                    if (phoneNumber > 0) {
                        val cursorPhone = activity?.contentResolver?.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", arrayOf(id), null)

                        if (cursorPhone != null) {
                            if(cursorPhone.count > 0) {
                                while (cursorPhone.moveToNext()) {
                                    val phoneNumValue = cursorPhone.getString(
                                            cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                                    builder.append("Contact: ")
                                            .append(name)
                                            .append(", Phone Number: ")
                                            .append(phoneNumValue)
                                            .append("\n\n")

                                    //println(name)

                                    //phContactsList?.add(ContactsList(name,"",0,"",false))

                                    //println(phContactsList?.toString())


                                    //Log.e("Name ===>",phoneNumValue);
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

                Toast.makeText(context,"No contacts available",Toast.LENGTH_LONG).show()
            }
        }
        if (cursor != null) {
            cursor.close()
        }
        return builder
    }*/

   /* private fun getContacts(): ArrayList<String>?{

        //var phcontacts : ArrayList<ContactsList>? = null
        // var phonecontacts : ArrayList<EventInvitationPhone>? = null

        var phName :ArrayList<String>? = null

        val builder = StringBuilder()
        val resolver: ContentResolver? = activity?.contentResolver
        val cursor = resolver?.query(ContactsContract.Contacts.CONTENT_URI, null, null, null,
                null)

        if (cursor != null) {

            if (cursor.count > 0) {

                while (cursor.moveToNext()) {

                    val id = cursor?.getColumnIndex(ContactsContract.Contacts._ID)?.let { cursor.getString(it) }

                    val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                    val phoneNumber = (cursor.getString(
                            cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))).toInt()

                    if (phoneNumber > 0) {
                        val cursorPhone = activity?.contentResolver?.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", arrayOf(id), null)

                        if (cursorPhone != null) {
                            if(cursorPhone.count > 0) {
                                while (cursorPhone.moveToNext()) {
                                    val phoneNumValue = cursorPhone.getString(
                                            cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                                    builder.append("Contact: ").append(name).append(", Phone Number: ").append(
                                            phoneNumValue).append("\n\n")

                                    phName?.add(name)

                                    *//*phoneContactsList?.add(ContactsList(name,"",0,"",false))

                                    phone_contacts_RecyclerView.adapter = phoneContactsList?.let { ContactsAdapter(it) }*//*

                                    // phContactsList?.add(EventInvitationPhone(name,phoneNumValue))

                                    // phonecontacts?.add(EventInvitationPhone(name,phoneNumValue))

                                    // println(phContactsList)

                                    //println(name +" "+ phoneNumValue)

                                    //phContactsList?.add(ContactsList(name,"",0,"",false))

                                    //phcontacts?.add(ContactsList(name,"",0,"",false))

                                    //println(phContactsList?.toString())


                                    //Log.e("Name ===>",phoneNumValue);
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

                Toast.makeText(context,"No contacts available",Toast.LENGTH_LONG).show()
            }
        }
        if (cursor != null) {
            cursor.close()
        }
        return phName
    }*/


 /*   private fun initView(){
        //initializeRecyclerView()
    }

    private fun initializeRecyclerView() {
        //recyclerView = rootView.findViewById(R.id.phone_contacts_RecyclerView)
        //add_contacts_contacts_RecyclerView = rootView.findViewById(R.id.add_contacts_contacts_RecyclerView);
        add_contacts_groups_RecyclerView = rootView.findViewById(R.id.add_contacts_groups_RecyclerView);
        add_contacts_users_RecyclerView = rootView.findViewById(R.id.add_contacts_users_RecyclerView);

        //add_contacts_contacts_RecyclerView.layoutManager = LinearLayoutManager(activity)
        //recyclerView.layoutManager = LinearLayoutManager(activity)
        add_contacts_groups_RecyclerView.layoutManager = LinearLayoutManager(activity)
        add_contacts_users_RecyclerView.layoutManager = LinearLayoutManager(activity)

        select_contacts_phone_checkbox = rootView.findViewById(R.id.select_contacts_phone_checkbox)
        *//*  select_contacts_contacts_checkbox = rootView.findViewById(R.id.select_contacts_contacts_checkbox)
          select_contacts_group_checkbox = rootView.findViewById(R.id.select_contacts_group_checkbox)
          select_contacts_users_checkbox = rootView.findViewById(R.id.select_contacts_users_checkbox)*//*
        add_contacts_ok = rootView.findViewById(R.id.add_contacts_ok)
        add_contacts_cancel = rootView.findViewById(R.id.add_contacts_cancel)
        //setUpDummyData();
        //recyclerView.adapter = adapter
    }

    private fun setUpDummyData(){
        var list: ArrayList<ContactsModel> = ArrayList<ContactsModel>()
        list.add(ContactsModel("User 1", "12345" , false))
        list.add(ContactsModel("User 2", "12345", false))*//*
        list.add(ContactsModel("User 3", "12345", false))
        list.add(ContactsModel("User 4", "12345", false))
        list.add(ContactsModel("User 5", "12345", false))
        list.add(ContactsModel("User 6", "12345", false))
        list.add(ContactsModel("User 7", "12345", false))
        list.add(ContactsModel("User 8", "12345", false))
        list.add(ContactsModel("User 9", "12345", false))*//*
        //adapter = ContactsAdapter(list)
        //recyclerView.adapter = adapter


        if(list.size <=5) {
            val params = add_contacts_contacts_RecyclerView.getLayoutParams() as ConstraintLayout.LayoutParams
            params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
            add_contacts_contacts_RecyclerView.setLayoutParams(params)
        }
        //add_contacts_contacts_RecyclerView.adapter = adapter


        if(list.size <=5) {
            //val params1 = recyclerView.getLayoutParams() as ConstraintLayout.LayoutParams
            //params1.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
            //recyclerView.setLayoutParams(params1)
        }
        //recyclerView.adapter = adapter


        if(list.size <=5) {
            val params2 = add_contacts_groups_RecyclerView.getLayoutParams() as ConstraintLayout.LayoutParams
            params2.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
            add_contacts_groups_RecyclerView.setLayoutParams(params2)
        }
        add_contacts_groups_RecyclerView.adapter = adapter


        if(list.size <=5) {
            val params3 = add_contacts_users_RecyclerView.getLayoutParams() as ConstraintLayout.LayoutParams
            params3.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
            add_contacts_users_RecyclerView.setLayoutParams(params3)
        }
        add_contacts_users_RecyclerView.adapter = adapter


        //loadContacts();
    }*/

    override fun onResume() {
        super.onResume()
        addcontactSearchEditText.addTextChangedListener(textListener)
    }

    override fun onPause() {
        addcontactSearchEditText.removeTextChangedListener(textListener)
        super.onPause()
    }

    override fun onDestroy() {
        textChangedJob?.cancel()
        super.onDestroy()
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    interface AddContactsFragmentListner {
        fun AddSelectedParticipants(participants : ArrayList<ActivityParticipants>)
    }

   /* override fun addSelectedContacts(contacts: ArrayList<ActivityParticipants>) {

        participantsList = contacts
    }*/

    override fun addSelectedContactsFriends(contacts: ArrayList<ActivityParticipants>) {

        participantsListContacts.clear()

        for (item in contacts){

            if (!participantsListContacts.contains(item)){

                participantsListContacts.add(item)

                //listner.AddSelectedParticipants(participantsList)
            }
        }
    }

    override fun addSelectedContactsGroups(contacts: ArrayList<Group>) {

        participantsListGroups.clear()

        for (item in contacts){

            for (list in item.users){

                if (!participantsListGroups.contains(ActivityParticipants(list.first_name, list.id))){

                    participantsListGroups.add(ActivityParticipants(list.first_name, list.id))

                    //listner.AddSelectedParticipants(participantsList)
                }
            }
        }
    }

    override fun addSelectedContactsUsers(contacts: ArrayList<ActivityParticipants>) {

        participantsListUsers.clear()

        for (item in contacts){

            if (!participantsListUsers.contains(item)){

                participantsListUsers.add(item)

                //listner.AddSelectedParticipants(participantsList)
            }
        }
    }
}
























//phoneContactsList?.add(ContactsList(name,"",0,"",false))

//phone_contacts_RecyclerView.adapter = phoneContactsList?.let { ContactsAdapter(it) }

// phContactsList?.add(EventInvitationPhone(name,phoneNumValue))

// phonecontacts?.add(EventInvitationPhone(name,phoneNumValue))

// println(phContactsList)

//println(name +" "+ phoneNumValue)

//phContactsList?.add(ContactsList(name,"",0,"",false))

//phcontacts?.add(ContactsList(name,"",0,"",false))

//println(phContactsList?.toString())

//Log.e("Name ===>",phoneNumValue);
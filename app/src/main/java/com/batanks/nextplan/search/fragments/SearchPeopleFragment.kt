package com.batanks.nextplan.search.fragments

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseFragment
import com.batanks.nextplan.arch.response.Status
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.common.getLoadingDialog
import com.batanks.nextplan.home.fragment.contacts.ContactsAdapter
import com.batanks.nextplan.home.fragment.contacts.ContactsModel
import com.batanks.nextplan.home.fragment.contacts.GroupsAdapter
import com.batanks.nextplan.home.viewmodel.ContactsViewModel
import com.batanks.nextplan.home.viewmodel.GroupsViewModel
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.search.adapters.SearchContactsAdapter
import com.batanks.nextplan.search.adapters.SearchGroupsAdapter
import com.batanks.nextplan.search.adapters.SearchPhoneContactsAdapter
import com.batanks.nextplan.search.adapters.SearchUsersAdapter
import com.batanks.nextplan.swagger.api.ContactsAPI
import com.batanks.nextplan.swagger.api.GroupsAPI
import com.batanks.nextplan.swagger.model.ContactsList
import com.batanks.nextplan.swagger.model.Group
import com.batanks.nextplan.swagger.model.InlineResponse2001
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_search_people.*
import kotlinx.android.synthetic.main.layout_create_followups.*

//search_groups_list

class SearchPeopleFragment : BaseFragment()  {
    protected lateinit var rootView: View
    /*lateinit var recyclerView: RecyclerView
    lateinit var adapter : ContactsAdapter*/

    //lateinit var adapter : ContactsAdapter
    //lateinit var groupAdapter : GroupsAdapter

    lateinit var contactsRecyclerView : RecyclerView
    lateinit var contactList : ArrayList<ContactsList>
    lateinit var contactsAdapter : SearchContactsAdapter

    lateinit var groupsRecyclerView : RecyclerView
    lateinit var groupList : List<Group>
    lateinit var groupsAdapter : SearchGroupsAdapter

    lateinit var usersRecyclerView: RecyclerView
    lateinit var usersAdapter: SearchUsersAdapter

    /*lateinit var phContactsRecyclerView : RecyclerView
    lateinit var phContactsAdapter : SearchPhoneContactsAdapter*/

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        rootView = inflater.inflate(R.layout.fragment_search_people, container, false)

        contactsRecyclerView = rootView.findViewById(R.id.rv_settings_contacts)
        contactsRecyclerView.layoutManager = LinearLayoutManager(activity)

        groupsRecyclerView = rootView.findViewById(R.id.rv_search_groups)
        groupsRecyclerView.layoutManager = LinearLayoutManager(activity)

        usersRecyclerView = rootView.findViewById(R.id.rv_search_users)
        usersRecyclerView.layoutManager = LinearLayoutManager(activity)

       /* phContactsRecyclerView = rootView.findViewById(R.id.phone_contacts_RecyclerView)
        phContactsRecyclerView.layoutManager = LinearLayoutManager(activity)*/

        initView()

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        loadingDialog = context?.getLoadingDialog(0, R.string.loading_list_please_wait, theme = R.style.AlertDialogCustom)

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

                    contactsAdapter = SearchContactsAdapter(contactList)

                    if(contactList.size <=5) {
                        val params = contactsRecyclerView.getLayoutParams() as ConstraintLayout.LayoutParams
                        params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                        contactsRecyclerView.setLayoutParams(params)
                    }
                    contactsRecyclerView.adapter = contactsAdapter

                    println(contactList)

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

                    groupsAdapter = SearchGroupsAdapter(groupList)

                    if(groupList.size <=5) {
                        val params = groupsRecyclerView.getLayoutParams() as ConstraintLayout.LayoutParams
                        params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                        groupsRecyclerView.setLayoutParams(params)
                    }
                    groupsRecyclerView.adapter = groupsAdapter

                    println(groupList)

                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })

        rl_select_contact_phone_contacts.setOnClickListener {

            phone_contacts_RecyclerView.visibility = VISIBLE

        }

        img_contacts_down_arrow_icon.setOnClickListener {

            search_contacts_list.visibility = VISIBLE

            img_contacts_down_arrow_icon.visibility = GONE

            img_contacts_up_arrow_icon.visibility = VISIBLE
        }

        img_contacts_up_arrow_icon.setOnClickListener {

            search_contacts_list.visibility = GONE

            img_contacts_down_arrow_icon.visibility = VISIBLE

            img_contacts_up_arrow_icon.visibility = GONE

        }

        img_group_down_arrow_icon.setOnClickListener {

            search_groups_list.visibility = VISIBLE

            img_group_down_arrow_icon.visibility = GONE

            img_group_up_arrow_icon.visibility = VISIBLE
        }

        img_group_up_arrow_icon.setOnClickListener {

            search_groups_list.visibility = GONE

            img_group_down_arrow_icon.visibility = VISIBLE

            img_group_up_arrow_icon.visibility = GONE

        }

        img_user_down_arrow_icon.setOnClickListener {

            search_users_list.visibility = VISIBLE

            img_user_down_arrow_icon.visibility = GONE

            img_user_up_arrow_icon.visibility = VISIBLE

        }

        img_user_up_arrow_icon.setOnClickListener {

            search_users_list.visibility = GONE

            img_user_down_arrow_icon.visibility = VISIBLE

            img_user_up_arrow_icon.visibility = GONE
        }

        img_ph_contacts_down_arrow_icon.setOnClickListener {

            search_phone_contacts_list.visibility = VISIBLE

            img_ph_contacts_down_arrow_icon.visibility = GONE

            img_ph_contacts_up_arrow_icon.visibility = VISIBLE
        }

        img_ph_contacts_up_arrow_icon.setOnClickListener {

            search_phone_contacts_list.visibility = GONE

            img_ph_contacts_down_arrow_icon.visibility = VISIBLE

            img_ph_contacts_up_arrow_icon.visibility = GONE
        }

        extFab_followup.setOnClickListener{

            showDialog(view!!.context)
        }

        //addcontactSearchTextField.endIconMode

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

    private fun initView(){

        initRecyclerViews()

    }

    private fun setUpDummyData(){

        var contactsList: ArrayList<ContactsModel> = ArrayList<ContactsModel>()
        contactsList.add(ContactsModel("Name FirstName", "12345", false))
        contactsList.add(ContactsModel("Name FirstName", "12345", false))
        contactsList.add(ContactsModel("Name FirstName", "12345", false))
        contactsList.add(ContactsModel("Name FirstName", "12345", false))
        contactsList.add(ContactsModel("Name FirstName", "12345", false))
        contactsList.add(ContactsModel("Name FirstName", "12345", false))
        contactsList.add(ContactsModel("Name FirstName", "12345", false))
        contactsList.add(ContactsModel("Name FirstName", "12345", false))
        contactsList.add(ContactsModel("Name FirstName", "12345", false))

        var groupsList: ArrayList<ContactsModel> = ArrayList<ContactsModel>()
        groupsList.add(ContactsModel("Group 1", "12345", false))
        groupsList.add(ContactsModel("Group 2", "12345", false))
        groupsList.add(ContactsModel("Group 3", "12345", false))
        groupsList.add(ContactsModel("Group 4", "12345", false))
        groupsList.add(ContactsModel("Group 5", "12345", false))
        groupsList.add(ContactsModel("Group 6", "12345", false))
        groupsList.add(ContactsModel("Group 7", "12345", false))
        groupsList.add(ContactsModel("Group 8", "12345", false))
        groupsList.add(ContactsModel("Group 9", "12345", false))

        var usersList: ArrayList<ContactsModel> = ArrayList<ContactsModel>()
        usersList.add(ContactsModel("Name FirstName", "12345", false))
        usersList.add(ContactsModel("Name FirstName", "12345", false))
        usersList.add(ContactsModel("Name FirstName", "12345", false))
        usersList.add(ContactsModel("Name FirstName", "12345", false))
        usersList.add(ContactsModel("Name FirstName", "12345", false))
        usersList.add(ContactsModel("Name FirstName", "12345", false))
        usersList.add(ContactsModel("Name FirstName", "12345", false))
        usersList.add(ContactsModel("Name FirstName", "12345", false))
        usersList.add(ContactsModel("Name FirstName", "12345", false))

        var phContactsList: ArrayList<ContactsModel> = ArrayList<ContactsModel>()
        phContactsList.add(ContactsModel("Name FirstName", "12345", false))
        phContactsList.add(ContactsModel("Name FirstName", "12345", false))
        phContactsList.add(ContactsModel("Name FirstName", "12345", false))
        phContactsList.add(ContactsModel("Name FirstName", "12345", false))
        phContactsList.add(ContactsModel("Name FirstName", "12345", false))
        phContactsList.add(ContactsModel("Name FirstName", "12345", false))
        phContactsList.add(ContactsModel("Name FirstName", "12345", false))
        phContactsList.add(ContactsModel("Name FirstName", "12345", false))
        phContactsList.add(ContactsModel("Name FirstName", "12345", false))

        /*adapter = ContactsAdapter(contactsList)
        recyclerView.adapter = adapter*/

      /*  contactsAdapter = SearchContactsAdapter(contactsList)
        contactsRecyclerView.adapter = contactsAdapter

        groupsAdapter = SearchGroupsAdapter(groupsList)
        groupsRecyclerView.adapter = groupsAdapter*/

        usersAdapter = SearchUsersAdapter(contactsList)
        usersRecyclerView.adapter = usersAdapter

        /*phContactsAdapter = SearchPhoneContactsAdapter(contactsList)
        phContactsRecyclerView.adapter = phContactsAdapter*/

        //loadContacts();
    }

    private fun initRecyclerViews() {
      /*  recyclerView = rootView.findViewById(R.id.phone_contacts_RecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)*/

       /* contactsRecyclerView = rootView.findViewById(R.id.rv_settings_contacts)
        contactsRecyclerView.layoutManager = LinearLayoutManager(activity)

        groupsRecyclerView = rootView.findViewById(R.id.rv_search_groups)
        groupsRecyclerView.layoutManager = LinearLayoutManager(activity)

        usersRecyclerView = rootView.findViewById(R.id.rv_search_users)
        usersRecyclerView.layoutManager = LinearLayoutManager(activity)

        phContactsRecyclerView = rootView.findViewById(R.id.phone_contacts_RecyclerView)
        phContactsRecyclerView.layoutManager = LinearLayoutManager(activity)*/

        //select_contacts_phone_checkbox = rootView.findViewById(R.id.select_contacts_phone_checkbox)
        setUpDummyData();
        //recyclerView.adapter = adapter
    }

    private fun showDialog(context : Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.layout_create_followups)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        /*val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)*/

        val btn_create_followups_cancel = dialog.findViewById(R.id.btn_create_followups_cancel) as Button
        val btn_create_followups_ok = dialog.findViewById(R.id.btn_create_followups_ok) as Button
        val tip_create_followups_name = dialog.findViewById(R.id.tip_create_followups_name) as TextInputLayout
        val input_create_followups_name = dialog.findViewById(R.id.input_create_followups_name) as TextInputEditText

        /*dialog.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        tip_create_followups_name.editText?.setOnFocusChangeListener(object:View.OnFocusChangeListener {

            override fun onFocusChange(v:View, hasFocus:Boolean) {
                if (hasFocus)
                {
                    dialog.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
                }
            }
        })*/

        /*input_create_followups_name.setOnFocusChangeListener(object:View.OnFocusChangeListener {

            override fun onFocusChange(v:View, hasFocus:Boolean) {
                if (hasFocus)
                {
                    dialog.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
                }
            }
        })*/


        btn_create_followups_cancel.setOnClickListener {

            dialog.dismiss()
        }

        btn_create_followups_ok.setOnClickListener {

//            if (TextUtils.isEmpty(tip_create_followups_name.editText?.text.toString())){
            if (tip_create_followups_name.editText?.length()!! >= 1){

                dialog.dismiss()

            } else {

                tip_create_followups_name.editText?.error = "Follow Up name should contain atleast one character"
                input_create_followups_name.requestFocus()
            }
        }

        //input_create_followups_name.requestFocus()

        dialog.show()

        /*dialog.window?.decorView?.setOnFocusChangeListener(object:View.OnFocusChangeListener {

            override fun onFocusChange(v:View, hasFocus:Boolean) {
                if (hasFocus)
                {
                    dialog.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
                }
            }
        })*/

        dialog.window?.decorView?.setOnTouchListener { v, event ->

            if (event?.action == MotionEvent.ACTION_DOWN) {

                    val imm = v?.getContext()?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                    v.clearFocus()
                }
            false
        }

        /*view.setOnTouchListener(object : View.OnTouchListener {

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                if (event?.action == MotionEvent.ACTION_DOWN) {

                    val imm = v?.getContext()?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                    v.clearFocus()
                }

                return false
            }
        })*/

    }

}
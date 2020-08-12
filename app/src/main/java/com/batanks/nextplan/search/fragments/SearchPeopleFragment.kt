package com.batanks.nextplan.search.fragments

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseFragment
import com.batanks.nextplan.home.fragment.contacts.ContactsModel
import com.batanks.nextplan.search.adapters.SearchContactsAdapter
import com.batanks.nextplan.search.adapters.SearchGroupsAdapter
import com.batanks.nextplan.search.adapters.SearchPhoneContactsAdapter
import com.batanks.nextplan.search.adapters.SearchUsersAdapter
import kotlinx.android.synthetic.main.fragment_search_people.*


class SearchPeopleFragment : BaseFragment()  {
    protected lateinit var rootView: View
    /*lateinit var recyclerView: RecyclerView
    lateinit var adapter : ContactsAdapter*/

    lateinit var contactsRecyclerView : RecyclerView
    lateinit var contactsAdapter : SearchContactsAdapter

    lateinit var groupsRecyclerView : RecyclerView
    lateinit var groupsAdapter : SearchGroupsAdapter

    lateinit var usersRecyclerView: RecyclerView
    lateinit var usersAdapter: SearchUsersAdapter

    lateinit var phContactsRecyclerView : RecyclerView
    lateinit var phContactsAdapter : SearchPhoneContactsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        rootView = inflater.inflate(R.layout.fragment_search_people, container, false)

        initView()

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

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

        addcontactSearchTextField.endIconMode

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

        contactsAdapter = SearchContactsAdapter(contactsList)
        contactsRecyclerView.adapter = contactsAdapter

        groupsAdapter = SearchGroupsAdapter(groupsList)
        groupsRecyclerView.adapter = groupsAdapter

        usersAdapter = SearchUsersAdapter(contactsList)
        usersRecyclerView.adapter = usersAdapter

        phContactsAdapter = SearchPhoneContactsAdapter(contactsList)
        phContactsRecyclerView.adapter = phContactsAdapter

        //loadContacts();
    }

    private fun initRecyclerViews() {
      /*  recyclerView = rootView.findViewById(R.id.phone_contacts_RecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)*/

        contactsRecyclerView = rootView.findViewById(R.id.rv_settings_contacts)
        contactsRecyclerView.layoutManager = LinearLayoutManager(activity)

        groupsRecyclerView = rootView.findViewById(R.id.rv_search_groups)
        groupsRecyclerView.layoutManager = LinearLayoutManager(activity)

        usersRecyclerView = rootView.findViewById(R.id.rv_search_users)
        usersRecyclerView.layoutManager = LinearLayoutManager(activity)

        phContactsRecyclerView = rootView.findViewById(R.id.phone_contacts_RecyclerView)
        phContactsRecyclerView.layoutManager = LinearLayoutManager(activity)
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

        val btn_create_followups_cancel = dialog.findViewById(R.id.btn_create_followups_cancel) as Button
        val btn_create_followups_ok = dialog.findViewById(R.id.btn_create_followups_ok) as Button

        btn_create_followups_cancel.setOnClickListener {
            dialog.dismiss()
        }
        btn_create_followups_ok.setOnClickListener { dialog.dismiss() }
        dialog.show()

    }
}
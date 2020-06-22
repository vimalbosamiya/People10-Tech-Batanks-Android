package com.batanks.nextplan.home.fragment.action

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseDialogFragment
import com.batanks.nextplan.home.fragment.contacts.ContactsAdapter
import com.batanks.nextplan.home.fragment.contacts.ContactsModel
import kotlinx.android.synthetic.main.assign_people_fragment.*

class AssignPeopleFragment (private val listner : AssignPeopleFragmentListner): BaseDialogFragment() , View.OnClickListener ,
        Assign_People_Adapter.assignPeopleRecyclerViewCallBack {

    protected lateinit var rootView: View
    lateinit var assign_people_recyclerview: RecyclerView
    lateinit var adapter : Assign_People_Adapter
    lateinit var selected_assignee : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.App_DialogFragment_Theme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView =  inflater.inflate(R.layout.assign_people_fragment, container, false)
        initView()
        return rootView
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        assign_people_Button.setOnClickListener(this)
    }

    private fun initView(){
        initializeRecyclerView()
    }

    private fun initializeRecyclerView() {
        assign_people_recyclerview = rootView.findViewById(R.id.assign_people_recyclerview)
        assign_people_recyclerview.layoutManager = LinearLayoutManager(activity)
        //select_contacts_phone_checkbox = rootView.findViewById(R.id.select_contacts_phone_checkbox)
        setUpDummyData();
        //recyclerView.adapter = adapter
    }
    private fun setUpDummyData(){
        var list: ArrayList<ContactsModel> = ArrayList<ContactsModel>()
        list.add(ContactsModel("User 1", "12345", true))
        list.add(ContactsModel("User 2", "12345", false))
        list.add(ContactsModel("User 3", "12345", false))
        list.add(ContactsModel("User 4", "12345", false))
        list.add(ContactsModel("User 5", "12345", false))
        list.add(ContactsModel("User 6", "12345", false))
        list.add(ContactsModel("User 7", "12345", false))
        list.add(ContactsModel("User 8", "12345", false))
        list.add(ContactsModel("User 9", "12345", false))
        adapter = Assign_People_Adapter(this, list)
        assign_people_recyclerview.adapter = adapter
        //loadContacts();
    }

    override fun assignSelectedContact(selection: String) {
        selected_assignee = selection
    }

    interface AssignPeopleFragmentListner {
        fun AddSelectedAssignee(contact : String)
    }
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.assign_people_Button ->{
                if(!TextUtils.isEmpty(selected_assignee))
                listner.AddSelectedAssignee(selected_assignee)
                dismiss()
            }

        }
    }
}
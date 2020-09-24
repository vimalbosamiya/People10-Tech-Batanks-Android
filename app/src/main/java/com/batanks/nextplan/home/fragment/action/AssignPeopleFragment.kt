package com.batanks.nextplan.home.fragment.action

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseDialogFragment
import com.batanks.nextplan.arch.response.Status
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.common.getLoadingDialog
import com.batanks.nextplan.home.fragment.contacts.ContactsAdapter
import com.batanks.nextplan.home.fragment.contacts.ContactsModel
import com.batanks.nextplan.home.viewmodel.ContactsViewModel
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.swagger.api.ContactsAPI
import com.batanks.nextplan.swagger.model.ContactsList
import com.batanks.nextplan.swagger.model.InlineResponse2001
import kotlinx.android.synthetic.main.assign_people_fragment.*

class AssignPeopleFragment (private val listner : AssignPeopleFragmentListner): BaseDialogFragment() , View.OnClickListener ,
        Assign_People_Adapter.assignPeopleRecyclerViewCallBack {

    protected lateinit var rootView: View
    lateinit var assign_people_recyclerview: RecyclerView
    lateinit var adapter : Assign_People_Adapter
     var selected_assignee : String = ""
    var assigneeId : Int? = 0
    lateinit var contactList : List<ContactsList>

    private val contactsViewModel: ContactsViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            getContext()?.let {
                RetrofitClient.getRetrofitInstance(it)?.create(ContactsAPI::class.java)?.let {
                    ContactsViewModel(it)
                }
            }
        }).get(ContactsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.App_DialogFragment_Theme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView =  inflater.inflate(R.layout.assign_people_fragment, container, false)
        //initView()
        assign_people_recyclerview = rootView.findViewById(R.id.assign_people_recyclerview)
        assign_people_recyclerview.layoutManager = LinearLayoutManager(activity)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        loadingDialog = context?.getLoadingDialog(0, R.string.loading_list_please_wait, theme = R.style.AlertDialogCustom)

        contactsViewModel.getContactsList()

        contactsViewModel.responseLiveData.observe(viewLifecycleOwner, Observer{ response ->

            when(response.status){
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()

                    contactsViewModel.response = response.data as InlineResponse2001

                    contactList = contactsViewModel.response!!.results

                    println(contactList)

                    assign_people_recyclerview.adapter = Assign_People_Adapter(this, contactList)
                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })

        assign_people_Button.setOnClickListener(this)
    }

    private fun initView(){
        //initializeRecyclerView()
    }

    private fun initializeRecyclerView() {
        assign_people_recyclerview = rootView.findViewById(R.id.assign_people_recyclerview)
        assign_people_recyclerview.layoutManager = LinearLayoutManager(activity)
        //select_contacts_phone_checkbox = rootView.findViewById(R.id.select_contacts_phone_checkbox)
        //setUpDummyData();
        //recyclerView.adapter = adapter
    }

    /*private fun setUpDummyData(){
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
    }*/

    override fun assignSelectedContact(selection: String, selected: Boolean, id: Int) {
        selected_assignee = selection
        assigneeId = id

    }

    interface AssignPeopleFragmentListner {
        fun AddSelectedAssignee(contact : String, id: Int)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.assign_people_Button ->{

                if(!TextUtils.isEmpty(selected_assignee)){

                    assigneeId?.let { listner.AddSelectedAssignee(selected_assignee, it) }
                    dismiss()

                }else {

                    dismiss()
                }
            }
        }
    }
}
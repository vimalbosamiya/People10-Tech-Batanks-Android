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
import com.batanks.nextplan.home.fragment.tabfragment.publicplan.AssignActivityParticipantsAdapter
import com.batanks.nextplan.home.viewmodel.ContactsViewModel
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.swagger.api.ContactsAPI
import com.batanks.nextplan.swagger.model.*
import kotlinx.android.synthetic.main.assign_people_fragment.*

class AssignPeopleFragment (private val listner : AssignPeopleFragmentListner,private val event : Event?, private val fromAction : Boolean,
                            private var defaultSelectedGuests : ArrayList<ActivityParticipant>?,  private var defaultSelectedAssigne : String?,
                            /*private var defaultSelectedGuests : ArrayList<String>?*/) : BaseDialogFragment(), View.OnClickListener,
                            Assign_People_Adapter.assignPeopleRecyclerViewCallBack, AssignActivityParticipantsAdapter.assignPeopleActivityRecyclerViewCallBack {

    protected lateinit var rootView: View
    lateinit var assign_people_recyclerview: RecyclerView
    lateinit var adapter : Assign_People_Adapter
     var selected_assignee : String? = null
     var selected_Contact : Guests? = null
     var activityParticipants : ArrayList<Guests>? = null
    var assigneeId : Int? = null
    lateinit var contactList : ArrayList<ContactsList>

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

        //loadingDialog = context?.getLoadingDialog(0, R.string.loading_list_please_wait, theme = R.style.AlertDialogCustom)

        if(fromAction == true){

            assign_people_recyclerview.adapter =
                event!!.guests?.let { Assign_People_Adapter(this, it, defaultSelectedAssigne) }

        } else {

            assign_people_header.setText(getString(R.string.add_guest_caps))
            assign_people_Button.setText(getString(R.string.add_guest_all_caps))

            assign_people_recyclerview.adapter = event!!.guests?.let {
                AssignActivityParticipantsAdapter(this,
                    it, defaultSelectedGuests)
            }
        }

        /*contactsViewModel.getContactsList()

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

                    //assign_people_recyclerview.adapter = Assign_People_Adapter(this, contactList)
                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())

                    println(response.error?.message.toString())
                }
            }
        })*/

        assign_people_Button.setOnClickListener(this)
    }

    override fun assignSelectedContact(selection: Guests?, selected: Boolean, id: Int?) {
        selected_Contact = selection
        selected_assignee = selection?.user?.username
        assigneeId = id
    }

    override fun assignSelectedContactsActivity(selection: ArrayList<Guests>?) {

        activityParticipants = selection
        println(selection)
    }

    interface AssignPeopleFragmentListner {
        fun AddSelectedAssignee(contact : Guests?, id: Int?)
        fun AddSelectedActivityParticipants(activityParticipants : ArrayList<Guests>?)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.assign_people_Button ->{

                if(fromAction == true){

                    if(!TextUtils.isEmpty(selected_assignee)){

                        //assigneeId?.let { selected_assignee?.let { it1 -> listner.AddSelectedAssignee(it1, it) } }

                        listner.AddSelectedAssignee(selected_Contact, assigneeId)
                        dismiss()

                    }else {

                        dismiss()
                    }

                }else {

                    listner.AddSelectedActivityParticipants(activityParticipants)
                    dismiss()
                }
            }
        }
    }


}
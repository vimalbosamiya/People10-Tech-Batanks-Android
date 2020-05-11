package com.batanks.nextplan.home.fragment.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseDialogFragment
import kotlinx.android.synthetic.main.add_contact_fragment.*

class AddContactsFragment : BaseDialogFragment() {

    protected lateinit var rootView: View
    lateinit var recyclerView: RecyclerView
    lateinit var adapter : ContactsAdapter
    lateinit var select_contacts_phone_checkbox : CheckBox


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.App_DialogFragment_Theme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView =  inflater.inflate(R.layout.add_contact_fragment, container, false)

        initView()

        select_contacts_phone_checkbox.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                recyclerView.visibility = View.GONE
            } else {
                recyclerView.visibility = View.VISIBLE
            }
        }

        return rootView
    }
    private fun initView(){
        initializeRecyclerView()
    }

    private fun initializeRecyclerView() {
        recyclerView = rootView.findViewById(R.id.phone_contacts_RecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        select_contacts_phone_checkbox = rootView.findViewById(R.id.select_contacts_phone_checkbox)
        setUpDummyData();
        //recyclerView.adapter = adapter
    }
    private fun setUpDummyData(){
        var list: ArrayList<ContactsModel> = ArrayList<ContactsModel>()
        list.add(ContactsModel("User 1", "12345"))
        list.add(ContactsModel("User 2", "12345"))
        list.add(ContactsModel("User 3", "12345"))
        list.add(ContactsModel("User 4", "12345"))
        list.add(ContactsModel("User 5", "12345"))
        list.add(ContactsModel("User 6", "12345"))
        list.add(ContactsModel("User 7", "12345"))
        list.add(ContactsModel("User 8", "12345"))
        list.add(ContactsModel("User 9", "12345"))
        adapter = ContactsAdapter(list)
        recyclerView.adapter = adapter
    }
}
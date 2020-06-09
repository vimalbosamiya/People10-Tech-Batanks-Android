package com.batanks.nextplan.Settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.home.fragment.contacts.ContactsAdapter
import com.batanks.nextplan.home.fragment.contacts.ContactsModel
import kotlinx.android.synthetic.main.activity_contact.*

class Contact : AppCompatActivity() {
    lateinit var rv_settings_contacts: RecyclerView
    lateinit var rv_settings_groups : RecyclerView
    lateinit var adapter : ContactsAdapter_Settings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        rv_settings_contacts = findViewById(R.id.rv_settings_contacts)
        rv_settings_contacts.layoutManager = LinearLayoutManager(this)

        rv_settings_groups = findViewById(R.id.rv_settings_groups)
        rv_settings_groups.layoutManager = LinearLayoutManager(this)


        img_settings_contacts_contacts_downarrow.setOnClickListener(View.OnClickListener {
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
        })

        setUpDummyData()
    }

    private fun setUpDummyData(){
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
        //loadContacts();
    }
}

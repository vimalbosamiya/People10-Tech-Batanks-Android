package com.batanks.nextplan.Settings

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.Settings.Adapters.ContactsAdapter_Settings
import com.batanks.nextplan.Settings.Adapters.GroupsAdapter_Settings
import com.batanks.nextplan.home.fragment.contacts.ContactsModel
import kotlinx.android.synthetic.main.activity_contact.*

class Contact : AppCompatActivity() {
    lateinit var rv_settings_contacts: RecyclerView
    lateinit var rv_settings_groups : RecyclerView
    lateinit var adapter : ContactsAdapter_Settings
    lateinit var groups_adapter : GroupsAdapter_Settings
    lateinit var rl_settings_create_new_groups : RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        rv_settings_contacts = findViewById(R.id.rv_settings_contacts)
        rv_settings_contacts.layoutManager = LinearLayoutManager(this)

        rv_settings_groups = findViewById(R.id.rv_settings_groups)
        rv_settings_groups.layoutManager = LinearLayoutManager(this)

        rl_settings_create_new_groups = findViewById(R.id.rl_settings_create_new_groups)


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
        rl_settings_create_new_groups.setOnClickListener(View.OnClickListener {
            showDialog()
        })

        img_contacts_close.setOnClickListener {

            finish()
        }

        setUpDummyData()
    }


    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_create_group)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btn_create_group_cancel = dialog.findViewById(R.id.btn_create_group_cancel) as Button
        val btn_create_group_ok = dialog.findViewById(R.id.btn_create_group_ok) as Button

        btn_create_group_cancel.setOnClickListener {
            dialog.dismiss()
        }
        btn_create_group_ok.setOnClickListener { dialog.dismiss() }
        dialog.show()

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
    }
}

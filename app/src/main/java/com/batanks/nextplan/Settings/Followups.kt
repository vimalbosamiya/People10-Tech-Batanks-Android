package com.batanks.nextplan.Settings

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.home.fragment.contacts.ContactsModel
import kotlinx.android.synthetic.main.activity_followups.*

class Followups : AppCompatActivity() {

    lateinit var rv_settings_followups : RecyclerView
    lateinit var adapter : FollowupsAdapter_Settings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_followups)

        extFab_followup.setOnClickListener(View.OnClickListener {
            showDialog()
        })

        rv_settings_followups = findViewById(R.id.rv_settings_followups)
        rv_settings_followups.layoutManager = LinearLayoutManager(this)
        setUpDummyData()
    }

    private fun setUpDummyData(){
        var list: ArrayList<ContactsModel> = ArrayList<ContactsModel>()
        list.add(ContactsModel("Follow ups 1", "12345" , false))
        list.add(ContactsModel("Follow ups 2", "12345", false))
        list.add(ContactsModel("Follow ups 3", "12345", false))
        list.add(ContactsModel("Follow ups 4", "12345", false))
        list.add(ContactsModel("Follow ups 5", "12345", false))
        list.add(ContactsModel("Follow ups 6", "12345", false))
        list.add(ContactsModel("Follow ups 7", "12345", false))
        list.add(ContactsModel("Follow ups 8", "12345", false))
        list.add(ContactsModel("Follow ups 9", "12345", false))
        list.add(ContactsModel("Follow ups 10", "12345", false))
        list.add(ContactsModel("Follow ups 11", "12345", false))
        list.add(ContactsModel("Follow ups 12", "12345", false))
        list.add(ContactsModel("Follow ups 13", "12345", false))
        list.add(ContactsModel("Follow ups 14", "12345", false))
        list.add(ContactsModel("Follow ups 15", "12345", false))
        adapter = FollowupsAdapter_Settings(list)
        rv_settings_followups.adapter = adapter
    }
    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
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

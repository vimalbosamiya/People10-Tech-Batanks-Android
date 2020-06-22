package com.batanks.nextplan.Settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.Settings.Adapters.Followup_Sorting_Adapter
import com.batanks.nextplan.Settings.Adapters.FollowupsAdapter_Settings
import com.batanks.nextplan.arch.BaseDialogFragment
import com.batanks.nextplan.home.fragment.contacts.ContactsModel

class Followups_Fragment : BaseDialogFragment() {
    protected lateinit var rootView: View
    lateinit var rv_select_followup_items : RecyclerView
    lateinit var adapter : Followup_Sorting_Adapter
    lateinit var select_followup_cancel : Button
    lateinit var select_followup_ok : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.App_DialogFragment_Theme)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView =  inflater.inflate(R.layout.fragment_followups, container, false)

        rv_select_followup_items = rootView.findViewById(R.id.rv_select_followup_items)
        rv_select_followup_items.layoutManager = LinearLayoutManager(activity)
        select_followup_cancel = rootView.findViewById(R.id.select_followup_cancel)
        select_followup_ok = rootView.findViewById(R.id.select_followup_ok)
        select_followup_cancel.setOnClickListener(View.OnClickListener {
            dismiss()
        })
        select_followup_ok.setOnClickListener(View.OnClickListener {
            dismiss()
        })
        setUpDummyData()
        return rootView
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
        adapter = Followup_Sorting_Adapter(list)
        rv_select_followup_items.adapter = adapter
    }
}
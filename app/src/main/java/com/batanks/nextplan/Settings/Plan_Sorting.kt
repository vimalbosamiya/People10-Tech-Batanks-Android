package com.batanks.nextplan.Settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.Settings.Adapters.Plan_Sorting_Adapter
import com.batanks.nextplan.home.fragment.contacts.ContactsModel

class Plan_Sorting : AppCompatActivity() {

    lateinit var rv_plan_sorting : RecyclerView
    lateinit var adapter : Plan_Sorting_Adapter
    lateinit var rl_plan_sort_followups : RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan_sorting)

        rv_plan_sorting = findViewById(R.id.rv_plan_sorting)
        rv_plan_sorting.layoutManager = LinearLayoutManager(this)
        rl_plan_sort_followups = findViewById(R.id.rl_plan_sort_followups)
        rl_plan_sort_followups.setOnClickListener(View.OnClickListener {
            supportFragmentManager
                    .beginTransaction()
                    .add(Followups_Fragment(), Followups_Fragment::class.java.canonicalName)
                    .commitAllowingStateLoss()
        })
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

        adapter = Plan_Sorting_Adapter(list)
        rv_plan_sorting.adapter = adapter
    }
}

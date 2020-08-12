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
        list.add(ContactsModel("Drafts", "12345" , false))
        list.add(ContactsModel("Mines", "12345", false))
        list.add(ContactsModel("Invited to", "12345", false))
        list.add(ContactsModel("News", "12345", false))
        list.add(ContactsModel("Trip", "12345", false))
        list.add(ContactsModel("Professional", "12345", false))
        list.add(ContactsModel("Leisure", "12345", false))
        list.add(ContactsModel("Institutional", "12345", false))
        list.add(ContactsModel("Other", "12345", false))


        adapter = Plan_Sorting_Adapter(list)
        rv_plan_sorting.adapter = adapter
    }
}

package com.batanks.nextplan.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseFragment
import com.batanks.nextplan.home.fragment.tabfragment.TabsPagerAdapter
import kotlinx.android.synthetic.main.fragment_add_new_plan.*

class CreatePlanFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_new_plan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val ref = requireActivity() as AppCompatActivity
        ref.setSupportActionBar(toolBar)
        ref.supportActionBar?.title = "Add Plan"
        ref.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        ref.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_header)

        val tabsPagerAdapter = TabsPagerAdapter(requireFragmentManager())
        view_pager.adapter = tabsPagerAdapter

        tabs.setupWithViewPager(view_pager)

        val tabOne = LayoutInflater.from(view.context).inflate(R.layout.custom_tab, null) as TextView
        tabOne.text = "PRIVATE"
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_private_plan_tablayout, 0, 0)
        tabs.getTabAt(0)?.customView = tabOne

        val tabTwo = LayoutInflater.from(view.context).inflate(R.layout.custom_tab, null) as TextView
        tabTwo.text = "PUBLIC"
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_public_plan_tablayout, 0, 0)
        tabs.getTabAt(1)?.customView = tabTwo

        tabs.getTabAt(0)?.icon = ref.getDrawable(R.drawable.ic_private_plan_tablayout)
        tabs.getTabAt(1)?.icon = ref.getDrawable(R.drawable.ic_public_plan_tablayout)
    }

    companion object {
        const val TAG = "CreatePlanFragment"
    }
}
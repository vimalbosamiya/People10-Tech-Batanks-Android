package com.batanks.nextplan.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseFragment
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_notifications.*

class NotificationsFragment : BaseFragment(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val ref = requireActivity() as AppCompatActivity
        ref.setSupportActionBar(toolBar)
        ref.supportActionBar?.title = "Notifications "
        ref.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val homeAsUpIndicator: Unit? = ref.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_header)

        toolBar.setOnClickListener {

            Toast.makeText(activity,"Working", Toast.LENGTH_SHORT).show()

            //activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.addToBackStack(SearchFragment.TAG)?.commit()

        }

        // toolBar.setOnMenuItemClickListener

        val tabsPagerAdapter = NotificationsTabsAdapter(childFragmentManager)
        view_pager.adapter = tabsPagerAdapter

        tabs.setupWithViewPager(view_pager)

        val tabOne = LayoutInflater.from(view.context).inflate(R.layout.custom_tab, null) as TextView
        tabOne.text = "ALL"
        tabOne.setTextColor(resources.getColor(R.color.colorWhite))
        //tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_people_tablayout, 0, 0)
        tabs.getTabAt(0)?.customView = tabOne

        val tabTwo = LayoutInflater.from(view.context).inflate(R.layout.custom_tab, null) as TextView
        tabTwo.text = "PRIVATE"
        tabTwo.setTextColor(resources.getColor(R.color.colorWhite))
        //tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_public_plan_tablayout, 0, 0)
        tabs.getTabAt(1)?.customView = tabTwo

        val tabThree = LayoutInflater.from(view.context).inflate(R.layout.custom_tab, null) as TextView
        tabThree.text = "PUBLIC"
        tabThree.setTextColor(resources.getColor(R.color.colorWhite))
        //tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_private_plan_tablayout, 0, 0)
        tabs.getTabAt(2)?.customView = tabThree

        tabs.getTabAt(0)?.icon = ref.getDrawable(R.drawable.ic_people_tablayout)
        tabs.getTabAt(1)?.icon = ref.getDrawable(R.drawable.ic_public_plan_tablayout)
        tabs.getTabAt(2)?.icon = ref.getDrawable(R.drawable.ic_private_plan_tablayout)


    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.extFab!!.visibility = View.VISIBLE
    }

    companion object {
        const val TAG = "NotificationsFragment"
    }
}
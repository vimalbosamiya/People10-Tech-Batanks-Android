package com.batanks.nextplan.search.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseFragment
import com.batanks.nextplan.search.SearchTabsAdapter
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.layout_add_plan_add_period.*

class SearchFragment : BaseFragment(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val ref = requireActivity() as AppCompatActivity
        ref.setSupportActionBar(searchToolBar)
        ref.supportActionBar?.title = "Search"
        ref.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        ref.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_header)

        searchToolBar.setNavigationOnClickListener {

            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()

            activity?.appBarLayout?.visibility = View.VISIBLE

            ///activity?.extFab!!.visibility = View.VISIBLE             //uncomment

            //Toast.makeText(activity,"Back Button Working from Navigation" , Toast.LENGTH_SHORT).show()

        }

       // val tabsPagerAdapter = SearchTabsAdapter(childFragmentManager)
        //view_pager.adapter = tabsPagerAdapter

        tabs.setupWithViewPager(view_pager)

        val tabOne = LayoutInflater.from(view.context).inflate(R.layout.custom_tab, null) as TextView
        tabOne.text = "ALL"
        tabOne.setTextColor(resources.getColor(R.color.colorWhite))
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_people_tablayout, 0, 0)
        tabs.getTabAt(0)?.customView = tabOne

        val tabTwo = LayoutInflater.from(view.context).inflate(R.layout.custom_tab, null) as TextView
        tabTwo.text = "PUBLIC"
        tabTwo.setTextColor(resources.getColor(R.color.colorWhite))
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_public_plan_tablayout, 0, 0)
        tabs.getTabAt(1)?.customView = tabTwo

        val tabThree = LayoutInflater.from(view.context).inflate(R.layout.custom_tab, null) as TextView
        tabThree.text = "PRIVATE"
        tabThree.setTextColor(resources.getColor(R.color.colorWhite))
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_private_plan_tablayout, 0, 0)
        tabs.getTabAt(2)?.customView = tabThree

        tabs.getTabAt(0)?.icon = ref.getDrawable(R.drawable.ic_people_tablayout)
        tabs.getTabAt(1)?.icon = ref.getDrawable(R.drawable.ic_public_plan_tablayout)
        tabs.getTabAt(2)?.icon = ref.getDrawable(R.drawable.ic_private_plan_tablayout)

    }

    companion object {
        const val TAG = "SearchFragment"
    }
    override fun onDestroy() {
        super.onDestroy()
        ///activity?.extFab!!.visibility = View.VISIBLE                 //uncomment
    }
}
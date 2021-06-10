package com.batanks.nextplan.home.fragment.tabfragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.batanks.nextplan.home.HomePlanPreview
import com.batanks.nextplan.home.fragment.tabfragment.privateplan.PrivatePlanFragment
import com.batanks.nextplan.home.fragment.tabfragment.publicplan.PublicPlanFragment
import com.batanks.nextplan.home.home_tabs.AllHomeFragment
import com.batanks.nextplan.swagger.model.GetEventListHome

class TabsPagerAdapter(fragmentManager: FragmentManager, private var draft : Boolean,  private val eventId : Int?, private val editButtonClicked : Boolean, private val deleteButtonClicked : Boolean,
                       private val listener : PublicPlanFragment.PublicPlanFragmentListener?)
    : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) /*, PublicPlanFragment.PublicPlanFragmentListener*/ {

    private val stringArray = arrayOf("Private", "Public")

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> PublicPlanFragment(draft, eventId, listener, editButtonClicked, deleteButtonClicked, true)
            1 -> PrivatePlanFragment(draft, eventId, null , editButtonClicked, deleteButtonClicked, false)
            else -> PublicPlanFragment(draft, eventId, listener, editButtonClicked, deleteButtonClicked, true)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? = stringArray[position]

    override fun getCount(): Int = stringArray.size

   /* override fun refreshHomeFragmentData(success: Boolean) {

        val frag = getItem(0)

       *//* val allHomeFragment : AllHomeFragment = AllHomeFragment()

        allHomeFragment.refreshData()*//*

        val homePlanPreview : HomePlanPreview = HomePlanPreview()

        homePlanPreview.refreshData()

        *//*if (frag!= null && frag.isVisible && frag.isAdded){

            println("Looks Good")

        } else {

            println("Somethings wrong")
        }*//*
    }*/
}
package com.batanks.nextplan.home.fragment.tabfragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.batanks.nextplan.home.fragment.tabfragment.privateplan.PrivatePlanFragment
import com.batanks.nextplan.home.fragment.tabfragment.publicplan.PublicPlanFragment

class TabsPagerAdapter(fragmentManager: FragmentManager)
    : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val stringArray = arrayOf("Private", "Public")

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> PublicPlanFragment()
            1 -> PrivatePlanFragment()
            else -> PrivatePlanFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? = stringArray[position]

    override fun getCount(): Int = stringArray.size
}
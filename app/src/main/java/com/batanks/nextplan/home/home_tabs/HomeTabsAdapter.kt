package com.batanks.nextplan.home.home_tabs

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class HomeTabsAdapter (fragmentManager: FragmentManager)
    : FragmentPagerAdapter(fragmentManager/*, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT*/) {

    private val stringArray = arrayOf("All","Private","Public")

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> AllHomeFragment()
            1 -> PrivateHomeFragment()
            2 -> PublicHomeFragment()
            else ->{AllHomeFragment()}
        }
    }

    override fun getPageTitle(position: Int): CharSequence? = stringArray[position]

    override fun getCount(): Int = stringArray.size
}
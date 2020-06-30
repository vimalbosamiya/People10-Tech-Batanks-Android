package com.batanks.nextplan.search

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.batanks.nextplan.search.fragments.SearchPeopleFragment
import com.batanks.nextplan.search.fragments.SearchPublicEventFragment

class SearchTabsAdapter (fragmentManager: FragmentManager)
    : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)  {


    private val stringArray = arrayOf("People","Private", "Public")

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> SearchPeopleFragment()
            1 -> SearchPublicEventFragment()
            2 -> SearchPublicEventFragment()
            else -> SearchPeopleFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? = stringArray[position]

    override fun getCount(): Int = stringArray.size
}
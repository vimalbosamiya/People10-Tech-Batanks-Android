package com.batanks.nextplan.notifications

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class NotificationsTabsAdapter (fragmentManager: FragmentManager)
    : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)  {


    private val stringArray = arrayOf("All","Private", "Public")

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> AllNotificationsFragment()
            1 -> PrivateNotificationsFragment()
            2 -> PublicNotificationsFragment()
            else -> AllNotificationsFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? = stringArray[position]

    override fun getCount(): Int = stringArray.size
}
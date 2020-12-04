package com.batanks.nextplan.notifications

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.notifications.viewmodel.NotificationsViewModel
import com.batanks.nextplan.swagger.api.NotificationsAPI
import java.security.AccessController.getContext

class NotificationsTabsAdapter (fragmentManager: FragmentManager)
    : FragmentPagerAdapter(fragmentManager/*, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT*/)  {




    private val stringArray = arrayOf("All","Private","Public")

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> AllNotificationsFragment()
            1 -> PrivateNotificationsFragment()
            2 -> PublicNotificationsFragment()
            else ->{PublicNotificationsFragment()}
        }
    }

    override fun getPageTitle(position: Int): CharSequence? = stringArray[position]

    override fun getCount(): Int = stringArray.size
}
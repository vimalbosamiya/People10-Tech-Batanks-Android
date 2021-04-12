package com.batanks.nextplan.invitationstatus.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.batanks.nextplan.invitationstatus.fragments.DeclineFragment
import com.batanks.nextplan.invitationstatus.fragments.GuestsFragment
import com.batanks.nextplan.invitationstatus.fragments.ParticipantsFragment

class InvitationStatusTabsAdapter (fragmentManager: FragmentManager, private val eventId : Int)
    : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val stringArray = arrayOf("Guests","Participants", "Declin")

    override fun getItem(position: Int): Fragment {

        return when (position) {

            0 ->GuestsFragment(eventId )
            1 -> ParticipantsFragment(eventId)
            2 -> DeclineFragment(eventId)
            else -> GuestsFragment(eventId)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? = stringArray[position]

    override fun getCount(): Int = stringArray.size
}
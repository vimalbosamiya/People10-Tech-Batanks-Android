package com.batanks.nextplan.home.fragment.action

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.batanks.nextplan.R
import com.batanks.nextplan.Settings.Account
import com.batanks.nextplan.arch.BaseDialogFragment
import com.batanks.nextplan.home.fragment.tabfragment.AddActivityFragment
import kotlinx.android.synthetic.main.fragment_add_action.*

class AddActionFragment : BaseDialogFragment() , View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.App_DialogFragment_Theme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_action, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        assignParticipantButton.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.assignParticipantButton -> {
                requireActivity().supportFragmentManager
                        .beginTransaction()
                        .add(AssignPeopleFragment(), AssignPeopleFragment::class.java.canonicalName)
                        .commitAllowingStateLoss()

            }
        }
    }

}
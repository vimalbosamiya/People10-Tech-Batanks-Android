package com.batanks.newplan.home.fragment.tabfragment.privateplan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.batanks.newplan.R
import com.batanks.newplan.arch.BaseFragment

class PrivatePlanFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_private_new_plan, container, false)
    }
}
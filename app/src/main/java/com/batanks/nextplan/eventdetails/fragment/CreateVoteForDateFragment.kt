package com.batanks.nextplan.eventdetails.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseFragment

class CreateVoteForDateFragment : Fragment() {

     override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.fragment_vote_for_date, container, false)

         return view
    }
}
package com.batanks.nextplan.eventdetails.fragment

import android.content.Context
import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseFragment

class CreateVoteForDateMultipleFragment :  Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_vote_for_date_multiple, container, false)

        return view
    }
}
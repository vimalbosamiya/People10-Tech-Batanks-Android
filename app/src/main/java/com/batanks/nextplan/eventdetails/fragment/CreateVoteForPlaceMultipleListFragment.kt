package com.batanks.nextplan.eventdetails.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.eventdetails.adapter.VoteForDateMultipleListAdapter
import com.batanks.nextplan.eventdetails.adapter.VoteForPlaceMultipleListAdapter
import com.batanks.nextplan.eventdetails.dataclass.MultipleDateDisplay
import com.batanks.nextplan.eventdetails.dataclass.MultiplePlaceDisplay

class CreateVoteForPlaceMultipleListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_vote_for_place_multiple_list, container, false)

       /* val recyclerView = view.findViewById<RecyclerView>(R.id.VoteForPlaceMultipleRecyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        val places = ArrayList<MultiplePlaceDisplay>()

        places.add(MultiplePlaceDisplay(1,"First Place","Bangalore","People10, Bangalore",
                10))
        places.add(MultiplePlaceDisplay(2,"Second Place","Chennai","People10 Chennai",
                20))
        places.add(MultiplePlaceDisplay(3,"Third Place","Hyderabad","People10 Hyderabad",
                30))
         *//*places.add(MultiplePlaceDisplay(4,"Fourth Place","Mumbai","People10 Mumbai",
                 40))*//*

        val adapter = VoteForPlaceMultipleListAdapter(places,view.context)

        recyclerView?.adapter = adapter*/


        return view
    }
}
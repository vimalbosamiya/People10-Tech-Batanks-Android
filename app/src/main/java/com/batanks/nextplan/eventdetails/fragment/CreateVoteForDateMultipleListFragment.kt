package com.batanks.nextplan.eventdetails.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.eventdetails.adapter.VoteForDateMultipleListAdapter
import com.batanks.nextplan.eventdetails.dataclass.MultipleDateDisplay

class CreateVoteForDateMultipleListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_vote_for_date_multiple_list, container, false)

      /*  view.setOnClickListener {

            Toast.makeText(context,"CreateVoteForDateMultipleListFragment",Toast.LENGTH_LONG).show()
        }*/

        val recyclerView = view.findViewById<RecyclerView>(R.id.VoteForDateMultipleRecyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        val dates = ArrayList<MultipleDateDisplay>()

        dates.add(MultipleDateDisplay(1,"First date","Thu, Mar 13 2019","Thu, Mar 13 2019 09:00 am",
                "Wed, Mar 14 2019 06:00 pm",10))
        dates.add(MultipleDateDisplay(2,"Second date","Fri, Mar 16 2019","Thu, Mar 16 2019 09:00 am",
                "Wed, Mar 17 2019 06:00 pm",20))
        dates.add(MultipleDateDisplay(3,"Third date","Sat, Mar 17 2019","Thu, Mar 17 2019 09:00 am",
                "Wed, Mar 18 2019 06:00 pm",30))
       /* dates.add(MultipleDateDisplay(4,"Fourth date","Sun, Mar 19 2019","Thu, Mar 19 2019 09:00 am",
                "Wed, Mar 20 2019 06:00 pm",40))*/

        val adapter = VoteForDateMultipleListAdapter(dates,view.context)

        recyclerView?.adapter = adapter

        return view
    }
}
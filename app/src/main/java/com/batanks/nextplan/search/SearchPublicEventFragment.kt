package com.batanks.nextplan.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseFragment
import com.batanks.nextplan.home.adapter.HomePlanPreviewAdapter

class SearchPublicEventFragment : BaseFragment() {

    lateinit var eventsRecyclerView : RecyclerView
    lateinit var eventsAdapter : HomePlanPreviewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.fragment_search_public_event, container, false)

        eventsRecyclerView = view.findViewById(R.id.publicEventRecyclerView)
        eventsRecyclerView.layoutManager = LinearLayoutManager(activity)

        eventsRecyclerView.adapter = HomePlanPreviewAdapter(listOf<String>())

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)


    }

    private fun initView(){

        initRecyclerView()

    }

    private fun setUpDummyData(){

    }

    private fun initRecyclerView() {

        setUpDummyData();

    }
}
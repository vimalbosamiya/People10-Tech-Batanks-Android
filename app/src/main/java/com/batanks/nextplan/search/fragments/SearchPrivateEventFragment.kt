package com.batanks.nextplan.search.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseFragment
import com.batanks.nextplan.arch.response.Status
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.common.getLoadingDialog
/*import com.batanks.nextplan.home.adapter.HomePlanPreviewAdapter*/
import com.batanks.nextplan.home.viewmodel.HomePlanPreviewViewModel
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.search.viewmodel.SearchViewModel
import com.batanks.nextplan.swagger.api.EventAPI
import com.batanks.nextplan.swagger.api.SearchAPI
import com.batanks.nextplan.swagger.model.EventList
import com.batanks.nextplan.swagger.model.GetEventListHome
import com.batanks.nextplan.swagger.model.InlineResponse2002

class SearchPrivateEventFragment : BaseFragment() {

    lateinit var eventsRecyclerView : RecyclerView
    //lateinit var eventsAdapter : HomePlanPreviewAdapter

    private val homePlanPreviewViewModel: HomePlanPreviewViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            getContext()?.let {
                RetrofitClient.getRetrofitInstance(it)?.create(EventAPI::class.java)?.let {
                    HomePlanPreviewViewModel(it)
                }
            }
        }).get(HomePlanPreviewViewModel::class.java)
    }

    private val searchViewModel: SearchViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            getContext()?.let {
                RetrofitClient.getRetrofitInstance(it)?.create(SearchAPI::class.java)?.let {
                    SearchViewModel(it)
                }
            }
        }).get(SearchViewModel::class.java)
    }

    lateinit var eventList : ArrayList<GetEventListHome>
    lateinit var searchList : ArrayList<GetEventListHome>
    val privateEventList :  ArrayList<GetEventListHome> = arrayListOf()

    var recyclerView: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.fragment_search_public_event_issue, container, false)

        eventsRecyclerView = view.findViewById(R.id.publicEventRecyclerView)
        eventsRecyclerView.layoutManager = LinearLayoutManager(activity)

        //eventsRecyclerView.adapter = HomePlanPreviewAdapter(listOf<String>())

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        loadingDialog = context?.getLoadingDialog(0, R.string.loading_list_please_wait, theme = R.style.AlertDialogCustom)

        homePlanPreviewViewModel.eventList()

        homePlanPreviewViewModel.responseLiveData.observe(viewLifecycleOwner, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()

                    homePlanPreviewViewModel.response = response.data as InlineResponse2002

                    eventList = homePlanPreviewViewModel.response!!.results

                    for (item in eventList){

                        if(item.private == true){

                            privateEventList.add(item)
                        }
                    }

                    //eventsRecyclerView?.adapter = HomePlanPreviewAdapter(privateEventList)

                    //eventAdapter.notifyDataSetChanged()
                    //var events_list = listOf(response.data as EventList)
                    //var res : EventListResponse = response.data as EventListResponse
                    //println(response.data )
                    //println(eventList)
                    //var events_list = res.results
                    //eventList = res.results
                    //if(events_list != null)
                    //recyclerView?.adapter = HomePlanPreviewAdapter(listOf<String>())
                    //recyclerView?.adapter = HomePlanPreviewAdapter(events_list)
                    //println(events_list)

                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })
    }
}
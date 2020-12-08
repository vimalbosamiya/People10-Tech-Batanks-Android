package com.batanks.nextplan.home.home_tabs

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
import com.batanks.nextplan.home.adapter.HomePlanPreviewAdapter
import com.batanks.nextplan.home.viewmodel.HomePlanPreviewViewModel
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.swagger.api.EventAPI
import com.batanks.nextplan.swagger.model.GetEventListHome
import com.batanks.nextplan.swagger.model.InlineResponse2002

class AllHomeFragment : BaseFragment(){

    lateinit var recyclerView: RecyclerView
    lateinit var eventList : ArrayList<GetEventListHome>

    private val homePlanPreviewViewModel: HomePlanPreviewViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            getContext()?.let {
                RetrofitClient.getRetrofitInstance(it)?.create(EventAPI::class.java)?.let {
                    HomePlanPreviewViewModel(it)
                }
            }
        }).get(HomePlanPreviewViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.fragment_all_home, container, false)

        loadingDialog = context?.getLoadingDialog(0, R.string.loading_list_please_wait, theme = R.style.AlertDialogCustom)

        recyclerView = view.findViewById(R.id.homeScreenRecyclerView)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(activity)

        showLoader()

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

                    recyclerView?.adapter = HomePlanPreviewAdapter(eventList)

                    //eventAdapter.notifyDataSetChanged()
                    //var events_list = listOf(response.data as EventList)
                    //var res : EventListResponse = response.data as EventListResponse
                    // println(response.data )
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
                    println(response.error?.message.toString())
                }
            }
        })

        return view
    }
}
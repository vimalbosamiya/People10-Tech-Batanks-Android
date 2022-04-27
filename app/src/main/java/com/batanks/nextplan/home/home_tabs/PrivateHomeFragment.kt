package com.batanks.nextplan.home.home_tabs

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.batanks.nextplan.R
import com.batanks.nextplan.Settings.PlanSorting
import com.batanks.nextplan.arch.BaseFragment
import com.batanks.nextplan.arch.response.Status
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.home.adapter.HomePlanPreviewAdapter
import com.batanks.nextplan.home.viewmodel.HomePlanPreviewViewModel
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.swagger.api.EventAPI
import com.batanks.nextplan.swagger.model.GetEventListHome
import com.batanks.nextplan.swagger.model.InlineResponse2002
import kotlinx.android.synthetic.main.fragment_all_home.*

class PrivateHomeFragment : BaseFragment()/*, HomePlanPreviewAdapter.HomePlanPreviewAdapterListener*/ {

    lateinit var recyclerView: RecyclerView
    private var isViewShown = false
    private val REQUEST_FILTER = 12
    private var filter : String? = null
    private var filterType : String? = null
    private var userId : Int = 0

    private var Apiresponse : InlineResponse2002? = null
    private var eventList : ArrayList<GetEventListHome> = arrayListOf()
    var filteredEventList : ArrayList<GetEventListHome> = arrayListOf()
    private var privateEventList : ArrayList<GetEventListHome> = arrayListOf()

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

        userId  = context?.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE)!!.getInt("ID",0)

        val view = inflater.inflate(R.layout.fragment_all_home, container, false)

        recyclerView = view.findViewById(R.id.homeScreenRecyclerView)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(activity)

        //homePlanPreviewViewModel.eventList()

        homePlanPreviewViewModel.responseLiveData.observe(viewLifecycleOwner, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()

                    privateEventList.clear()

                    Apiresponse = response.data as InlineResponse2002

                    eventList = Apiresponse!!.results

                    for (item in eventList){

                        if(item.private == true){

                            if (!privateEventList.contains(item)){

                                privateEventList.add(item)
                            }
                        }
                    }

                    recyclerView?.adapter = HomePlanPreviewAdapter(true, privateEventList)
                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                    println(response.error?.message.toString())
                }
            }
        })

        //recyclerView?.adapter = HomePlanPreviewAdapter(HomePlanPreviewViewModel.privateEventList/*,this*/)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pullToRefresh.setOnRefreshListener(object: SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {

                if (filteredEventList.size == 0){

                    homePlanPreviewViewModel.eventList()

                } else {

                    homePlanPreviewViewModel.eventList()

                    recyclerView?.adapter = HomePlanPreviewAdapter(true, filteredEventList)

                    //Collections.shuffle(filteredEventList)
                }

                //homePlanPreviewViewModel.eventList()

                pullToRefresh.setRefreshing(false)
            }
        })

        filterIconAll.setOnClickListener {

            val  intent = Intent(view.context, PlanSorting:: class.java)

            if (!filter.isNullOrEmpty()) {

                intent.putExtra("S_FILTER", filter)
                intent.putExtra("S_FILTER_TYPE", filterType)
            }
            //startActivity(intent)
            startActivityForResult(intent, REQUEST_FILTER)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        filteredEventList.clear()

        if (requestCode == REQUEST_FILTER && resultCode == Activity.RESULT_OK) {

            filter = data?.getStringExtra("FILTER")
            filterType = data?.getStringExtra("FILTER_TYPE")

            println(filter + filterType)

//            filteredEventList.clear()

            if (!filter.isNullOrEmpty()){

                if (filterType == "CATEGORY"){

                    for(item in privateEventList){

                        if (item.category?.name == filter){

                            filteredEventList.add(item)

                        } /*else {

                            filteredEventList.remove(item)
                        }*/
                    }

                    recyclerView?.adapter = HomePlanPreviewAdapter(true, filteredEventList)

                } else if (filterType == "FOLLOWUP"){

                    for(item in privateEventList){

                        if (item.title.contains(filter!!)){

                            filteredEventList.add(item)

                        } /*else {

                            filteredEventList.remove(item)
                        }*/
                    }

                    recyclerView?.adapter = HomePlanPreviewAdapter(true, filteredEventList)

                } else if (filterType == "EVENTTYPE"){

                    if (filter == "Drafts" || filter == "Brouillon"){

                        for(item in privateEventList){

                            if (item.draft == true){

                                filteredEventList.add(item)

                            } /*else {

                            filteredEventList.remove(item)
                        }*/
                        }

                        recyclerView?.adapter = HomePlanPreviewAdapter(true, filteredEventList)

                    } else if (filter == "Mines" || filter == "Mes plans crées"){

                        for(item in privateEventList){

                            if (item.creator.id == userId && item.draft == false){

                                filteredEventList.add(item)

                            } /*else {

                            filteredEventList.remove(item)
                        }*/
                        }

                        recyclerView?.adapter = HomePlanPreviewAdapter(true, filteredEventList)

                    } else if (filter == "Invited to" || filter == " Mes invitations reçues"){

                        for(item in privateEventList){

                            if (item.creator.id != userId && item.draft == false){

                                filteredEventList.add(item)

                            } /*else {

                            filteredEventList.remove(item)
                        }*/
                        }

                        recyclerView?.adapter = HomePlanPreviewAdapter(true, filteredEventList)
                    }
                }
            }else {

                recyclerView?.adapter = HomePlanPreviewAdapter(true, eventList)
            }
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        if (isVisibleToUser && isResumed()){

            onResume()

        } else{

            println("Private visible text set to null")
        }

        /*if (view != null && isVisibleToUser) {

            isViewShown = true
        } else {
            isViewShown = false
        }*/
    }

    override fun onResume() {
        super.onResume()

        if (!getUserVisibleHint()) { return }

        if (filteredEventList.size == 0){

            homePlanPreviewViewModel.eventList()

        } else {

            recyclerView?.adapter = HomePlanPreviewAdapter(true, filteredEventList)
        }
    }

    /*override fun openPlanFragment(getEventListHome: GetEventListHome) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }*/
}
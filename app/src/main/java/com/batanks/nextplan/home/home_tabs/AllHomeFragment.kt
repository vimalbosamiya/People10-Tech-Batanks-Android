package com.batanks.nextplan.home.home_tabs

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
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
import com.batanks.nextplan.search.viewmodel.SearchViewModel
import com.batanks.nextplan.swagger.api.EventAPI
import com.batanks.nextplan.swagger.api.SearchAPI
import com.batanks.nextplan.swagger.model.GetEventListHome
import com.batanks.nextplan.swagger.model.InlineResponse2002
import kotlinx.android.synthetic.main.fragment_all_home.*
import kotlin.collections.ArrayList


class AllHomeFragment : BaseFragment()/*, PublicPlanFragment.PublicPlanFragmentListener*/ /*, HomePlanPreviewAdapter.HomePlanPreviewAdapterListener*/{

    lateinit var recyclerView: RecyclerView
    private var isViewShown = false
    private val REQUEST_FILTER = 12
    private var filter : String? = null
    private var filterType : String? = null
    private var userId : Int = 0

    var eventList : ArrayList<GetEventListHome> = arrayListOf()
    var filteredEventList : ArrayList<GetEventListHome> = arrayListOf()
    var dummyFilteredEventList : ArrayList<GetEventListHome> = arrayListOf()

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        userId  = context?.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE)!!.getInt("ID",0)

        val view = inflater.inflate(R.layout.fragment_all_home, container, false)

        //loadingDialog = context?.getLoadingDialog(0, R.string.loading_list_please_wait, theme = R.style.AlertDialogCustom)

        recyclerView = view.findViewById(R.id.homeScreenRecyclerView)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(activity)

        showLoader()

        //homePlanPreviewViewModel.eventList()

        homePlanPreviewViewModel.responseLiveData.observe(viewLifecycleOwner, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()

                    homePlanPreviewViewModel.response = response.data as InlineResponse2002

                    eventList = homePlanPreviewViewModel.response!!.results

                   /* if (eventList.size <= 1) {
                        val params = recyclerView.getLayoutParams() as ConstraintLayout.LayoutParams
                        params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                        recyclerView.setLayoutParams(params)
                    }*/

                    /*for (item in homePlanPreviewViewModel.eventList){

                        if(item.private == true){

                            if (!HomePlanPreviewViewModel.privateEventList.contains(item)){

                                HomePlanPreviewViewModel.privateEventList.add(item)
                            }

                        } else if(item.private != true){

                            if (!HomePlanPreviewViewModel.publicEventList.contains(item)){

                                HomePlanPreviewViewModel.publicEventList.add(item)
                            }
                        }
                    }*/

                    recyclerView?.adapter = HomePlanPreviewAdapter(true, eventList)
                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                    println(response.error?.message.toString())
                }
            }
        })

      /*  searchViewModel.apiSearchListWithType(getString(R.string.all))

        searchViewModel.responseLiveData.observe(viewLifecycleOwner, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()

                    searchViewModel.response = response.data as InlineResponse2002

                    eventList = searchViewModel.response!!.results

                    recyclerView?.adapter = HomePlanPreviewAdapter(eventList)
                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                    println(response.error?.message.toString())
                }
            }
        })*/

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

                           filter = null

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

        if (requestCode == REQUEST_FILTER && resultCode == RESULT_OK) {

           filter = data?.getStringExtra("FILTER")
           filterType = data?.getStringExtra("FILTER_TYPE")

            println(filter + filterType)

//            filteredEventList.clear()

            if (!filter.isNullOrEmpty()){

                if (filterType == "CATEGORY"){

                    for(item in eventList){

                        if (item.category?.name == filter){

                            filteredEventList.add(item)

                        } /*else {

                            filteredEventList.remove(item)
                        }*/
                    }

                    recyclerView?.adapter = HomePlanPreviewAdapter(true, filteredEventList)

                } else if (filterType == "FOLLOWUP"){

                    for(item in eventList){

                        if (item.title.contains(filter!!)){

                            filteredEventList.add(item)

                        } /*else {

                            filteredEventList.remove(item)
                        }*/
                    }

                    recyclerView?.adapter = HomePlanPreviewAdapter(true, filteredEventList)

                } else if (filterType == "EVENTTYPE"){

                    if (filter == "Drafts" || filter == "Brouillon"){

                        for(item in eventList){

                            if (item.draft == true){

                                filteredEventList.add(item)

                            } /*else {

                            filteredEventList.remove(item)
                        }*/
                        }

                        recyclerView?.adapter = HomePlanPreviewAdapter(true, filteredEventList)

                    } else if (filter == "Mines" || filter == "Mes plans crées"){

                        for(item in eventList){

                            if (item.creator.id == userId && item.draft == false){

                                filteredEventList.add(item)

                            } /*else {

                            filteredEventList.remove(item)
                        }*/
                        }

                        recyclerView?.adapter = HomePlanPreviewAdapter(true, filteredEventList)

                    } else if (filter == "Invited to" || filter == " Mes invitations reçues"){

                        for(item in eventList){

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

            println("All visible text set to null")
        }

        /*if (view != null && isVisibleToUser) {

            isViewShown = true

            println("All Home Frag Visible ")

        } else {
            isViewShown = false

            println("All Home Frag Not Visible ")
        }*/
    }

    fun updateList(isSuccess : Boolean ){

        if (isSuccess == true){

            homePlanPreviewViewModel.eventList()
        }
    }

    public fun refreshData(){

        onResume()
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

  /*  override fun refreshHomeFragmentData(success: Boolean) {

        println("came to All Home Fragment")
    }*/

    /*override fun refreshHomeFragmentData() {

        homePlanPreviewViewModel.eventList()
    }*/

    /*override fun openPlanFragment(getEventListHome: GetEventListHome) {

        //requireActivity().supportFragmentManager.beginTransaction().add(R.id.draftFrameLayout,PublicPlanFragment(),PublicPlanFragment::class.java.canonicalName).commitAllowingStateLoss()
        //draftFrameLayout.visibility = View.VISIBLE
        //requireActivity().supportFragmentManager.beginTransaction().add(R.id.frameLayout, CreatePlanFragment(), CreatePlanFragment.TAG).addToBackStack(CreatePlanFragment.TAG).commit()
        //frameLayout.visibility = View.VISIBLE
        println("from All home fragment")
    }*/
}
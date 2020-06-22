package com.batanks.nextplan.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.Settings.Settings
import com.batanks.nextplan.arch.BaseAppCompatActivity
import com.batanks.nextplan.arch.response.Status
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.common.getLoadingDialog
import com.batanks.nextplan.eventdetailsadmin.EventDetailViewAdmin
import com.batanks.nextplan.home.adapter.HomePlanPreviewAdapter
import com.batanks.nextplan.home.fragment.CreatePlanFragment
import com.batanks.nextplan.search.SearchFragment
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.home.viewmodel.HomePlanPreviewViewModel
import com.batanks.nextplan.swagger.api.EventAPI
import kotlinx.android.synthetic.main.activity_home.*

class HomePlanPreview : BaseAppCompatActivity(), View.OnClickListener {

    var recyclerView: RecyclerView? = null

    private val homePlanPreviewViewModel: HomePlanPreviewViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            RetrofitClient.getRetrofitInstance(this)?.create(EventAPI::class.java)?.let {
                HomePlanPreviewViewModel(it)
            }
        }).get(HomePlanPreviewViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val toolbar: Toolbar = findViewById(R.id.customToolBar)
        setSupportActionBar(toolbar)

        loadingDialog = this.getLoadingDialog(0, R.string.loading_list_please_wait, theme = R.style.AlertDialogCustom)

        recyclerView = findViewById(R.id.homeScreenRecyclerView)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(this)

        recyclerView?.adapter = HomePlanPreviewAdapter(listOf<String>())


        /*showLoader()
        homePlanPreviewViewModel.getHomePlanEvent()*/

        homePlanPreviewViewModel.responseLiveData.observe(this, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    //hideLoader()
                    //var events_list = listOf(response.data as EventList)
                    //if(events_list != null)
                    //recyclerView?.adapter = HomePlanPreviewAdapter(listOf<EventList>())
                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })

        extFab.setOnClickListener(this)
        img_settings.setOnClickListener(this)
        search.setOnClickListener(this)

        filterIcon.setOnClickListener {

            intent = Intent(this, EventDetailViewAdmin:: class.java)
            startActivity(intent)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.extFab -> {
                frameLayout.visibility = View.VISIBLE
                extFab.visibility = View.GONE
                supportFragmentManager.beginTransaction()
                        .add(R.id.frameLayout, CreatePlanFragment())
                        .addToBackStack(CreatePlanFragment.TAG).commit()
            }
            R.id.img_settings -> {
                intent = Intent(this, Settings :: class.java)
                startActivity(intent)
            }

            R.id.search -> {

                frameLayout.visibility = View.VISIBLE
                extFab.visibility = View.GONE
                supportFragmentManager.beginTransaction()
                        .add(R.id.frameLayout, SearchFragment())
                        .addToBackStack(SearchFragment.TAG).commit()
            }
        }
    }
}
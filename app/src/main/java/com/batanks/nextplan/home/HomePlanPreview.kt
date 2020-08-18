package com.batanks.nextplan.home

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.Settings.Plan_Sorting
import com.batanks.nextplan.Settings.Settings
import com.batanks.nextplan.arch.BaseAppCompatActivity
import com.batanks.nextplan.arch.response.Status
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.common.getLoadingDialog
import com.batanks.nextplan.home.adapter.HomePlanPreviewAdapter
import com.batanks.nextplan.home.fragment.CreatePlanFragment
import com.batanks.nextplan.search.fragments.SearchFragment
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.home.viewmodel.HomePlanPreviewViewModel
import com.batanks.nextplan.notifications.NotificationsFragment
import com.batanks.nextplan.swagger.api.EventAPI
import com.batanks.nextplan.swagger.model.EventList
import com.batanks.nextplan.swagger.model.EventListResponse
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_home.*
import org.json.JSONObject

class HomePlanPreview : BaseAppCompatActivity(), View.OnClickListener {

    var recyclerView: RecyclerView? = null
    lateinit var eventRecyclerView : RecyclerView
    lateinit var eventAdapter : HomePlanPreviewAdapter
    lateinit var eventList : List<EventList>

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
        //recyclerView?.adapter = HomePlanPreviewAdapter(listOf())
        //recyclerView?.adapter = HomePlanPreviewAdapter(listOf<String>())
        //initRecyclerViews()

        showLoader()

        homePlanPreviewViewModel.getHomePlanEvent()

        homePlanPreviewViewModel.responseLiveData.observe(this, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()

                    homePlanPreviewViewModel.response = response.data as EventListResponse

                    eventList = homePlanPreviewViewModel.response!!.results

                    recyclerView?.adapter = HomePlanPreviewAdapter(eventList)

                    //eventAdapter.notifyDataSetChanged()
                    //var events_list = listOf(response.data as EventList)
                    //var res : EventListResponse = response.data as EventListResponse

                    println(eventList)

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

        //initRecyclerViews()

        extFab.setOnClickListener(this)
        img_settings.setOnClickListener(this)
        search.setOnClickListener(this)
        notification.setOnClickListener(this)

        filterIcon.setOnClickListener {

            intent = Intent(this, Plan_Sorting:: class.java)
            startActivity(intent)
        }
    }

    /*private fun setUpData(){

        eventAdapter = HomePlanPreviewAdapter(eventList)
        recyclerView?.adapter = eventAdapter

        println(eventList)

    }

    private fun initRecyclerViews() {

        *//*eventRecyclerView = findViewById(R.id.homeScreenRecyclerView)
        eventRecyclerView.layoutManager = LinearLayoutManager(this)*//*

        recyclerView = findViewById(R.id.homeScreenRecyclerView)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(this)

        setUpData()
    }*/

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v: View? = getCurrentFocus()
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm?.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
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

            R.id.notification -> {

                frameLayout.visibility = View.VISIBLE
                extFab.visibility = View.GONE
                supportFragmentManager.beginTransaction()
                        .add(R.id.frameLayout, NotificationsFragment())
                        .addToBackStack(NotificationsFragment.TAG).commit()
            }
        }
    }
}
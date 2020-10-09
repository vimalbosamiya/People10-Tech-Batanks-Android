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
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.Settings.Plan_Sorting
import com.batanks.nextplan.Settings.Settings
import com.batanks.nextplan.Settings.viewmodel.ProfileModel
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
import com.batanks.nextplan.swagger.api.AuthenticationAPI
import com.batanks.nextplan.swagger.api.EventAPI
import com.batanks.nextplan.swagger.model.*
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_home.*
import org.json.JSONObject

class HomePlanPreview : BaseAppCompatActivity(), View.OnClickListener {



    lateinit var recyclerView: RecyclerView
    lateinit var eventRecyclerView : RecyclerView
    lateinit var eventAdapter : HomePlanPreviewAdapter
    lateinit var eventList : List<EventList>

    var user_obj : User? = null

    private val homePlanPreviewViewModel: HomePlanPreviewViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            RetrofitClient.getRetrofitInstance(this)?.create(EventAPI::class.java)?.let {
                HomePlanPreviewViewModel(it)
            }
        }).get(HomePlanPreviewViewModel::class.java)
    }

    private val profileViewModel: ProfileModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            RetrofitClient.getRetrofitInstance(this)?.create(AuthenticationAPI::class.java)?.let {
                ProfileModel(it)
            }
        }).get(ProfileModel::class.java)
    }

    override fun onResume(){
        super.onResume()

        //Toast.makeText(this,"On Resume is called", Toast.LENGTH_LONG).show()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        ModelPreferencesManager.with(this)

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
        //homePlanPreviewViewModel.eventList()

        homePlanPreviewViewModel.responseLiveData.observe(this, Observer { response ->

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
                    println(response.data )
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

        profileViewModel.getUserProfile()

        profileViewModel.responseLiveData.observe(this, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()
                    user_obj = response.data as User

                    val userName: String? = user_obj?.username
                    val firstName: String? = user_obj?.first_name
                    val lastName: String? = user_obj?.last_name
                    val email: String? = user_obj?.email
                    val phoneNumber: String? = user_obj?.phone_number

                    ModelPreferencesManager.put(user_obj, "USER_DATA")

                    getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putString("USERNAME", userName).apply()
                    getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putString("FIRSTNAME", firstName).apply()
                    getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putString("LASTNAME", lastName).apply()
                    getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putString("EMAIL", email).apply()
                    getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putString("PHONENUMBER", phoneNumber).apply()

                }
                Status.ERROR -> {
                    hideLoader()
                    Toast.makeText(context() , "Something went wrong", Toast.LENGTH_SHORT).show()
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
            finish()
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

                /*finish();
                overridePendingTransition( 0, 0);
                Intent.FLAG_ACTIVITY_NO_ANIMATION
                startActivity(getIntent());
                overridePendingTransition( 0, 0);*/

                frameLayout.visibility = View.VISIBLE
                extFab.visibility = View.GONE
                supportFragmentManager.beginTransaction()
                        .add(R.id.frameLayout, CreatePlanFragment(),CreatePlanFragment.TAG)
                        .addToBackStack(CreatePlanFragment.TAG).commit()

//                recyclerView?.adapter?.notifyItemRangeChanged(0, eventList.size);

                //notifyDataSetChange()
            }
            R.id.img_settings -> {

                intent = Intent(this, Settings :: class.java)
                startActivity(intent)
                finish()
            }

            R.id.search -> {

                frameLayout.visibility = View.VISIBLE
                extFab.visibility = View.GONE
                supportFragmentManager.beginTransaction()
                        .add(R.id.frameLayout, SearchFragment())
                        .addToBackStack(SearchFragment.TAG).commit()

               // recyclerView?.adapter?.notifyItemRangeChanged(0, eventList.size);
                //val eventListCopy : List<EventList>
                /*val eventListCopyItem = mutableListOf<EventList>().apply {

                    addAll(eventList)
                }
                eventList = listOf()

                eventList = eventListCopyItem

                recyclerView?.adapter?.notifyDataSetChanged()*/


                //notifyDataSetChange()
            }

            R.id.notification -> {

                frameLayout.visibility = View.VISIBLE
                extFab.visibility = View.GONE
                supportFragmentManager.beginTransaction()
                        .add(R.id.frameLayout, NotificationsFragment())
                        .addToBackStack(NotificationsFragment.TAG).commit()

               // notifyDataSetChange()
            }
        }
    }

   internal fun notifyDataSetChange(){

       //supportFragmentManager.beginTransaction().remove(CreatePlanFragment()).commit()

        recyclerView = findViewById(R.id.homeScreenRecyclerView)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(this)

        homePlanPreviewViewModel.getHomePlanEvent()

        homePlanPreviewViewModel.responseLiveData.observe(this, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    //showLoader()
                }
                Status.SUCCESS -> {
                    //hideLoader()

                    homePlanPreviewViewModel.response = response.data as InlineResponse2002

                    eventList = homePlanPreviewViewModel.response!!.results

                    recyclerView?.adapter = HomePlanPreviewAdapter(eventList)

                    (recyclerView.adapter as HomePlanPreviewAdapter).notifyDataSetChanged()



                    //eventAdapter.notifyDataSetChanged()
                    //var events_list = listOf(response.data as EventList)
                    //var res : EventListResponse = response.data as EventListResponse
                    println(response.data )
                    println(eventList)

                    //var events_list = res.results
                    //eventList = res.results
                    //if(events_list != null)
                    //recyclerView?.adapter = HomePlanPreviewAdapter(listOf<String>())
                    //recyclerView?.adapter = HomePlanPreviewAdapter(events_list)
                    //println(events_list)

                }
                Status.ERROR -> {
                    //hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })
    }
}
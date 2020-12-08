package com.batanks.nextplan.home

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.GONE
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.Settings.Settings
import com.batanks.nextplan.arch.BaseAppCompatActivity
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.home.adapter.HomePlanPreviewAdapter
import com.batanks.nextplan.home.home_tabs.HomeTabsAdapter
import com.batanks.nextplan.search.fragments.SearchFragment
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.home.viewmodel.HomePlanPreviewViewModel
import com.batanks.nextplan.notifications.NotificationsFragment
import com.batanks.nextplan.swagger.api.EventAPI
import com.batanks.nextplan.swagger.model.*
import kotlinx.android.synthetic.main.activity_home.*

class HomePlanPreview : BaseAppCompatActivity(), View.OnClickListener {

    lateinit var recyclerView: RecyclerView
    lateinit var eventRecyclerView : RecyclerView
    lateinit var eventAdapter : HomePlanPreviewAdapter
    lateinit var eventList : ArrayList<GetEventListHome>

    //var user_obj : User? = null

    private val homePlanPreviewViewModel: HomePlanPreviewViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            RetrofitClient.getRetrofitInstance(this)?.create(EventAPI::class.java)?.let {
                HomePlanPreviewViewModel(it)
            }
        }).get(HomePlanPreviewViewModel::class.java)
    }

    /*private val profileViewModel: ProfileModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            RetrofitClient.getRetrofitInstance(this)?.create(AuthenticationAPI::class.java)?.let {
                ProfileModel(it)
            }
        }).get(ProfileModel::class.java)
    }*/

    override fun onResume(){
        super.onResume()

        //Toast.makeText(this,"On Resume is called", Toast.LENGTH_LONG).show()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        ModelPreferencesManager.with(this)

        val toolbar: Toolbar = findViewById(R.id.homeToolBar)
        setSupportActionBar(toolbar)              //uncomment

        val tabsPagerAdapter = HomeTabsAdapter(supportFragmentManager)
        home_view_pager.adapter = tabsPagerAdapter

        homeTabs.setupWithViewPager(home_view_pager)

        val tabOne = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as TextView
        tabOne.text = getString(R.string.all)
        tabOne.textSize = 16F
        tabOne.setTextColor(resources.getColor(R.color.colorWhite))
        //tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_people_tablayout, 0, 0)
        homeTabs.getTabAt(0)?.customView = tabOne

        val tabTwo = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as TextView
        tabTwo.text = getString(R.string._private)
        tabTwo.textSize = 16F
        tabTwo.setTextColor(resources.getColor(R.color.colorWhite))
        //tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_public_plan_tablayout, 0, 0)
        homeTabs.getTabAt(1)?.customView = tabTwo

        val tabThree = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as TextView
        tabThree.text = getString(R.string._public)
        tabThree.textSize = 16F
        tabThree.setTextColor(resources.getColor(R.color.colorWhite))
        //tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_private_plan_tablayout, 0, 0)
        homeTabs.getTabAt(2)?.customView = tabThree

        homeTabs.getTabAt(0)?.icon = getDrawable(R.drawable.ic_people_tablayout)
        homeTabs.getTabAt(1)?.icon = getDrawable(R.drawable.ic_private_plan_tablayout)
        homeTabs.getTabAt(2)?.icon = getDrawable(R.drawable.ic_public_plan_tablayout)




        /*loadingDialog = this.getLoadingDialog(0, R.string.loading_list_please_wait, theme = R.style.AlertDialogCustom)

        recyclerView = findViewById(R.id.homeScreenRecyclerView)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(this)*/

        //recyclerView?.adapter = HomePlanPreviewAdapter(listOf())
        //recyclerView?.adapter = HomePlanPreviewAdapter(listOf<String>())
        //initRecyclerViews()

        ///showLoader()         //uncomment

        /*profileViewModel.getUserProfile()

        profileViewModel.responseLiveData.observe(this, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()
                    user_obj = response.data as User

                    val id: Int? = user_obj?.id
                    val userName: String? = user_obj?.username
                    val firstName: String? = user_obj?.first_name
                    val lastName: String? = user_obj?.last_name
                    val email: String? = user_obj?.email
                    val phoneNumber: String? = user_obj?.phone_number

                    ModelPreferencesManager.put(user_obj, "USER_DATA")

                    getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE).edit().putInt("ID", id!!).apply()
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
        })*/

        ///homePlanPreviewViewModel.eventList()         //uncomment
        //homePlanPreviewViewModel.eventList()

       /* homePlanPreviewViewModel.responseLiveData.observe(this, Observer { response ->

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
        })*/            //uncomment

        //initRecyclerViews()

        ///extFab.setOnClickListener(this)          //uncomment
        img_settings.setOnClickListener(this)
        search.setOnClickListener(this)
        notification.setOnClickListener(this)

        /*filterIcon.setOnClickListener {

            intent = Intent(this, Plan_Sorting:: class.java)
            startActivity(intent)
            finish()
        }*/     //uncomment
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
            /*R.id.extFab -> {

                *//*finish();
                overridePendingTransition( 0, 0);
                Intent.FLAG_ACTIVITY_NO_ANIMATION
                startActivity(getIntent());
                overridePendingTransition( 0, 0);*//*

                *//*frameLayout.visibility = View.VISIBLE
                extFab.visibility = View.GONE
                supportFragmentManager.beginTransaction()
                        .add(R.id.frameLayout, CreatePlanFragment(),CreatePlanFragment.TAG)
                        .addToBackStack(CreatePlanFragment.TAG).commit()*//*  //uncomment

//                recyclerView?.adapter?.notifyItemRangeChanged(0, eventList.size);

                //notifyDataSetChange()
            }*/             //uncomment

            R.id.img_settings -> {

                intent = Intent(this, Settings :: class.java)
                startActivity(intent)
                finish()
            }                        //uncomment

            R.id.search -> {

                frameLayout.visibility = View.VISIBLE
                ///extFab.visibility = View.GONE            //uncomment
                supportFragmentManager.beginTransaction()
                        .add(R.id.frameLayout, SearchFragment())
                        .addToBackStack(SearchFragment.TAG).commit()

                appBarLayout.visibility = GONE

               // recyclerView?.adapter?.notifyItemRangeChanged(0, eventList.size);
                //val eventListCopy : List<EventList>
                /*val eventListCopyItem = mutableListOf<EventList>().apply {

                    addAll(eventList)
                }
                eventList = listOf()

                eventList = eventListCopyItem

                recyclerView?.adapter?.notifyDataSetChanged()*/


                //notifyDataSetChange()
            }                     //uncomment

            R.id.notification -> {

                frameLayout.visibility = View.VISIBLE
                ///extFab.visibility = View.GONE                //uncomment
                supportFragmentManager.beginTransaction()
                        .add(R.id.frameLayout, NotificationsFragment())
                        .addToBackStack(NotificationsFragment.TAG).commit()

               // notifyDataSetChange()
            }                 //uncomment
        }
    }

   /*internal fun notifyDataSetChange(){
  if(true){

      return
  }
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
    }*/
}
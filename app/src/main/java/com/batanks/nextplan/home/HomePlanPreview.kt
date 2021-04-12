package com.batanks.nextplan.home

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.GONE
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.batanks.nextplan.R
import com.batanks.nextplan.Settings.Settings
import com.batanks.nextplan.arch.BaseAppCompatActivity
import com.batanks.nextplan.arch.response.Status
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.home.adapter.HomePlanPreviewAdapter
import com.batanks.nextplan.home.fragment.CreatePlanFragment
import com.batanks.nextplan.home.fragment.tabfragment.publicplan.PublicPlanFragment
import com.batanks.nextplan.home.home_tabs.AllHomeFragment
import com.batanks.nextplan.home.home_tabs.HomeTabsAdapter
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.home.viewmodel.HomePlanPreviewViewModel
import com.batanks.nextplan.notifications.Notification
import com.batanks.nextplan.search.Search
import com.batanks.nextplan.swagger.api.EventAPI
import com.batanks.nextplan.swagger.model.*
import kotlinx.android.synthetic.main.activity_home.*

class HomePlanPreview : BaseAppCompatActivity(), View.OnClickListener, PublicPlanFragment.PublicPlanFragmentListener/*, HomePlanPreviewAdapter.HomePlanPreviewAdapterListener*/ {

    var list : InlineResponse2002? = null

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

        homePlanPreviewViewModel.eventList()

        homePlanPreviewViewModel.responseLiveData.observe(this, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()

                    list = response.data as InlineResponse2002

                    if (list?.unread_notifications!! == 0){

                        notification.setImageResource(R.drawable.ic_notifications)

                    } else if(list?.unread_notifications!! > 0){

                        notification.setImageResource(R.drawable.ic_notification_with_alert)
                    }
                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                    println(response.error?.message.toString())
                }
            }
        })

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

       /* pullToRefresh.setOnRefreshListener(object: SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {

                println("working")
                recreate()
                pullToRefresh.setRefreshing(false)
            }
        })*/




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

        extFab.setOnClickListener(this)          //uncomment
        img_settings.setOnClickListener(this)
        search.setOnClickListener(this)
        notification.setOnClickListener(this)

        /*filterIcon.setOnClickListener {

            intent = Intent(this, Plan_Sorting:: class.java)
            startActivity(intent)
            finish()
        }*/     //uncomment
    }

    override fun onBackPressed() {

        showDialog()

        //println("working fine proceed.")
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.exit_pop_up)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        /*val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)*/

        val btn_create_followups_cancel = dialog.findViewById(R.id.btn_create_followups_cancel) as Button
        val btn_create_followups_ok = dialog.findViewById(R.id.btn_create_followups_ok) as Button

        btn_create_followups_cancel.setOnClickListener { dialog.dismiss() }

        btn_create_followups_ok.setOnClickListener {

            dialog.dismiss()

            finishAffinity()
        }

        dialog.show()

        dialog.window?.decorView?.setOnTouchListener { v, event ->

            if (event?.action == MotionEvent.ACTION_DOWN) {

                val imm = v?.getContext()?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                v.clearFocus()
            }
            false
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

               /* finish();
                overridePendingTransition( 0, 0);
                Intent.FLAG_ACTIVITY_NO_ANIMATION
                startActivity(getIntent());
                overridePendingTransition( 0, 0);*/

                val edit: Boolean = false

                extFab.visibility = View.GONE

                supportFragmentManager.beginTransaction()
                        .add(R.id.frameLayout, CreatePlanFragment(edit, null, false, false),CreatePlanFragment.TAG)
                        .addToBackStack(CreatePlanFragment.TAG).commit()  //uncomment

                Handler().postDelayed({

                    frameLayout.visibility = View.VISIBLE
                    appBarLayout.visibility = GONE
                    search.visibility = View.GONE
                    notification.visibility = View.GONE
                    img_settings.visibility = View.GONE

                }, DELAY)



//                recyclerView?.adapter?.notifyItemRangeChanged(0, eventList.size);

                //notifyDataSetChange()
            }             //uncomment

            R.id.img_settings -> {

                intent = Intent(this, Settings :: class.java)
                startActivity(intent)
                finish()
            }                        //uncomment

            R.id.search -> {

                ///extFab.visibility = View.GONE            //uncomment

              /*  frameLayout.visibility = View.VISIBLE
                supportFragmentManager.beginTransaction()
                        .add(R.id.frameLayout, SearchFragment())
                        .addToBackStack(SearchFragment.TAG).commit()

                appBarLayout.visibility = GONE*/

                intent = Intent(this, Search :: class.java)
                startActivity(intent)
                finish()

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

                intent = Intent(this, Notification :: class.java)
                startActivity(intent)
                finish()

                ///extFab.visibility = View.GONE  //uncomment
               /* frameLayout.visibility = View.VISIBLE
                supportFragmentManager.beginTransaction()
                        .add(R.id.frameLayout, NotificationsFragment())
                        .addToBackStack(NotificationsFragment.TAG).commit()*/
                // notifyDataSetChange()
            }         //uncomment
        }
    }


    companion object {
        const val DELAY : Long = 1 * 1000 //times in milliseconds
    }

    /*override fun openPlanFragment(getEventListHome: GetEventListHome) {

        *//*supportFragmentManager.beginTransaction()
                .add(R.id.frameLayout, CreatePlanFragment(),CreatePlanFragment.TAG)
                .addToBackStack(CreatePlanFragment.TAG).commit()  //uncomment
        appBarLayout.visibility = GONE
        frameLayout.visibility = View.VISIBLE
        extFab.visibility = View.GONE*//*

        println("Open called ")
    }*/

    public fun refreshData(){

        /*finish()
       startActivity(getIntent())*/

        println("working")
        recreate()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        println("coming here")
    }

    override fun refreshHomeFragmentData(success: Boolean) {

        println("Came to HomePlanPreview")

        /*finish()
        startActivity(getIntent())*/

        /*val allHomeFragment = AllHomeFragment()
        allHomeFragment.updateList(success)*/
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
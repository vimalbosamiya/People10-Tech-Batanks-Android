package com.batanks.nextplan.Settings

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.Settings.Adapters.Filter_Sorting_Adapter
import com.batanks.nextplan.arch.BaseAppCompatActivity
import com.batanks.nextplan.arch.response.Status
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.home.HomePlanPreview
import com.batanks.nextplan.home.fragment.tabfragment.publicplan.viewmodel.CategoryViewModel
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.swagger.api.CategoryAPI
import com.batanks.nextplan.swagger.model.CategoryList
import com.batanks.nextplan.swagger.model.Filter
import com.batanks.nextplan.swagger.model.FilterType
import com.batanks.nextplan.swagger.model.InlineResponse200
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_plan_sorting.*
import java.lang.reflect.Type

class PlanSorting : BaseAppCompatActivity(), View.OnClickListener, Filter_Sorting_Adapter.PlanFilterCallBack {

    val filters : ArrayList<Filter> = arrayListOf()
    var selectedFilter : Filter? = null

    var sFilter : String? = null
    var sFilterType : String? = null

    lateinit var rv_category : RecyclerView
    lateinit var rv_filters : RecyclerView
    lateinit var rv_follow_ups : RecyclerView

    private val completeList : ArrayList<String> = arrayListOf()

    lateinit var categoryList : List<CategoryList>
    var followUpList : ArrayList<String> = arrayListOf()

    lateinit var adapter : Filter_Sorting_Adapter
    lateinit var rl_plan_sort_followups : RelativeLayout

    private val categoryViewModel: CategoryViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            RetrofitClient.getRetrofitInstance(this)?.create(CategoryAPI::class.java)?.let {
                CategoryViewModel(it)
            }
        }).get(CategoryViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan_sorting)

        sFilter = intent.getStringExtra("S_FILTER")
        sFilterType = intent.getStringExtra("S_FILTER_TYPE")

        //loadingDialog = this.getLoadingDialog(0, R.string.loading_list_please_wait, theme = R.style.AlertDialogCustom)

        rv_category = findViewById(R.id.rv_category)
        rv_category.layoutManager = LinearLayoutManager(this)

        rv_filters = findViewById(R.id.rv_filters)
        rv_filters.layoutManager = LinearLayoutManager(this)

        rv_follow_ups = findViewById(R.id.rv_follow_ups)
        rv_follow_ups.layoutManager = LinearLayoutManager(this)

        /*rl_plan_sort_followups = findViewById(R.id.rl_plan_sort_followups)
        rl_plan_sort_followups.setOnClickListener(View.OnClickListener {
            supportFragmentManager
                    .beginTransaction()
                    .add(Followups_Fragment(), Followups_Fragment::class.java.canonicalName)
                    .commitAllowingStateLoss()
        })*/
        //setUpDummyData()

        categoryViewModel.getCategoryList()

        categoryViewModel.responseLiveData.observe(this, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()

                    categoryViewModel.response = response.data as InlineResponse200

                    categoryViewModel.categoryList = categoryViewModel.response!!.results

                    categoryList = categoryViewModel.response!!.results

                    for (item in categoryList){

                        item.name?.let { completeList.add(it) }

                        filters.add(Filter(item.name, item.picture, FilterType.CATEGORY, false))

                        println(filters)
                    }

                    //println("Complete list is " + completeList)

                    //rv_category?.adapter = PlanSortingAdapter(categoryList)
                    rv_category?.adapter = Filter_Sorting_Adapter(filters, this, sFilter, sFilterType)

                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                    println(response.error?.message.toString())
                }
            }
        })

        filterData()
        loadData()

        /* adapter = Filter_Sorting_Adapter(filters*//*, true*//*)
         rv_filters.adapter = adapter*/

        edit_account_ok.setOnClickListener(this)
        edit_account_cancel.setOnClickListener(this)

    }

    override fun onBackPressed() {
        super.onBackPressed()

        //TODO pass filter values from here

        intent = Intent(this, HomePlanPreview:: class.java)
        startActivity(intent)
    }

    private fun filterData(){

       /*val filterlist : ArrayList<String> = ArrayList<String>()

        filterlist.add("Drafts")
        filterlist.add("Mines")
        filterlist.add("Invited to")*/

        completeList.add(getString(R.string.drafts))
        completeList.add(getString(R.string.mines))
        completeList.add(getString(R.string.invited_to))

       /* adapter = Filter_Sorting_Adapter(filterlist, true)
        rv_filters.adapter = adapter*/

        filters.add(Filter(getString(R.string.drafts), null, FilterType.EVENTTYPE, false))
        filters.add(Filter(getString(R.string.mines), null,  FilterType.EVENTTYPE, false))
        filters.add(Filter(getString(R.string.invited_to), null, FilterType.EVENTTYPE, false))
   }

    private fun loadData() {

        val sharedPreferences = getSharedPreferences("FOLLOW _UP_PREFERENCE", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("follow up list", null)
        val type: Type = object : TypeToken<ArrayList<String?>?>() {}.type

        if (json != null){

            followUpList = gson?.fromJson<ArrayList<String>>(json, type)

            for (item in followUpList){

                filters.add(Filter(item, null, FilterType.FOLLOWUP, false))
            }

        } else{ }

        if (followUpList.size == 0) { followUpList = ArrayList() }

        completeList.addAll(followUpList)

        //rv_follow_ups.adapter = Filter_Sorting_Adapter(followUpList, false)
    }

    override fun onClick(v: View?) {

        when(v?.id){

            R.id.edit_account_ok -> {

                val intent = Intent(this,HomePlanPreview::class.java)
                intent.putExtra("FILTER",selectedFilter?.filter)
                intent.putExtra("FILTER_TYPE",selectedFilter?.filterType.toString())
                setResult(Activity.RESULT_OK, intent)
                finish()
            }

            R.id.edit_account_cancel -> {

                finish()
            }
        }
    }

    override fun SelectedFilter(filter: Filter?) {

        selectedFilter = filter
    }

    /* private fun filter(category: String?, filter: String? ,followUp : String?){


     }*/
}

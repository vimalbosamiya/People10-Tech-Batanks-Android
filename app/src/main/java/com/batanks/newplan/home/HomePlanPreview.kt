package com.batanks.newplan.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.newplan.R
import com.batanks.newplan.arch.BaseAppCompatActivity
import com.batanks.newplan.arch.response.Status
import com.batanks.newplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.newplan.common.getLoadingDialog
import com.batanks.newplan.home.adapter.HomePlanPreviewAdapter
import com.batanks.newplan.home.fragment.CreatePlanFragment
import com.batanks.newplan.network.RetrofitClient
import com.batanks.newplan.home.viewmodel.HomePlanPreviewViewModel
import com.batanks.newplan.swagger.api.AuthenticationAPI
import kotlinx.android.synthetic.main.activity_home.*

class HomePlanPreview : BaseAppCompatActivity(), View.OnClickListener {

    var recyclerView: RecyclerView? = null

    private val homePlanPreviewViewModel: HomePlanPreviewViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            RetrofitClient.getRetrofitInstance(this)?.create(AuthenticationAPI::class.java)?.let {
                HomePlanPreviewViewModel(it)
            }
        }).get(HomePlanPreviewViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val toolbar: Toolbar = findViewById(R.id.customToolBar)
        setSupportActionBar(toolbar)

        loadingDialog = this.getLoadingDialog(0, R.string.creating_user_please_wait, theme = R.style.AlertDialogCustom)

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

                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })

        extFab.setOnClickListener(this)
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
        }
    }
}
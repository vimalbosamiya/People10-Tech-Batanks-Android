package com.batanks.nextplan.notifications.fragments

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
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.notifications.adapters.NotificationsListAdapter
import com.batanks.nextplan.notifications.viewmodel.NotificationsViewModel
import com.batanks.nextplan.swagger.api.NotificationsAPI
import com.batanks.nextplan.swagger.model.NotificationRead
import com.batanks.nextplan.swagger.model.NotificationsList
import com.batanks.nextplan.swagger.model.NotificationsResponse

class PublicNotificationsFragment  : BaseFragment(){

    lateinit var notificationsRecyclerView : RecyclerView
    private var isViewShown = false

    private var notificationResponse: NotificationRead? = null

    private val notificationsViewModel: NotificationsViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            getContext()?.let {
                RetrofitClient.getRetrofitInstance(it)?.create(NotificationsAPI::class.java)?.let {
                    NotificationsViewModel(it)
                }
            }
        }).get(NotificationsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.fragment_all_notifications, container, false)

        notificationsRecyclerView = view.findViewById(R.id.notificationsRecyclerView)
        notificationsRecyclerView?.setHasFixedSize(true)
        notificationsRecyclerView.layoutManager = LinearLayoutManager(activity)

        notificationsViewModel.getNotifications()

        notificationsViewModel.responseLiveData.observe(viewLifecycleOwner, Observer{ response ->

            when(response.status){
                Status.LOADING -> {
                    showLoader()
                }

                Status.SUCCESS -> {
                    hideLoader()

                    notificationsViewModel.notificationsList.clear()

                    notificationsViewModel.response = response.data as NotificationsResponse

                    notificationsViewModel.notificationsList = notificationsViewModel.response!!.results

                    //NotificationsViewModel.privatenotificationsList = notificationsViewModel.notificationsList
                    //NotificationsViewModel.publicnotificationsList = notificationsViewModel.notificationsList

                    NotificationsViewModel.publicnotificationsList.clear()

                    for (item in notificationsViewModel.notificationsList){

                       /* if(item.event_is_private == true){

                            if (!NotificationsViewModel.privatenotificationsList.contains(item)){

                                NotificationsViewModel.privatenotificationsList.add(item)
                            }

                        } else*/ if(item.event_is_private == false){

                            if (!NotificationsViewModel.publicnotificationsList.contains(item)){

                                NotificationsViewModel.publicnotificationsList.add(item)
                            }
                        }
                    }

                    notificationsRecyclerView.adapter = NotificationsListAdapter(NotificationsViewModel.publicnotificationsList,notificationsViewModel)

                    //println(notificationsViewModel.notificationsList)

                    /* notificationsViewModel.response.results

                    populateCategory(categoryViewModel.categoryList!!)

                    println(categoryViewModel.categoryList)*/
                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })

        notificationsViewModel.responseLiveDataMarkAsRead.observe(viewLifecycleOwner, Observer{ response ->

            when(response.status){
                Status.LOADING -> {
                    showLoader()
                }

                Status.SUCCESS -> {
                    hideLoader()

                    notificationResponse = response.data as NotificationRead

                    notificationsViewModel.getNotifications()
                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })

        //notificationsRecyclerView.adapter = NotificationsListAdapter(NotificationsViewModel.publicnotificationsList, notificationsViewModel)

        return view
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        if (view != null && isVisibleToUser) {

            isViewShown = true

            //notificationsRecyclerView.adapter = NotificationsListAdapter(NotificationsViewModel.publicnotificationsList,notificationsViewModel)

        } else {
            isViewShown = false
        }
    }
}
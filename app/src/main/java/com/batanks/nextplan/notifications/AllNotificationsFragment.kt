package com.batanks.nextplan.notifications

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
import com.batanks.nextplan.notifications.viewmodel.NotificationsViewModel
import com.batanks.nextplan.swagger.api.NotificationsAPI
import com.batanks.nextplan.swagger.model.InlineResponse2003
import com.batanks.nextplan.swagger.model.NotificationsList

class AllNotificationsFragment  : BaseFragment(){

    lateinit var notificationsRecyclerView : RecyclerView
    lateinit var notificationsAdapter : NotificationsListAdapter

    //var notificationsList :  ArrayList<NotificationsList> = arrayListOf()

    private val notificationsViewModel: NotificationsViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            getContext()?.let {
                RetrofitClient.getRetrofitInstance(it)?.create(NotificationsAPI::class.java)?.let {
                    NotificationsViewModel(it)
                }
            }
        }).get(NotificationsViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()

        /*notificationsViewModel.getNotifications()

        notificationsViewModel.responseLiveData.observe(viewLifecycleOwner, Observer{ response ->

            when(response.status){
                Status.LOADING -> {
                    showLoader()
                }

                Status.SUCCESS -> {
                    hideLoader()

                    notificationsViewModel.response = response.data as InlineResponse2003

                    notificationsViewModel.notificationsList = notificationsViewModel.response!!.results

                    for (item in notificationsViewModel.notificationsList) {

                        if (item.event._private == true) {

                            notificationsViewModel.privatenotificationsList.add(item)

                        }
                    }

                    println(notificationsViewModel.privatenotificationsList)

                    notificationsRecyclerView.adapter = context?.let { NotificationsListAdapter(notificationsViewModel.notificationsList, it) }

                    //println("All Notifications called")

                    //println(notificationsViewModel.response)

                    *//*notificationsViewModel.response.results

                    populateCategory(categoryViewModel.categoryList!!)

                    println(categoryViewModel.categoryList)*//*
                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })*/
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.fragment_all_notifications, container, false)

        notificationsRecyclerView = view.findViewById(R.id.notificationsRecyclerView)
        notificationsRecyclerView.layoutManager = LinearLayoutManager(activity)

        /*notificationsRecyclerView.adapter = NotificationsListAdapter(listOf<String>())*/

        loadingDialog = context?.getLoadingDialog(0, R.string.loading_list_please_wait, theme = R.style.AlertDialogCustom)

        notificationsRecyclerView.adapter = context?.let { NotificationsListAdapter(notificationsViewModel.notificationsList, it) }

        //notificationsViewModel.getNotifications()

        /*notificationsViewModel.responseLiveData.observe(viewLifecycleOwner, Observer{ response ->

            when(response.status){
                Status.LOADING -> {
                    showLoader()
                }

                Status.SUCCESS -> {
                    hideLoader()

                    notificationsViewModel.response = response.data as InlineResponse2003

                    notificationsViewModel.notificationsList = notificationsViewModel.response!!.results

                    for (item in notificationsViewModel.notificationsList) {

                        if (item.event._private == true) {

                            notificationsViewModel.privatenotificationsList.add(item)

                        }
                    }

                    println(notificationsViewModel.privatenotificationsList)

                    notificationsRecyclerView.adapter = context?.let { NotificationsListAdapter(notificationsViewModel.notificationsList, it) }

                    //println("All Notifications called")

                    //println(notificationsViewModel.response)

                    *//*notificationsViewModel.response.results

                    populateCategory(categoryViewModel.categoryList!!)

                    println(categoryViewModel.categoryList)*//*
                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })*/

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        /*for (item in notificationsViewModel.notificationsList){

            if(item.event._private == true){

                notificationsViewModel.privatenotificationsList.add(item)

            }
            if (item.event._private == false){

                notificationsViewModel.publicnotificationsList.add(item)
            }
        }*/
    }
}
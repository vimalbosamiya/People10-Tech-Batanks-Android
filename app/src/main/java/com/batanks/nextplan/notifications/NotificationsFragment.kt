package com.batanks.nextplan.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseFragment
import com.batanks.nextplan.arch.response.Status
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.notifications.adapters.NotificationsTabsAdapter
import com.batanks.nextplan.notifications.viewmodel.NotificationsViewModel
import com.batanks.nextplan.swagger.api.NotificationsAPI
import com.batanks.nextplan.swagger.model.InlineResponse2003
import kotlinx.android.synthetic.main.fragment_notifications.*

class NotificationsFragment : BaseFragment(){

    private val notificationsViewModel: NotificationsViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            getContext()?.let {
                RetrofitClient.getRetrofitInstance(it)?.create(NotificationsAPI::class.java)?.let {
                    NotificationsViewModel(it)
                }
            }
        }).get(NotificationsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val ref = requireActivity() as AppCompatActivity
        ref.setSupportActionBar(notificationToolBar)
        ref.supportActionBar?.title = "Notifications "
        ref.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        ref.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_header)

        notificationToolBar.setNavigationOnClickListener {

            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()

            ///activity?.extFab!!.visibility = View.VISIBLE             //uncomment

            //Toast.makeText(activity,"Back Button Working from Navigation" , Toast.LENGTH_SHORT).show()
        }


        notificationsViewModel.getNotifications()

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



                    val tabsPagerAdapter = NotificationsTabsAdapter(childFragmentManager)
                    view_pager.adapter = tabsPagerAdapter



                    tabs.setupWithViewPager(view_pager)

                    val tabOne = LayoutInflater.from(view.context).inflate(R.layout.custom_tab, null) as TextView
                    tabOne.text = "ALL"
                    tabOne.setTextColor(resources.getColor(R.color.colorWhite))
                    //tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_people_tablayout, 0, 0)
                    tabs.getTabAt(0)?.customView = tabOne

                    val tabTwo = LayoutInflater.from(view.context).inflate(R.layout.custom_tab, null) as TextView
                    tabTwo.text = "PRIVATE"
                    tabTwo.setTextColor(resources.getColor(R.color.colorWhite))
                    //tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_public_plan_tablayout, 0, 0)
                    tabs.getTabAt(1)?.customView = tabTwo

                    val tabThree = LayoutInflater.from(view.context).inflate(R.layout.custom_tab, null) as TextView
                    tabThree.text = "PUBLIC"
                    tabThree.setTextColor(resources.getColor(R.color.colorWhite))
                    //tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_private_plan_tablayout, 0, 0)
                    tabs.getTabAt(2)?.customView = tabThree

                    tabs.getTabAt(0)?.icon = ref.getDrawable(R.drawable.ic_people_tablayout)
                    tabs.getTabAt(1)?.icon = ref.getDrawable(R.drawable.ic_private_plan_tablayout)
                    tabs.getTabAt(2)?.icon = ref.getDrawable(R.drawable.ic_public_plan_tablayout)

                    //notificationsRecyclerView.adapter = context?.let { NotificationsListAdapter(notificationsViewModel.notificationsList, it) }

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

    override fun onDestroy() {
        super.onDestroy()
        ///activity?.extFab!!.visibility = View.VISIBLE             //uncomment
    }

    companion object {
        const val TAG = "NotificationsFragment"
    }
}
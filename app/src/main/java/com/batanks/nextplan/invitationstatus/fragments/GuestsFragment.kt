package com.batanks.nextplan.invitationstatus.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batanks.nextplan.R
import com.batanks.nextplan.arch.BaseFragment
import com.batanks.nextplan.arch.response.Status
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.common.getLoadingDialog
import com.batanks.nextplan.eventdetails.viewmodel.EventDetailViewModel
import com.batanks.nextplan.invitationstatus.adapters.InvitationStatusListAdapter
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.swagger.api.EventAPI
import androidx.lifecycle.Observer
import com.batanks.nextplan.swagger.model.Event
import com.batanks.nextplan.swagger.model.Guests


class GuestsFragment(private val eventId : Int) : BaseFragment() {

    lateinit var invitationStatusRecyclerview : RecyclerView

    private val eventDetailViewModel: EventDetailViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            getContext()?.let {
                RetrofitClient.getRetrofitInstance(it)?.create(EventAPI::class.java)?.let {
                    EventDetailViewModel(it)
                }
            }
        }).get(EventDetailViewModel::class.java)
    }

    private var event : Event? = null
    private var guestsList : ArrayList<Guests> = arrayListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.fragment_invitation_status, container, false)

        //loadingDialog = context?.getLoadingDialog(0, R.string.loading_list_please_wait, theme = R.style.AlertDialogCustom)

        invitationStatusRecyclerview = view.findViewById(R.id.invitationStatusRecyclerView)
        invitationStatusRecyclerview?.setHasFixedSize(true)
        invitationStatusRecyclerview.layoutManager = LinearLayoutManager(activity)

        //showLoader()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventDetailViewModel.getEventData(eventId.toString())

        eventDetailViewModel.responseLiveData.observe(viewLifecycleOwner, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }

                Status.SUCCESS -> {
                    hideLoader()

                    event = response.data as Event

                    guestsList = event!!.guests!!

                    invitationStatusRecyclerview.adapter = InvitationStatusListAdapter(guestsList, eventDetailViewModel, eventId)
                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                    println(response.error)

                }
            }
        })

        eventDetailViewModel.responseLiveDataGuest.observe(viewLifecycleOwner, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }

                Status.SUCCESS -> {
                    hideLoader()

                    eventDetailViewModel.getEventData(eventId.toString())
                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                    println(response.error)

                }
            }
        })
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        if (isVisibleToUser && isResumed()){

            onResume()

        } else{

            println("Accepted Participants of the event")
        }
    }

    override fun onResume() {
        super.onResume()

        if (!getUserVisibleHint()) { return }

        eventDetailViewModel.getEventData(eventId.toString())

    }
}
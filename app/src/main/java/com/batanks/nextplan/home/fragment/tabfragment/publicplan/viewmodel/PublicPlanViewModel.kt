package com.batanks.nextplan.home.fragment.tabfragment.publicplan.viewmodel

import androidx.lifecycle.ViewModel
import com.batanks.nextplan.swagger.model.Activity
import com.batanks.nextplan.swagger.model.EventDate
import com.batanks.nextplan.swagger.model.EventPlace
import com.batanks.nextplan.swagger.model.Place
import com.batanks.nextplan.swagger.model.Task
import io.reactivex.disposables.CompositeDisposable

class PublicPlanViewModel : ViewModel() {

    private val disposables = CompositeDisposable()
    var eventDate = ArrayList<EventDate>()
    var place = ArrayList<Place>()
    var action = ArrayList<Task>()
    var activity = ArrayList<Activity>()
    var activityDate = ArrayList<EventDate>()

    override fun onCleared() {
        disposables.clear()
    }
}
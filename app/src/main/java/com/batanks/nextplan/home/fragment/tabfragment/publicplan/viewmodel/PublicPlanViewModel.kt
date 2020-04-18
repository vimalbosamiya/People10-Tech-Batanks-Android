package com.batanks.nextplan.home.fragment.tabfragment.publicplan.viewmodel

import androidx.lifecycle.ViewModel
import com.batanks.nextplan.swagger.model.EventDate
import com.batanks.nextplan.swagger.model.Place
import io.reactivex.disposables.CompositeDisposable

class PublicPlanViewModel : ViewModel() {

    private val disposables = CompositeDisposable()
    var eventDate = ArrayList<EventDate>()
    var place = ArrayList<Place>()

    override fun onCleared() {
        disposables.clear()
    }
}
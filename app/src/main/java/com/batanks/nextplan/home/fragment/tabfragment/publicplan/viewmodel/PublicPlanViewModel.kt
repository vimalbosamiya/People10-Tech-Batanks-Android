package com.batanks.nextplan.home.fragment.tabfragment.publicplan.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.batanks.nextplan.arch.response.ApiResponse
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.swagger.api.CategoryAPI
import com.batanks.nextplan.swagger.api.EventAPI
import com.batanks.nextplan.swagger.model.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PublicPlanViewModel (private val eventApi: EventAPI) : ViewModel() {

    private val disposables = CompositeDisposable()
    val responseLiveData: MutableLiveData<ApiResponse> = MutableLiveData()

    //var response : Event? = null

    var eventDate = ArrayList<PostDates>()
    var place = ArrayList<PostPlaces>()
    var action = ArrayList<PostTasks>()
    var activity = ArrayList<PostActivities>()
    var activityDate = ArrayList<EventDate>()


    fun createEvent(data: PostEvent?){

        disposables.add(eventApi.apiEventCreate(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveData.setValue(ApiResponse.loading())
                }.doOnNext {}.subscribe({ result ->
                    //RetrofitClient.cookieJar?.persist()
                    responseLiveData.setValue(ApiResponse.success(result))
                }) { throwable ->
                    responseLiveData.setValue(ApiResponse.error(throwable))
                })

    }

    fun getCategory(data: PostEvent?){

        disposables.add(eventApi.apiEventCreate(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveData.setValue(ApiResponse.loading())
                }.doOnNext {}.subscribe({ result ->
                    //RetrofitClient.cookieJar?.persist()
                    responseLiveData.setValue(ApiResponse.success(result))
                }) { throwable ->
                    responseLiveData.setValue(ApiResponse.error(throwable))
                })

    }

    override fun onCleared() {
        disposables.clear()
    }
}
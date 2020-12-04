package com.batanks.nextplan.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.batanks.nextplan.arch.response.ApiResponse
import com.batanks.nextplan.swagger.api.AuthenticationAPI
import com.batanks.nextplan.swagger.api.EventAPI
import com.batanks.nextplan.swagger.model.EventListResponse
import com.batanks.nextplan.swagger.model.InlineResponse2002
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomePlanPreviewViewModel(private val authApi: EventAPI) : ViewModel() {

    private val disposables = CompositeDisposable()
    val responseLiveData: MutableLiveData<ApiResponse> = MutableLiveData()
    var response : InlineResponse2002? = null

    fun getHomePlanEvent() {

        disposables.add(authApi.apiEventCreatedList()

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveData.setValue(ApiResponse.loading())
                }
                .doOnNext {
                    println(it)
                }.subscribe({ result ->
                    responseLiveData.setValue(ApiResponse.success(result))
                }) { throwable ->
                    responseLiveData.setValue(ApiResponse.error(throwable))
                })
    }

    fun eventList() {

        disposables.add(authApi.apiEventList()

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveData.setValue(ApiResponse.loading())
                }
                .doOnNext {
                    println(it)
                }.subscribe({ result ->
                    responseLiveData.setValue(ApiResponse.success(result))
                }) { throwable ->
                    responseLiveData.setValue(ApiResponse.error(throwable))
                })
    }

    override fun onCleared() {
        disposables.clear()
    }
}
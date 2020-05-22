package com.batanks.nextplan.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.batanks.nextplan.arch.response.ApiResponse
import com.batanks.nextplan.swagger.api.AuthenticationAPI
import com.batanks.nextplan.swagger.api.EventAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomePlanPreviewViewModel(private val authApi: EventAPI) : ViewModel() {

    private val disposables = CompositeDisposable()
    val responseLiveData: MutableLiveData<ApiResponse> = MutableLiveData()

    fun getHomePlanEvent() {
        disposables.add(authApi.apiEventCreatedList(1,1)
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
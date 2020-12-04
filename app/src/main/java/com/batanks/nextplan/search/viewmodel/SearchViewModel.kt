package com.batanks.nextplan.search.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.batanks.nextplan.arch.response.ApiResponse
import com.batanks.nextplan.swagger.api.SearchAPI
import com.batanks.nextplan.swagger.model.InlineResponse2002
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchViewModel (private val searchApi: SearchAPI) : ViewModel() {

    private val disposables = CompositeDisposable()
    val responseLiveData: MutableLiveData<ApiResponse> = MutableLiveData()
    var response : InlineResponse2002? = null

    fun getSearchList(type:String, category:String?, keywords:String?) {

        disposables.add(searchApi.apiSearchList(type, category, keywords)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveData.setValue(ApiResponse.loading())
                }
                .doOnNext {
                    //println(it)
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
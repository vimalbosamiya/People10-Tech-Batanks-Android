package com.batanks.nextplan.Settings.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.batanks.nextplan.arch.response.ApiResponse
import com.batanks.nextplan.swagger.api.LanguageAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LanguageViewModel (private val langApi : LanguageAPI) : ViewModel()  {

    private val disposables = CompositeDisposable()
    val responseLiveData: MutableLiveData<ApiResponse> = MutableLiveData()
    var response : ArrayList<HashMap<String, String>> = arrayListOf()

    fun getLanguageList() {

        disposables.add(langApi.apiLanguageList()

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveData.setValue(ApiResponse.loading())
                }
                .doOnNext {
                   // println(it)
                }.subscribe({ result ->
                    responseLiveData.setValue(ApiResponse.success(result))
                }) { throwable ->
                    responseLiveData.setValue(ApiResponse.error(throwable))
                })
    }
}
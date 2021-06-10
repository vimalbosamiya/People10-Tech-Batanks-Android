package com.batanks.nextplan.search.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.batanks.nextplan.arch.response.ApiResponse
import com.batanks.nextplan.swagger.api.SearchAPI
import com.batanks.nextplan.swagger.model.ContactsList
import com.batanks.nextplan.swagger.model.InlineResponse2002
import com.batanks.nextplan.swagger.model.UserSearch
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchViewModel (private val searchApi: SearchAPI) : ViewModel() {

    private val disposables = CompositeDisposable()
    val responseLiveData: MutableLiveData<ApiResponse> = MutableLiveData()
    val responseLiveDataUsers: MutableLiveData<ApiResponse> = MutableLiveData()
    var response : InlineResponse2002? = null
    var participantsListUsers : ArrayList<ContactsList> = arrayListOf()
    var participantsUsers : ArrayList<ContactsList> = arrayListOf()
    //var usersSearchResponse : UserSearch? = null

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

    fun apiSearchListWithType(type:String) {

        disposables.add(searchApi.apiSearchListWithType(type)

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

    fun apiSearchListWithTypeAndCategory(type:String, category:String?) {

        disposables.add(searchApi.apiSearchListWithTypeAndCategory(type, category)

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

    fun apiSearchListWithTypeAndKeyword(type:String, keywords:String?) {

        disposables.add(searchApi.apiSearchListWithTypeAndKeyword(type, keywords)

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

    fun getSearchUsers(search:String) {

        disposables.add(searchApi.apiSearchUsers(search)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveDataUsers.setValue(ApiResponse.loading())
                }
                .doOnNext {
                    //println(it)
                }.subscribe({ result ->
                    responseLiveDataUsers.setValue(ApiResponse.success(result))
                }) { throwable ->
                    responseLiveDataUsers.setValue(ApiResponse.error(throwable))
                })
    }

    override fun onCleared() {
        disposables.clear()
    }
}
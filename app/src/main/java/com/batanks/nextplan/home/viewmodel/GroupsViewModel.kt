package com.batanks.nextplan.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.batanks.nextplan.arch.response.ApiResponse
import com.batanks.nextplan.swagger.api.GroupsAPI
import com.batanks.nextplan.swagger.model.AddGroupContact
import com.batanks.nextplan.swagger.model.Group
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class GroupsViewModel (private val groupsApi: GroupsAPI) : ViewModel()  {

    private val disposables = CompositeDisposable()
    val responseLiveData: MutableLiveData<ApiResponse> = MutableLiveData()
    val responseLiveDataPost: MutableLiveData<ApiResponse> = MutableLiveData()
    var response : List<Group>? = null

    fun getGroupsList() {

        disposables.add(groupsApi.apiGroupsList()

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

    fun createGroup (addGroupContact: AddGroupContact?) {
        disposables.add(groupsApi.apiGroupsCreate(addGroupContact)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                   // responseLiveDataPost.setValue(ApiResponse.loading())
                }.doOnNext {
                    //println(it)
                }.subscribe({ result ->
                    //RetrofitClient.cookieJar?.persist()
                    //responseLiveDataPost.setValue(ApiResponse.success(result))
                }) { throwable ->
                   // responseLiveDataPost.setValue(ApiResponse.error(throwable))
                })
    }

    override fun onCleared() {
        disposables.clear()
    }
}
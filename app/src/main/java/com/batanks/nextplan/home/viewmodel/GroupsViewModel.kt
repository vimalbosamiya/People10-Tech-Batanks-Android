package com.batanks.nextplan.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.batanks.nextplan.arch.response.ApiResponse
import com.batanks.nextplan.swagger.api.GroupsAPI
import com.batanks.nextplan.swagger.model.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class GroupsViewModel (private val groupsApi: GroupsAPI) : ViewModel()  {

    private val disposables = CompositeDisposable()
    val responseLiveData: MutableLiveData<ApiResponse> = MutableLiveData()
    val responseLiveDataPost: MutableLiveData<ApiResponse> = MutableLiveData()
    val responseLiveDataAddContact: MutableLiveData<ApiResponse> = MutableLiveData()
    val responseLiveDataCreateGroup: MutableLiveData<ApiResponse> = MutableLiveData()
    val responseLiveDataEditGroup: MutableLiveData<ApiResponse> = MutableLiveData()
    var response : List<Group>? = null

    fun getGroupsList() {

        disposables.add(groupsApi.apiGroupsList()

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

    fun createGroup (addGroupContact: AddGroupContact?) {
        disposables.add(groupsApi.apiGroupsCreate(addGroupContact)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveDataPost.setValue(ApiResponse.loading())
                }.doOnNext {
                    //println(it)
                }.subscribe({ result ->
                    //RetrofitClient.cookieJar?.persist()
                    responseLiveDataPost.setValue(ApiResponse.success(result))
                }) { throwable ->
                    responseLiveDataPost.setValue(ApiResponse.error(throwable))
                })
    }

    fun createGroupWithoutId (groupWithoutId: GroupWithoutId?) {
        disposables.add(groupsApi.apiGroupsCreateWithoutId(groupWithoutId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveDataCreateGroup.setValue(ApiResponse.loading())
                }.doOnNext {
                    //println(it)
                }.subscribe({ result ->
                    //RetrofitClient.cookieJar?.persist()
                    responseLiveDataCreateGroup.setValue(ApiResponse.success(result))
                }) { throwable ->
                    responseLiveDataCreateGroup.setValue(ApiResponse.error(throwable))
                })
    }

    fun addContactToGroup (id: String, data: AddContactToGroup) {
        disposables.add(groupsApi.apiGroupsAddContact(id,data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveDataAddContact.setValue(ApiResponse.loading())
                }.doOnNext {
                    //println(it)
                }.subscribe({ result ->
                    //RetrofitClient.cookieJar?.persist()
                    responseLiveDataAddContact.setValue(ApiResponse.success(result))
                }) { throwable ->
                    responseLiveDataAddContact.setValue(ApiResponse.error(throwable))
                })
    }

    /*fun editGroup (id: String, data: GroupEdit) {
        disposables.add(groupsApi.apiGroupsUpdate(id,data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveDataEditGroup.setValue(ApiResponse.loading())
                }.doOnNext {
                    //println(it)
                }.subscribe({ result ->
                    //RetrofitClient.cookieJar?.persist()
                    responseLiveDataEditGroup.setValue(ApiResponse.success(result))
                }) { throwable ->
                    responseLiveDataEditGroup.setValue(ApiResponse.error(throwable))
                })
    }*/

    override fun onCleared() {
        disposables.clear()
    }
}
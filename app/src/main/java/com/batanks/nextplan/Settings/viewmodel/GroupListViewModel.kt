package com.batanks.nextplan.Settings.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.batanks.nextplan.arch.response.ApiResponse
import com.batanks.nextplan.swagger.api.GroupsAPI
import com.batanks.nextplan.swagger.model.Contact
import com.batanks.nextplan.swagger.model.ContactsList
import com.batanks.nextplan.swagger.model.Group
import com.batanks.nextplan.swagger.model.GroupEdit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class GroupListViewModel (private val groupApi : GroupsAPI) : ViewModel()  {

    private val disposables = CompositeDisposable()
    val responseLiveData1: MutableLiveData<ApiResponse> = MutableLiveData()
    val responseLiveData2: MutableLiveData<ApiResponse> = MutableLiveData()
    val responseLiveData3: MutableLiveData<ApiResponse> = MutableLiveData()
    var response : ArrayList<ContactsList> = arrayListOf()

    fun getGroupList(id : String) {

        disposables.add(groupApi.apiGroupsRead(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveData1.setValue(ApiResponse.loading())
                }.doOnNext { }.subscribe({ result ->
                    //RetrofitClient.cookieJar?.persist()
                    responseLiveData1.setValue(ApiResponse.success(result))
                }) { throwable ->
                    responseLiveData1.setValue(ApiResponse.error(throwable))
                })
    }

    fun renameGroup(id : String, data : GroupEdit) {

        disposables.add(groupApi.apiGroupsUpdate(id, data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveData2.setValue(ApiResponse.loading())
                }.doOnNext { }.subscribe({ result ->
                    //RetrofitClient.cookieJar?.persist()
                    responseLiveData2.setValue(ApiResponse.success(result))
                }) { throwable ->
                    responseLiveData2.setValue(ApiResponse.error(throwable))
                })
    }

    fun deleteGroup(id : String) {

        disposables.add(groupApi.apiGroupsDelete(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveData3.setValue(ApiResponse.loading())
                }.doOnNext { }.subscribe({ result ->
                    //RetrofitClient.cookieJar?.persist()
                    responseLiveData3.setValue(ApiResponse.success(result))
                }) { throwable ->
                    responseLiveData3.setValue(ApiResponse.error(throwable))
                })
    }
}
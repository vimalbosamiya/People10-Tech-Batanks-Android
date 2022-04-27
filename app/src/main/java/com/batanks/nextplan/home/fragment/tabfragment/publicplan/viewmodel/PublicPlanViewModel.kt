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
    val responseLiveDataCategory: MutableLiveData<ApiResponse> = MutableLiveData()
    val responseLiveDataUpdate: MutableLiveData<ApiResponse> = MutableLiveData()
    val responseLiveDataPartialUpdate: MutableLiveData<ApiResponse> = MutableLiveData()

    //var response : Event? = null

    var eventDate = ArrayList<PostDates>()
    var publicEventDate = ArrayList<PostDates>()
    var place = ArrayList<PostPlaces>()
    var publicPlace = ArrayList<PostPlaces>()
    var action = ArrayList<PostTasks>()
    var publicAction = ArrayList<PostTasks>()
    var activity = ArrayList<PostActivities>()
    var publicActivity = ArrayList<PostActivities>()
    var participants : PostGuests? = null
    var publicParticipants : PostGuests? = null

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

    fun updateEvent(id: String?, data: PostEvent?){

        disposables.add(eventApi.apiEventPartialUpdate(id, data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveDataUpdate.setValue(ApiResponse.loading())
                }.doOnNext {}.subscribe({ result ->
                    //RetrofitClient.cookieJar?.persist()
                    responseLiveDataUpdate.setValue(ApiResponse.success(result))
                }) { throwable ->
                    responseLiveDataUpdate.setValue(ApiResponse.error(throwable))
                })
    }

    fun apiEventPartialUpdate(id: String?, data: PostEvent?){

        disposables.add(eventApi.apiEventPartialUpdate(id, data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveDataPartialUpdate.setValue(ApiResponse.loading())
                }.doOnNext {}.subscribe({ result ->
                    //RetrofitClient.cookieJar?.persist()
                    responseLiveDataPartialUpdate.setValue(ApiResponse.success(result))
                }) { throwable ->
                    responseLiveDataPartialUpdate.setValue(ApiResponse.error(throwable))
                })
    }

    /*fun apiEventPartialUpdate(id: String?, data: PostEvent?){

        eventApi.apiEventPartialUpdate(id, data)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    responseLiveDataPartialUpdate.setValue(ApiResponse.loading())
                }?.doOnNext {}?.subscribe({ result ->
                    //RetrofitClient.cookieJar?.persist()
                    responseLiveData.setValue(result?.let { ApiResponse.success(it) })
                }) { throwable ->
                    responseLiveData.setValue(ApiResponse.error(throwable))
                }?.let { disposables.add(it) }
    }*/

    fun getCategory(data: PostEvent?){

        disposables.add(eventApi.apiEventCreate(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveDataCategory.setValue(ApiResponse.loading())
                }.doOnNext {}.subscribe({ result ->
                    //RetrofitClient.cookieJar?.persist()
                    responseLiveDataCategory.setValue(ApiResponse.success(result))
                }) { throwable ->
                    responseLiveDataCategory.setValue(ApiResponse.error(throwable))
                })

    }

    override fun onCleared() {
        disposables.clear()
    }
}
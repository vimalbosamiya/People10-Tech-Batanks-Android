package com.batanks.nextplan.notifications.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.batanks.nextplan.arch.response.ApiResponse
import com.batanks.nextplan.swagger.api.NotificationsAPI
import com.batanks.nextplan.swagger.model.InlineResponse2003
import com.batanks.nextplan.swagger.model.NotificationsList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NotificationsViewModel (private val notificationsApi: NotificationsAPI) : ViewModel() {

    private val disposables = CompositeDisposable()
    val responseLiveData: MutableLiveData<ApiResponse> = MutableLiveData()
    var response : InlineResponse2003? = null
    var notificationsList :  ArrayList<NotificationsList> = arrayListOf()
    val publicnotificationsList : ArrayList<NotificationsList> = arrayListOf()
    val privatenotificationsList :  ArrayList<NotificationsList> = arrayListOf()

    fun getNotifications() {

        disposables.add(notificationsApi.apiNotificationsList(100,0)

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
}
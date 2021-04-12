package com.batanks.nextplan.notifications.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.batanks.nextplan.arch.response.ApiResponse
import com.batanks.nextplan.swagger.api.NotificationsAPI
import com.batanks.nextplan.swagger.model.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NotificationsViewModel (private val notificationsApi: NotificationsAPI) : ViewModel() {

    private val disposables = CompositeDisposable()
    val responseLiveData: MutableLiveData<ApiResponse> = MutableLiveData()
    val responseLiveDataMarkAsRead: MutableLiveData<ApiResponse> = MutableLiveData()
    var response : NotificationsResponse? = null
    var notificationsList :  ArrayList<NotificationModel> = arrayListOf()


    fun getNotifications() {

        disposables.add(notificationsApi.apiNotificationsList()

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

    fun apiNotificationMarkAsRead(data: NotificationRead?) {

        disposables.add(notificationsApi.apiNotificationMarkAsRead(data)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveDataMarkAsRead.setValue(ApiResponse.loading())
                }
                .doOnNext {
                    //println(it)
                }.subscribe({ result ->
                    responseLiveDataMarkAsRead.setValue(ApiResponse.success(result))
                }) { throwable ->
                    responseLiveDataMarkAsRead.setValue(ApiResponse.error(throwable))
                })
    }

    override fun onCleared() {
        disposables.clear()
    }

    companion object{

        var publicnotificationsList : ArrayList<NotificationModel> = arrayListOf()
        var privatenotificationsList :  ArrayList<NotificationModel> = arrayListOf()
    }
}
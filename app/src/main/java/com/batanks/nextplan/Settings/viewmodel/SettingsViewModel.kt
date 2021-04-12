package com.batanks.nextplan.Settings.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.batanks.nextplan.arch.response.ApiResponse
import com.batanks.nextplan.swagger.api.AuthenticationAPI
import com.batanks.nextplan.swagger.model.SettingsGet
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SettingsViewModel (private val authApi : AuthenticationAPI) : ViewModel() {

    private val disposables = CompositeDisposable()
    val responseLiveData: MutableLiveData<ApiResponse> = MutableLiveData()
    val putResponseLiveData: MutableLiveData<ApiResponse> = MutableLiveData()
    val PatchResponseLiveData: MutableLiveData<ApiResponse> = MutableLiveData()
    var settingsResponse : SettingsGet? = null

    fun getSettings() {

        disposables.add(authApi.apiAuthenticationGetSetings()

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

    fun putSettings(data : SettingsGet) {

        disposables.add(authApi.apiAuthenticationPutSetings(data)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    putResponseLiveData.setValue(ApiResponse.loading())
                }
                .doOnNext {
                    // println(it)
                }.subscribe({ result ->
                    putResponseLiveData.setValue(ApiResponse.success(result))
                }) { throwable ->
                    putResponseLiveData.setValue(ApiResponse.error(throwable))
                })
    }

    fun patchSettings(data : SettingsGet) {

        disposables.add(authApi.apiAuthenticationPatchSetings(data)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    PatchResponseLiveData.setValue(ApiResponse.loading())
                }
                .doOnNext {
                    // println(it)
                }.subscribe({ result ->
                    PatchResponseLiveData.setValue(ApiResponse.success(result))
                }) { throwable ->
                    PatchResponseLiveData.setValue(ApiResponse.error(throwable))
                })
    }
}
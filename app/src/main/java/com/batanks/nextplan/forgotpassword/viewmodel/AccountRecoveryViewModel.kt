package com.batanks.nextplan.forgotpassword.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.batanks.nextplan.arch.response.ApiResponse
import com.batanks.nextplan.swagger.api.AuthenticationAPI
import com.batanks.nextplan.swagger.model.PasswordLost
import com.batanks.nextplan.swagger.model.mode.ResetPasswordConfirm
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AccountRecoveryViewModel(private val authApi: AuthenticationAPI) : ViewModel() {

    private val disposables = CompositeDisposable()
    val responseLiveData: MutableLiveData<ApiResponse> = MutableLiveData()

    fun sendMail(password: PasswordLost) {
        disposables.add(authApi.apiAuthenticationForgotCreate(password)
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

    fun resetPassword(resetPassword: ResetPasswordConfirm) {
        disposables.add(authApi.apiAuthenticationResetPassword(resetPassword)
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

    override fun onCleared() {
        disposables.clear()
    }

}
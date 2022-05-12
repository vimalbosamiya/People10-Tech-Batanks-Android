package com.batanks.nextplan.signing.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.batanks.nextplan.arch.response.ApiResponse
import com.batanks.nextplan.swagger.api.AuthenticationAPI
import com.batanks.nextplan.swagger.model.Login
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray
import org.json.JSONObject


class SigninViewModel(private val authApi: AuthenticationAPI) : ViewModel() {

    private val disposables = CompositeDisposable()
    val responseLiveData: MutableLiveData<ApiResponse> = MutableLiveData()

    fun performSignIn(login: Login) {
        disposables.add(authApi.apiAuthenticationLoginCreateSingle(login)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveData.setValue(ApiResponse.loading())
                }.doOnNext {
                    //println(it)
                }.subscribe({ result ->
                    //RetrofitClient.cookieJar?.persist()
                    responseLiveData.setValue(ApiResponse.success(result))
                }) { throwable ->
                if (throwable is retrofit2.HttpException) {
                    val response = throwable.response()?.errorBody()?.string()
                    val responseBody = JSONObject(response)
                    val jArray: JSONArray = responseBody.getJSONArray("non_field_errors")
                    for (i in 0 until jArray.length()) {
                        val value = jArray.getString(i)
                        Log.e("json", "$i=$value")
                        responseLiveData.value = ApiResponse.failure(value)
                    }
                }
                //responseLiveData.setValue(ApiResponse.error(throwable))
                })
    }

    override fun onCleared() {
        disposables.clear()
    }


}
package com.batanks.newplan.signing.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.batanks.newplan.network.RetrofitClient
import com.batanks.newplan.swagger.api.AuthenticationAPI

class SigninViewModel : ViewModel() {

    private val mutableLiveData = MutableLiveData<List<String>>()

    fun getLiveData(): LiveData<List<String>> {
        if (mutableLiveData.value.isNullOrEmpty()) {
            performLoginRequest()
        }
        return mutableLiveData
    }

    private fun performLoginRequest() {
        /*val authApi = RetrofitClient.getRetrofitInstance()?.create(AuthenticationAPI::class.java)*/
        /*authApi?.apiAuthenticationLoginCreate()*/
    }
}
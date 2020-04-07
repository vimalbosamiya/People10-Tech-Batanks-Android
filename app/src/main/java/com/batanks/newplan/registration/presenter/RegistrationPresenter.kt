package com.batanks.newplan.registration.presenter

import com.batanks.newplan.network.RetrofitClient
import com.batanks.newplan.registration.contract.RegistrationContract
import com.batanks.newplan.swagger.api.AuthenticationAPI
import com.batanks.newplan.swagger.model.RegisterUser
import com.google.gson.Gson
import io.reactivex.rxjava3.disposables.CompositeDisposable
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class RegistrationPresenter constructor() : RegistrationContract.IPresenter {

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override var view: RegistrationContract.IView? = null

    override val isAttached: Boolean
        get() = view != null

    override fun onAttach(view: RegistrationContract.IView) {
        this.view = view
    }

    override fun onDetach() {
        compositeDisposable.clear()
        this.view = null
    }

    override fun createUser(user: RegisterUser) {
        val retrofitClient = view?.context()?.let { RetrofitClient.getRetrofitInstance(it)?.create(AuthenticationAPI::class.java)?.apiAuthenticationRegisterCreate(user) }
        retrofitClient?.enqueue(object : Callback<RegisterUser> {

            override fun onResponse(call: Call<RegisterUser>, response: Response<RegisterUser>) {
                if (response.isSuccessful && response.code() == HttpURLConnection.HTTP_OK) {
                    view?.processResponse()
                } else {
                    view?.showMessage(getMessage(response.errorBody()))
                }
            }

            override fun onFailure(call: Call<RegisterUser>, t: Throwable) {
                view?.showMessage(t.message.toString())
            }
        })
    }

    private fun getMessage(errorBody: ResponseBody?): String {
        return try {
            val error = JSONObject(errorBody?.string())
            val yourHashMap = Gson().fromJson(error.toString(), HashMap::class.java)
            return yourHashMap.toString()
        } catch (e: Exception) {
            errorBody.toString()
        }
    }
}
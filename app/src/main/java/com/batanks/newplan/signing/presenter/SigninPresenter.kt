package com.batanks.newplan.signing.presenter

import com.batanks.newplan.network.RetrofitClient
import com.batanks.newplan.signing.contract.SigninContract
import com.batanks.newplan.swagger.api.AuthenticationAPI
import com.batanks.newplan.swagger.model.Login
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.net.HttpURLConnection
import javax.security.auth.callback.Callback

class SigninPresenter constructor() : SigninContract.IPresenter {

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override var view: SigninContract.IView? = null

    override val isAttached: Boolean
        get() = view != null

    override fun onAttach(view: SigninContract.IView) {
        this.view = view
    }

    override fun onDetach() {
        compositeDisposable.clear()
        this.view = null
    }

    override fun performLogin(login: Login) {
        val retrofitClient = view?.context()?.let { RetrofitClient.getRetrofitInstance(it)?.create(AuthenticationAPI::class.java)?.apiAuthenticationLoginCreate(login) }
        retrofitClient?.enqueue(object : Callback, retrofit2.Callback<Login> {

            override fun onResponse(call: retrofit2.Call<Login>, response: retrofit2.Response<Login>) {
                if (response.isSuccessful && response.code() == HttpURLConnection.HTTP_OK) {
                    view?.processResponse()
                } else {
                    view?.showMessage("Something went wrong.")
                }
            }

            override fun onFailure(call: retrofit2.Call<Login>, t: Throwable) {
                view?.showMessage(t.message.toString())
            }
        })
    }
}
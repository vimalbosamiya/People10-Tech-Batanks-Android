package com.batanks.newplan.registration.presenter

import com.batanks.newplan.registration.contract.RegistrationContract
import io.reactivex.rxjava3.disposables.CompositeDisposable

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
}
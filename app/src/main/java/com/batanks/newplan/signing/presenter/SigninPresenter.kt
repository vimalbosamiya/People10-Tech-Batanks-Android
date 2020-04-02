package com.batanks.newplan.signing.presenter

import com.batanks.newplan.signing.contract.SigninContract
import io.reactivex.rxjava3.disposables.CompositeDisposable

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
}
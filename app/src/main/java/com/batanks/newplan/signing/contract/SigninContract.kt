package com.batanks.newplan.signing.contract

import com.batanks.newplan.arch.BaseContract
import com.batanks.newplan.swagger.model.Login

interface SigninContract {

    interface IView : BaseContract.BasicLoadingView {
        fun processResponse()
    }

    interface IPresenter : BaseContract.LoadingPresenter<IView> {
        fun performLogin(login: Login)
    }

    interface Listener {

    }
}
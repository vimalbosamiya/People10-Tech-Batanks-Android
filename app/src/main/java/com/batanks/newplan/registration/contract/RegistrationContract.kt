package com.batanks.newplan.registration.contract

import com.batanks.newplan.arch.BaseContract
import com.batanks.newplan.swagger.model.RegisterUser

interface RegistrationContract {

    interface IView : BaseContract.BasicLoadingView {
        fun processResponse()
    }

    interface IPresenter : BaseContract.LoadingPresenter<IView> {
        fun createUser(user: RegisterUser)
    }

    interface Listener {

    }
}
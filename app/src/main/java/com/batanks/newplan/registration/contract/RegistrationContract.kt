package com.batanks.newplan.registration.contract

import com.batanks.newplan.arch.BaseContract

interface RegistrationContract {

    interface IView : BaseContract.BasicLoadingView {

    }

    interface IPresenter : BaseContract.LoadingPresenter<IView> {

    }

    interface Listener {

    }
}
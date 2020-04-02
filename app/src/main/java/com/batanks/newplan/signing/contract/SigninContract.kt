package com.batanks.newplan.signing.contract

import com.batanks.newplan.arch.BaseContract

interface SigninContract {

    interface IView : BaseContract.BasicLoadingView {

    }

    interface IPresenter : BaseContract.LoadingPresenter<IView> {

    }

    interface Listener {

    }
}
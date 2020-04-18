package com.batanks.nextplan.arch

import android.content.Context
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

interface BaseContract {

    interface BasicView {
        fun context(): Context
    }

    interface BasicLoadingView : BasicView {
        fun showLoader()
        fun hideLoader()
        fun handleError(error: Throwable)
        fun showMessage(message: String)
        fun showMessage(message: String, title: String, showPositiveButton: Boolean = false)
    }

    interface View<out T : ViewModel> : BasicView

    interface Presenter<V> {
        val view: V?
        val isAttached: Boolean
        fun onAttach(view: V)
        fun onDetach()
    }

    interface LoadingView<out T : ViewModel> : View<T>, BasicLoadingView

    interface LoadingPresenter<V> : Presenter<V> {
        val compositeDisposable: CompositeDisposable
    }
}
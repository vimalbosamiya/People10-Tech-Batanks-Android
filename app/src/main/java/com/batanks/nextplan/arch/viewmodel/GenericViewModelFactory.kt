package com.batanks.nextplan.arch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GenericViewModelFactory<T>(val params: () -> T) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(clazz: Class<T>): T {
        return params() as T
    }
}
package com.batanks.nextplan.application

import android.app.Application
import com.facebook.stetho.Stetho

class BatanksApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}
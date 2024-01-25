package com.wema.lagosdeveloper

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication  : Application(){

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object{
        var instance : MainApplication? = null
            private set
    }
}
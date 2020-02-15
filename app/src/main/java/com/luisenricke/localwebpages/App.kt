package com.luisenricke.localwebpages

import android.app.Application
import com.luisenricke.localwebpages.data.WebDatabase
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())

        WebDatabase.getInstance(this)
    }
}
package com.kostas.redone.di

import android.app.Application
import androidx.work.Configuration
import com.kostas.redone.data.worker.ForexWorkerFactory
import javax.inject.Inject

class DollarApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: ForexWorkerFactory

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }
}
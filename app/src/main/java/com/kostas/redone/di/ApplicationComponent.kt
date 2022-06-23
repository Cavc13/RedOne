package com.kostas.redone.di

import android.app.Application
import com.kostas.redone.ui.chart.СhartFragment
import com.kostas.redone.ui.dollar.DollarNotificationFragment
import com.kostas.redone.ui.dollar.HomeFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class,
        WorkerModule::class
    ]
)
interface ApplicationComponent {

    fun inject(fragment: DollarNotificationFragment)

    fun inject(fragment: СhartFragment)

    fun inject(fragment: HomeFragment)

    fun inject(application: DollarApp)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application,
        ): ApplicationComponent
    }
}
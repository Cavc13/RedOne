package com.kostas.redone.di

import android.app.Application
import com.kostas.redone.data.database.AppDatabase
import com.kostas.redone.data.database.DollarInfoDao
import com.kostas.redone.data.network.api.ApiFactory
import com.kostas.redone.data.network.api.ApiService
import com.kostas.redone.data.repository.DollarRepositoryImpl
import com.kostas.redone.domain.DollarRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindDollarRepository(impl: DollarRepositoryImpl): DollarRepository

    companion object {

        @Provides
        @ApplicationScope
        fun provideDollarInfoDao (
            application: Application
        ) : DollarInfoDao {
            return AppDatabase.getInstance(application).dollarPriceInfoDao()
        }

        @Provides
        @ApplicationScope
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }
    }
}
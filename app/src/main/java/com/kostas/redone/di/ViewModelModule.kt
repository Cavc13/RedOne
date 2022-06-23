package com.kostas.redone.di

import androidx.lifecycle.ViewModel
import com.kostas.redone.ui.chart.ChartViewModel
import com.kostas.redone.ui.dollar.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ChartViewModel::class)
    fun bindChartViewModel(viewModel: ChartViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel
}
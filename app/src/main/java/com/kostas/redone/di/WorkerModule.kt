package com.kostas.redone.di

import com.kostas.redone.data.worker.ChildWorkerFactory
import com.kostas.redone.data.worker.DollarWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerModule {
    @Binds
    @IntoMap
    @WorkerKey(DollarWorker::class)
    fun bindDollarWorkerFactory(factory: DollarWorker.Factory): ChildWorkerFactory
}

package com.kostas.redone.data.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import javax.inject.Inject
import javax.inject.Provider

class ForexWorkerFactory @Inject constructor(
    private val workerMap: Map<Class<out ListenableWorker>, @JvmSuppressWildcards Provider<ChildWorkerFactory>>
): WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters,
    ): ListenableWorker {
        val entry = workerMap.entries.find{
            Class.forName(workerClassName).isAssignableFrom(it.key)
        }
        val factory = entry?.value
            ?: throw IllegalArgumentException("unknown worker class name: $workerClassName")
        return factory.get().create(appContext, workerParameters)
    }
}


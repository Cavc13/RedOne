package com.kostas.redone.data.repository

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.kostas.redone.data.database.DollarInfoDao
import com.kostas.redone.data.mapper.DollarMapper
import com.kostas.redone.data.network.api.ApiService
import com.kostas.redone.data.resource.getPastAndCurrentMonth
import com.kostas.redone.data.resource.isInternetConnected
import com.kostas.redone.data.worker.DollarWorker
import com.kostas.redone.domain.DollarRepository
import com.kostas.redone.domain.model.Dollar
import javax.inject.Inject

class DollarRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dollarMapper: DollarMapper,
    private val dollarInfoDao: DollarInfoDao,
    private val application: Application
): DollarRepository {

    override suspend fun getMonthDollarPriceApi(): List<Dollar> {
        val pastAndCurrentPair = getPastAndCurrentMonth()
        Log.d("Rtx", "${pastAndCurrentPair.first} ${pastAndCurrentPair.second}")
        if (isInternetConnected(application)) {
            val listDollarDto = apiService.getUsdRate(
                pastAndCurrentPair.first,
                pastAndCurrentPair.second
            ).body()?.arrayDollar
            Log.d("RepImpl", "listDto $listDollarDto")
            val listDollarInfoDb = listDollarDto?.map {
                dollarMapper.mapDollarDtoToDollarInfoDb(it)
            }
            Log.d("RepImpl", "listDb $listDollarInfoDb")
            listDollarInfoDb?.let {
                dollarInfoDao.deleteDollarPriceList()
                dollarInfoDao.insertDollarPriceList(it)
            }
        }

        return getDollarListInfoDb()
    }

    override suspend fun getDollarListInfoDb(): List<Dollar> {
        val listDollar = dollarInfoDao.getDollarPriceList()?.map {
            dollarMapper.mapDollarInfoDbToDollar(it)
        } ?: emptyList()

        return listDollar
    }

    override suspend fun startDollarWorker(checkPoint: Double) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationch =
                NotificationChannel("dollar_channel", "Kostka", NotificationManager.IMPORTANCE_HIGH)
            notificationch.description = "description"
            val notificationManager =
                application.getSystemService(
                    AppCompatActivity.NOTIFICATION_SERVICE
                ) as NotificationManager

            notificationManager.createNotificationChannel(notificationch)
        }

        WorkManager.getInstance(application).apply {
            Log.d("KTx", "It's a life!")
            enqueueUniquePeriodicWork(
                DollarWorker.NAME,
                ExistingPeriodicWorkPolicy.REPLACE,
                DollarWorker.makeRequest(checkPoint)
            )
        }
    }
}
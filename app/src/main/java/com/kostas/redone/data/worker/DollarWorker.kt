package com.kostas.redone.data.worker

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.kostas.redone.R
import com.kostas.redone.data.database.DollarInfoDao
import com.kostas.redone.data.database.DollarInfoDbModel
import com.kostas.redone.data.mapper.DollarMapper
import com.kostas.redone.data.network.api.ApiService
import com.kostas.redone.data.resource.getCurrentTime
import com.kostas.redone.data.resource.getPastAndCurrentMonth
import com.kostas.redone.data.resource.isInternetConnected
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.random.Random

class DollarWorker(
    private val context: Context,
    workerParameters: WorkerParameters,
    private val coinInfoDao: DollarInfoDao,
    private val apiService: ApiService,
    private val mapper: DollarMapper,
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val dollarDb: DollarInfoDbModel?
        val checkPoint = inputData.getDouble(CHECK_POINT_KEY, 0.0)
        if (isInternetConnected(context)) {
            val rangeMonth = getPastAndCurrentMonth()
            val listDollarDto = apiService.getUsdRate(rangeMonth.first, rangeMonth.second)
                                        .body()
                                        ?.arrayDollar
            if (listDollarDto != null) {
                val listDollarInfoDb = listDollarDto.map {
                    mapper.mapDollarDtoToDollarInfoDb(it)
                }
                coinInfoDao.insertDollarPriceList(listDollarInfoDb)
                dollarDb = coinInfoDao.getPriceInfoAboutDollar(
                    getCurrentTime()
                )
                return if (dollarDb != null) {
                    val dollarFromDb = dollarDb.value.replace(",", ".", false).toDouble()

                    if (dollarFromDb >= checkPoint) {
                        getDollarNotification(checkPoint, dollarFromDb)
                        Result.success()
                    } else {
                        Result.retry()
                    }
                } else {
                    Result.retry()
                }
            } else {
                return Result.retry()
            }

        } else {
            return Result.retry()
        }
    }

    class Factory @Inject constructor(
        private val dollarInfoDao: DollarInfoDao,
        private val apiService: ApiService,
        private val mapper: DollarMapper,
    ) : ChildWorkerFactory {
        override fun create(
            context: Context,
            workerParameters: WorkerParameters,
        ): ListenableWorker {
            return DollarWorker(
                context,
                workerParameters,
                dollarInfoDao,
                apiService,
                mapper
            )
        }
    }

    private fun getDollarNotification(checkPoint: Double, currentPoint: Double) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationCompat = NotificationCompat.Builder(context, CHANNEL_NAME)
            .setSmallIcon(R.drawable.coindollar)
            .setContentText("$CONTENT_TEXT_CP $checkPoint  $CONTENT_TEXT_CD $currentPoint")
            .setContentTitle(NOTIF_CONTENT_TITLE)
            .build()

        manager.notify(Random.nextInt(), notificationCompat)
    }

    companion object {
        const val NAME = "DollarWorker"

        private const val CHANNEL_NAME = "dollar_channel"
        private const val CONTENT_TEXT_CP = "Доллар дошел до отметки"
        private const val CONTENT_TEXT_CD = "и на данный момент составляет"
        private const val NOTIF_CONTENT_TITLE = "Оповещение из ЦБ"
        private const val CHECK_POINT_KEY = "check_point_key"

        fun makeRequest(checkPoint: Double): PeriodicWorkRequest {
            val data = Data.Builder()
                .putDouble(CHECK_POINT_KEY, checkPoint)
                .build()

            return PeriodicWorkRequestBuilder<DollarWorker>(1, TimeUnit.DAYS)
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(
                            NetworkType.CONNECTED
                        )
                        .build()
                )
                .setInputData(data)
                .build()
        }
    }
}
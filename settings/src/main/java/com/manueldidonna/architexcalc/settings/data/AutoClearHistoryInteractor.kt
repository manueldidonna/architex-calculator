package com.manueldidonna.architexcalc.settings.data

import androidx.work.BackoffPolicy
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.manueldidonna.architexcalc.core.Interactor
import com.manueldidonna.architexcalc.settings.data.workers.AutoClearHistoryWorker
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.util.concurrent.TimeUnit

class AutoClearHistoryInteractor(private val workManager: WorkManager) : Interactor<Long> {
    override val dispatcher: CoroutineDispatcher = Dispatchers.Default
    override suspend fun invoke(param: Long) {
        if (param == 0L) {
            workManager.cancelUniqueWork(AutoClearHistoryWorker.TAG)
        } else {
            val req = PeriodicWorkRequestBuilder<AutoClearHistoryWorker>(param, TimeUnit.DAYS)
                .addTag(AutoClearHistoryWorker.TAG)
                .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 15L, TimeUnit.MINUTES)
                .setInitialDelay(param, TimeUnit.DAYS)
                .build()
            workManager.enqueueUniquePeriodicWork(
                AutoClearHistoryWorker.TAG,
                ExistingPeriodicWorkPolicy.REPLACE,
                req
            )
        }
    }
}
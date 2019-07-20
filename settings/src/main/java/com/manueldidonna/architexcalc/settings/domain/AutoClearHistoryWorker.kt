package com.manueldidonna.architexcalc.settings.domain

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.manueldidonna.architexcalc.core.db.daos.HistoryDao
import kotlinx.coroutines.coroutineScope
import org.koin.core.KoinComponent
import org.koin.core.get
import timber.log.Timber

class AutoClearHistoryWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams), KoinComponent {

    override suspend fun doWork(): Result = coroutineScope {
        try {
            get<HistoryDao>().deleteAll()
            Result.success()
        } catch (ex: Exception) {
            Timber.e("Error clearing history from worker")
            Result.retry()
        }
    }

    companion object {
        const val TAG = "worker:auto_clear_history"
    }
}
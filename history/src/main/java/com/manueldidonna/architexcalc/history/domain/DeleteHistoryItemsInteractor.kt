package com.manueldidonna.architexcalc.history.domain

import com.manueldidonna.architexcalc.core.Interactor
import com.manueldidonna.architexcalc.core.db.daos.HistoryDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DeleteHistoryItemsInteractor(private val historyDao: HistoryDao) : Interactor<List<Long>> {
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO
    override suspend fun invoke(param: List<Long>) {
        param.forEach { id ->
            historyDao.deleteById(id)
        }
    }
}
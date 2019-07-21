package com.manueldidonna.architexcalc.history.domain

import com.manueldidonna.architexcalc.core.Interactor
import com.manueldidonna.architexcalc.core.db.daos.HistoryDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DeleteAllHistoryItemsInteractor(private val historyDao: HistoryDao) : Interactor<Unit> {
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO
    override suspend fun invoke(param: Unit) {
        historyDao.deleteAll()
    }
}
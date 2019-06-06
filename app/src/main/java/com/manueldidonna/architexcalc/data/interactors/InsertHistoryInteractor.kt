package com.manueldidonna.architexcalc.data.interactors

import com.manueldidonna.architexcalc.core.Interactor
import com.manueldidonna.architexcalc.core.db.daos.HistoryDao
import com.manueldidonna.architexcalc.core.db.entities.HistoryEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class InsertHistoryInteractor(
    private val historyDao: HistoryDao
) : Interactor<InsertHistoryInteractor.Params> {
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun invoke(param: Params) {
        historyDao.insert(
            HistoryEntity(
                expression = param.expression,
                result = param.result
            )
        )
    }

    data class Params(val expression: String, val result: String)
}



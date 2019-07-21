package com.manueldidonna.architex.calculator.domain

import com.manueldidonna.architexcalc.core.Interactor
import com.manueldidonna.architexcalc.core.db.daos.HistoryDao
import com.manueldidonna.architexcalc.core.db.entities.HistoryEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class InsertHistoryItemInteractor(
    private val historyDao: HistoryDao
) : Interactor<InsertHistoryItemInteractor.Params> {
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



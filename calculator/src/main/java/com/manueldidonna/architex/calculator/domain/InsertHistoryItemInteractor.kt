package com.manueldidonna.architex.calculator.domain

import com.manueldidonna.architexcalc.core.Interactor
import com.manueldidonna.architexcalc.database.HistoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class InsertHistoryItemInteractor(
    private val historyRepository: HistoryRepository
) : Interactor<InsertHistoryItemInteractor.Params> {
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun invoke(param: Params) {
        historyRepository.insert(param.expression, param.result)
    }

    data class Params(val expression: String, val result: String)
}



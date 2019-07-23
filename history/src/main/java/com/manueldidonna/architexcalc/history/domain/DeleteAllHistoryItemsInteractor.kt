package com.manueldidonna.architexcalc.history.domain

import com.manueldidonna.architexcalc.core.Interactor
import com.manueldidonna.architexcalc.database.HistoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DeleteAllHistoryItemsInteractor(private val historyRepository: HistoryRepository) : Interactor<Unit> {
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO
    override suspend fun invoke(param: Unit) {
        historyRepository.deleteAll()
    }
}
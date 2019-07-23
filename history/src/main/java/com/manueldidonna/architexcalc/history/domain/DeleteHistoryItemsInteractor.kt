package com.manueldidonna.architexcalc.history.domain

import com.manueldidonna.architexcalc.core.Interactor
import com.manueldidonna.architexcalc.database.HistoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DeleteHistoryItemsInteractor(private val historyRepository: HistoryRepository) : Interactor<List<Long>> {
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO
    override suspend fun invoke(param: List<Long>) {
        param.forEach { id ->
            historyRepository.deleteById(id)
        }
    }
}
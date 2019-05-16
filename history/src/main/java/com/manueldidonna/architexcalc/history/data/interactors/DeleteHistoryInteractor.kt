package com.manueldidonna.architexcalc.history.data.interactors

import com.manueldidonna.architexcalc.core.Interactor
import com.manueldidonna.architexcalc.core.db.daos.HistoryDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DeleteHistoryInteractor(private val historyDao: HistoryDao) : Interactor<DeleteHistoryInteractor.Params> {
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun invoke(param: Params) {
        if (param.deleteAll) {
            require(param.idsToDelete == null) {
                "Params::idsToDelete must be null if Params::deleteAll == true"
            }
            historyDao.deleteAll()
        } else {
            require(param.idsToDelete != null) {
                "Params::idsToDelete must not be null if Params::deleteAll == false"
            }
            param.idsToDelete.forEach { id ->
                historyDao.deleteById(id)
            }
        }
    }

    data class Params(val deleteAll: Boolean, val idsToDelete: List<Long>? = null)
}
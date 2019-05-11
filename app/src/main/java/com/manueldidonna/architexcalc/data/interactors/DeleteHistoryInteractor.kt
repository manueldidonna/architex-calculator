package com.manueldidonna.architexcalc.data.interactors

import com.manueldidonna.architexcalc.core.Interactor
import com.manueldidonna.architexcalc.core.db.daos.HistoryDao
import com.manueldidonna.architexcalc.core.db.entities.HistoryEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DeleteHistoryInteractor(private val historyDao: HistoryDao) : Interactor<DeleteHistoryInteractor.Params> {
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun invoke(param: Params) {
        if (param.deleteAll) {
            require(param.entitiesToDelete == null) {
                "Params::entitiesToDelete must be null if Params::deleteAll == true"
            }
            historyDao.deleteAll()
        } else {
            require(param.entitiesToDelete != null) {
                "Params::entitiesToDelete must not be null if Params::deleteAll == false"
            }
            param.entitiesToDelete.forEach { entity ->
                historyDao.delete(entity)
            }
        }
    }

    data class Params(val deleteAll: Boolean, val entitiesToDelete: List<HistoryEntity>? = null)
}
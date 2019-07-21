package com.manueldidonna.architexcalc.history.domain

import com.manueldidonna.architexcalc.core.RxInteractor
import com.manueldidonna.architexcalc.core.db.daos.HistoryDao
import com.manueldidonna.architexcalc.core.db.entities.HistoryEntity
import io.reactivex.Observable

class GetHistoryItemsInteractor(
    private val historyDao: HistoryDao
) : RxInteractor<Unit, List<HistoryEntity>>() {
    override fun createObservable(params: Unit): Observable<List<HistoryEntity>> {
        return historyDao.getHistoryEntities()
    }

    operator fun invoke(): GetHistoryItemsInteractor {
        invoke(Unit)
        return this
    }
}